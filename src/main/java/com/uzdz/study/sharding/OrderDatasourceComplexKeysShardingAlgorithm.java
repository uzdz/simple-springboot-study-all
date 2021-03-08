package com.uzdz.study.sharding;

import com.google.common.collect.Lists;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.Map;

public class OrderDatasourceComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        Map<String, Collection<String>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        if(columnNameAndShardingValuesMap.containsKey("trade_master_no")){
            Collection<String> tradeMasterNos = columnNameAndShardingValuesMap.get("trade_master_no");
            String tradeMasterNo = tradeMasterNos.iterator().next();
            String datasourceSuffix = tradeMasterNo.substring(0, 1);
            for (String availableTargetName : availableTargetNames) {
                if(availableTargetName.endsWith(datasourceSuffix)){
                    return Lists.newArrayList(availableTargetName);
                }
            }
        }
        if(columnNameAndShardingValuesMap.containsKey("pay_order_no")){
            Collection<String> payOrderNos = columnNameAndShardingValuesMap.get("pay_order_no");
            String payOrderNo = payOrderNos.iterator().next();
            String datasourceSuffix = payOrderNo.substring(0, 1);
            for (String availableTargetName : availableTargetNames) {
                if(availableTargetName.endsWith(datasourceSuffix)){
                    return Lists.newArrayList(availableTargetName);
                }
            }
        }
        throw new UnsupportedOperationException();
    }

}
