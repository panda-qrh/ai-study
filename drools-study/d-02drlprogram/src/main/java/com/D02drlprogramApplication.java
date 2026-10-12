package com;

import com.pojo.Order;
import com.pojo.User;
import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class D02drlprogramApplication {

    public static void main(String[] args) {
//        orderDiscount();
        userValueCategory();
//        costCollect();

    }

    public static void costCollect() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/cost-collect.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)){
            throw new RuntimeException("规则编译出错："+kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        try{
            User zs = new User("张三", 25, "普通");
            Order o1 = new Order(zs, 100);
            Order o2 = new Order(zs, 500);
            Order o3 = new Order(zs, 1500);
            Order o4 = new Order(zs, 2000);
            Order o5 = new Order(zs, 3000);

            kieSession.insert(zs);
            kieSession.insert(o1);
            kieSession.insert(o2);
            kieSession.insert(o3);
            kieSession.insert(o4);
            kieSession.insert(o5);

            System.out.printf("触发了%s条规则",kieSession.fireAllRules());


        }finally {
            kieSession.dispose();
        }
    }

    public static void userValueCategory(){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem()
                .write(ResourceFactory.newClassPathResource("rules/user-value-category.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        if(kieBuilder.getResults().hasMessages(Message.Level.ERROR)){
            throw new RuntimeException("规则编译失败："+kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        try{
            User zs = new User("张三", 25, "VIP");
            User ls = new User("李四", 30, "普通");
            Order ls1 = new Order(ls, 800, Order.NO_DISCOUNT);
            Order ls2 = new Order(ls, 1800, Order.NO_DISCOUNT);

            User ww = new User("王五", 70, "普通");
            Order ww1 = new Order(ww, 300, Order.NO_DISCOUNT,true);
            Order ww2 = new Order(ww, 200, Order.NO_DISCOUNT,true);

            kieSession.insert(zs);

            kieSession.insert(ls);
            kieSession.insert(ls1);
            kieSession.insert(ls2);

            kieSession.insert(ww);
            kieSession.insert(ww1);
            kieSession.insert(ww2);

            int fired = kieSession.fireAllRules();
            System.out.println("触发规则数："+fired);

        }finally{
            kieSession.dispose();
        }
    }

    public static void orderDiscount(){
        //1、入口工厂
        KieServices kieServices = KieServices.Factory.get();
        //2、把DRL规则文件写入内存文件系统
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem()
                                                .write(ResourceFactory.newClassPathResource("rules/order-discount.drl"));
        //3、编译规则
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
        //4、检查编译结果，有错误立即抛出
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)){
            throw new RuntimeException("规则编译失败："+kieBuilder.getResults().toString());
        }
        //5、生成规则容器，创建会话
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        //6、插入事实
        try {
            Order order1 = new Order(new User("张三", 25, "VIP"), 1500, Order.NO_DISCOUNT);
            Order order2 = new Order(new User("李四", 30, "普通"), 800, Order.NO_DISCOUNT);
            Order order3 = new Order(new User("王五", 70, "普通"), 300, Order.NO_DISCOUNT);
            Order order4 = new Order(new User("赵六", 30, "普通"), 200, Order.NO_DISCOUNT);

            kieSession.insert(order1);
            kieSession.insert(order2);
            kieSession.insert(order3);
            kieSession.insert(order4);
            //7、触发所有规则
            int fired = kieSession.fireAllRules();
            System.out.println("触发规则数："+fired);
            System.out.printf("""
                    order1 折扣： %s
                    order2 折扣： %s
                    order3 折扣： %s
                    order4 折扣： %s
                    """,order1.getDiscount(),order2.getDiscount(),order3.getDiscount(),order4.getDiscount());
        }finally {
            kieSession.dispose();
        }



    }

}
