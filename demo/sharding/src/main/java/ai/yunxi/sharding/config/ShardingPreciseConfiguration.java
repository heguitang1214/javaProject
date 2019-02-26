package ai.yunxi.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ShardingPreciseConfiguration {

    public DataSource getDataSource() throws SQLException {
        ShardingRuleConfiguration config = new ShardingRuleConfiguration();
        config.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        config.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        config.getBindingTableGroups().add("t_order, t_order_item");
        //        // 配置分库
        config.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id",
                "ds${user_id % 2}"));
        // 配置分表
        config.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id",
                "t_order${order_id % 2}"));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), config,
                new HashMap<String, Object>(), new Properties());
    }

    private static TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order");
        result.setActualDataNodes("ds${0..1}.t_order${[0, 1]}");
        return result;
    }

    private static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order_item");
        result.setActualDataNodes("ds${0..1}.t_order_item_${[0, 1]}");
        return result;
    }

    private Map<String, DataSource> createDataSourceMap() {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/ds0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("ds0", dataSource1);

        // 配置第二个数据源
        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/ds1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("ds1", dataSource2);
        return dataSourceMap;
    }
}
