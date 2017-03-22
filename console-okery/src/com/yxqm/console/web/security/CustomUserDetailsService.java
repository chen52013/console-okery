package com.yxqm.console.web.security;

import com.alibaba.fastjson.JSON;
import com.yxqm.console.cache.TairConstants;
import com.yxqm.console.cache.TairManager;
import com.yxqm.console.system.bean.RolePrivilegeRefBean;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.exception.ConsoleSystemException;
import com.yxqm.console.utils.SpringContextUtil;
import com.yxqm.console.web.bussiness.IRoleService;
import com.yxqm.console.web.bussiness.IUserService;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

	IUserService userService;

	IRoleService roleService;

//	@Autowired
//	@Qualifier("tairManagerService")
//	TairManager tairManager;

	@Autowired
	private LoginAttemptService loginAttemptService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserBean userParams = new UserBean();
		if (!StringUtils.isNotBlank(username)) {
			LOG.error("登陆失败, username={}", username);
			throw new RuntimeException("帐号或密码错误,3次失败会导致帐号被锁定!");
		}
		userParams.setLoginName(username);
		UserBean user = null;
		try {
			if (StringUtils.isNotBlank(username)) {
				user = userService.queryUser(userParams);
			}
		} catch (ConsoleSystemException e) {
			LOG.error("登陆异常, username={}", username, e);
			throw new RuntimeException("帐号或密码错误,3次失败会导致帐号被锁定!", e);
		} catch (Exception e) {
			LOG.error("登陆异常, username={}", username, e);
			throw new RuntimeException("帐号或密码错误,3次失败会导致帐号被锁定!", e);
		}
		if (user == null) {
			throw new RuntimeException("帐号或密码错误,3次失败会导致帐号被锁定!");
		} else {
			if (user.getStatus() != 0) {
				LOG.error("帐号被锁定, username={}", username);
				throw new RuntimeException("登录失败！帐号被锁定");
			}
		}
//		if (loginAttemptService.isBlocked(username)) {
//			user.setU_status("1");
//			user.setU_user_id(user.getUserId() + "");
//			userService.updateUser(user);
//			throw new RuntimeException("登录失败！帐号被锁定");
//		}

		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = user.getStatus() == 0;

		CustomUser userdetail = new CustomUser(user.getUserCode(), user.getUserName(), user.getExtSysId(),
				user.getExtSysObjectId(), user.getExtSysObjectValue(), user.getUserId(), user.getLoginName(),
				user.getLoginPwd(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
//		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("user", user);
//		properties.put("enabled", enables);
//		properties.put("accountNonExpired", accountNonExpired);
//		properties.put("credentialsNonExpired", credentialsNonExpired);
//		properties.put("accountNonLocked", accountNonLocked);
//		properties.put("grantedAuths", grantedAuths);
//		((TairManager) SpringContextUtil.getBean("tairManagerService")).putCache(TairConstants.NS_AUTH,
//				"userdetail_" + username, JSON.toJSONString(properties));
		return userdetail;
	}

	private Set<GrantedAuthority> obtionGrantedAuthorities(UserBean user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();

		List<RolePrivilegeRefBean> rolePrivilegeRefBeans = roleService.queryPrivilegeByUserRole(user);

		for (RolePrivilegeRefBean rolePrivilegeRefBean : rolePrivilegeRefBeans) {
			authSet.add(new GrantedAuthorityImpl(rolePrivilegeRefBean.getPrivilege_code()));
		}
		return authSet;
	}
}
