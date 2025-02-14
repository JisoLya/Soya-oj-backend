package com.liu.soyaoj.judge.sandbox.Impl;

import com.liu.soyaoj.judge.sandbox.CodeSandbox;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeRequest;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeResponse;

/**
 * 示例代码沙箱
 */
public class ExampleSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest request) {
        System.out.println("ExampleCodeSandbox");
        return new ExecuteCodeResponse();
    }
}
