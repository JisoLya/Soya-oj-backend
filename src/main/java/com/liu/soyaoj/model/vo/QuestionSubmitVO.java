package com.liu.soyaoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.liu.soyaoj.judge.sandbox.model.JudgeInfo;
import com.liu.soyaoj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class QuestionSubmitVO {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息（json 对象）
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

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
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 题目信息
     */
    private QuestionVO questionVO;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        QuestionSubmitVO vo = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, vo);
        String info = questionSubmit.getJudgeInfo();
        if (info != null) {
            vo.setJudgeInfo(JSONUtil.toBean(info, JudgeInfo.class));
        }
        return vo;
    }

    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit submit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, submit);
        JudgeInfo judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            submit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return submit;
    }
}
