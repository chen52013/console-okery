package com.yxqm.console.system.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.system.IUserDao;
import com.yxqm.console.system.bean.UserBean;
import com.yxqm.console.system.bean.UserRoleRefBean;

@Service("userDao")
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class UserDaoImpl extends AbsSysDao implements IUserDao {

	public UserBean queryUser(UserBean user) throws ConsoleDaoException {
		return (UserBean) console_sqlMapClientTemplate.queryForObject("user.queryUser", user);
	}

	@Override
	public int queryRows(Object object) throws ConsoleDaoException {

		Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("user.queryUserRows",
				(UserBean) object);
		if (null != totalNum) {
			return totalNum.intValue();
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> queryList(Object object) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryUserList", (UserBean) object);
	}

	@Override
	public int addUser(final UserBean userBean) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object user_id = console_sqlMapClientTemplate.insert("user.addUser", userBean);
				if (null != user_id) {
					return Integer.parseInt(user_id.toString());
				}
				status.setRollbackOnly();
				return 0;
			}
		});
		return resultCode;
	}

	@Override
	public UserBean queryUserById(int userId) throws ConsoleDaoException {
		return (UserBean) console_sqlMapClientTemplate.queryForObject("user.queryUserById", String.valueOf(userId));
	}

	@Override
	public int updateUser(final UserBean userBean) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {

				Object user_id = console_sqlMapClientTemplate.update("user.updateUser", userBean);
				if (null != user_id) {
					return Integer.parseInt(user_id.toString());
				}
				status.setRollbackOnly();
				return 0;
			}
		});
		return resultCode;
	}

	@Override
	public int deleteUser(final List<UserBean> userBeanList) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {

					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

						executor.startBatch();
						for (UserBean userBean : userBeanList) {
							executor.delete("user.deleteSysUser", userBean);

							executor.delete("user.deleteSysUserRole", userBean);
						}
						return executor.executeBatch();
					}

				});

				return Integer.valueOf(obj.toString());
			}
		});
		return resultCode.intValue();
	}

	@Override
	public int queryUserDataChecked(UserBean userBean) throws ConsoleDaoException {
		Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("user.queryUserDataChecked", userBean);
		if (null != totalNum) {
			return totalNum.intValue();
		}
		return 0;
	}

	@Override
	public int addUserRole(final List<UserRoleRefBean> userRoleRefBeanList) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {

				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {

					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
						UserRoleRefBean tempUserRoleRefBean = userRoleRefBeanList.get(0);

						@SuppressWarnings("unused")
						Object affectedRows = console_sqlMapClientTemplate.delete("user.deleteUserRole",
								tempUserRoleRefBean);

						executor.startBatch();

						if (!"".equals(tempUserRoleRefBean.getRole_code())
								&& tempUserRoleRefBean.getRole_code() != null) {

							for (UserRoleRefBean userRoleRefBean : userRoleRefBeanList) {
								executor.insert("user.addUserRole", userRoleRefBean);
							}
						} else {
							/**
							 * 处理不分配任何角色给用户的情况
							 */
							Integer tempResult = 1;
							return tempResult;
						}
						return executor.executeBatch();
					}

				});

				return Integer.valueOf(obj.toString());
			}
		});
		return resultCode.intValue();
	}

	@Override
	public List<Map<String, Object>> queryInsertUserProfileHistory(Map<String, Object> params)
			throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryInsertUserProfileHistory", params);
	}

	@Override
	public List<Map<String, Object>> queryUserProfileHistoryList(Map<String, Object> params)
			throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryUserProfileHistoryList", params);
	}

	@Override
	public int updateUserProfile(Map<String, Object> params) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.update("user.updateUserProfile", params);
	}

	@Override
	public List<Map<String, Object>> queryUserIdList(Map<String, Object> params) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryUserIdList", params);
	}

}
