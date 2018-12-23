package ai.yunxi.demo.dao.exp;

public class CreateOrderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CreateOrderException() {
		super();
	}

	public CreateOrderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public CreateOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreateOrderException(String message) {
		super(message);
	}

	public CreateOrderException(Throwable cause) {
		super(cause);
	}
}
