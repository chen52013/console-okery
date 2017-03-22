package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.RolePrivilegeRefBean;
import com.yxqm.console.system.bean.UserBean;

import java.util.List;


public interface IRoleDao {
    /**
     * 查询某用户角�?
             *
     * @param user
     * @return
     * @throws ConsoleDaoException
     */
    List<RoleBean> queryRoleByUser(UserBean user) throws ConsoleDaoException;

    List<RolePrivilegeRefBean> queryPrivilegeByUserRole(UserBean user)
        throws ConsoleDaoException;

    /**
     * 查询角色记录
     *
     * @param role
     * @return
     * @throws ConsoleDaoException
     */
    List<RoleBean> queryRole(RoleBean role) throws ConsoleDaoException;

    /**
     * 查询角色总记录数
     *
     * @param role
     * @return
     * @throws ConsoleDaoException
     */
    int queryRoleRows(RoleBean role) throws ConsoleDaoException;

    /**
     * 添加角色
     *
     * @param role
     * @return
     * @throws ConsoleDaoException
     */
    int addRole(RoleBean role) throws ConsoleDaoException;

    /**
     * 修改角色
     *
     * @param role
     * @return
     * @throws ConsoleDaoException
     */
    int updateRole(RoleBean role) throws ConsoleDaoException;

    /**
     * 删除角色
     *
     * @param roleBeanList
     * @return
     * @throws ConsoleDaoException
     */
    int deleteRole(List<RoleBean> roleBeanList) throws ConsoleDaoException;

    /**
     * 分配角色权限
     *
     * @param rolePrivilegeRefBeanList
     * @return
     * @throws ConsoleDaoException
     */
    int assignPrivilege(List<RolePrivilegeRefBean> rolePrivilegeRefBeanList)
        throws ConsoleDaoException;

    /**
     * 角色数据�?��
     * @param roleBean
     * @return
     * @throws ConsoleDaoException
     */
    public int roleDataChecked(RoleBean roleBean) throws ConsoleDaoException;
}
