package com.yxqm.console.web.exception;

public class ConsoleActionException extends RuntimeException {
	private static final long serialVersionUID = -2768142057067494671L;

	public ConsoleActionException(String message) {
		super(message);
	}

	public ConsoleActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConsoleActionException(Throwable cause) {
		super(cause);
	}

	protected ConsoleActionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}