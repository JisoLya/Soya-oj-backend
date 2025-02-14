package com.liu.soyaoj.judge.sandbox;

import com.liu.soyaoj.judge.sandbox.Impl.ExampleSandbox;
import com.liu.soyaoj.judge.sandbox.Impl.RemoteSandbox;
import com.liu.soyaoj.judge.sandbox.Impl.ThirdPartySandbox;

public class CodeSandboxFactory {
    public static CodeSandbox getInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteSandbox();
            case "third-party":
                return new ThirdPartySandbox();
            default:
                return new ExampleSandbox();
        }
    }
}
