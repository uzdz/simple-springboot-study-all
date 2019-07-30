package com.uzdz.study.datasource;

import com.uzdz.study.datasource.conf.DataSourceProperties;
import com.uzdz.study.datasource.conf.DataSourceSelector;
import com.uzdz.study.datasource.conf.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author uzdz
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Pointcut("@annotation(com.uzdz.study.datasource.conf.DataSourceSelector)")
    public void selector(){}

    @Before("selector()")
    public void before(JoinPoint point){

        Map<String, List<Resource>> collect = dataSourceProperties.getDatasource().stream()
                .collect(Collectors.groupingBy(Resource::getName));

        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        DataSourceSelector annotation = methodSignature.getMethod()
                .getAnnotation(DataSourceSelector.class);

        if(annotation != null && collect.containsKey(annotation.value())){

            DataSourceContextHolder.setDB(annotation.value());

        } else {

            Optional<Resource> defaultDataSource = dataSourceProperties.getDatasource().stream()
                    .filter((data) -> data.isDefaultDataSource()).findFirst();

            DataSourceContextHolder.setDB(defaultDataSource.get().getName());
        }
    }

    @After("selector()")
    public void after(){
        DataSourceContextHolder.clearDB();
    }

}
