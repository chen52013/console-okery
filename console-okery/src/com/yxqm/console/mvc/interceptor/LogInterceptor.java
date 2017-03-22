package com.yxqm.console.mvc.interceptor;

import com.yxqm.console.web.exception.ConsoleActionException;
import com.yxqm.console.web.security.CustomUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			CustomUser user = (CustomUser) auth.getPrincipal();
			try {
				if (user != null)
					LOG.debug("user:{}, url:{}, params:{}",
							new Object[] { user.getUsername(), request.getServletPath(), request.getParameterMap() });
			} catch (Exception e) {
				throw new ConsoleActionException("preHandle报错！");
			}
		}

		return true;
	}

	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
}