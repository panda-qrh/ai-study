package com;


import com.pojo.Order;
import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class D03rulepropsApplication {

    public static void main(String[] args) {
//        approvalRequest();
//        discount();
        orderFlow();
    }

    public static void orderFlow() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/order-flow.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("规则编译失败" +kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            Order order = new Order("1", 20_000, false);

            kieSession.insert(order);

            kieSession.getAgenda().getAgendaGroup("risk").setFocus();
            kieSession.fireAllRules();

            kieSession.getAgenda().getAgendaGroup("validate").setFocus();
            kieSession.fireAllRules();

            kieSession.getAgenda().getAgendaGroup("checkout").setFocus();
            kieSession.fireAllRules();

        }finally {
            kieSession.dispose();
        }

    }

    public static void discount() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/discount.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("规则编译失败" + kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            Order order = new Order("1", 20_000, false);

            kieSession.insert(order);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
    }

    public static void approvalRequest() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/approval.drl"));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("规则编译失败：" + kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try {
            Order zs = new Order("张三", 15_000, false);
            Order ls = new Order("李四", 20_000, true);
            kieSession.insert(zs);
            kieSession.insert(ls);
            kieSession.fireAllRules();

            kieSession.getAgenda().getAgendaGroup("risk").setFocus();
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
    }

}
