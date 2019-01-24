package com.zero.demo.config;

import java.util.List;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.zaxxer.hikari.HikariDataSource;

public class ZookeeperCentralConfigurer {
	private static final Logger log = LoggerFactory.getLogger(ZookeeperCentralConfigurer.class);
	
	private CuratorFramework zkClient;
	private TreeCache treeCache;

	private String zkServers;
	private String zkPath;
	private int sessionTimeout;
	private Properties props;

	public ZookeeperCentralConfigurer(String zkServers, String zkPath, int sessionTimeout) {
		this.zkServers = zkServers;
		this.zkPath = zkPath;
		this.sessionTimeout = sessionTimeout;
		this.props = new Properties();

		initZkClient();
		getConfigData();
		addZkListener();
	}

	private void initZkClient() {
		zkClient = CuratorFrameworkFactory.builder().connectString(zkServers).sessionTimeoutMs(sessionTimeout)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		zkClient.start();
	}

	private void getConfigData() {
		log.info("======= ZooKeeper中读取JDBC配置 =======");
		try {
			List<String> list = zkClient.getChildren().forPath(zkPath);
			for (String key : list) {
				String value = new String(zkClient.getData().forPath(zkPath + "/" + key));
				if (value != null && value.length() > 0) {
					props.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addZkListener() {
		TreeCacheListener listener = new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				if (event.getType() == TreeCacheEvent.Type.NODE_UPDATED) {
					getConfigData();
					WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
					HikariDataSource dataSource = (HikariDataSource) ctx.getBean("dataSource");
					System.out.println("================"+props.getProperty("url"));
					dataSource.setJdbcUrl(props.getProperty("url"));
				}
			}
		};

		treeCache = new TreeCache(zkClient, zkPath);
		try {
			treeCache.start();
			treeCache.getListenable().addListener(listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Properties getProps() {
		return props;
	}

	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	public void setZkPath(String zkPath) {
		this.zkPath = zkPath;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
}
