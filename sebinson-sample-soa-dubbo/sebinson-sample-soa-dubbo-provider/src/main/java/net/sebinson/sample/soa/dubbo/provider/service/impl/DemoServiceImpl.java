package net.sebinson.sample.soa.dubbo.provider.service.impl;

import net.sebinson.sample.soa.dubbo.provider.service.DemoService;

public class DemoServiceImpl implements DemoService{

    public String sayHello(String name) {
        return "Hello " + name;
    }
}
