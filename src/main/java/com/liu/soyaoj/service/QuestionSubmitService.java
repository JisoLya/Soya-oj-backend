package com.liu.soyaoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.soyaoj.model.dto.question.QuestionQueryRequest;
import com.liu.soyaoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.liu.soyaoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.liu.soyaoj.model.entity.Question;
import com.liu.soyaoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.soyaoj.model.entity.User;
import com.liu.soyaoj.model.vo.QuestionSubmitVO;
import com.liu.soyaoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author liuyan
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-01-01 11:44:46
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 提交记录的ID
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitVOPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitVOPage,User loginUser);
}
