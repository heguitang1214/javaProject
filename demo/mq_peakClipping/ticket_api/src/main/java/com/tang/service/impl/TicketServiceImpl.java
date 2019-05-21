package com.tang.service.impl;

import com.tang.entity.Ticket;
import com.tang.mapper.ITicketMapper;
import com.tang.service.ITicketService;
import com.tang.utils.JsonUtil;
import com.tang.utils.SnowflakeOrderServiceImpl;
import org.springframework.stereotype.Service;


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
