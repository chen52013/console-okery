package com.yxqm.console.web.action.sys;

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

import com.yxqm.console.system.bean.MenuBean;
import com.yxqm.console.system.bean.ResourceBean;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bussiness.IMenuService;
import com.yxqm.console.web.bussiness.IResourceService;
import com.yxqm.console.web.security.CustomInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ResourceAction {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResourceAction.class);
	
	@Autowired
	@Qualifier("resource")
	IResourceService resource;
	
	@Autowired
	@Qualifier("menuService")
	IMenuService menuService;
	
//	@Autowired
//	@Qualifier("customSecurityMetadataSource")
//	protected CustomInvocationSecurityMetadataSource customSecurityMetadataSource;
	
	/**
	 * 跳转到资源管理界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toResourcesListPage.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String toResourcesListPage(Model model){
		return "system/resources/resourcesList";
	}
	
	/**
	 * 跳转到添加资源界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAddResourcePage.do",method =  { RequestMethod.GET,
			RequestMethod.POST })
	public String toAddResourcePage(Model model){
		return "system/resources/addResource";
	}
	
	/**
	 * 跳转到修改资源界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdateResourcePage.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String toUpdateResourcePage(HttpServletRequest request,
			HttpServletResponse response) {
		String resource_id = request.getParameter("resource_id");
		ResourceBean resourceBean = resource.queryResourceById(Integer.parseInt(resource_id));
		request.setAttribute("resourceBean", resourceBean);
		return "system/resources/updateResource";
	}
	
	/**
	 * 查询资源列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryResourceList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> queryResourceList(
			@ModelAttribute ResourceBean resourceBean,
			HttpServletRequest request, HttpServletResponse response) {
		int totalRows = resource.queryResourceRows(resourceBean);
		List<Map<String, Object>> lstMap = resource.queryResourceList(resourceBean);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", resourceBean.getCurPage());
		resMap.put("data", lstMap);
		return resMap;
	}
	
	/**
	 * 查询菜单列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryMenuSelectList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> queryMenuList(
			@ModelAttribute ResourceBean resourceBean,
			HttpServletRequest request, HttpServletResponse response) {
		MenuBean menu = new MenuBean();
		List<MenuBean> lstMap = menuService.queryMenu(menu);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("data", lstMap);
		return resMap;
	}

	/**
	 * 资源数据检查
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "resourceDataChecked.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> resourceDataChecked(
			@ModelAttribute ResourceBean resourceBean,
			HttpServletRequest request, HttpServletResponse response) {
		Boolean result = true;
		String resultMsg = "";
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", result);
		resMap.put("msg", resultMsg);
		return resMap;
	}
	
	/**
	 * 添加资源信息
	 * @param resourceBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addResource.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse addResource(
			@ModelAttribute ResourceBean resourceBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int resource_id = 0;
		try {
			resource_id = resource.addResource(resourceBean);
		} catch (Exception e) {
			LOG.error("添加资源信息异常", e);
		}
		if (resource_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("资源信息添加失败");
		} else {
//			customSecurityMetadataSource.getAllConfigAttributes();//添加资源时需要刷新TairManager缓存
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("资源信息添加成功！");
		}
		return forumResponse;
	}
	
	/**
	 * 修改资源
	 * @param resourceBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateResource.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse updateResource(
			@ModelAttribute ResourceBean resourceBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int resource_id = 0;
		try {
			resource_id = resource.updateResource(resourceBean);
		} catch (Exception e) {
			LOG.error("修改资源信息异常", e);
		}
		if (resource_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("修改资源信息失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("修改资源信息成功");
		}
		return forumResponse;
	}
	
	/**
	 * 删除资源
	 * 
	 * @param resourceBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteResource.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse deleteResource(
			@ModelAttribute ResourceBean resourceBean,
			HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int affectedRows = resource.deleteResource(resourceBean);
		if (affectedRows == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("资源删除失败");
		} else {
//			customSecurityMetadataSource.getAllConfigAttributes();//删除资源时需要刷新TairManager缓存
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("资源删除成功");
		}
		return forumResponse;
	}
}
