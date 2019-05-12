package com.tang.mapper;

import com.tang.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;


/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年10月24日 下午9:36:53
 * 
 */
@Mapper
public interface ITicketMapper {

	public int createTicket(Ticket ticket);
}
