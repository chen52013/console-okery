package com.yxqm.console.web.bussiness;

import com.yxqm.console.system.bean.MenuBean;
import com.yxqm.console.system.exception.ConsoleSystemException;

import java.util.List;
import java.util.Map;


public interface IMenuService {
    /**
     * 菜单查询
     *
     * @param menu
     * @return
     * @throws ConsoleSystemException
     */
    List<MenuBean> queryMenu(MenuBean menu) throws ConsoleSystemException;

    /**
     * 查询菜单总数
     * @param menuBean
     * @return
     * @throws ConsoleSystemException
     */
    public int queryMenuRows(MenuBean menuBean) throws ConsoleSystemException;

    /**
     * 查询菜单数据
     * @param menuBean
     * @return
     * @throws ConsoleSystemException
     */
    public List<Map<String, Object>> queryMenuList(MenuBean menuBean)
        throws ConsoleSystemException;

    /**
     * 查询父菜单菜单数�?
             * @param menuBean
     * @return
     * @throws ConsoleSystemException
     */
    public List<Map<String, Object>> queryParentMenuSelectList(
        MenuBean menuBean) throws ConsoleSystemException;

    /**
     * 添加菜单信息
     * @param menuBean
     * @return
     * @throws ConsoleSystemException
     */
    public int addMenu(MenuBean menuBean) throws ConsoleSystemException;

    /**
     * 通过菜单ID查询
     *
     * @param menuId
     * @return
     * @throws ConsoleSystemException
     */
    public MenuBean queryMenuById(int menuId) throws ConsoleSystemException;

    /**
     * 修改菜单信息
     * @param menuBean
     * @return
     * @throws ConsoleSystemException
     */
    public int updateMenu(MenuBean menuBean) throws ConsoleSystemException;

    /**
     * 删除菜单信息
     * @param menuBean
     * @return
     * @throws ConsoleSystemException
     */
    public int deleteMenu(MenuBean menuBean) throws ConsoleSystemException;
}
