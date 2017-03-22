package com.yxqm.console.cache;

public interface TairConstants {
	int NS_AUTH = 0;
	int NS_DATA = 1;
	int NS_JOB = 2;
	int NS_UPDATE = 3;

	int DEFAULT_EXPIRE_TIME = 0;
	String TAIR_KEY_CONSOLE_BUSINESS_CODE = "CONSOLEBUSINESS_";
	
	String TAIR_KEY_SECURITY_METADATA_CODE="SECURITY_METADATA_CONSOLE_AUTH";
	String TAIR_KEY_SECURITY_CONFIG_ATTRIBUTE="SECURITY_METADATA_CONFIG_ATTRIBUTE";

	
	//缓存过期时间
	public static final Integer EXPIRE_ONE_WEEK = 604800;
	
	public static final Integer EXPIRE_THIRTY_DAYS = 2592000;
	//五分钟
	public static final Integer EXPIRE_FIVE_MINUTE = 300;
}
