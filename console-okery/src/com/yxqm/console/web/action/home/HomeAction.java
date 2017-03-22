package com.yxqm.console.web.action.home;

import com.yxqm.console.system.bean.MenuBean;
import com.yxqm.console.web.bussiness.IMenuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.ApplicationContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeAction {
    private static final Logger LOG = LoggerFactory.getLogger(HomeAction.class);
    @Autowired
    @Qualifier("menuService")
    IMenuService menuService;

    @RequestMapping(value = "index.do", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        List<MenuBean> menuBeanLts = menuService.queryMenu(null);
        List<MenuBean> topMenus = new ArrayList<MenuBean>();
        List<MenuBean> firstLevelMenu = new ArrayList<MenuBean>();

        for (MenuBean menu : menuBeanLts) {
            int menuLevel = Integer.parseInt(menu.getMenu_level());
            String url = menu.getResource_url();
            boolean isAllowed = false;

            try {
                isAllowed = isAllowed(url, request);
            } catch (IOException e) {
                LOG.error("权限异常", e);
            }

            if (isAllowed) {
                switch (menuLevel) {
                case 0:
                    topMenus.add(menu);

                    break;

                case 1:
                    firstLevelMenu.add(menu);

                    break;

                default:
                    break;
                }
            }
        }

        Collections.sort(topMenus,
            new Comparator<MenuBean>() {
                @Override
                public int compare(MenuBean b1, MenuBean b2) {
                    int menuIndex1 = Integer.parseInt(b1.getMenu_index());
                    int menuIndex2 = Integer.parseInt(b2.getMenu_index());

                    if (menuIndex1 > menuIndex2) {
                        return 1;
                    } else if (menuIndex1 < menuIndex2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

        for (MenuBean topMenu : topMenus) {
            long topMenuId = Long.parseLong(topMenu.getMenu_id());
            List<MenuBean> firstSubMenu = new ArrayList<MenuBean>();

            for (MenuBean firstMenu : firstLevelMenu) {
                long firstParentId = Long.parseLong(firstMenu.getParent_id());

                if (topMenuId == firstParentId) {
                    firstSubMenu.add(firstMenu);
                }
            }

            sort(firstSubMenu);
            topMenu.setSubMenu(firstSubMenu);
        }

        List<Map<String, Object>> parents = new ArrayList<Map<String, Object>>();
        List<MenuBean> twoSubMenuLts = null;
        List<MenuBean> firstSubMenuLts = null;

        for (MenuBean topMenu : topMenus) {
            String menuName = topMenu.getMenu_name();
            long parent_id = Long.parseLong(topMenu.getParent_id());
            String menu_id = topMenu.getMenu_id();
            String menu_url = topMenu.getMenu_url();
            String class_name = topMenu.getClass_name();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("parent_id", parent_id);
            data.put("menu_name", menuName);
            data.put("menu_id", menu_id);
            data.put("menu_url", menu_url);
            data.put("class_name", class_name);
            firstSubMenuLts = topMenu.getSubMenu();
            data.put("firstSubMenu", firstSubMenuLts);

            for (MenuBean firstSubMenu : firstSubMenuLts) {
                twoSubMenuLts = firstSubMenu.getSubMenu();
                data.put("twoSubMenu", twoSubMenuLts);
            }

            parents.add(data);
        }

        model.addAttribute("parentMenu", parents);

        return "home/index";
    }

    public static boolean isAllowed(String url, HttpServletRequest request)
        throws IOException {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        Authentication currentUser = SecurityContextHolder.getContext()
                                                          .getAuthentication();
        boolean is = false;

        try {
            is = getPrivilegeEvaluator(request)
                     .isAllowed(contextPath, url, "GET", currentUser);
        } catch (IOException e) {
            throw new IOException("权限评估异常", e);
        }

        return is;
    }

    public static WebInvocationPrivilegeEvaluator getPrivilegeEvaluator(
        HttpServletRequest request) throws IOException {
        WebInvocationPrivilegeEvaluator privEvaluatorFromRequest = (WebInvocationPrivilegeEvaluator) request.getAttribute(WebAttributes.WEB_INVOCATION_PRIVILEGE_EVALUATOR_ATTRIBUTE);

        if (privEvaluatorFromRequest != null) {
            return privEvaluatorFromRequest;
        }

        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        Map<String, WebInvocationPrivilegeEvaluator> wipes = ctx.getBeansOfType(WebInvocationPrivilegeEvaluator.class);

        if (wipes.size() == 0) {
            throw new IOException(
                "No visible WebInvocationPrivilegeEvaluator instance could be found in the application context. There must be at least one in order to support the use of URL access checks in 'authorize' tags.");
        }

        return (WebInvocationPrivilegeEvaluator) wipes.values().toArray()[0];
    }

    private void sort(List<MenuBean> topMenus) {
        Collections.sort(topMenus,
            new Comparator<MenuBean>() {
                @Override
                public int compare(MenuBean b1, MenuBean b2) {
                    int menuIndex1 = Integer.parseInt(b1.getMenu_index());
                    int menuIndex2 = Integer.parseInt(b2.getMenu_index());

                    if (menuIndex1 > menuIndex2) {
                        return 1;
                    } else if (menuIndex1 < menuIndex2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
    }

    public static void main(String[] args) {
        double sum0 = 0;
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        int all = 10000;

        for (int i = 0; i < all; i++) {
            int num = (int) (Math.random() * 4);

            if (num == 0) {
                sum0++;
            }

            if (num == 1) {
                sum1++;
            }

            if (num == 2) {
                sum2++;
            }

            if (num == 3) {
                sum3++;
            }
        }

        System.out.println("0的概�?" + (sum0 / all));
        System.out.println("1的概�?" + (sum1 / all));
        System.out.println("2的概�?" + (sum2 / all));
        System.out.println("3的概�?" + (sum3 / all));
    }
}
