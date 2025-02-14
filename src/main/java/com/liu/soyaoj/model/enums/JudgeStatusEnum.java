package com.liu.soyaoj.model.enums;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public enum JudgeStatusEnum {
    ACCEPTED("成功", "Accepted"),
    WRONG_ANSWER("解答错误", "Wrong Answer"),
    EXCEED_MEMORY_LIMIT("内存超出限制", "Exceed Memory Limit"),
    EXCEED_TIME_LIMIT("时间超出限制", "Exceed Time Limit"),
    EXCEED_OUTPUT_LIMIT("输出超出限制", "Exceed Output Limit"),
    EXECUTION_ERROR("执行出错", "Execution Error"),
    SYSTEM_ERROR("系统出错", "System Error"),
    COMPILE_ERROR("编译出错", "Compile Error"),
    WAITING("等待执行结果中","Waiting"),
    ;


    private final String text;
    private final String value;

    JudgeStatusEnum(String text, String value) {
        this.value = value;
        this.text = text;
    }

    public List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public JudgeStatusEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (JudgeStatusEnum item : JudgeStatusEnum.values()) {
            if (value.equals(item.value)) {
                return item;
            }
        }
        return null;
    }

    public JudgeStatusEnum getEnumByText(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        for (JudgeStatusEnum item : JudgeStatusEnum.values()) {
            if (text.equals(item.text)) {
                return item;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
