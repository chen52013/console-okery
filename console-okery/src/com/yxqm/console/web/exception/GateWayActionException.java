package com.yxqm.console.web.exception;

public class GateWayActionException extends RuntimeException {
	private static final long serialVersionUID = -2768142057067494671L;

	public GateWayActionException(String message) {
		super(message);
	}

	public GateWayActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public GateWayActionException(Throwable cause) {
		super(cause);
	}

	protected GateWayActionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}