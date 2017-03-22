package com.yxqm.console.utils;

public class Consts {
	public static final String RES_SUCCESS = "0";
	public static final String RES_FAILED = "-1";
	public static final String RES_DUPLICATE = "-2";
	public static final String ERROR_1000 = "service.non_support";
	public static final String ERROR_1001 = "service.requset_params_is_null";
	public static final String ERROR_1002 = "service.system_error";
	public static final String ERROR_1003 = "service.audit_data_failed";
	public static final String ERROR_1004 = "service.ref_data_null";
	public static final String ERROR_1005 = "service.request_params_format_error";
	public static final String PUSH_MESSAGE_MSG_TYPE_EVENT = "1";
	public static final String PUSH_MESSAGE_MSG_TYPE_ODDS = "2";
	public static final String PUSH_MESSAGE_MSG_TYPE_SCORE = "3";
	public static final String PUSH_MESSAGE_MSG_TYPE_STATUS = "4";
	public static final String PUSH_MESSAGE_MSG_TYPE_DATA_STATUS = "7";
	public static final String CACHE_GROUP_UPDATE_STATUS = "UPDATE_STATUS";
	public static final String CACHE_GROUP_UPDATE_SCORE = "UPDATE_SCORE";
	public static final String CACHE_GROUP_UPDATE_STATUS_COUNTS = "UPDATE_STATUS_COUNTS";
	public static final Integer EXPIRE_ONE_WEEK = Integer.valueOf(604800);
	public static final Integer EXPIRE_THIRTY_DAYS = Integer.valueOf(2592000);
}