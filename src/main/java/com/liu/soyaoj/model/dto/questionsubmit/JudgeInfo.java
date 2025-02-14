package com.liu.soyaoj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 题目判断信息
 */
@Data
public class JudgeInfo {
    /**
     * 判题信息
     */
    private String message;
    /**
     * 判题所耗时长
     */
    private Long time;
    /**
     * 内存占用
     */
    private Long memory;
}
