package com.yxqm.console.system.menu;

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
import com.yxqm.console.system.IMenuDao;
import com.yxqm.console.system.bean.MenuBean;

@Service("menuDao")
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class MenuDaoImpl extends AbsSysDao implements IMenuDao {

	public List<MenuBean> queryMenu(MenuBean menu) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("menu.queryMenu", menu);
	}

	@Override
	public int queryRows(Object object) throws ConsoleDaoException {
		Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("menu.queryMenuRows",
				(MenuBean) object);
		if (null != totalNum) {
			return totalNum.intValue();
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> queryList(Object object) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("menu.queryMenuList", (MenuBean) object);
	}

	@Override
	public int addMenu(final MenuBean menuBean) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object menu_id = console_sqlMapClientTemplate.insert("menu.addMenu", menuBean);
				if (null != menu_id) {
					return Integer.parseInt(menu_id.toString());
				}
				status.setRollbackOnly();
				return 0;
			}
		});
		return resultCode;
	}

	@Override
	public List<Map<String, Object>> queryParentMenuSelectList(MenuBean menuBean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("menu.queryParentMenuSelectList", menuBean);
	}

	@Override
	public MenuBean queryMenuById(int menuId) throws ConsoleDaoException {
		return (MenuBean) console_sqlMapClientTemplate.queryForObject("menu.queryMenuById", String.valueOf(menuId));
	}

	@Override
	public int updateMenu(final MenuBean menuBean) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {

				Object menu_id = console_sqlMapClientTemplate.update("menu.updateMenu", menuBean);
				if (null != menu_id) {
					return Integer.parseInt(menu_id.toString());
				}
				status.setRollbackOnly();
				return 0;
			}
		});
		return resultCode;
	}

	@Override
	public int deleteMenu(final List<MenuBean> menuBeanList) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {

					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

						executor.startBatch();
						for (MenuBean menuBean : menuBeanList) {
							executor.delete("menu.deleteMenu", menuBean);

							executor.delete("menu.deleteSubMenu", menuBean);
						}
						return executor.executeBatch();
					}

				});

				return Integer.valueOf(obj.toString());
			}
		});
		return resultCode.intValue();
	}
}
