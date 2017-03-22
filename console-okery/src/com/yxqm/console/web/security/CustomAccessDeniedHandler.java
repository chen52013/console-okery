package com.yxqm.console.web.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private String accessDeniedUrl;

    public CustomAccessDeniedHandler() {
    }

    public CustomAccessDeniedHandler(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    public void handle(HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException)
        throws IOException, ServletException {
        response.sendRedirect(request.getServletContext().getContextPath() +
            accessDeniedUrl);

        //		String deniedMessage = accessDeniedException.getMessage();
        //		request.getSession().setAttribute("ERROR",
        //				deniedMessage);
    }

    public String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }

    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }
}
