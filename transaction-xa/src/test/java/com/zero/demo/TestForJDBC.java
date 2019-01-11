package com.zero.demo;

import com.zero.demo.service.jdbc.MoneyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.yunxi.demo.service.jdbc.MoneyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationJDBC.xml"})
public class TestForJDBC {

	@Autowired
	private MoneyService moneyService;

	@Test
	// 转账操作
	public void saveToDB() throws Exception {
		moneyService.transfer(1, 2, 1000);
	}
}
