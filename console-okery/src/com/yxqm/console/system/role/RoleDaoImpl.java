package com.yxqm.console.system.role;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.system.IRoleDao;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.bean.RolePrivilegeRefBean;
import com.yxqm.console.system.bean.UserBean;

@Service("roleDao")
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class RoleDaoImpl extends AbsSysDao implements IRoleDao {

	@Override
	public List<RoleBean> queryRoleByUser(UserBean user) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryRoleByUser", user);
	}

	@Override
	public List<RoleBean> queryRole(RoleBean role) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryRole", role);
	}

	@Override
	public int queryRoleRows(RoleBean role) throws ConsoleDaoException {
		return (Integer) console_sqlMapClientTemplate.queryForObject("user.queryRoleRows", role);
	}

	@Override
	public int addRole(RoleBean role) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.update("user.addRole", role);
	}

	@Override
	public int updateRole(RoleBean role) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.update("user.updateRole", role);
	}

	@Override
	public int deleteRole(final List<RoleBean> roleBeanList) throws ConsoleDaoException {

		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {

					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

						executor.startBatch();
						for (RoleBean roleBean : roleBeanList) {
							executor.delete("user.deleteRole", roleBean);

							executor.delete("user.deleteRolePrivilegeRef", roleBean);
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
	public List<RolePrivilegeRefBean> queryPrivilegeByUserRole(UserBean user) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryPrivilegeByUserRole", user);
	}

	@Override
	public int assignPrivilege(final List<RolePrivilegeRefBean> rolePrivilegeRefBeanList) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {

				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {

					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
						RolePrivilegeRefBean tempRolePrivilegeRefBean = rolePrivilegeRefBeanList.get(0);

						@SuppressWarnings("unused")
						Object affectedRows = console_sqlMapClientTemplate.delete("user.deleteRolePrivilege",
								tempRolePrivilegeRefBean);

						executor.startBatch();

						if (!"".equals(tempRolePrivilegeRefBean.getPrivilege_code())
								&& tempRolePrivilegeRefBean.getPrivilege_code() != null) {

							for (RolePrivilegeRefBean rolePrivilegeRefBean : rolePrivilegeRefBeanList) {
								executor.insert("user.assignPrivilege", rolePrivilegeRefBean);
							}
						} else {
							/**
							 * 处理不分配任何权限给角色的情况
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
	public int roleDataChecked(RoleBean roleBean) throws ConsoleDaoException {
		Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("user.roleDataChecked", roleBean);
		if (null != totalNum) {
			return totalNum.intValue();
		}
		return 0;
	}
}
