package com.liu.soyaoj.judge.sandbox.Impl;

import com.liu.soyaoj.judge.sandbox.CodeSandbox;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeRequest;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeResponse;

public class RemoteSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest request) {
        System.out.println("Remote Sandbox");
        return null;
    }
}
