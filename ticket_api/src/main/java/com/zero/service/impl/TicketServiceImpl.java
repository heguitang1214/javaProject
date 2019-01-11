package com.zero.service.impl;

import ai.yunxi.utils.SnowflakeOrderServiceImpl;
import com.zero.entity.Ticket;
import com.zero.mapper.ITicketMapper;
import com.zero.service.ITicketService;
import com.zero.utils.JsonUtil;
import com.zero.utils.SnowflakeOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.yunxi.entity.Ticket;
import ai.yunxi.mapper.ITicketMapper;
import ai.yunxi.service.ITicketService;
import ai.yunxi.utils.JsonUtil;

import javax.annotation.Resource;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年10月24日 下午9:36:17
 * 
 */
@Service
public class TicketServiceImpl implements ITicketService {
	
	@Resource
	private ITicketMapper ticketMapper;

	@Resource
	private SnowflakeOrderServiceImpl idGeneration;

	@Override
	public String createTicket(String userId) {
		try {
			Ticket ticket = new Ticket(String.valueOf(idGeneration.nextId()), userId);
			System.out.println("insert into ticket:ticketId="+ticket.getTicketId());
			int row = ticketMapper.createTicket(ticket);
			if(row>0){
				return JsonUtil.fromBean(ticket);
			}
			System.out.println("--------------------null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
