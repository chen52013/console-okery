/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.springframework.security.web.authentication;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import com.yxqm.console.cache.TairConstants;
import com.yxqm.console.cache.TairManager;
import com.yxqm.console.googleauth.GoogleAuthenticator;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.utils.Constants;
import com.yxqm.console.utils.SpringContextUtil;
import com.yxqm.console.web.bussiness.IUserService;
import com.yxqm.console.web.context.CustomPropertyPlaceholderConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashMap;


/**
 * Processes an authentication form submission. Called {@code AuthenticationProcessingFilter} prior to Spring Security
 * 3.0.
 * <p>
 * Login forms must present two parameters to this filter: a username and
 * password. The default parameter names to use are contained in the
 * static fields {@link #SPRING_SECURITY_FORM_USERNAME_KEY} and {@link #SPRING_SECURITY_FORM_PASSWORD_KEY}.
 * The parameter names can also be changed by setting the {@code usernameParameter} and {@code passwordParameter}
 * properties.
 * <p>
 * This filter by default responds to the URL {@code /j_spring_security_check}.
 *
 * @author Ben Alex
 * @author Colin Sampaleanu
 * @author Luke Taylor
 * @since 3.0
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //~ Static fields/initializers =====================================================================================

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
    public static final String SPRING_SECURITY_FORM_VERIFICATION_KEY = "j_verification_code";
    /**
     * @deprecated If you want to retain the username, cache it in a customized {@code AuthenticationFailureHandler}
     */
    @Deprecated
    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String verificationParameter = SPRING_SECURITY_FORM_VERIFICATION_KEY;
    private boolean postOnly = true;

    //~ Constructors ===================================================================================================

    @Autowired
	@Qualifier("userService")
	IUserService userService;
    
    private static final Logger LOG = LoggerFactory.getLogger(UsernamePasswordAuthenticationFilter.class);
    
    public UsernamePasswordAuthenticationFilter() {
        super("/j_spring_security_check");
    }

    //~ Methods ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String verification = obtainVerification(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if(verification == null){
        	verification = "";
        }
        
        username = username.trim();
        password = password.trim();  
        verification = verification.trim();
        
        //如果为true则显示身份验证器
        String is_enabled = CustomPropertyPlaceholderConfigurer.getProperty("verfication.status.enabled", "false");
        if(is_enabled == "true" || is_enabled.equals("true")){
        	UserBean userParams = new UserBean();
        	userParams.setLoginName(username);
        	//通过用户的登录名 去数据库查用户的身份秘钥
        	UserBean user = userService.queryUser(userParams);
        	
        	//验证手机动态码
        	if(StringUtils.isNotBlank(verification)){
        		long ver_code = Long.parseLong(verification);
        		long times = System.currentTimeMillis();
        		GoogleAuthenticator ga = new GoogleAuthenticator();
        		ga.setWindowSize(5); //should give 5 * 30 seconds of grace...
        		if(StringUtils.isNotBlank(user.getSecret_key())){
        			boolean is_true = ga.check_code(user.getSecret_key(), ver_code,times);
        			if(!is_true){
        				LOG.error("登陆失败, secret_key={}", user.getSecret_key());
        				throw new BadCredentialsException("登录失败！动态验证码错误");
        			}else{
        				user.setIs_auth(1);
        				user.setU_user_id(user.getUserId()+"");
        				userService.updateUser(user);
        				LOG.debug("验证码验证成功, secret_key={}",user.getSecret_key());
        			}
        		}else{
        			LOG.error("登陆失败, 用户secret_key不存在!");
        			throw new BadCredentialsException("登陆失败, 用户secret_key不存在!");
        		}
        	}else{
        		LOG.error("登陆失败, 用户secret_key不存在!");
        		throw new BadCredentialsException("登陆失败, 用户secret_key不存在!");
        	}
        }
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);

        //SSO1, 添加cookie缓存auth
        if (StringUtils.isNotBlank(username)){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                String cache_key = Hex.encodeHexString(md.digest(username.getBytes(Charset.defaultCharset())));
                Cookie cookie = new Cookie(Constants.CONSOLE_AUTH_COOKIE_KEY, cache_key);
                cookie.setPath("/");
                response.addCookie(cookie);
                HashMap<String,Object> authenticationMap = new HashMap<>();
                authenticationMap.put("username", username);
                authenticationMap.put("password", password);
                authenticationMap.put("verfication", verification);
                authenticationMap.put("authorities", authentication.getAuthorities());
//                ((TairManager) SpringContextUtil.getBean("tairManagerService")).putCache(TairConstants.NS_DATA, String.format("%s_%s", Constants.CONSOLE_AUTH_PREFIX, cache_key), authenticationMap, 0, TairConstants.EXPIRE_ONE_WEEK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return authentication;
    }

    /**
     * Enables subclasses to override the composition of the password, such as by including additional values
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by including additional values
     * and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }
    
    protected String obtainVerification(HttpServletRequest request) {
        return request.getParameter(verificationParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login request.
     *
     * @param usernameParameter the parameter name. Defaults to "j_username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login request..
     *
     * @param passwordParameter the parameter name. Defaults to "j_password".
     */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a POST request, an exception will
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }

    public final String getPasswordParameter() {
        return passwordParameter;
    }
}
