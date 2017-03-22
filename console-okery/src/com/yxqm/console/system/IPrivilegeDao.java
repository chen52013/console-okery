package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.RoleBean;

import java.util.List;


public interface IPrivilegeDao {
    /**
     * 添加权限
     *
     * @param bean
     * @return
     * @throws ConsoleDaoException
     */
    int add(PrivilegeBean bean) throws ConsoleDaoException;

    /**
     * 删除权限
     *
     * @param privilegeId
     * @return
     * @throws ConsoleDaoException
     */
    int del(String[] privilegeId) throws ConsoleDaoException;

    /**
     * 更新权限
     *
     * @param bean
     * @return
     * @throws ConsoleDaoException
     */
    int update(PrivilegeBean bean) throws ConsoleDaoException;

    /**
     * 查询权限
     *
     * @param bean
     * @return
     * @throws ConsoleDaoException
     */
    List<PrivilegeBean> list(PrivilegeBean bean) throws ConsoleDaoException;

    /**
     * 查询记录�?
             *
     * @param bean
     * @return
     * @throws ConsoleDaoException
     */
    int listRows(PrivilegeBean bean) throws ConsoleDaoException;

    /**
     * 根据角色查询权限
     *
     * @param roleBean
     * @return
     */
    List<PrivilegeBean> queryPrivilegeByRole(RoleBean roleBean)
        throws ConsoleDaoException;

    /**
     * 权限数据�?��
     * @param privilegeBean
     * @return
     * @throws ConsoleDaoException
     */
    public int privilegeDataChecked(PrivilegeBean privilegeBean)
        throws ConsoleDaoException;
}
