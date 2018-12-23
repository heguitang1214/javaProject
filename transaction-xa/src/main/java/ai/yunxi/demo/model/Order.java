package ai.yunxi.demo.model;

import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer orderId;
	private Integer productId;
	private String customer;
	private Integer number;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
