package com.uzdz.study;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * @author uzdz
 */
@Component
public class TableName  implements InitializingBean {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private ApplicationContext applicationContext;

    private PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("@", "@");

    @Override
    public void afterPropertiesSet() throws Exception {
        Environment environment = applicationContext.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles == null || activeProfiles.length == 0) {
            throw new RuntimeException();
        }

        String currentProfile = activeProfiles[0];
        Properties properties = new Properties();
        //table suffix 变量
        properties.put("env", currentProfile);

        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.getMappedStatements()
                .forEach(ms -> {

                    SqlSource targetSqlSource = ms.getSqlSource();
                    SqlSource proxySqlSource = (SqlSource) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                            new Class[]{SqlSource.class}, (proxy, method, args) -> {

                        if (!method.getName().equalsIgnoreCase("getBoundSql")) {
                            return method.invoke(targetSqlSource, args);
                        }
                        BoundSql rawBoundSql = (BoundSql) method.invoke(targetSqlSource, args);
                        String rawSql = rawBoundSql.getSql();
                        String sql = propertyPlaceholderHelper.replacePlaceholders(rawSql, properties);
                        return new BoundSql(configuration, sql, rawBoundSql.getParameterMappings(), args);
                    });

                    ReflectionUtils.doWithFields(MappedStatement.class, f -> {
                        ReflectionUtils.makeAccessible(f);
                        f.set(ms, proxySqlSource);
                    }, f -> f.getName().equalsIgnoreCase("sqlSource"));
                });

    }
}
