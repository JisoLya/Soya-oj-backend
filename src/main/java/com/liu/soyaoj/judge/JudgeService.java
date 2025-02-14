package com.liu.soyaoj.judge;

import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeResponse;
import com.liu.soyaoj.model.vo.QuestionSubmitVO;

public interface JudgeService {

    QuestionSubmitVO doJudge(long questionSubmitId);

}
