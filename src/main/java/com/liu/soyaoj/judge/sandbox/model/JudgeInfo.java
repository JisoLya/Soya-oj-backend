package com.liu.soyaoj.judge.sandbox.model;

import lombok.Data;

/**
 * 题目判断信息
 */
@Data
public class JudgeInfo {

    private Boolean success;

    /**
     * 判题信息
     */
    private String message;
    /**
     * 判题所耗时长
     */
    private Long timeLimit;
    /**
     * 内存占用
     */
    private Long memoryLimit;
}
