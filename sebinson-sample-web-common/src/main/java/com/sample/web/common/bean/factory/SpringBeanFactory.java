package com.sample.web.common.bean.factory;

import java.beans.Introspector;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanFactory implements ApplicationContextAware {
    private static ApplicationContext context;

    public SpringBeanFactory() {
    }

    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Class<T> clazz) {
        return (T) getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getBean(Introspector.decapitalize(clazz.getSimpleName()), clazz);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

}
