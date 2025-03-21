package com.liu.soyaoj.judge.sandbox.Impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.liu.soyaoj.common.ErrorCode;
import com.liu.soyaoj.exception.BusinessException;
import com.liu.soyaoj.judge.sandbox.CodeSandbox;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeRequest;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeResponse;

public class RemoteSandbox implements CodeSandbox {
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest request) {
        String url = "http://192.168.121.129:8080/executeCode";
        String requestJson = JSONUtil.toJsonStr(request);
        String response = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(requestJson)
                .execute()
                .body();
        if (StrUtil.isBlankIfStr(response)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "execute remote codeSandbox error");
        }
        return JSONUtil.toBean(response, ExecuteCodeResponse.class);
    }
}
