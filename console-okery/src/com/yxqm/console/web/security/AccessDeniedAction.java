package com.yxqm.console.web.security;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AccessDeniedAction {
    @RequestMapping(value = "accessDenied.do", method = RequestMethod.GET)
    public String toLoginPage(Model model) {
        return "common/accessDenied";
    }
}
