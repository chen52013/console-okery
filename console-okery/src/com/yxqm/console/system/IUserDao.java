package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.bean.UserRoleRefBean;

import java.util.List;
import java.util.Map;


public interface IUserDao extends ISysBaseDao {
    UserBean queryUser(UserBean user) throws ConsoleDaoException;

    /**
         * 添加用户信息
         * @param userBean
         * @return
         * @throws ConsoleDaoException
         */
    public int addUser(UserBean userBean) throws ConsoleDaoException;

    /**
     * 通过用户ID查询
     *
     * @param userId
     * @return
     * @throws ConsoleSystemException
     */
    public UserBean queryUserById(int userId) throws ConsoleDaoException;

    /**
     * 修改用户信息
     *
     * @param userBean
     * @return
     * @throws ConsoleForumException
     */
    public int updateUser(UserBean userBean) throws ConsoleDaoException;

    /**
     * 删除用户
     * @param userBeanList
     * @return
     * @throws ConsoleDaoException
     */
    public int deleteUser(List<UserBean> userBeanList)
        throws ConsoleDaoException;

    /**
     * 用户数据�?��
     *
     * @param userBean
     * @return
     * @throws ConsoleDaoException
     */
    public int queryUserDataChecked(UserBean userBean)
        throws ConsoleDaoException;

    /**
     * 添加用户角色
     * @param userRoleRefBeanList
     * @return
     * @throws ConsoleDaoException
     */
    public int addUserRole(List<UserRoleRefBean> userRoleRefBeanList)
        throws ConsoleDaoException;

    public List<Map<String, Object>> queryInsertUserProfileHistory(
        Map<String, Object> params) throws ConsoleDaoException;

    public List<Map<String, Object>> queryUserProfileHistoryList(
        Map<String, Object> params) throws ConsoleDaoException;

    public int updateUserProfile(Map<String, Object> params)
        throws ConsoleDaoException;

    public List<Map<String, Object>> queryUserIdList(Map<String, Object> params)
        throws ConsoleDaoException;
}
