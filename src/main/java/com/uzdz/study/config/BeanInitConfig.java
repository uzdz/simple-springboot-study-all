package com.uzdz.study.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanInitConfig implements InitializingBean {

    public void initMethod(){
        System.out.println("@Bean的initMethod，在BeanInitConfig初始化构造函数执行完之后执行。");
    }

    @PostConstruct
    public void jsrInit(){
        System.out.println("jsrInit()，在BeanInitConfig初始化完之后执行。");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean的其他属性依赖注入已完成，调用afterPropertiesSet()方法。");
    }

    @PreDestroy
    public void jsrDestroy(){
        System.out.println("jsrDestroy()，在BeanInitConfig销毁后执行。");
    }

    public void destroyMethod(){
        System.out.println("@Bean的destroyMethod，在BeanInitConfig销毁之后执行。");
    }

    public BeanInitConfig() {
        System.out.println("自身初始化构造函数执行。");
    }
}
