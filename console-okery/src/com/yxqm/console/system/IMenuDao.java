package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.bean.MenuBean;

import java.util.List;
import java.util.Map;


public interface IMenuDao extends ISysBaseDao {
    List<MenuBean> queryMenu(MenuBean menu) throws ConsoleDaoException;

    /**
          * 添加菜单信息
          * @param menuBean
          * @return
          * @throws ConsoleDaoException
          */
    public int addMenu(MenuBean menuBean) throws ConsoleDaoException;

    /**
     * 查询父菜单数�?
             *
     * @param menuBean
     * @return
     * @throws ConsoleDaoException
     */
    public List<Map<String, Object>> queryParentMenuSelectList(
        MenuBean menuBean) throws ConsoleDaoException;

    /**
     * 通过菜单ID查询
     *
     * @param menuId
     * @return
     * @throws ConsoleDaoException
     */
    public MenuBean queryMenuById(int menuId) throws ConsoleDaoException;

    /**
     * 修改菜单信息
     * @param menuBean
     * @return
     * @throws ConsoleDaoException
     */
    public int updateMenu(MenuBean menuBean) throws ConsoleDaoException;

    /**
     * 删除菜单信息
     * @param menuBeanList
     * @return
     * @throws ConsoleSystemException
     */
    public int deleteMenu(List<MenuBean> menuBeanList)
        throws ConsoleDaoException;
}
