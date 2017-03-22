package com.yxqm.console.web.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import org.springframework.stereotype.Component;


@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) e.getSource();
        loginAttemptService.loginSucceeded(usernamePasswordAuthenticationToken.getPrincipal()
                                                                              .toString());
    }
}
