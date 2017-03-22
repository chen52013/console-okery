package com.yxqm.console.exception;

public class ConsoleForumException extends RuntimeException {
	private static final long serialVersionUID = -6036967267195498287L;

	public ConsoleForumException(String message) {
		super(message);
	}

	public ConsoleForumException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConsoleForumException(Throwable cause) {
		super(cause);
	}

	protected ConsoleForumException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}