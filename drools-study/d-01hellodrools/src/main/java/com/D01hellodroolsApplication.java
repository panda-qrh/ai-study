package com;


import com.pojo.UserMessage;
import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class D01hellodroolsApplication {

    public static void main(String[] args) {
        //1、入口工厂
        KieServices kieServices = KieServices.Factory.get();

        //2、吧DRL规则文件写入内存文件系统
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/hello.drl"));

        //3、编译规则（显示启用可执行模型，drools 10推荐）
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);

        //4、检查编译结果，有错误立即抛出
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("规则编译失败："+kieBuilder.getResults().getMessages());
        }

        //5、生成规则容器，创建会话
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        //6、插入事实
        try {
            UserMessage message1 = new UserMessage("Hello Drools!", UserMessage.HELLO,null);
            UserMessage message2 = new UserMessage("Get Out,Drools!", UserMessage.GOODBYE,null);
            kieSession.insert(message1);
            kieSession.insert(message2);

            //7、触发所有匹配的规则
            int fired = kieSession.fireAllRules();
            System.out.println("触发规则数："+fired);
            System.out.println("message1执行后内容："+ message1);
            System.out.println("\nmessage2执行后内容："+ message2);
        }finally {
            //8、用完必须释放
            kieSession.dispose();
        }

    }

}
