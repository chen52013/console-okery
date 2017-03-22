package com.yxqm.console.web.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;


public class CustomAccessDecisionVoter implements AccessDecisionVoter {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object,
        Collection attributes) {
        return 0;
    }
}
