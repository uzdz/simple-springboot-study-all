package com.uzdz.study.config.multiple;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @description: phoenix 配置 DataSource
 * @author: uzdz
 * @create: 2019-09-20 18:06
 **/

@Slf4j
@Configuration
public class PhoenixConfig {

    @Bean(name = "phoenixDataSource")
    @ConfigurationProperties(prefix = "phoenix.datasource")
    public DataSource phoenixDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "jdbcPhoenixTemplate")
    public JdbcTemplate phoenixTemplate(@Qualifier("phoenixDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
