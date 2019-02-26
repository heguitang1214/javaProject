package ai.yunxi.sharding.config;

import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class RangeShardingDBAlgorithm implements RangeShardingAlgorithm<Integer> {
    @Override
    public Collection<String> doSharding(final Collection<String> databaseNames,
                                         final RangeShardingValue<Integer> shardingValue) {
        Set<String> result = new LinkedHashSet<>();
        int lower = shardingValue.getValueRange().lowerEndpoint();
        int upper = shardingValue.getValueRange().upperEndpoint();
        for (int i = lower; i <= upper; i++) {
            for (String each : databaseNames) { //ds0,ds1
                if (each.endsWith(i % databaseNames.size() + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
