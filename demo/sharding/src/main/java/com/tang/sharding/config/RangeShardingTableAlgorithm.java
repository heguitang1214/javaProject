package com.tang.sharding.config;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class RangeShardingTableAlgorithm implements RangeShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(final Collection<String> tableNames,
                                         final RangeShardingValue<Integer> shardingValue) {
        Set<String> result = new LinkedHashSet<>();
        if (Range.closed(2000000,
                7000000).encloses(shardingValue.getValueRange())) {
            for (String each : tableNames) {
                if (each.endsWith("0")) {
                    result.add(each);
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return result;
    }

}
