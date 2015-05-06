package net.sebinson.sample.demos.gof.proxy.dynamic;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    public static void main(String[] args) {
        //创建具体类ClassB的处理对象  
        Invoker invoker1 = new Invoker(new ClassA());
        //获得具体类ClassA的代理  
        IClass ac1 = (IClass) Proxy.newProxyInstance(IClass.class.getClassLoader(), new Class[] { IClass.class }, invoker1);
        //调用ClassA的show方法。  
        ac1.show();

        //创建具体类ClassB的处理对象  
        Invoker invoker2 = new Invoker(new ClassB());
        //获得具体类ClassB的代理  
        IClass ac2 = (IClass) Proxy.newProxyInstance(IClass.class.getClassLoader(), new Class[] { IClass.class }, invoker2);
        //调用ClassB的show方法。  
        ac2.show();
    }

}
