package com.yxqm.console.web.security;

import com.yxqm.console.cache.TairConstants;
import com.yxqm.console.cache.TairManager;
import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.ResourceBean;
import com.yxqm.console.web.bussiness.IPrivilegeService;
import com.yxqm.console.web.bussiness.IResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class CustomInvocationSecurityMetadataSource
    implements FilterInvocationSecurityMetadataSource {
    @Autowired
    @Qualifier("resource")
    IResourceService resource;
    @Autowired
    @Qualifier("privilegeService")
    IPrivilegeService privilegeService;

    //	@Autowired
    //	@Qualifier("tairManagerService")
    TairManager tairManager;
    Map<String, Collection<ConfigAttribute>> resourcesMap = new HashMap<String, Collection<ConfigAttribute>>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
        throws IllegalArgumentException {
        Map<String, Collection<ConfigAttribute>> resourceMap = resourcesMap;

        //Map<String, Collection<ConfigAttribute>>  resourceMap = tairManager.getValue(TairConstants.NS_AUTH, TairConstants.TAIR_KEY_SECURITY_METADATA_CODE);
        Iterator<String> it = resourceMap.keySet().iterator();

        while (it.hasNext()) {
            String resURL = it.next();
            RequestMatcher pathMatcher = new AntPathRequestMatcher(resURL);

            if (pathMatcher.matches(((FilterInvocation) object).getRequest())) {
                Collection<ConfigAttribute> returnCollection = resourceMap.get(resURL);

                return returnCollection;
            }
        }

        throw new AccessDeniedException("Access is denied");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        // 获取所有资源数据
     	HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("is_enable", "true");
		List<ResourceBean> resouces = resource.queryResources(params);

        // 获取所有资源与权限的关系数据
        List<PrivilegeResourceRefBean> roleResoucesRef = resource.queryPrivilegeResourcesRef(new PrivilegeResourceRefBean());

        PrivilegeBean privilegeBean = new PrivilegeBean();
		privilegeBean.setPageSize(Integer.MAX_VALUE);
		List<PrivilegeBean> privileges = privilegeService.list(privilegeBean);
        Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();

        for (PrivilegeBean privilege : privileges) {
            ConfigAttribute configAttribute = new SecurityConfig(privilege.getPrivilege_code());
            configAttributes.add(configAttribute);
        }

        for (ResourceBean resouceBean : resouces) {
            Collection<ConfigAttribute> configPrivileges = new ArrayList<ConfigAttribute>();
            String resourceCode = resouceBean.getResourceCode();
            String resourceUrl = resouceBean.getResourceUrl();

            for (PrivilegeResourceRefBean privilegeResourceRefBean : roleResoucesRef) {
                String refResouceCode = privilegeResourceRefBean.getResource_code();
                String privilegeCode = privilegeResourceRefBean.getPrivilege_code();

                if (resourceCode.equals(refResouceCode)) {
                    ConfigAttribute configAttribute = new SecurityConfig(privilegeCode);
                    configPrivileges.add(configAttribute);
                }

                if (configAttributes.size() > 0) {
                    resourceMap.put(resourceUrl, configPrivileges);
                }
            }
        }

        resourcesMap.putAll(resourceMap);

        return configAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
