package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.ResourceBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IResourceDao extends ISysBaseDao {
    List<ResourceBean> queryResources(HashMap<String, Object> params)
        throws ConsoleDaoException;

    List<PrivilegeResourceRefBean> queryPrivilegeResourcesRef(
        PrivilegeResourceRefBean roleResourceRefBean)
        throws ConsoleDaoException;

    /**
     * 添加资源信息
     * @param resourceBean
     * @return
     * @throws ConsoleDaoException
     */
    public int addResource(ResourceBean resourceBean)
        throws ConsoleDaoException;

    /**
     * 通过资源ID查询
     *
     * @param resourceId
     * @return
     * @throws ConsoleDaoException
     */
    public ResourceBean queryResourceById(int resourceId)
        throws ConsoleDaoException;

    /**
     * 修改资源信息
     *
     * @param resourceBean
     * @return
     * @throws ConsoleDaoException
     */
    public int updateResource(ResourceBean resourceBean)
        throws ConsoleDaoException;

    /**
     * 删除资源信息
     *
     * @param resourceBeanList
     * @return
     * @throws ConsoleDaoException
     */
    public int deleteResource(List<ResourceBean> resourceBeanList)
        throws ConsoleDaoException;

    /**
     * 资源数据�?��
     *
     * @param resourceBean
     * @return
     * @throws ConsoleDaoException
     */
    public int queryResourceDataChecked(ResourceBean resourceBean)
        throws ConsoleDaoException;

    public List<HashMap<String, Object>> queryResourceListByPrivilegeCode(
        HashMap<String, Object> params) throws ConsoleDaoException;

    List<Map<String, Object>> queryResourceUrlList(Map<String, Object> params)
        throws ConsoleDaoException;

    /**
     * 把该资源也加到该资源的父资源对应的权限下
     */
    int insertResource(Map<String, Object> params) throws ConsoleDaoException;
}
