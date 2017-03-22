package com.yxqm.console.exception;

public class ConsoleDaoException extends RuntimeException {
	private static final long serialVersionUID = -7453334452585416111L;

	public ConsoleDaoException(String message) {
		super(message);
	}

	public ConsoleDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConsoleDaoException(Throwable cause) {
		super(cause);
	}

	protected ConsoleDaoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}