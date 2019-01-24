package com.zero.demo.dao.exp;

public class ReduceStockException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReduceStockException() {
		super();
	}

	public ReduceStockException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReduceStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReduceStockException(String message) {
		super(message);
	}

	public ReduceStockException(Throwable cause) {
		super(cause);
	}
}
