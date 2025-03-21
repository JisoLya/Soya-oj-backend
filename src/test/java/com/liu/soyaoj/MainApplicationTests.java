package com.liu.soyaoj;

import com.liu.soyaoj.judge.sandbox.CodeSandbox;
import com.liu.soyaoj.judge.sandbox.CodeSandboxFactory;
import com.liu.soyaoj.judge.sandbox.Impl.RemoteSandbox;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeRequest;
import com.liu.soyaoj.judge.sandbox.model.ExecuteCodeResponse;
import com.liu.soyaoj.model.enums.JudgeStatusEnum;
import com.liu.soyaoj.model.enums.QuestionSubmitLanguageEnum;
import com.liu.soyaoj.model.enums.QuestionSubmitStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liliu">程序员鱼皮</a>
 * @from <a href="https://liu.icu">编程导航知识星球</a>
 */
@SpringBootTest
class MainApplicationTests {

    //支持灵活配置！
    @Value("${sandbox.type}")
    public String type;

    @Test
    void contextLoads() {
    }

    @Test
    void testSandbox() {
        CodeSandbox sandbox = CodeSandboxFactory.getInstance(type);
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.CPP.getValue();
        ExecuteCodeRequest codeRequest = new ExecuteCodeRequest(language, code, new ArrayList<>(), 200L);
        ExecuteCodeResponse response = sandbox.execute(codeRequest);
        System.out.println(response);
    }

    @Test
    void testRemote() {
        RemoteSandbox remoteSandbox = new RemoteSandbox();
        String code = "public class Main{\n" +
                "    public static void main(String[] args) throws InterruptedException{\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        int c = a + b;\n" +
                "        System.out.println(c);\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1 1");
        ExecuteCodeRequest codeRequest = new ExecuteCodeRequest(language, code, arrayList, 200L);
        ExecuteCodeResponse response = remoteSandbox.execute(codeRequest);
        System.out.println(response);
    }

    @Test
    void testRemoteWrongCode() {
        RemoteSandbox remoteSandbox = new RemoteSandbox();
        String code = "public class Main{\n" +
                "    public static void main(String[] args) throws InterruptedException{\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        int c = a + b;\n" +
                "        System.out.println(c);\n" +
                "    }\n";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1 1");
        ExecuteCodeRequest codeRequest = new ExecuteCodeRequest(language, code, arrayList, 200L);
        ExecuteCodeResponse response = remoteSandbox.execute(codeRequest);
        System.out.println(response);
    }

    @Test
    void TestWrongInput() {
        RemoteSandbox remoteSandbox = new RemoteSandbox();
        String code = "public class Main{\n" +
                "    public static void main(String[] args) throws InterruptedException{\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        int c = a + b;\n" +
                "        System.out.println(c);\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        ExecuteCodeRequest codeRequest = new ExecuteCodeRequest(language, code, arrayList, 200L);
        ExecuteCodeResponse response = remoteSandbox.execute(codeRequest);
        System.out.println(response);
    }
}
