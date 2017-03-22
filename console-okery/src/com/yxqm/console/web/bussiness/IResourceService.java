package com.yxqm.console.web.bussiness;

import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.ResourceBean;
import com.yxqm.console.system.exception.ConsoleSystemException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IResourceService {
    List<ResourceBean> queryResources(HashMap<String, Object> params)
        throws ConsoleSystemException;

    List<PrivilegeResourceRefBean> queryPrivilegeResourcesRef(
        PrivilegeResourceRefBean refBean) throws ConsoleSystemException;

    /**
     * 查询资源总数
     * @param resourceBean
     * @return
     * @throws ConsoleSystemException
     */
    public int queryResourceRows(ResourceBean resourceBean)
        throws ConsoleSystemException;

    /**
     * 查询资源数据
     * @param resourceBean
     * @return
     * @throws ConsoleSystemException
     */
    public List<Map<String, Object>> queryResourceList(
        ResourceBean resourceBean) throws ConsoleSystemException;

    /**
     * 添加资源信息
     * @param resourceBean
     * @return
     * @throws ConsoleSystemException
     */
    public int addResource(ResourceBean resourceBean)
        throws ConsoleSystemException;

    /**
     * 通过资源ID查询
     *
     * @param resource_id
     * @return
     * @throws ConsoleSystemException
     */
    public ResourceBean queryResourceById(int resourceId)
        throws ConsoleSystemException;

    /**
     * 修改资源信息
     *
     * @param resourceBean
     * @return
     * @throws ConsoleSystemException
     */
    public int updateResource(ResourceBean resourceBean)
        throws ConsoleSystemException;

    /**
     * 删除资源信息
     *
     * @param resourceBean
     * @return
     * @throws ConsoleSystemException
     */
    public int deleteResource(ResourceBean resourceBean)
        throws ConsoleSystemException;

    /**
     * 资源数据�?��
     * @param resourceBean
     * @return
     * @throws ConsoleSystemException
     */
    public int queryResourceDataChecked(ResourceBean resourceBean)
        throws ConsoleSystemException;

    public List<HashMap<String, Object>> queryResourceListByPrivilegeCode(
        HashMap<String, Object> params) throws ConsoleSystemException;
}
