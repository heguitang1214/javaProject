package com.zero.demo.controller;

import java.io.Serializable;

public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int orderId;
	private int brandId;
	private int productId;
	private String orderDate;
	private String payDate;
	private double totalPrice;
	private int discount;
	private double payPrice;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}
}
