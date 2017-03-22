package com.yxqm.console.web.bussiness;

import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.bean.UserRoleRefBean;
import com.yxqm.console.system.exception.ConsoleSystemException;

import java.util.List;
import java.util.Map;


public interface IUserService {
    UserBean queryUser(UserBean user) throws ConsoleSystemException;

    /**
          * 查询用户总数
          * @param userBean
          * @return
          * @throws ConsoleSystemException
          */
    public int queryUserRows(UserBean userBean) throws ConsoleSystemException;

    /**
     * 查询用户数据
     * @param userBean
     * @return
     * @throws ConsoleSystemException
     */
    public List<Map<String, Object>> queryUserList(UserBean userBean)
        throws ConsoleSystemException;

    /**
     * 添加用户信息
     * @param userBean
     * @return
     * @throws ConsoleSystemException
     */
    public int addUser(UserBean userBean) throws ConsoleSystemException;

    /**
     * 通过用户ID查询
     *
     * @param userId
     * @return
     * @throws ConsoleSystemException
     */
    public UserBean queryUserById(int userId) throws ConsoleSystemException;

    /**
     * 修改用户信息
     *
     * @param userBean
     * @return
     * @throws ConsoleSystemException
     */
    public int updateUser(UserBean userBean) throws ConsoleSystemException;

    /**
     * 删除用户
     *
     * @param userBean
     * @return
     * @throws ConsoleSystemException
     */
    public int deleteUser(UserBean userBean) throws ConsoleSystemException;

    /**
     * 用户数据�?��
     * @param userBean
     * @return
     * @throws ConsoleSystemException
     */
    public int queryUserDataChecked(UserBean userBean)
        throws ConsoleSystemException;

    /**
     * 添加用户角色
     * @param userRoleRefBean
     * @return
     * @throws ConsoleSystemException
     */
    public int addUserRole(UserRoleRefBean userRoleRefBean)
        throws ConsoleSystemException;
}
