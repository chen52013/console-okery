package com.yxqm.console.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;


public class CustomAccessDecisionManager implements AccessDecisionManager {
    private static final Logger LOG = LoggerFactory.getLogger(CustomAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object,
        Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException {
        Object obj = (Object) authentication.getPrincipal().toString();
        LOG.info("访问资源的用户为{}", obj);

        if (configAttributes == null) {
            throw new AccessDeniedException("Access is denied");
        }

        Iterator<ConfigAttribute> ite = configAttributes.iterator();

        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needGrant = ((SecurityConfig) ca).getAttribute();

            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needGrant.trim().equals(ga.getAuthority().trim())) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("Access is denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }
}
