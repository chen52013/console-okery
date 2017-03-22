package com.yxqm.console.web.bussiness.impl;

import com.yxqm.console.system.IRoleDao;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.RolePrivilegeRefBean;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.exception.ConsoleSystemException;
import com.yxqm.console.web.bussiness.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Autowired
    @Qualifier("roleDao")
    protected IRoleDao roleDao;

    public List<RoleBean> queryRoleByUser(UserBean user)
        throws ConsoleSystemException {
        return roleDao.queryRoleByUser(user);
    }

    @Override
    public List<RoleBean> queryRole(RoleBean role)
        throws ConsoleSystemException {
        return roleDao.queryRole(role);
    }

    @Override
    public int queryRoleRows(RoleBean role) throws ConsoleSystemException {
        return roleDao.queryRoleRows(role);
    }

    @Override
    public int addRole(RoleBean role) throws ConsoleSystemException {
        return roleDao.addRole(role);
    }

    @Override
    public int updateRole(RoleBean role) throws ConsoleSystemException {
        return roleDao.updateRole(role);
    }

    @Override
    public int deleteRole(RoleBean roleBean) throws ConsoleSystemException {
        try {
            int affectedRows = 0;
            List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
            String roleIdStr = roleBean.getRoleId();
            String roleCodeStr = roleBean.getRoleCode();

            if (!"".equals(roleIdStr) && (roleIdStr != null)) {
                int offset = roleIdStr.indexOf(",");

                if (offset < 0) { //处理批量操作时只选择了一条数据的情况
                    roleBeanList.add(roleBean);
                    affectedRows = roleDao.deleteRole(roleBeanList);
                } else { //处理批量操作 id,id1,id2 …�?

                    if (!"".equals(roleIdStr) && !"".equals(roleCodeStr) &&
                            (roleIdStr != null) && (roleCodeStr != null)) {
                        String[] roleIdArray = roleIdStr.split(",");
                        String[] roleCodeArray = roleCodeStr.split(",");
                        RoleBean tempRoleBean = null;

                        for (int i = 0; i < roleIdArray.length; i++) {
                            tempRoleBean = new RoleBean();
                            tempRoleBean.setRoleId(roleIdArray[i]);
                            tempRoleBean.setRoleCode(roleCodeArray[i]);

                            roleBeanList.add(tempRoleBean);
                        }
                    }

                    affectedRows = roleDao.deleteRole(roleBeanList);
                }
            }

            return affectedRows;
        } catch (ConsoleSystemException ex) {
            throw new ConsoleSystemException("删除角色信息异常", ex);
        }
    }

    @Override
    public List<RolePrivilegeRefBean> queryPrivilegeByUserRole(UserBean user)
        throws ConsoleSystemException {
        return roleDao.queryPrivilegeByUserRole(user);
    }

    @Override
    public int assignPrivilege(RolePrivilegeRefBean rolePrivilegeRefBean)
        throws ConsoleSystemException {
        try {
            int affectedRows = 0;
            List<RolePrivilegeRefBean> rolePrivilegeRefBeanList = new ArrayList<RolePrivilegeRefBean>();
            String roleCodeStr = rolePrivilegeRefBean.getRole_code();
            String privilegeCodeStr = rolePrivilegeRefBean.getPrivilege_code();

            if (!"".equals(roleCodeStr) && (roleCodeStr != null)) {
                int offset = roleCodeStr.indexOf(",");

                /**
                 * 判断处理只分配一个权限给角色的情况或不分配任何权限给角色的情�?
                 */
                if (offset < 0) {
                    /**
                     * 判断处理不分配任何权限给角色的情�?
                     */
                    if ((!"".equals(roleCodeStr) && (roleCodeStr != null)) &&
                            ("".equals(privilegeCodeStr) ||
                            (privilegeCodeStr == null))) {
                        rolePrivilegeRefBean.setPrivilege_code(null);
                        rolePrivilegeRefBeanList.add(rolePrivilegeRefBean);
                        affectedRows = roleDao.assignPrivilege(rolePrivilegeRefBeanList);

                        /**
                         * 处理只分配一个权限给角色的情�?
                         */
                    } else if (!"".equals(roleCodeStr) &&
                            !"".equals(privilegeCodeStr) &&
                            (roleCodeStr != null) &&
                            (privilegeCodeStr != null)) {
                        rolePrivilegeRefBeanList.add(rolePrivilegeRefBean);
                        affectedRows = roleDao.assignPrivilege(rolePrivilegeRefBeanList);
                    }

                    /**
                     * 处理分配多个权限给角色的情况
                     */
                } else {
                    if (!"".equals(roleCodeStr) &&
                            !"".equals(privilegeCodeStr) &&
                            (roleCodeStr != null) &&
                            (privilegeCodeStr != null)) {
                        String[] roleCodeArray = roleCodeStr.split(",");
                        String[] privilegeCodeArray = privilegeCodeStr.split(
                                ",");
                        RolePrivilegeRefBean tempRolePrivilegeRefBean = null;

                        for (int i = 0; i < privilegeCodeArray.length; i++) {
                            tempRolePrivilegeRefBean = new RolePrivilegeRefBean();
                            tempRolePrivilegeRefBean.setRole_code(roleCodeArray[i]);
                            tempRolePrivilegeRefBean.setPrivilege_code(privilegeCodeArray[i]);

                            rolePrivilegeRefBeanList.add(tempRolePrivilegeRefBean);
                        }
                    }

                    affectedRows = roleDao.assignPrivilege(rolePrivilegeRefBeanList);
                }
            }

            return affectedRows;
        } catch (ConsoleSystemException ex) {
            throw new ConsoleSystemException("分配角色权限异常", ex);
        }
    }

    @Override
    public int roleDataChecked(RoleBean roleBean) throws ConsoleSystemException {
        try {
            return roleDao.roleDataChecked(roleBean);
        } catch (ConsoleSystemException ex) {
            throw new ConsoleSystemException("角色数据�?��异常", ex);
        }
    }
}
