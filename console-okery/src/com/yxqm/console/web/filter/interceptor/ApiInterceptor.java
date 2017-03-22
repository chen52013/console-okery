package com.yxqm.console.web.filter.interceptor;

import org.springframework.http.MediaType;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");

        String format = request.getParameter("format");

        if ("xml".equalsIgnoreCase(format)) {
            Set<MediaType> mediaTypes = new HashSet<MediaType>();
            mediaTypes.add(MediaType.APPLICATION_XML);

            request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,
                mediaTypes);
        } else {
            Set<MediaType> mediaTypes = new HashSet<MediaType>();
            mediaTypes.add(MediaType.APPLICATION_JSON);

            request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,
                mediaTypes);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        // TODO Auto-generated method stub
    }
}
