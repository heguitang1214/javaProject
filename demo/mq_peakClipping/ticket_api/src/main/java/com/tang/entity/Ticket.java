package com.tang.entity;
/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年10月24日 下午9:42:59
 * 
 */
public class Ticket {

	private String ticketId;
	private String userId;
	
	public Ticket() {}
	/**
	 * @param ticketId
	 * @param userId
	 */
	public Ticket(String ticketId, String userId) {
		super();
		this.ticketId = ticketId;
		this.userId = userId;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
