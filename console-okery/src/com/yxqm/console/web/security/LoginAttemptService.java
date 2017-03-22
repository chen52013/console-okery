package com.yxqm.console.web.security;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.atomic.AtomicInteger;


public class LoginAttemptService {
    private int maxTriedTimes = 3;

    public LoginAttemptService() {
    }

    //	@Autowired
    //	@Qualifier("tairManagerService")
    //	TairManager tairManagerService;
    public int getMaxTriedTimes() {
        return maxTriedTimes;
    }

    public void setMaxTriedTimes(int maxTriedTimes) {
        this.maxTriedTimes = maxTriedTimes;
    }

    public void loginSucceeded(String userName) {
        //		tairManagerService.putCache(TairConstants.NS_UPDATE, String.format("login_cache:%s", userName), "0", 0, 60);
    }

    public void loginFailed(String userName) {
        //		String login_times_str = tairManagerService.getValue(TairConstants.NS_UPDATE, String.format("login_cache:%s", userName));
        //		AtomicInteger login_times_int = new AtomicInteger(Integer.parseInt(StringUtils.defaultString(login_times_str, "0")));
        //		tairManagerService.putCache(TairConstants.NS_UPDATE, String.format("login_cache:%s", userName), login_times_int.addAndGet(1) + "", 0, 60);
    }

    //	public boolean isBlocked(String userName) {
    //		String login_times_str = tairManagerService.getValue(TairConstants.NS_UPDATE, String.format("login_cache:%s", userName));
    //		Integer login_times_int = Integer.parseInt(StringUtils.defaultString(login_times_str, "0"));
    //		return login_times_int >= maxTriedTimes;
    //	}
}
