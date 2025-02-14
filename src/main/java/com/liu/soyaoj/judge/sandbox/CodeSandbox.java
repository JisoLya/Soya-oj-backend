package com.liu.soyaoj.judge.sandbox;

import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeRequest;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     * @param request 执行代码的请求
     * @return
     */
    ExecuteCodeResponse execute(ExecuteCodeRequest request);
}
