package com.tang.controller;

import com.tang.service.ComputeService;
import com.tang.service.LogLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogController {

	@Autowired
	private ComputeService computeService;

	// 用于动态修改日志级别的Service
	@Autowired
	private LogLevelService logService;

	// http://localhost:8080/jmx-log/compute/m
	@RequestMapping(value = "/compute/{m}", method = RequestMethod.GET)
	@ResponseBody
	public String compute(@PathVariable int m) {
		int ret = computeService.compute(m);
		return "Return:" + ret;
	}

	// 动态更改日志级别的函数
	@RequestMapping(value = "/change/{name}/{level}", method = RequestMethod.GET)
	@ResponseBody
	public String changeLevel(@PathVariable String name, @PathVariable int level) {
		// 动态修改类的日志级别
		logService.setLevel(name, level);
		return "Set " + name + "'s level OK!";
	}

}
