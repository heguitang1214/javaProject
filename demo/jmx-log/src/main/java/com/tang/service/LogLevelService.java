package com.tang.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

@Service
public class LogLevelService {
	private static final Logger log = LoggerFactory.getLogger(LogLevelService.class);

	// 参数name：代表要修改日志级别的类名
	// 参数level：要动态修改日志的级别
	public void setLevel(String name, int level) {
		// 获取Logger 环境上文  ApplicationContext  getBean("")
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		// 判断设置级别的类名不为空
		if (name != null && !"".equals(name)) {
			// 根据名字获取相应的Logger
			ch.qos.logback.classic.Logger vLogger = loggerContext.getLogger(name);
			if ("root".equals(name)) {
				// 设置root的Logger级别
				log.info("不允许更改Root的日志级别");
				// vLogger.setLevel(Level.toLevel(level));
			} else {
				// 根据类名设置Logger级别
				log.info("更改了" + name + "的日志级别");
				if (vLogger != null)
					vLogger.setLevel(Level.toLevel(level));
			}
		}

		// 获取应用中所有的Logger
		List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
		for (ch.qos.logback.classic.Logger logger : loggerList) {
			log.info(logger.getName() + "," + logger.getLevel());
		}
	}
}
