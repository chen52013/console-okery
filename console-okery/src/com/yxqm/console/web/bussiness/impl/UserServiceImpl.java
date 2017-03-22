package com.yxqm.console.web.bussiness.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yxqm.console.web.bussiness.IUserService;
import com.yxqm.console.system.IUserDao;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.bean.UserRoleRefBean;
import com.yxqm.console.system.exception.ConsoleSystemException;

@Service("userService")
public class UserServiceImpl implements IUserService{

	@Autowired
	@Qualifier("userDao")
	protected IUserDao userDao;
	
	public UserBean queryUser(UserBean user) throws ConsoleSystemException {
		return userDao.queryUser(user);
	}

	@Override
	public int queryUserRows(UserBean userBean) throws ConsoleSystemException {
		try {
			return userDao.queryRows(userBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询用户列表总记录异常", ex);
		}
	}
	
	@Override
	public List<Map<String, Object>> queryUserList(UserBean userBean)
			throws ConsoleSystemException {
		try {
			return userDao.queryList(userBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询用户列表记录异常", ex);
		}
	}
	
	@Override
	public int addUser(UserBean userBean) throws ConsoleSystemException {
		try {
			String extSysId = userBean.getExt_sys_id();//外部系统ID
			String u_user_id = userBean.getU_user_id();
			String user_mobile = userBean.getUser_mobile();
			//外部圈子系统处理(ext_sys_object_id为圈子系统的t_user表中的user_id，ext_sys_object_value为user_mobile)
			if(!StringUtils.isEmpty(extSysId) && !StringUtils.isEmpty(u_user_id) && !StringUtils.isEmpty(user_mobile)
					&& "1".equals(extSysId)){
				userBean.setExt_sys_object_id(u_user_id);
				userBean.setExt_sys_object_value(user_mobile);
			}
			
			int user_id = userDao.addUser(userBean);
			return user_id;
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("添加用户信息异常", ex);
		}
	}
	
	@Override
	public UserBean queryUserById(int userId) throws ConsoleSystemException {
		return userDao.queryUserById(userId);
	}
	
	@Override
	public int updateUser(UserBean userBean) throws ConsoleSystemException {
		try {
			int user_id = 0;
			String extSysId = userBean.getExt_sys_id();//外部系统ID
			String u_user_id = userBean.getU_user_id();
			String user_mobile = userBean.getUser_mobile();
			//外部圈子系统处理(ext_sys_object_id为圈子系统的t_user表中的user_id，ext_sys_object_value为user_mobile)
			if(!StringUtils.isEmpty(extSysId) && !StringUtils.isEmpty(u_user_id) && !StringUtils.isEmpty(user_mobile)
					&& "1".equals(extSysId)){
				userBean.setExt_sys_object_id(u_user_id);
				userBean.setExt_sys_object_value(user_mobile);
			}
			if(!StringUtils.isEmpty(extSysId) && "0".equals(extSysId)){
				userBean.setExt_sys_object_id("0");
				userBean.setExt_sys_object_value("0");
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("u_user_id", u_user_id);
			String login_pwd = userBean.getU_login_pwd();
			List<Map<String, Object>> UserList = userDao.queryInsertUserProfileHistory(params);
			if(!UserList.isEmpty()){
				for(Map<String, Object> userList : UserList){
					if(!StringUtils.isEmpty(userList.get("odd_value"))){
						if(!StringUtils.isEmpty(login_pwd)){
							if(login_pwd.equals(userList.get("odd_value"))){
								return 2;
							}
						}
					}
				}
			}
			user_id = userDao.updateUser(userBean);
			return user_id;
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("修改用户信息异常", ex);
		}
	}
	
	@Override
	public int deleteUser(UserBean userBean) throws ConsoleSystemException {
		try {
			int affectedRows = 0;
			List<UserBean> userBeanList = new ArrayList<UserBean>();
			String userIdStr = userBean.getU_user_id();
			String userCodeStr = userBean.getU_user_code();
			if(!"".equals(userIdStr) && userIdStr != null){
				int offset = userIdStr.indexOf(",");
				if (offset < 0) {//处理批量操作时只选择了一条数据的情况
					
					userBeanList.add(userBean);
					affectedRows = userDao.deleteUser(userBeanList);
					
				}else{//处理批量操作 id,id1,id2 ……
					
					if(!"".equals(userIdStr) && !"".equals(userCodeStr) 
							&& userIdStr != null && userCodeStr != null){
						
						String[] userIdArray = userIdStr.split(",");
						String[] userCodeArray = userCodeStr.split(",");
						UserBean tempUserBean = null;
						for(int i = 0; i<userIdArray.length; i++){
							tempUserBean = new UserBean();
							tempUserBean.setU_user_id(userIdArray[i]);
							tempUserBean.setU_user_code(userCodeArray[i]);
							
							userBeanList.add(tempUserBean);
						}
					}
					
					affectedRows = userDao.deleteUser(userBeanList);
				}
			}
			
			return affectedRows;
			
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("删除用户信息异常", ex);
		}
	}
	
	@Override
	public int queryUserDataChecked(UserBean userBean)
			throws ConsoleSystemException {
		try {
			return userDao.queryUserDataChecked(userBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("用户数据检查异常", ex);
		}
	}
	
	@Override
	public int addUserRole(UserRoleRefBean userRoleRefBean)
			throws ConsoleSystemException {
		try {
			int affectedRows = 0;
			List<UserRoleRefBean> userRoleRefBeanList = new ArrayList<UserRoleRefBean>();
			String userCodeStr = userRoleRefBean.getUser_code();
			String roleCodeStr = userRoleRefBean.getRole_code();
			if(!"".equals(userCodeStr) && userCodeStr != null){
				
				int offset = userCodeStr.indexOf(",");
				/**
				 * 判断处理只分配一个角色给用户的情况或不分配任何角色给用户的情况
				 */
				if (offset < 0) {
					/**
					 * 判断处理不分配任何角色给用户的情况
					 */
					if((!"".equals(userCodeStr) && userCodeStr != null) && 
							("".equals(roleCodeStr) || roleCodeStr == null)){
						
						userRoleRefBean.setRole_code(null);
						userRoleRefBeanList.add(userRoleRefBean);
						affectedRows = userDao.addUserRole(userRoleRefBeanList);
						/**
						 * 处理只分配一个角色给用户的情况
						 */
					}else if(!"".equals(userCodeStr) && !"".equals(roleCodeStr) 
							&& userCodeStr != null && roleCodeStr != null){
						
						userRoleRefBeanList.add(userRoleRefBean);
						affectedRows = userDao.addUserRole(userRoleRefBeanList);
					}
					
					/**
					 * 处理分配多个角色给用户的情况
					 */
				}else{
					if(!"".equals(userCodeStr) && !"".equals(roleCodeStr) 
							&& userCodeStr != null && roleCodeStr != null){
						
						String[] userCodeArray = userCodeStr.split(",");
						String[] roleCodeArray = roleCodeStr.split(",");
						UserRoleRefBean tempUserRoleRefBean = null;
						for(int i = 0; i<roleCodeArray.length; i++){
							tempUserRoleRefBean = new UserRoleRefBean();
							tempUserRoleRefBean.setUser_code(userCodeArray[i]);
							tempUserRoleRefBean.setRole_code(roleCodeArray[i]);
							
							userRoleRefBeanList.add(tempUserRoleRefBean);
						}
					}
					
					affectedRows = userDao.addUserRole(userRoleRefBeanList);
				}
			}
			
			return affectedRows;
			
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("添加用户角色信息异常", ex);
		}
	}

}
