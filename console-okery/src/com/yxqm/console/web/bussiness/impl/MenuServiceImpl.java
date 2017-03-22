package com.yxqm.console.web.bussiness.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yxqm.console.system.IMenuDao;
import com.yxqm.console.system.bean.MenuBean;
import com.yxqm.console.system.exception.ConsoleSystemException;
import com.yxqm.console.web.bussiness.IMenuService;

@Service("menuService")
public class MenuServiceImpl implements IMenuService{
	@Autowired
	@Qualifier("menuDao")
	protected IMenuDao menuDao;
	
	public List<MenuBean> queryMenu(MenuBean menu)
			throws ConsoleSystemException {
		return menuDao.queryMenu(menu);
	}
	
	@Override
	public int queryMenuRows(MenuBean menuBean) throws ConsoleSystemException {
		try {
			return menuDao.queryRows(menuBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询菜单列表总记录异常", ex);
		}
	}
	
	@Override
	public List<Map<String, Object>> queryMenuList(MenuBean menuBean)
			throws ConsoleSystemException {
		try {
			return menuDao.queryList(menuBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询菜单列表记录异常", ex);
		}
	}
	
	@Override
	public List<Map<String, Object>> queryParentMenuSelectList(MenuBean menuBean)
			throws ConsoleSystemException {
		try {
			return menuDao.queryParentMenuSelectList(menuBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("查询菜单列表记录异常", ex);
		}
	}
	
	@Override
	public int addMenu(MenuBean menuBean) throws ConsoleSystemException {
		try {
			String s = UUID.randomUUID().toString();
			String menu_code = s.substring(0, 8) + s.substring(9, 13) 
					+ s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
			menuBean.setMenu_code(menu_code);
			return menuDao.addMenu(menuBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("添加菜单信息异常", ex);
		}
	}
	
	@Override
	public MenuBean queryMenuById(int menuId) throws ConsoleSystemException {
		return menuDao.queryMenuById(menuId);
	}
	
	@Override
	public int updateMenu(MenuBean menuBean) throws ConsoleSystemException {
		try {
			return menuDao.updateMenu(menuBean);
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("修改菜单信息异常", ex);
		}
	}
	
	@Override
	public int deleteMenu(MenuBean menuBean) throws ConsoleSystemException {
		try {
			int affectedRows = 0;
			List<MenuBean> menuBeanList = new ArrayList<MenuBean>();
			String menuIdStr = menuBean.getMenu_id();
			if(!"".equals(menuIdStr) && menuIdStr != null){
				int offset = menuIdStr.indexOf(",");
				if (offset < 0) {//处理批量操作时只选择了一条数据的情况
					
					menuBeanList.add(menuBean);
					affectedRows = menuDao.deleteMenu(menuBeanList);
					
				}else{//处理批量操作 id,id1,id2 ……
					
					if(!"".equals(menuIdStr) && menuIdStr != null){
						
						String[] menuIdArray = menuIdStr.split(",");
						MenuBean tempMenuBean = null;
						for(int i = 0; i<menuIdArray.length; i++){
							tempMenuBean = new MenuBean();
							tempMenuBean.setMenu_id(menuIdArray[i]);
							
							menuBeanList.add(tempMenuBean);
						}
					}
					
					affectedRows = menuDao.deleteMenu(menuBeanList);
				}
			}
			
			return affectedRows;
			
		} catch (ConsoleSystemException ex) {
			throw new ConsoleSystemException("删除菜单信息异常", ex);
		}
	}
    
}
