package com.uzdz.study.datasource.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.uzdz.study.datasource.DataSourceContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据源
 * @author uzdz
 */
@Order(Integer.MIN_VALUE)
@Configuration
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties dataSource;

    @Bean("dataSource")
    public AbstractRoutingDataSource dynamicSource() {
        AbstractRoutingDataSource abstractRoutingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceContextHolder.getDB();
            }
        };

        Optional<Resource> defaultResource = dataSource.getDatasource().stream()
                .filter((data) -> data.isDefaultDataSource()).findFirst();

        if (!defaultResource.isPresent()) {
            throw new RuntimeException("default database can not be null!");
        }

        List<Resource> datasource = dataSource.getDatasource();

        Map<Object, Object> resource = new HashMap<>(16);

        datasource.stream().forEach((source) -> {

            DruidDataSource druidDataSource = new DruidDataSource();

            BeanUtils.copyProperties(source, druidDataSource);

            resource.put(source.getName(), druidDataSource);

            if (source.isDefaultDataSource()) {
                abstractRoutingDataSource.setDefaultTargetDataSource(druidDataSource);
            }
        });

        abstractRoutingDataSource.setTargetDataSources(resource);

        return abstractRoutingDataSource;
    }
}
