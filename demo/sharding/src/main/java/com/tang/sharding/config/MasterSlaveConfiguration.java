package com.tang.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MasterSlaveConfiguration {

    public DataSource getDataSource() throws SQLException {
        // 配置读写分离规则
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("ds_master_slave");
        masterSlaveRuleConfig.setMasterDataSourceName("ds_master");
        masterSlaveRuleConfig.setSlaveDataSourceNames(Arrays.asList("ds_slave0", "ds_slave1"));

        // 获取数据源对象
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(),
                masterSlaveRuleConfig, new HashMap<String, Object>(), new Properties());
        return dataSource;
    }

    private Map<String, DataSource> createDataSourceMap() {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置主库
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        masterDataSource.setUrl("jdbc:mysql://localhost:3306/ds_master");
        masterDataSource.setUsername("root");
        masterDataSource.setPassword("123456");
        dataSourceMap.put("ds_master", masterDataSource);

        // 配置第一个从库
        DruidDataSource slaveDataSource1 = new DruidDataSource();
        slaveDataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        slaveDataSource1.setUrl("jdbc:mysql://localhost:3306/ds_slave0");
        slaveDataSource1.setUsername("root");
        slaveDataSource1.setPassword("123456");
        dataSourceMap.put("ds_slave0", slaveDataSource1);

        // 配置第二个从库
        DruidDataSource slaveDataSource2 = new DruidDataSource();
        slaveDataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        slaveDataSource2.setUrl("jdbc:mysql://localhost:3306/ds_slave1");
        slaveDataSource2.setUsername("root");
        slaveDataSource2.setPassword("123456");
        dataSourceMap.put("ds_slave1", slaveDataSource2);
        return dataSourceMap;
    }
}
