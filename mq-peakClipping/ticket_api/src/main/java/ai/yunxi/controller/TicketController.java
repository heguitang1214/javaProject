package ai.yunxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.yunxi.service.ITicketService;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年10月24日 下午9:28:31
 * 
 */
@RestController
public class TicketController {

	@Autowired
	private ITicketService service;
	
	@RequestMapping(value="/createTicket", produces = "application/json;charset=UTF-8;")
	public String createTicket(@RequestParam String userId){
		return service.createTicket(userId);
	}
}
