package com.tang.sharding.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class PreciseShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> tableNames,
                             PreciseShardingValue<Long> shardingValue) {
        for (String key : tableNames) {
            if (key.endsWith(shardingValue.getValue() % tableNames.size() + "")) {
                return key;
            }
        }
        throw new UnsupportedOperationException();
    }
}
