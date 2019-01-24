package com.zero.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	@Autowired
	private PaymentDao dao;

	public Payment getById() {
		return dao.findById();
	}
}
