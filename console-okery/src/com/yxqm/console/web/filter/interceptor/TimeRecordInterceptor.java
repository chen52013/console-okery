package com.yxqm.console.web.filter.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StopWatch;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TimeRecordInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TimeRecordInterceptor.class);
    private static final ThreadLocal<StopWatch> stopHolder = new ThreadLocal<StopWatch>();

    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch("GATEWAY");
        stopHolder.set(stopWatch);
        stopWatch.start();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        StopWatch stopWatch = stopHolder.get();

        if (stopWatch.isRunning()) {
            stopWatch.stop();
        }

        stopHolder.remove();
        LOG.info("服务【{}】执行时�?{}毫秒", request.getServletPath(),
            stopWatch.getTotalTimeMillis());
    }
}
