package com.tang.demo.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ZookeeperPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private ZookeeperCentralConfigurer zkCentralConfigurer;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, zkCentralConfigurer.getProps());
	}

	public void setZkCentralConfigurer(ZookeeperCentralConfigurer zkCentralConfigurer) {
		this.zkCentralConfigurer = zkCentralConfigurer;
	}
}
