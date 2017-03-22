package com.yxqm.console.web.bussiness.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yxqm.console.system.IResourceDao;
import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.ResourceBean;
import com.yxqm.console.system.exception.ConsoleSystemException;
import com.yxqm.console.web.bussiness.IResourceService;

@Service("resource")
public class ResourceServiceImpl implements IResourceService {
	@Autowired
	@Qualifier("resourceDao")
	protected IResourceDao resourceDao;
	
	@Override
	public List<ResourceBean> queryResources(HashMap<String,Object> params)
			throws ConsoleSystemException {
		return resourceDao.queryResources(params);
	}
	@Override
	public List<PrivilegeResourceRefBean> queryPrivilegeResourcesRef(
			PrivilegeResourceRefBean refBean) throws ConsoleSystemException {
		return resourceDao.queryPrivilegeResourcesRef(refBean);
	}
	
	@Override
	public int queryResourceRows(ResourceBean resourceBean)
			throws ConsoleSystemException {
		try {
			return resourceDao.queryRows(resourceBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询资源列表总记录异常", ex);
		}
	}
	
	@Override
	public List<Map<String, Object>> queryResourceList(ResourceBean resourceBean)
			throws ConsoleSystemException {
		try {
			return resourceDao.queryList(resourceBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询资源列表记录异常", ex);
		}
	}

	@Override
	public int addResource(ResourceBean resourceBean)
			throws ConsoleSystemException {
		try {
			String s = UUID.randomUUID().toString();
			String resource_code = "resource_" + s.substring(0, 8) + s.substring(9, 13) 
					+ s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
			resourceBean.setResource_code(resource_code);
			String resource_id = resourceBean.getResource_id();
			String father_resource = resourceBean.getFather_resource();
			String son_resource = resourceBean.getSon_resource();
			String sun_resource = resourceBean.getSun_resource();
			String parent_resource = "0";
			if(StringUtils.isNotBlank(father_resource)){
				if(StringUtils.isNotBlank(son_resource) && !son_resource.equals(resource_id)){
					if(StringUtils.isNotBlank(sun_resource) && !sun_resource.equals(resource_id)){
						parent_resource = sun_resource;
					}else{
						parent_resource = son_resource;
					}
				}else{
					parent_resource = father_resource;
				}
			}
			resourceBean.setParent_resource(parent_resource);
			return resourceDao.addResource(resourceBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("添加资源信息异常", ex);
		}
	}
	
	@Override
	public ResourceBean queryResourceById(int resourceId)
			throws ConsoleSystemException {
		return resourceDao.queryResourceById(resourceId);
	}
	
	@Override
	public int updateResource(ResourceBean resourceBean)
			throws ConsoleSystemException {
		try {
			String resource_id = resourceBean.getResource_id();
			String father_resource = resourceBean.getFather_resource();
			String son_resource = resourceBean.getSon_resource();
			String sun_resource = resourceBean.getSun_resource();
			String parent_resource = "0";
			if(StringUtils.isNotBlank(father_resource)){
				if(StringUtils.isNotBlank(son_resource) && !son_resource.equals(resource_id)){
					if(StringUtils.isNotBlank(sun_resource) && !sun_resource.equals(resource_id)){
						parent_resource = sun_resource;
					}else{
						parent_resource = son_resource;
					}
				}else{
					parent_resource = father_resource;
				}
			}
			resourceBean.setParent_resource(parent_resource);
			return resourceDao.updateResource(resourceBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("修改资源信息异常", ex);
		}
	}
	
	@Override
	public int deleteResource(ResourceBean resourceBean) 
			throws ConsoleSystemException {
		try {
			int affectedRows = 0;
			List<ResourceBean> resourceBeanList = new ArrayList<ResourceBean>();
			String resourceIdStr = resourceBean.getResource_id();
			String resourceCodeStr = resourceBean.getResource_code();
			if(!"".equals(resourceIdStr) && resourceIdStr != null){
				
				int offset = resourceIdStr.indexOf(",");
				if (offset < 0) {//处理批量操作时只选择了一条数据的情况
					
					resourceBeanList.add(resourceBean);
					affectedRows = resourceDao.deleteResource(resourceBeanList);
					
				}else{//处理批量操作 id,id1,id2 ……
					
					if(!"".equals(resourceIdStr) && !"".equals(resourceCodeStr) 
							&& resourceIdStr != null && resourceCodeStr != null){
						
						String[] resourceIdArray = resourceIdStr.split(",");
						String[] resourceCodeArray = resourceCodeStr.split(",");
						ResourceBean tempResourceBean = null;
						for(int i = 0; i<resourceIdArray.length; i++){
							tempResourceBean = new ResourceBean();
							tempResourceBean.setResource_id(resourceIdArray[i]);
							tempResourceBean.setResource_code(resourceCodeArray[i]);
							
							resourceBeanList.add(tempResourceBean);
						}
					}
					
					affectedRows = resourceDao.deleteResource(resourceBeanList);
				}
			}
			
			return affectedRows;
			
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("删除资源信息异常", ex);
		}
	}
	
	@Override
	public int queryResourceDataChecked(ResourceBean resourceBean)
			throws ConsoleSystemException {
		try {
			return resourceDao.queryResourceDataChecked(resourceBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("资源数据检查异常", ex);
		}
	}
	
	@Override
	public List<HashMap<String, Object>> queryResourceListByPrivilegeCode(HashMap<String, Object> params) throws ConsoleSystemException {
		try {
			return resourceDao.queryResourceListByPrivilegeCode(params);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("根据角色查询资源关联数据时出错:"+ JSONObject.toJSONString(params), ex);
		}
	}
}
