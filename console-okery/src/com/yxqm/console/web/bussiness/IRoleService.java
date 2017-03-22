package com.yxqm.console.web.bussiness;

import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.RolePrivilegeRefBean;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.exception.ConsoleSystemException;

import java.util.List;


public interface IRoleService {
    /**
     * 通过用户查询角色
     *
     * @param user
     * @return
     * @throws ConsoleSystemException
     */
    List<RoleBean> queryRoleByUser(UserBean user) throws ConsoleSystemException;

    /**
     * 通过用户角色查询权限
     * @param user
     * @return
     * @throws ConsoleSystemException
     */
    List<RolePrivilegeRefBean> queryPrivilegeByUserRole(UserBean user)
        throws ConsoleSystemException;

    /**
     * 查询角色记录总数
     * @param role
     * @return
     * @throws ConsoleSystemException
     */
    int queryRoleRows(RoleBean role) throws ConsoleSystemException;

    /**
     * 查询角色记录
     * @param role
     * @return
     * @throws ConsoleSystemException
     */
    List<RoleBean> queryRole(RoleBean role) throws ConsoleSystemException;

    /**
     * 添加角色
     *
     * @param role
     * @return
     * @throws ConsoleSystemException
     */
    int addRole(RoleBean role) throws ConsoleSystemException;

    /**
     * 更新角色
     *
     * @param role
     * @return
     * @throws ConsoleSystemException
     */
    int updateRole(RoleBean role) throws ConsoleSystemException;

    /**
     * 删除角色
     *
     * @param roleBean
     * @return
     * @throws ConsoleSystemException
     */
    int deleteRole(RoleBean roleBean) throws ConsoleSystemException;

    /**
     * 分配角色权限
     *
     * @param rolePrivilegeRefBean
     * @return
     * @throws ConsoleSystemException
     */
    int assignPrivilege(RolePrivilegeRefBean rolePrivilegeRefBean)
        throws ConsoleSystemException;

    /**
     * 角色数据�?��
     * @param roleBean
     * @return
     * @throws ConsoleSystemException
     */
    public int roleDataChecked(RoleBean roleBean) throws ConsoleSystemException;
}
