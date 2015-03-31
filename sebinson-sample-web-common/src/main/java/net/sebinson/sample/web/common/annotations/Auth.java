package net.sebinson.sample.web.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权验证<br/>
 * default value Auth(verifyLogin = false,verifyURL = false)
 * @author C
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface Auth {
    /**
     * 是否验证登陆 true=验证 ,false = 不验证
     */
    public boolean verifyLogin() default false;

    /**
     * 是否验证URL true=验证 ,false = 不验证
     */
    public boolean verifyURL() default false;
    

}
