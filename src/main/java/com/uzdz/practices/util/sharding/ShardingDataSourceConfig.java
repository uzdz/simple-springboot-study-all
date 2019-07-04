package com.uzdz.practices.util.sharding;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Sharding-JDBC官网Document：
 * https://shardingsphere.apache.org/document/legacy/3.x/document/cn/overview/
 */
@Configuration
@EnableConfigurationProperties(ShardingMasterSlaveConfig.class)
public class ShardingDataSourceConfig {

    @Autowired(required = false)
    private ShardingMasterSlaveConfig shardingMasterSlaveConfig;

    @Bean("dataSource")
    public DataSource masterSlaveDataSource() throws SQLException {
        Map<String, DruidDataSource> dataSources = shardingMasterSlaveConfig.getDataSources();

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.putAll(dataSources);

        MasterSlaveRuleConfiguration masterSlaveRule = shardingMasterSlaveConfig.getMasterSlaveRule();

        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRule, new HashMap<>(0));
    }
}
