package com.yxqm.console.web.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yxqm.console.system.bean.MenuBean;
import com.yxqm.console.web.bussiness.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yxqm.console.system.bean.MenuBean;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bussiness.IMenuService;
import com.yxqm.console.web.context.CustomPropertyPlaceholderConfigurer;

@Controller
public class MenuAction {

	private static final Logger LOG = LoggerFactory.getLogger(MenuAction.class);
	
	@Autowired
	@Qualifier("menuService")
	IMenuService menuService;
	
	/**
	 * 跳转到菜单管理界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toMenuListPage.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String toMenuListPage(Model model) {
		return "system/menu/menuList";
	}
	
	/**
	 * 跳转到添加菜单界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAddMenuPage.do",method =  { RequestMethod.GET,
			RequestMethod.POST })
	public String toAddMenuPage(HttpServletRequest request,
			HttpServletResponse response){
		String modules = CustomPropertyPlaceholderConfigurer.getProperty("module.names");
		String[] module_names = modules.split(",");
		request.setAttribute("module_names", module_names);
		return "system/menu/addMenu";
	}
	
	/**
	 * 跳转到修改菜单界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdateMenuPage.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String toUpdateMenuPage(HttpServletRequest request,
			HttpServletResponse response) {
		String menu_id = request.getParameter("menu_id");
		MenuBean menuBean = menuService.queryMenuById(Integer.parseInt(menu_id));
		String modules = CustomPropertyPlaceholderConfigurer.getProperty("module.names");
		String[] module_names = modules.split(",");
		request.setAttribute("module_names", module_names);
		request.setAttribute("menuBean", menuBean);
		return "system/menu/updateMenu";
	}
	
	/**
	 * 父菜单下拉列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryParentMenuSelectList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> queryParentMenuSelectList(
			@ModelAttribute MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		menuBean.setMenu_level("0,1");
		List<Map<String, Object>> lstMap = menuService.queryParentMenuSelectList(menuBean);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("data", lstMap);
		return resMap;
	}
	
	/**
	 * 查询菜单列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryMenuList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> queryMenuList(
			@ModelAttribute MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		int totalRows = menuService.queryMenuRows(menuBean);
		List<Map<String, Object>> lstMap = menuService.queryMenuList(menuBean);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", menuBean.getCurPage());
		resMap.put("data", lstMap);
		return resMap;
	}

	/**
	 * 添加菜单
	 * @param menuBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addMenu.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse addMenu(
			@ModelAttribute MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int menu_id = 0;
		try {
			menu_id = menuService.addMenu(menuBean);
		} catch (Exception e) {
			LOG.error("添加菜单信息异常", e);
		}
		if (menu_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("菜单信息添加失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("菜单信息添加成功！");
		}
		return forumResponse;
	}
	
	/**
	 * 修改菜单
	 * @param menuBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateMenu.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse updateMenu(
			@ModelAttribute MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int menu_id = 0;
		try {
			menu_id = menuService.updateMenu(menuBean);
		} catch (Exception e) {
			LOG.error("修改菜单信息异常", e);
		}
		if (menu_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("修改菜单信息失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("修改菜单信息成功");
		}
		return forumResponse;
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param menuBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteMenu.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse deleteMenu(
			@ModelAttribute MenuBean menuBean,
			HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int affectedRows = menuService.deleteMenu(menuBean);
		if (affectedRows == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("菜单删除失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("菜单删除成功");
		}
		return forumResponse;
	}
}
