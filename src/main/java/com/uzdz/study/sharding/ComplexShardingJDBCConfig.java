package com.uzdz.study.sharding;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Configuration
public class ComplexShardingJDBCConfig {

    @Bean
    public DataSource getShardingDataSource(HikariConfig hikariConfig) throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getShardingMessageTableRuleConfiguration());
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("order_0", createDataSource(datasourceOne(hikariConfig)));
        dataSourceMap.put("order_1", createDataSource(datasourceTwo(hikariConfig)));
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), "true");
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
    }

    private TableRuleConfiguration getShardingMessageTableRuleConfiguration() {
        TableRuleConfiguration shardingMessageConfiguration = new TableRuleConfiguration("tb_order", "order_${0..1}.tb_order_${0..7}");
        shardingMessageConfiguration.setDatabaseShardingStrategyConfig(messageDatasourceShardingStrategyConfig());
        shardingMessageConfiguration.setTableShardingStrategyConfig(messageTableShardingStrategyConfig());
        return shardingMessageConfiguration;
    }

    private ComplexShardingStrategyConfiguration messageDatasourceShardingStrategyConfig(){
        return new ComplexShardingStrategyConfiguration("trade_master_no,pay_order_no", new OrderDatasourceComplexKeysShardingAlgorithm());
    }

    private ShardingStrategyConfiguration messageTableShardingStrategyConfig() {
        return new ComplexShardingStrategyConfiguration("trade_master_no,pay_order_no", new OrderTableComplexKeysShardingAlgorithm());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public HikariConfig datasourceOne(HikariConfig config){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMinimumIdle(config.getMinimumIdle());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setMaximumPoolSize(config.getMaximumPoolSize());
        hikariConfig.setPoolName(config.getPoolName());
        hikariConfig.setMaxLifetime(config.getMaxLifetime());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        hikariConfig.setConnectionTestQuery(config.getConnectionTestQuery());
        return hikariConfig;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public HikariConfig datasourceTwo(HikariConfig config){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMinimumIdle(config.getMinimumIdle());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setMaximumPoolSize(config.getMaximumPoolSize());
        hikariConfig.setPoolName(config.getPoolName());
        hikariConfig.setMaxLifetime(config.getMaxLifetime());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        hikariConfig.setConnectionTestQuery(config.getConnectionTestQuery());
        return hikariConfig;
    }

    private HikariDataSource createDataSource(HikariConfig hikariConfig) {
        HikariDataSource sharding = new HikariDataSource();
        BeanUtils.copyProperties(hikariConfig, sharding);
        return sharding;
    }

}
