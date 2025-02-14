package com.liu.soyaoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.liu.soyaoj.model.dto.question.JudgeConfig;
import com.liu.soyaoj.model.entity.Question;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class QuestionVO {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置，json数组
     */
    private JudgeConfig judgeConfig;

    /**
     * 题目点赞数
     */
    private Integer thumbNum;

    /**
     * 题目收藏数
     */
    private Integer favorNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建题目人的信息
     */
    private UserVO userVo;

    public static QuestionVO objToVo(Question question) {
        QuestionVO vo = new QuestionVO();
        BeanUtils.copyProperties(question,vo);

        String questionTags = question.getTags();
        if (StringUtils.isNotBlank(questionTags)){
            //标签不为空
            vo.setTags(JSONUtil.toList(questionTags, String.class));
        }

        String questionJudgeConfig = question.getJudgeConfig();
        if (StringUtils.isNotBlank(questionJudgeConfig)){
            vo.setJudgeConfig(JSONUtil.toBean(questionJudgeConfig, JudgeConfig.class));
        }

        return vo;
    }
    public static Question voToObj(QuestionVO questionVO) {
        Question question = new Question();
        BeanUtils.copyProperties(questionVO,question);
        List<String> voTags = questionVO.getTags();
        if(voTags != null){
            question.setTags(JSONUtil.toJsonStr(voTags));
        }
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if (voJudgeConfig != null){
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }
}
