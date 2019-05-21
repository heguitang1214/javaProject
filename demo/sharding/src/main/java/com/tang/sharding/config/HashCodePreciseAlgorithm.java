package com.tang.sharding.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class HashCodePreciseAlgorithm implements PreciseShardingAlgorithm {
    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        // hashcode取模分片
        int size = availableTargetNames.size();
        int num = shardingValue.getValue().hashCode() % size;
        String[] list = (String[]) availableTargetNames.toArray(new String[size]);
        if (num <= availableTargetNames.size()) {
            return list[num];
        }
        return null;
    }
}
