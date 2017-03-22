package com.yxqm.console.system.exception;

public class ConsoleSystemException extends RuntimeException {
	private static final long serialVersionUID = -5890769248064559697L;

	public ConsoleSystemException(String message) {
		super(message);
	}

	public ConsoleSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConsoleSystemException(Throwable cause) {
		super(cause);
	}

	protected ConsoleSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}