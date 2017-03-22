package com.yxqm.console.web.bussiness;

import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.exception.ConsoleSystemException;

import java.util.List;


public interface IPrivilegeService {
    /**
     * 添加权限
     *
     * @param privilegeBean
     * @return
     */
    int add(PrivilegeBean privilegeBean);

    /**
     * 删除权限
     *
     * @param privilegeIds
     * @return
     */
    int del(String[] privilegeIds);

    /**
     * 更新权限
     *
     * @param privilegeBean
     * @return
     */
    int update(PrivilegeBean privilegeBean);

    /**
     * 查询权限
     *
     * @param privilegeBean
     * @return
     */
    List<PrivilegeBean> list(PrivilegeBean privilegeBean);

    /**
     * 查询记录�?
             *
     * @param privilegeBean
     * @return
     */
    int listRows(PrivilegeBean privilegeBean);

    /**
     * 根据角色查询权限
     *
     * @param roleBean
     * @return
     */
    List<PrivilegeBean> queryPrivilegeByRole(RoleBean roleBean)
        throws ConsoleSystemException;

    /**
     * 权限数据�?��
     * @param privilegeBean
     * @return
     * @throws ConsoleSystemException
     */
    public int privilegeDataChecked(PrivilegeBean privilegeBean)
        throws ConsoleSystemException;
}
