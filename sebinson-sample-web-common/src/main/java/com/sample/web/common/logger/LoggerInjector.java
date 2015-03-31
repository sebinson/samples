package com.sample.web.common.logger;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.sample.web.common.annotations.ALogger;


/**
 * <p>日志注入器
 * <p>根据类中的注解在类被实例化之前首先赋值被@Logger注解的的Filed
 * @author C
 */
public class LoggerInjector implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(ALogger.class) != null) {
                     Logger logger = LoggerFactory.getLogger(bean.getClass());
                    field.set(bean, logger);
                }
            }
        });
        return bean;
    }
}
