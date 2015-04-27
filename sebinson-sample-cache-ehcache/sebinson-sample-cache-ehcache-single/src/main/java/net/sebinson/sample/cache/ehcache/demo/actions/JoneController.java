package net.sebinson.sample.cache.ehcache.demo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/jone")
public class JoneController {

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request,HttpServletResponse response){
        
        return "/jone/hello";
    }
}
