package com.yxqm.console.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUser extends User {
    private static final long serialVersionUID = 2644584038249029265L;
    private long userId;
    private String realName;
    private String userCode;
    private int extSysId;
    private int extSysObjectId;
    private String extSysObjectValue;

    public CustomUser(String userCode, String realName, int extSysId,
        int extSysObjectId, String extSysObjectValue, long userId,
        String username, String password, boolean enabled,
        boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<?extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, authorities);
        setRealName(realName);
        setUserCode(userCode);
        setExtSysId(extSysId);
        setExtSysObjectId(extSysObjectId);
        setExtSysObjectValue(extSysObjectValue);
        setUserId(userId);
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getExtSysId() {
        return extSysId;
    }

    public void setExtSysId(int extSysId) {
        this.extSysId = extSysId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getExtSysObjectId() {
        return extSysObjectId;
    }

    public String getExtSysObjectValue() {
        return extSysObjectValue;
    }

    public void setExtSysObjectId(int extSysObjectId) {
        this.extSysObjectId = extSysObjectId;
    }

    public void setExtSysObjectValue(String extSysObjectValue) {
        this.extSysObjectValue = extSysObjectValue;
    }
}
