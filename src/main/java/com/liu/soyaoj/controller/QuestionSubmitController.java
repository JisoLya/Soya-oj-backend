package com.liu.soyaoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.soyaoj.annotation.AuthCheck;
import com.liu.soyaoj.common.BaseResponse;
import com.liu.soyaoj.common.ErrorCode;
import com.liu.soyaoj.common.ResultUtils;
import com.liu.soyaoj.constant.UserConstant;
import com.liu.soyaoj.exception.BusinessException;
import com.liu.soyaoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.liu.soyaoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.liu.soyaoj.model.entity.Question;
import com.liu.soyaoj.model.entity.QuestionSubmit;
import com.liu.soyaoj.model.entity.User;
import com.liu.soyaoj.model.vo.QuestionSubmitVO;
import com.liu.soyaoj.service.QuestionSubmitService;
import com.liu.soyaoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/liliu">程序员鱼皮</a>
 * @from <a href="https://liu.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次提交的题目id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交题目
        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取题目提交列表（除了管理员和自己以外，不能看到其他人提交的代码）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

}
