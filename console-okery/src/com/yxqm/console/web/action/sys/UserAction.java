package com.yxqm.console.web.action.sys;

import com.alibaba.fastjson.JSONObject;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.bean.UserRoleRefBean;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bussiness.IRoleService;
import com.yxqm.console.web.bussiness.IUserService;
import com.yxqm.console.web.security.CustomUser;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.bean.UserRoleRefBean;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bussiness.IRoleService;
import com.yxqm.console.web.bussiness.IUserService;
import com.yxqm.console.web.security.CustomUser;

@Controller
public class UserAction {
	private static final Logger LOG = LoggerFactory.getLogger(UserAction.class);

	@Autowired
	@Qualifier("userService")
	IUserService userService;

	@Autowired
	@Qualifier("roleService")
	IRoleService roleService;
	
	/**
	 * 跳转到用户管理界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUserListPage.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.VIEW,funcation="系统管理-用户管理",desc = "跳转到用户管理界面")
	public String toUserListPage(Model model) {
		return "system/user/userList";
	}
	
	/**
	 * 跳转到添加用户界面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toAddUserPage.do",method =  { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.VIEW,funcation="系统管理-用户管理",desc = "跳转到添加用户界面")
	public String toAddUserPage(Model model){
		return "system/user/addUser";
	}
	
	/**
	 * 跳转到修改用户界面
	 *
	 * @return
	 */
	@RequestMapping(value = "toUpdateUserPage.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.VIEW,funcation="系统管理-用户管理",desc = "跳转到修改用户界面")
	public String toUpdateUserPage(HttpServletRequest request,
			HttpServletResponse response) {
		String u_user_id = request.getParameter("u_user_id");
		UserBean userBean = userService.queryUserById(Integer.parseInt(u_user_id));
		
		request.setAttribute("userBean", userBean);
		return "system/user/updateUser";
	}
	
	/**
	 * 跳转到角色配置界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toUserRoleConfigPage.do",method =  { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.VIEW,funcation="系统管理-用户管理",desc = "跳转到角色配置界面")
	public String toUserRoleConfigPage(Model model){
		return "system/user/userRoleConfig";
	}
	
	/**
	 * 用户数据检查
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "userDataChecked.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.QUERY,funcation="用户数据检查",desc = "用户数据检查")
	public @ResponseBody Map<String, Object> userDataChecked(
			@ModelAttribute UserBean userBean,
			HttpServletRequest request, HttpServletResponse response) {
		String u_user_code = request.getParameter("u_user_code");
		String u_login_name = request.getParameter("u_login_name");
		Boolean result = true;
		String resultMsg = "";
		int userCodeExistCount = 0;
		int loginNameExistCount = 0;
		if(!StringUtils.isEmpty(u_user_code)){
			userBean.setU_user_code(u_user_code);
			userCodeExistCount = userService.queryUserDataChecked(userBean);
			if(userCodeExistCount != 0){
				result = false;
				resultMsg = "该用户编码已存在！";
			}

		}
		
		if(!StringUtils.isEmpty(u_login_name)){
			userBean.setU_login_name(u_login_name);
			loginNameExistCount = userService.queryUserDataChecked(userBean);
			if(loginNameExistCount != 0){
				result = false;
				resultMsg = "该登录名已存在！";
			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", result);
		resMap.put("msg", resultMsg);
		return resMap;
	}
	
	/**
	 * 查询用户角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryUserRole.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.QUERY,funcation="查询用户角色",desc = "查询用户角色")
	public @ResponseBody Map<String, Object> queryUserRole(
			@ModelAttribute UserBean userBean,
			HttpServletRequest request, HttpServletResponse response) {
		RoleBean roleBean = new RoleBean();
		List<RoleBean> roleList = roleService.queryRole(roleBean);
        List<RoleBean> userRoleList = roleService.queryRoleByUser(userBean);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser) auth.getPrincipal();
		// 过滤超级管理员角色
		if(!"admin".equals(user.getUsername())){
			Iterator<RoleBean> it = roleList.iterator();
			while(it.hasNext()){
				RoleBean i=it.next();
				if("ROLE_SUPERSYSTEM".equals(i.getRoleCode())){
					roleList.remove(i);
					break;
				}
			}
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("roleList", roleList);
		resMap.put("userRoleList", userRoleList);
		return resMap;
	}
	
	/**
	 * 查询用户列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryUserList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.QUERY,funcation="查询用户列表",desc = "查询用户列表")
	public @ResponseBody Map<String, Object> queryUserList(
			@ModelAttribute UserBean userBean,
			HttpServletRequest request, HttpServletResponse response) {
		int totalRows = userService.queryUserRows(userBean);
		List<Map<String, Object>> lstMap = userService.queryUserList(userBean);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", userBean.getCurPage());
		resMap.put("data", lstMap);
		return resMap;
	}
	
	/**
	 * 查询外部系统管理员列表(此处只查询了圈子管理员的用户)
	 * 
	 * @param request
	 * @param response
	 */
//	@RequestMapping(value = "queryExtSysUserSelectList.do", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.QUERY,funcation="查询外部系统管理员列表",desc = "查询外部系统管理员列表")
//	public @ResponseBody Map<String, Object> queryExtSysUserList(
//			@ModelAttribute UserBean userBean,
//			HttpServletRequest request, HttpServletResponse response) {
//		List<Map<String, Object>> lstMap = userService.queryExtSysUserList(userBean);
//		Map<String, Object> resMap = new HashMap<String, Object>();
//		resMap.put("data", lstMap);
//		return resMap;
//	}
	
	/**
	 * 添加用户
	 * @param userBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addUser.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.ADD,funcation="用户管理--添加用户",desc = "添加用户")
	public @ResponseBody ForumResponse addUser(
			@ModelAttribute UserBean userBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		String u_user_id = request.getParameter("u_user_id");
		if(StringUtils.isNotBlank(u_user_id)){
			userBean.setU_user_id(u_user_id);
		}
		String u_user_mobile = request.getParameter("u_user_mobile");
		if(StringUtils.isNotBlank(u_user_mobile)){
			userBean.setUser_mobile(u_user_mobile);
		}
		String u_login_pwd = request.getParameter("u_login_pwd");
		if(StringUtils.isNotBlank(u_login_pwd)){
			userBean.setU_login_pwd(u_login_pwd);
		}
		String u_user_name = request.getParameter("u_user_name");
		if(StringUtils.isNotBlank(u_user_name)){
			userBean.setU_user_name(u_user_name);
		}
		String u_login_name = request.getParameter("u_login_name");
		if(StringUtils.isNotBlank(u_login_name)){
			userBean.setU_login_name(u_login_name);
		}
		String u_mobile = request.getParameter("u_mobile");
		if(StringUtils.isNotBlank(u_mobile)){
			userBean.setU_mobile(u_mobile);
		}
		String u_user_code = request.getParameter("u_user_code");
		if(StringUtils.isNotBlank(u_user_code)){
			userBean.setU_user_code(u_user_code);
		}
		String u_status = request.getParameter("u_status");
		if(StringUtils.isNotBlank(u_status)){
			userBean.setU_status(u_status);
		}
		String u_remark = request.getParameter("u_remark");
		if(StringUtils.isNotBlank(u_remark)){
			userBean.setU_remark(u_remark);
		}
		int user_id = 0;
		try {
			user_id = userService.addUser(userBean);
		} catch (Exception e) {
			LOG.error("添加用户信息异常", e);
		}
		if (user_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("用户信息添加失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("用户信息添加成功！");
		}
		return forumResponse;
	}
	
	/**
	 * 修改用户
	 * @param userBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateUser.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.MODIFY,funcation="用户管理--修改用户",desc = "修改用户")
	public @ResponseBody ForumResponse updateUser(
			@ModelAttribute UserBean userBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		String action = request.getParameter("action");
		if("reset_password".equals(action)){
			String u_password = String.format("pchilltech#%s", DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd"));  //pchilltech#20160523
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				u_password = Hex.encodeHexString(md.digest(u_password.getBytes(Charset.defaultCharset())));
			} catch (NoSuchAlgorithmException e) {
				LOG.error("MD5 algorithm is not supported");
			}
			userBean.setU_login_pwd(u_password);
		}
		int user_id = 0;
		try {
			user_id = userService.updateUser(userBean);
			LOG.info("userId={}, password is modified, {}", user_id, JSONObject.toJSONString(userBean));
		} catch (Exception e) {
			LOG.error("修改用户信息异常, {}", JSONObject.toJSONString(userBean), e);
		}
		if (user_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("修改用户信息失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("修改用户信息成功");
		}
		return forumResponse;
	}
	
	/**
	 * 删除用户
	 * 
	 * @param userBean
	 * @param
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteUser.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.DELETE,funcation="用户管理--删除用户",desc = "删除用户")
	public @ResponseBody ForumResponse deleteUser(
			@ModelAttribute UserBean userBean,
			HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int affectedRows = userService.deleteUser(userBean);
		if (affectedRows == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("用户删除失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("用户删除成功");
		}
		return forumResponse;
	}
	
	/**
	 * 用户角色配置
	 * @param
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addUserRole.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.ADD,funcation="用户管理--用户角色配置",desc = "新增用户角色")
	public @ResponseBody ForumResponse addUserRole(
			@ModelAttribute UserRoleRefBean userRoleBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		int affectedRows = 0;
		try {
			affectedRows = userService.addUserRole(userRoleBean);
		} catch (Exception e) {
			LOG.error("用户角色配置异常", e);
		}
		if (affectedRows == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("用户角色添加失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("用户角色添加成功！");
		}
		return forumResponse;
	}
	
	/**
	 * 跳转到修改用户密码界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toChangeUserPwd.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.VIEW,funcation="跳转到修改用户密码界面",desc = "修改用户密码界面")
	public String toChangeUserPwd(
			HttpServletRequest request, HttpServletResponse response) {
		CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = customUser.getUsername();
		String password = customUser.getPassword();
		long userId = customUser.getUserId();
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		request.setAttribute("userId", userId);
		return "common/changePwd";
	}


	/**
	 * 修改当前用户的密码
	 * @param userBean
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "changeUserPwd.do", method = { RequestMethod.GET,
			RequestMethod.POST })
//	@WriteLog(funcationType=WriteType.MODIFY,funcation="修改当前用户的密码",desc = "修改当前用户的密码功能")
	public @ResponseBody ForumResponse changeUserPwd(
			@ModelAttribute UserBean userBean,
			HttpServletRequest request, HttpServletResponse response) {
		ForumResponse forumResponse = new ForumResponse();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser) auth.getPrincipal();
		userBean.setU_user_id(user.getUserId()+"");
		String u_password = request.getParameter("u_password");
		if(StringUtils.isNotBlank(u_password)){
			userBean.setU_login_pwd(u_password);
		}
		int user_id = 0;
		try {
			UserBean userParams = new UserBean();
			userParams.setLoginName(user.getUsername());
			UserBean user1 = null;
			user1 = userService.queryUser(userParams);
			userBean.setLoginPwd(user1.getLoginPwd() + "");
			user_id = userService.updateUser(userBean);
			LOG.info("userId={}, password is modified, {}", user_id, JSONObject.toJSONString(userBean));
		} catch (Exception e) {
			LOG.error("修改密码异常, {}", JSONObject.toJSONString(userBean), e);
		}

		if(user_id == 2){
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("当前密码与过去密码重复，修改失败");
			return forumResponse;
		}
		if (user_id == 0) {
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("修改密码失败");
		} else {
			forumResponse.setRes_code("1");
			forumResponse.setRes_msg("修改密码成功");
		}
		return forumResponse;
	}
}