package com.yxqm.console.system.privilege;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.system.IPrivilegeDao;
import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.RoleBean;

@Service("privilegeDao")
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class PrivilegeDaoImpl extends AbsSysDao implements IPrivilegeDao {

	@Override
	public int add(final PrivilegeBean bean) throws ConsoleDaoException {

		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object addPrivilegeRes = console_sqlMapClientTemplate.insert("user.addPrivilege", bean);

				if (null == addPrivilegeRes) {
					status.setRollbackOnly();
					return 0;
				}

				if (Integer.parseInt(addPrivilegeRes.toString()) == 0) {
					status.setRollbackOnly();
					return 0;
				}

				final String[] resourceCodes = bean.getPrivilege_content();
				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

						executor.startBatch();
						for (String resourceCode : resourceCodes) {
							Map<String, String> resourceRef = new HashMap<String, String>();
							resourceRef.put("resource_code", resourceCode);
							resourceRef.put("privilege_code", bean.getPrivilege_code());
							executor.insert("user.addPrivilegeResource", resourceRef);
						}
						return executor.executeBatch();
					}
				});
				int addPrivilegeResourceRes = Integer.valueOf(obj.toString());

				if (addPrivilegeResourceRes == 0) {
					status.setRollbackOnly();
					return 0;
				}

				return 1;
			}
		});
		return resultCode.intValue();
	}

	@Override
	public int del(final String[] privilegeCodes) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				// 删除权限角色关联数据
				console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

						executor.startBatch();
						for (String privilege_code : privilegeCodes) {
							executor.delete("user.delPrivilegeRoleRef", privilege_code);
						}
						return executor.executeBatch();
					}
				});

				// 删除权限资源关联数据
				Object delPrivilegeResourceRefRes = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
						executor.startBatch();
						for (String privilege_code : privilegeCodes) {
							executor.delete("user.delPrivilegeResourceRef", privilege_code);
						}
						return executor.executeBatch();
					}
				});
				int delPrivilegeResourceRefResV = Integer.valueOf(delPrivilegeResourceRefRes.toString());

				if (delPrivilegeResourceRefResV == 0) {
					status.setRollbackOnly();
					return 0;
				}

				// 删除权限数据
				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
						executor.startBatch();
						for (String privilege_code : privilegeCodes) {
							PrivilegeBean privilegeBean = new PrivilegeBean();
							privilegeBean.setPrivilege_code(privilege_code);
							executor.delete("user.delPrivilege", privilegeBean);
						}
						return executor.executeBatch();
					}
				});
				int objV = Integer.valueOf(obj.toString());

				if (objV == 0) {
					status.setRollbackOnly();
					return 0;
				}

				return 1;
			}
		});
		return resultCode.intValue();
	}

	@Override
	public int update(final PrivilegeBean bean) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				// 删除之前的权限资源关系数据
				@SuppressWarnings("unused")
				int delPrivilegeResourceRefRes = console_sqlMapClientTemplate.delete("user.delPrivilegeResourceRef",
						bean.getPrivilege_code());

				/*
				 * if(delPrivilegeResourceRefRes == 0){
				 * status.setRollbackOnly(); return 0; }
				 */

				// 添加最新的权限资源关系数据
				final String[] privilegeContents = bean.getPrivilege_content();
				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {

					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

						executor.startBatch();
						for (String privilege_content : privilegeContents) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("resource_code", privilege_content);
							map.put("privilege_code", bean.getPrivilege_code());

							executor.insert("user.addPrivilegeResource", map);
						}
						return executor.executeBatch();
					}
				});
				int objV = Integer.valueOf(obj.toString());

				if (objV == 0) {
					status.setRollbackOnly();
					return 0;
				}
				// 更新权限数据
				int updatePrivilegeRes = console_sqlMapClientTemplate.update("user.updatePrivilege", bean);

				if (updatePrivilegeRes == 0) {
					status.setRollbackOnly();
					return 0;
				}
				return 1;
			}

		});
		return resultCode.intValue();
	}

	@Override
	public List<PrivilegeBean> list(PrivilegeBean bean) throws ConsoleDaoException {
		List<PrivilegeBean> privilegeBeanLts = console_sqlMapClientTemplate.queryForList("user.listPrivilege", bean);

		for (PrivilegeBean privilegeBean : privilegeBeanLts) {
			PrivilegeResourceRefBean privilegeResourceRefBean = new PrivilegeResourceRefBean();
			privilegeResourceRefBean.setPrivilege_code(privilegeBean.getPrivilege_code());
			List<PrivilegeResourceRefBean> privilegeResourceRefBeans = console_sqlMapClientTemplate
					.queryForList("user.queryPrivilegeResourcesRef", privilegeResourceRefBean);
			privilegeBean.setPrivilegeResourceRefBeans(privilegeResourceRefBeans);
		}

		return privilegeBeanLts;
	}

	@Override
	public int listRows(PrivilegeBean bean) throws ConsoleDaoException {
		// TODO Auto-generated method stub
		return (Integer) console_sqlMapClientTemplate.queryForObject("user.listRows", bean);
	}

	@Override
	public List<PrivilegeBean> queryPrivilegeByRole(RoleBean roleBean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("user.queryPrivilegeByRole", roleBean);
	}

	@Override
	public int privilegeDataChecked(PrivilegeBean privilegeBean) throws ConsoleDaoException {
		Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("user.privilegeDataChecked",
				privilegeBean);
		if (null != totalNum) {
			return totalNum.intValue();
		}
		return 0;
	}

}
