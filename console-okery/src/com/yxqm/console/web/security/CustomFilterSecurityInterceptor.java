package com.yxqm.console.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor
    implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomFilterSecurityInterceptor.class);
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void invoke(FilterInvocation fi)
        throws IOException, ServletException {
        InterceptorStatusToken token = null;

        try {
            token = super.beforeInvocation(fi);
        } catch (AuthenticationCredentialsNotFoundException e) {
            LOG.error("会话超时");

            String url = fi.getRequestUrl();

            if ("/index.do".equals(url)) {
                fi.getRequest().getRequestDispatcher("/login.do")
                  .forward(fi.getRequest(), fi.getResponse());

                return;
            }

            fi.getRequest().getRequestDispatcher("/sessionTimeout.do")
              .forward(fi.getRequest(), fi.getResponse());

            return;
        }

        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public void setSecurityMetadataSource(
        FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}
