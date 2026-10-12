package com;


import com.pojo.Order;
import org.drools.compiler.builder.conf.DecisionTableConfigurationImpl;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.io.ClassPathResource;
import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.io.ResourceFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class D04decisiontableApplication {

    public static void main(String[] args) {
//        global();
//        query();
//    functionRule();
//        decisionTable();
//        fullDecisionTableFile();
        decisionTableExcel();

    }

    public static void decisionTableExcel() {
        //解析xlxs文件
        ClassPathResource file = new ClassPathResource("discount.xlsx");
        DecisionTableConfigurationImpl dtc = new DecisionTableConfigurationImpl();
        dtc.setInputType(DecisionTableInputType.XLSX);
        dtc.setResourceType(ResourceType.DRL);
        dtc.setWorksheetName("折扣分档");
        String drl = new DecisionTableProviderImpl().loadFromResource(file, dtc);

        System.out.println(drl);

        KieServices kieServices = KieServices.Factory.get();
        Resource drlResource = ResourceFactory.newByteArrayResource(drl.getBytes(StandardCharsets.UTF_8))
                .setResourceType(ResourceType.DRL)
                .setSourcePath("rules/discount-xlsx.drl");
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(drlResource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException(kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            List<String> list = new ArrayList<>();
            kieSession.setGlobal("resultList", list);
            Order zs = new Order("张三", 20_000);
            Order ls = new Order("李四", 2_000);
            kieSession.insert(zs);
            kieSession.insert(ls);
            kieSession.fireAllRules();

            System.out.printf("修改后：%s\t%s\n", zs, ls);

            double minAmount = 1234;
            QueryResults queryResults = kieSession.getQueryResults("高额订单查询", minAmount);
            queryResults.forEach(row -> System.out.println(row.get("$o")));

        } finally {
            kieSession.dispose();
        }


    }

    public static void decisionTable() {
        ClassPathResource file = new ClassPathResource("query.csv");

        DecisionTableConfigurationImpl dtc = new DecisionTableConfigurationImpl();
        dtc.setInputType(DecisionTableInputType.CSV);
        dtc.setResourceType(ResourceType.DRL);

        String drl = new DecisionTableProviderImpl().loadFromResource(file, dtc);

        KieServices kieServices = KieServices.Factory.get();
        Resource drlResource = ResourceFactory.newByteArrayResource(drl.getBytes(StandardCharsets.UTF_8))
                .setSourcePath("rules/decision-table.drl")
                .setResourceType(ResourceType.DRL);
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(drlResource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException(kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            Order zs = new Order("张三", 20_000);
            Order ls = new Order("李四", 2_000);

            kieSession.insert(zs);
            kieSession.insert(ls);

            int fired = kieSession.fireAllRules();
            System.out.println("触发的规则数: " + fired);

            System.out.println(zs);
            System.out.println(ls);

        } finally {
            kieSession.dispose();
        }

    }


    public static void fullDecisionTableFile() {
        ClassPathResource file = new ClassPathResource("dt-full-nogroup.csv");

        DecisionTableConfigurationImpl dtc = new DecisionTableConfigurationImpl();
        dtc.setInputType(DecisionTableInputType.CSV);
        dtc.setResourceType(ResourceType.DRL);

        String drl = new DecisionTableProviderImpl().loadFromResource(file, dtc);
        System.out.println("===== 决策表生成的完整 DRL =====");
        System.out.println(drl);
        System.out.println("===== DRL 结束 =====");

        KieServices kieServices = KieServices.Factory.get();
        Resource drlResource = ResourceFactory.newByteArrayResource(drl.getBytes(StandardCharsets.UTF_8))
                .setSourcePath("rules/dt-full.drl")
                .setResourceType(ResourceType.DRL);
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(drlResource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException(kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            List<String> resultList = new ArrayList<>();
            kieSession.setGlobal("resultList", resultList);

            Order zs = new Order("张三", 20_000);
            Order ls = new Order("李四", 2_000);

            kieSession.insert(zs);
            kieSession.insert(ls);

            int fired = kieSession.fireAllRules();
            System.out.println("触发的规则数: " + fired);
            System.out.println(zs);
            System.out.println(ls);

            QueryResults qr = kieSession.getQueryResults("高额订单查询");
            qr.forEach(row -> System.out.println("大额订单查询结果: " + row.get("$o")));
        } finally {
            kieSession.dispose();
        }
    }

    public static void functionRule() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/function-rule.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException(kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            Order zs = new Order("张三", 20_000);
            Order ls = new Order("李四", 2_000);

            kieSession.insert(zs);
            kieSession.insert(ls);

            kieSession.fireAllRules();

            System.out.println(zs);
            System.out.println(ls);
        } finally {
            kieSession.dispose();
        }
    }

    public static void query() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/query-rule.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException(kieBuilder.getResults().toString());
        }

        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            Order zs = new Order("张三", 12_000, true);
            Order ls = new Order("李四", 52_000, false);
            Order ww = new Order("王五", 12, true);

            kieSession.insert(zs);
            kieSession.insert(ls);
            kieSession.insert(ww);

            QueryResults queryResults = kieSession.getQueryResults("拦截订单");
            queryResults.forEach(row -> System.out.println(row.get("$o")));

        } finally {
            kieSession.dispose();
        }

    }

    public static void global() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/global-rule.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("规则编译出错：" + kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            List<String> list = new ArrayList();

            Order zs = new Order("张三", 12_000);
            Order ls = new Order("李四", 52_000);
            Order ww = new Order("王五", 12);

            kieSession.setGlobal("resultList", list);

            kieSession.insert(zs);
            kieSession.insert(ls);
            kieSession.insert(ww);

            kieSession.fireAllRules();

            System.out.println("大额订单： " + list);

        } finally {
            kieContainer.dispose();
        }
    }

}
