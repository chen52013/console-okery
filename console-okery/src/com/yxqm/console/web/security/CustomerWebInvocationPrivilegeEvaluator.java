/**
 * Project Name:console-web
 * File Name:CustomerWebInvocationPrivilegeEvaluator.java
 * Package Name:com.yxqm.console.web.security
 * Date:2015Âπ?Êú?2Êó•‰∏ãÂç?:48:23
 * Copyright (c) 2015, Èõ∑ÊπòÂâ?All Rights Reserved.
 *
*/
package com.yxqm.console.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

import org.springframework.util.Assert;

import java.util.Collection;


/**
 * ClassName:CustomerWebInvocationPrivilegeEvaluator <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2015Âπ?Êú?2Êó?‰∏ãÂçà3:48:23 <br/>
 * @author  Èõ∑ÊπòÂâ?
 * @version
 * @since    JDK 1.7
 * @see
 */
public class CustomerWebInvocationPrivilegeEvaluator
    implements WebInvocationPrivilegeEvaluator {
    protected static final Log logger = LogFactory.getLog(DefaultWebInvocationPrivilegeEvaluator.class);
    private final AbstractSecurityInterceptor securityInterceptor;

    public CustomerWebInvocationPrivilegeEvaluator(
        AbstractSecurityInterceptor securityInterceptor) {
        Assert.notNull(securityInterceptor, "SecurityInterceptor cannot be null");
        Assert.isTrue(FilterInvocation.class.equals(
                securityInterceptor.getSecureObjectClass()),
            "AbstractSecurityInterceptor does not support FilterInvocations");
        Assert.notNull(securityInterceptor.getAccessDecisionManager(),
            "AbstractSecurityInterceptor must provide a non-null AccessDecisionManager");

        this.securityInterceptor = securityInterceptor;
    }

    /**
     * Determines whether the user represented by the supplied <tt>Authentication</tt> object is
     * allowed to invoke the supplied URI.
     *
     * @param uri the URI excluding the context path (a default context path setting will be used)
     */
    public boolean isAllowed(String uri, Authentication authentication) {
        return isAllowed(null, uri, null, authentication);
    }

    /**
     * Determines whether the user represented by the supplied <tt>Authentication</tt> object is
     * allowed to invoke the supplied URI, with the given .
     * <p>
     * Note the default implementation of <tt>FilterInvocationSecurityMetadataSource</tt> disregards the
     * <code>contextPath</code> when evaluating which secure object metadata applies to a given
     * request URI, so generally the <code>contextPath</code> is unimportant unless you
     * are using a custom <code>FilterInvocationSecurityMetadataSource</code>.
     *
     * @param uri the URI excluding the context path
     * @param contextPath the context path (may be null, in which case a default value will be used).
     * @param method the HTTP method (or null, for any method)
     * @param authentication the <tt>Authentication</tt> instance whose authorities should be used in evaluation
     *          whether access should be granted.
     * @return true if access is allowed, false if denied
     */
    public boolean isAllowed(String contextPath, String uri, String method,
        Authentication authentication) {
        Assert.notNull(uri, "uri parameter is required");

        FilterInvocation fi = new FilterInvocation(contextPath, uri, method);
        Collection<ConfigAttribute> attrs = null;

        try {
            attrs = securityInterceptor.obtainSecurityMetadataSource()
                                       .getAttributes(fi);
        } catch (AccessDeniedException e) {
            securityInterceptor.setRejectPublicInvocations(true);
        }

        if (attrs == null) {
            if (securityInterceptor.isRejectPublicInvocations()) {
                return false;
            }

            return true;
        }

        if (authentication == null) {
            return false;
        }

        try {
            securityInterceptor.getAccessDecisionManager()
                               .decide(authentication, fi, attrs);
        } catch (AccessDeniedException unauthorized) {
            if (logger.isDebugEnabled()) {
                logger.debug(fi.toString() + " denied for " +
                    authentication.toString(), unauthorized);
            }

            return false;
        }

        return true;
    }
}
