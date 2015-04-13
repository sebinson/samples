package net.sebinson.sample.cache.ehcache.interceptors;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class MethodEhCacheInterceptor implements MethodInterceptor, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MethodEhCacheInterceptor.class);
    private Cache               cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void afterPropertiesSet() throws Exception {
        logger.info(cache + " A cache is required. Use setCache(Cache) to provide one.");
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {

        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        Object result = null;
        String cacheKey = getCacheKey(targetName, methodName, arguments);
        Element element = null;
        synchronized (this) {
            element = cache.get(cacheKey);
            if (element == null) {
                logger.info(cacheKey + "Add cache： " + cache.getName());
                // 调用实际的方法
                result = invocation.proceed();
                element = new Element(cacheKey, (Serializable) result);
                cache.put(element);
            } else {
                logger.info(cacheKey + "Use cache： " + cache.getName());
            }
        }
        return element.getObjectValue();
    }

    private String getCacheKey(String targetName, String methodName, Object[] arguments) {

        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        return sb.toString();
    }
}
