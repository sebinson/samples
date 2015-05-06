package net.sebinson.sample.demos.gof.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Invoker implements InvocationHandler {

    private IClass clazz;

    public Invoker(IClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        method.invoke(clazz, args);

        return null;
    }

}
