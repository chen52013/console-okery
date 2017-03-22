package com.yxqm.console.exception;

public class ConsoleBusinessException extends RuntimeException {
	private static final long serialVersionUID = -5890769248064559697L;

	public ConsoleBusinessException(String message) {
		super(message);
	}

	public ConsoleBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConsoleBusinessException(Throwable cause) {
		super(cause);
	}

	protected ConsoleBusinessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}