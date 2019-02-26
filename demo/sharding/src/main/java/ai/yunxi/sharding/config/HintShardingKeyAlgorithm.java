package ai.yunxi.sharding.config;

import com.alibaba.druid.util.StringUtils;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HintShardingKeyAlgorithm implements HintShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ShardingValue shardingValue) {
        System.out.println("shardingValue=" + shardingValue);
        System.out.println("availableTargetNames=" + availableTargetNames);
        List<String> shardingResult = new ArrayList<>();
        for (String targetName : availableTargetNames) {
            String suffix = targetName.substring(targetName.length() - 1);
            if (StringUtils.isNumber(suffix)) {
                // hint分片算法的ShardingValue有两种具体类型:
                // ListShardingValue和RangeShardingValue
                // 使用哪种取决于HintManager.addDatabaseShardingValue(String, String, ShardingOperator,...),ShardingOperator的类型
                ListShardingValue<Integer> tmpSharding = (ListShardingValue<Integer>) shardingValue;
                for (Integer value : tmpSharding.getValues()) {
                    if (value % 2 == Integer.parseInt(suffix)) {
                        shardingResult.add(targetName);
                    }
                }
            }
        }
        return shardingResult;
    }

}
