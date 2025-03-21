package com.liu.soyaoj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.soyaoj.common.ErrorCode;
import com.liu.soyaoj.constant.CommonConstant;
import com.liu.soyaoj.exception.BusinessException;
import com.liu.soyaoj.judge.JudgeService;
import com.liu.soyaoj.model.dto.question.QuestionQueryRequest;
import com.liu.soyaoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.liu.soyaoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.liu.soyaoj.model.entity.Question;
import com.liu.soyaoj.model.entity.QuestionSubmit;
import com.liu.soyaoj.model.entity.User;
import com.liu.soyaoj.model.enums.QuestionSubmitLanguageEnum;
import com.liu.soyaoj.model.enums.QuestionSubmitStatusEnum;
import com.liu.soyaoj.model.vo.QuestionSubmitVO;
import com.liu.soyaoj.model.vo.QuestionVO;
import com.liu.soyaoj.model.vo.UserVO;
import com.liu.soyaoj.service.QuestionService;
import com.liu.soyaoj.service.QuestionSubmitService;
import com.liu.soyaoj.mapper.QuestionSubmitMapper;
import com.liu.soyaoj.service.UserService;
import com.liu.soyaoj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author liuyan
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2025-01-01 11:44:46
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        // 执行判题服务
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(questionSubmitId);
        });
        return questionSubmitId;
    }

    @NotNull
    private static QuestionSubmit getSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setUserId(userId);
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        return questionSubmit;
    }


    /**
     * 获取查询包装类
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();

        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC)
                , sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        long userId = loginUser.getId();

        if (userId != questionSubmit.getUserId() || !userService.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        switch (questionSubmit.getStatus()) {
            case 0:
                questionSubmitVO.setStatus(QuestionSubmitStatusEnum.WAITING.getText());
                break;
            case 1:
                questionSubmitVO.setStatus(QuestionSubmitStatusEnum.RUNNING.getText());
                break;
            case 2:
                questionSubmitVO.setStatus(QuestionSubmitStatusEnum.SUCCEED.getText());
                break;
            case 3:
                questionSubmitVO.setStatus(QuestionSubmitStatusEnum.FAILED.getText());
                break;
            default:
                questionSubmitVO.setStatus("Undefined");
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}




