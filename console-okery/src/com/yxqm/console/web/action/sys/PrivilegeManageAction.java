package com.yxqm.console.web.action.sys;

import com.alibaba.fastjson.JSONArray;
import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.ResourceBean;
import com.yxqm.console.web.bussiness.IPrivilegeService;
import com.yxqm.console.web.bussiness.IResourceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrivilegeManageAction {
	@Autowired
	@Qualifier("privilegeService")
	IPrivilegeService privilegeService;
	
	@Autowired
	@Qualifier("resource")
	IResourceService resourceService;
	
//	@Autowired
//	@Qualifier("customSecurityMetadataSource")
//	CustomInvocationSecurityMetadataSource customSecurityMetadataSource;
	
	@RequestMapping(value = "toPrivilegeList.do", method = RequestMethod.GET)
	public String toRolePage(Model model) {
		return "system/privilege/privilegeList";
	}
	
	@RequestMapping(value = "queryPrivilegeList.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String,Object> queryPrivilegeList(@ModelAttribute PrivilegeBean privilegeBean,HttpServletRequest request){
		int totalRows = privilegeService.listRows(privilegeBean);
		List<PrivilegeBean> lst = privilegeService.list(privilegeBean);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", privilegeBean.getCurPage());
		resMap.put("data", lst);
		return resMap;
	}
	
	@RequestMapping(value = "toAddPrivilege.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String toAddPrivilege(Model model) {
		return "system/privilege/addPrivilege";
	}
	
	@RequestMapping(value = "addPrivilege.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String,Object> addPrivilege(@ModelAttribute PrivilegeBean privilegeBean){
		Map<String, Object> resMap = new HashMap<String, Object>();
		int res = privilegeService.add(privilegeBean);
		if(res == 0 ){
			resMap.put("res_code", "0");
			resMap.put("res_msg", "添加权限失败");
			
		}else{
//			customSecurityMetadataSource.getAllConfigAttributes();//添加权限时需要刷新TairManager缓存
			resMap.put("res_code", "1");
			resMap.put("res_msg", "添加权限成功");
		}
		
		return resMap;
	}
	
	@RequestMapping(value = "queryResourceContent.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String,Object> queryResourceContent(HttpServletRequest request){
		
		List<Map<String,Object>>  resources = resourceService.queryResourceList(new ResourceBean());
		
		String privilege_code = request.getParameter("privilege_code");
		List<Map<String,Object>> resourcesData = null;
		if(StringUtils.isNotEmpty(privilege_code)){
			PrivilegeResourceRefBean privilegeResourceRefBean = new PrivilegeResourceRefBean();
			privilegeResourceRefBean.setPrivilege_code(privilege_code);
		
			List<PrivilegeResourceRefBean> privilegeResourceRefBeans = resourceService.queryPrivilegeResourcesRef(privilegeResourceRefBean);
			resourcesData = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> resource:resources){
				String resource_code = (String)resource.get("resource_code");
				boolean isTrue = false;
				for(PrivilegeResourceRefBean privilegeResource :privilegeResourceRefBeans ){
					String resource_code_tmp = privilegeResource.getResource_code();
					if(resource_code.equals(resource_code_tmp)){
						isTrue = true;
					}
				}
				if(!isTrue){
					resourcesData.add(resource);
				}
			}
		}else{
			resourcesData = resources;
		}
		
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("res_code", "1");
		resMap.put("res_data",  resourcesData);
		return resMap;
	}

	@RequestMapping(value = "getResourceTree.do", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getResourceTree(HttpServletRequest request, HttpServletResponse response){
		try{
			HashMap<String,Object> params = new HashMap<>();
			if(StringUtils.isNotBlank(request.getParameter("privilege_code"))){
				params.put("privilege_code", request.getParameter("privilege_code"));
			}
			List<HashMap<String,Object>>  resources =resourceService.queryResourceListByPrivilegeCode(params);
			for(HashMap<String,Object> re : resources){
				re.put("id", re.get("resource_id").toString());
				re.put("name", re.get("resource_name").toString());
			}
			return JSONArray.toJSONString(resources);
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping(value = "toEditPrivilege.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String toEditPrivilege(Model model) {
		return "system/privilege/privilegeEdit";
	}
	
	@RequestMapping(value = "deletePrivilege.do", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody Map<String,Object> deletePrivilege(@RequestParam("privilege_codes") String privilege_code){
		String[] privilegeCodes  = privilege_code.split(",");
    	Map<String, Object> resMap = new HashMap<String, Object>();
		int res = privilegeService.del(privilegeCodes);
		if(res == 0 ){
			resMap.put("res_code", "0");
			resMap.put("res_msg", "删除权限失败");
			
		}else{
//			customSecurityMetadataSource.getAllConfigAttributes();//删除权限时需要刷新TairManager缓存
			resMap.put("res_code", "1");
			resMap.put("res_msg", "删除权限成功");
		}
		
		return resMap;
    }
	
	@RequestMapping(value = "editPrivilege.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String,Object> editPrivilege(@ModelAttribute PrivilegeBean privilegeBean){
		Map<String, Object> resMap = new HashMap<String, Object>();
		int res = privilegeService.update(privilegeBean);
		if(res == 0 ){
			resMap.put("res_code", "0");
			resMap.put("res_msg", "更新权限失败");
			
		}else{
//			customSecurityMetadataSource.getAllConfigAttributes();//更新权限时需要刷新TairManager缓存
			resMap.put("res_code", "1");
			resMap.put("res_msg", "更新权限成功");
		}
		
		return resMap;
	}
    
	/**
	 * 权限数据检查
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "privilegeDataChecked.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Map<String, Object> privilegeDataChecked(
			@ModelAttribute PrivilegeBean privilegeBean,
			HttpServletRequest request, HttpServletResponse response) {
		String privilege_code = request.getParameter("privilege_code");
		Boolean result = true;
		String resultMsg = "";
		int privilegeCodeExistCount = 0;
		if(!StringUtils.isEmpty(privilege_code)){
			privilegeBean.setPrivilege_code(privilege_code);
			privilegeCodeExistCount = privilegeService.privilegeDataChecked(privilegeBean);
			if(privilegeCodeExistCount != 0){
				result = false;
				resultMsg = "该权限编码已存在！";
			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", result);
		resMap.put("msg", resultMsg);
		return resMap;
	}
	
	/**
	 * 
	 * methodName: refreshPrivilegeCache
	 *
	 * 刷新权限缓存.<br/>    
	 *   
	 * @return
	 */
	@RequestMapping(value = "refreshPrivilegeCache.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String,Object> refreshPrivilegeCache(){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
//		customSecurityMetadataSource.getAllConfigAttributes();//刷新TairManager缓存
		resMap.put("res_code", "1");
		resMap.put("res_msg", "刷新成功");
		return resMap;
	}
}
