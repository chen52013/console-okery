package com.yxqm.console.web.action.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.RolePrivilegeRefBean;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bussiness.IPrivilegeService;
import com.yxqm.console.web.bussiness.IRoleService;
import com.yxqm.console.web.security.CustomUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class RoleManageAction {
	
	private static final Logger LOG = LoggerFactory.getLogger(RoleManageAction.class);
	
	@Autowired
	@Qualifier("roleService")
	IRoleService roleService;
	
	@Autowired
	@Qualifier("privilegeService")
	IPrivilegeService privilegeService;

	/**
	 * 跳转到角色列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toRoleList.do", method = RequestMethod.GET)
	public String toRolePage(Model model) {
		return "system/role/roleList";
	}
	
	/**
	 * 跳转到添加角色页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddRolePage.do", method = RequestMethod.GET)
	public String toAddRolePage(@ModelAttribute RoleBean roleBean,
			HttpServletRequest request) {
		return "system/role/addRole";
	}
	
	/**
	 * 跳转到修改角色页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toEditRolePage.do", method = RequestMethod.GET)
	public String toEditRolePage(@ModelAttribute RoleBean roleBean,
			HttpServletRequest request) {
		return "system/role/roleEdit";
	}

	/**
	 * 跳转到分配角色权限界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAssignPrivilegePage.do",method =  { RequestMethod.GET,
			RequestMethod.POST })
	public String toAssignPrivilegePage(Model model){
		return "system/role/assignPrivilege";
	}
	
	/**
	 * 查询角色权限
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryRolePrivilege.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> queryRolePrivilege(
			@ModelAttribute RoleBean roleBean,
			HttpServletRequest request, HttpServletResponse response) {
		PrivilegeBean privilegeBean = new PrivilegeBean();
		List<PrivilegeBean> privilegeList = privilegeService.list(privilegeBean);
        List<PrivilegeBean> rolePrivilegeList = privilegeService.queryPrivilegeByRole(roleBean);
        
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("privilegeList", privilegeList);
		resMap.put("rolePrivilegeList", rolePrivilegeList);
		return resMap;
	}
	
	/**
	 * 查询角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryRoleList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> queryRoleList(
			@ModelAttribute RoleBean roleBean, HttpServletRequest request) {
		int totalRows = roleService.queryRoleRows(roleBean);
		List<RoleBean> lst = roleService.queryRole(roleBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", roleBean.getCurPage());
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "addRole.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public  @ResponseBody Map<String, Object> addRole(@ModelAttribute RoleBean roleBean, HttpServletRequest request){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		int res = roleService.addRole(roleBean);
		if(res == 0){
			resMap.put("res_code", "0");
			resMap.put("res_msg","角色添加失败");
		}else{
			resMap.put("res_code", "1");
			resMap.put("res_msg","角色添加成功");
		}
		
		return resMap;
	}
	
	@RequestMapping(value = "editRole.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public  @ResponseBody Map<String, Object> editRole(@ModelAttribute RoleBean roleBean, HttpServletRequest request){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		int res = roleService.updateRole(roleBean);
		if(res == 0){
			resMap.put("res_code", "0");
			resMap.put("res_msg","角色修改失败");
		}else{
			resMap.put("res_code", "1");
			resMap.put("res_msg","角色修改成功");
		}
		
		return resMap;
	}
	
	@RequestMapping(value = "deleteRole.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse deleteRole(
			@ModelAttribute RoleBean roleBean,
			HttpServletResponse response){
		ForumResponse forumResponse = new ForumResponse();
		int affectedRows = roleService.deleteRole(roleBean);
		if (affectedRows == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("角色删除失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("角色删除成功");
		}
		return forumResponse;
	}
	
	/**
	 * 角色权限配置
	 * @param userBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "assignPrivilege.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ForumResponse assignPrivilege(
			@ModelAttribute RolePrivilegeRefBean rolePrivilegeRefBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int affectedRows = 0;
		try {
			affectedRows = roleService.assignPrivilege(rolePrivilegeRefBean);
		} catch (Exception e) {
			LOG.error("角色权限配置异常", e);
		}
		if (affectedRows == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("角色权限添加失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("角色权限添加成功！");
		}
		return forumResponse;
	}
	
	/**
	 * 角色数据检查
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "roleDataChecked.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> roleDataChecked(
			@ModelAttribute RoleBean roleBean,
			HttpServletRequest request, HttpServletResponse response) {
		String roleCode = request.getParameter("roleCode");
		Boolean result = true;
		String resultMsg = "";
		int roleCodeExistCount = 0;
		if(!StringUtils.isEmpty(roleCode)){
			roleBean.setRoleCode(roleCode);
			roleCodeExistCount = roleService.roleDataChecked(roleBean);
			if(roleCodeExistCount != 0){
				result = false;
				resultMsg = "该角色编码已存在！";
			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", result);
		resMap.put("msg", resultMsg);
		return resMap;
	}
}
