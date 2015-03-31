package net.sebinson.sample.soa.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:spring-dubbo-provider.xml" });
        context.start();

        System.in.read(); // 按任意键退出
    }
}
