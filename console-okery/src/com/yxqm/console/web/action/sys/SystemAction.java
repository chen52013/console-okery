package com.yxqm.console.web.action.sys;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxqm.console.googleauth.GoogleAuthenticator;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bussiness.IUserService;
import com.yxqm.console.web.context.CustomPropertyPlaceholderConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.UUID;


@Controller
public class SystemAction {
	
	@Autowired
	@Qualifier("userService")
	IUserService userService;
	
	@RequestMapping(value="login.do",method = RequestMethod.GET)
	public String toLoginPage(HttpServletRequest request,
			HttpServletResponse response){
		//是否显示身份验证器
		String is_show_verfication = CustomPropertyPlaceholderConfigurer.getProperty("verfication.status.enabled");
		request.setAttribute("is_show_verfication", is_show_verfication);
		return "common/login";
	}
	
	@RequestMapping(value="checkIsAuth.do",method={RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody ForumResponse checkIsAuth(HttpServletRequest request,
			HttpServletResponse response){
		ForumResponse forumResponse = new ForumResponse();
		UserBean userBean = new UserBean();
		String j_username = request.getParameter("j_username");
		if(StringUtils.isNotBlank(j_username)){
			userBean.setLoginName(j_username);
		}
		//根据用户名查询用户邮箱或者手机号码
		UserBean user = userService.queryUser(userBean);
		if(user != null){
			if(user.getIs_auth() == 1 || "1".equals(user.getIs_auth())){
				forumResponse.setRes_code("1");
				forumResponse.setRes_msg("用户已经授权！");
			}else{
				forumResponse.setRes_code("0");
				forumResponse.setRes_msg("用户未授权！");
			}
		}else{
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("用户未授权！");
		}
		return forumResponse;
	}
	
	
	@RequestMapping(value="createVerificationCode.do",method={RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody ForumResponse createVerificationCode(HttpServletRequest request,
			HttpServletResponse response){
		ForumResponse forumResponse = new ForumResponse();
		UserBean userBean = new UserBean();
		String j_username = request.getParameter("j_username");
		String u_mobile = null;
		String url = null;
		if(StringUtils.isNotBlank(j_username)){
			userBean.setLoginName(j_username);
		}
		//根据用户名查询用户邮箱或者手机号码
		UserBean user = userService.queryUser(userBean);
		if(user == null){
			forumResponse.setRes_code("0");
			forumResponse.setRes_msg("用户不存在！");
		}else{
			//应该先判断这个用户是否已经生成过动态二维码
			if(user.getIs_auth() != 0 && StringUtils.isNotBlank(user.getSecret_key())){
				forumResponse.setRes_code("0");
				forumResponse.setRes_msg("不能重复生成动态二维码！");
			}else{
				if(user.getU_mobile() != null && !"".equals(user.getU_mobile())){
					u_mobile = user.getU_mobile();
				}
				/**
				 * 判断用户数据库是否已经保存秘钥
				 * 若保存  说明用户生成过  从数据库读秘钥
				 * 若未保存  系统帮其生成秘钥
				 */
				String secret = null;
				if(StringUtils.isNotBlank(user.getSecret_key())){
					secret = user.getSecret_key();
				}else{
					//根据Google authenticator生成秘钥
					secret = GoogleAuthenticator.generateSecretKey();
				}
				
				/**
				 * 第一个参数：用户名
				 * 第二个参数：身份秘钥
				 */
				if(StringUtils.isNotBlank(j_username) && StringUtils.isNotBlank(u_mobile) && StringUtils.isNotBlank(secret)){
					String host = CustomPropertyPlaceholderConfigurer.getProperty("sys.flag","TEST").toUpperCase();
					if(host.contains("开发")){
						host = "TEST";
					}else if(host.contains("测试")){
						host = "TEST";
					}else if(host.contains("RC")){
						host = "RC";
					}else if(host.contains("生产")){
						host = "PRD";
					}
					url = GoogleAuthenticator.getQRBarcodeURL(j_username,secret);
					if(StringUtils.isNotBlank(url)){
						//将secret_key is_auth入库
						user.setSecret_key(secret);
						user.setU_user_id(user.getUserId()+"");
						if(StringUtils.isNotBlank(user.getU_user_id())){
							userService.updateUser(user);
						}
					}
				}
				if (url == null) {
					forumResponse.setRes_code("0");
					forumResponse.setRes_msg("二维码生成失败");
				} else {
					forumResponse.setRes_code("1");
					forumResponse.setOth_data(url);
					forumResponse.setRes_msg("二维码生成成功！");
				}
			}
		}
		return forumResponse;
	}
	
	@RequestMapping(value="accessDenied.do",method = RequestMethod.GET)
	public String toAccessDeniedPage(Model model){
		return "common/accessDenied";
	}
	
	@RequestMapping(value="sessionTimeout.do",method = RequestMethod.GET)
	public String toSessionTimeoutPage(Model model){
		return "common/sessionTimeout";
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
	}
}
