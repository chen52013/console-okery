package com.yxqm.console.system.bean;

import java.util.List;

public class MenuBean extends SysBaseBean {
	private static final long serialVersionUID = 3123561584245317979L;
	private List<MenuBean> subMenu;
	private String menu_id;
	private String menu_name;
	private String menu_url;
	private String menu_index;
	private String parent_id;
	private String parent_name;
	private String home_page_id;
	private String menu_level;
	private String menu_code;
	private String resource_url;
	private String class_name;
	private String module_name;

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getResource_url() {
		return this.resource_url;
	}

	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}

	public List<MenuBean> getSubMenu() {
		return this.subMenu;
	}

	public void setSubMenu(List<MenuBean> subMenu) {
		this.subMenu = subMenu;
	}

	public String getMenu_id() {
		return this.menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_name() {
		return this.menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_url() {
		return this.menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getMenu_index() {
		return this.menu_index;
	}

	public void setMenu_index(String menu_index) {
		this.menu_index = menu_index;
	}

	public String getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getHome_page_id() {
		return this.home_page_id;
	}

	public void setHome_page_id(String home_page_id) {
		this.home_page_id = home_page_id;
	}

	public String getMenu_level() {
		return this.menu_level;
	}

	public void setMenu_level(String menu_level) {
		this.menu_level = menu_level;
	}

	public String getMenu_code() {
		return this.menu_code;
	}

	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}

	public String getParent_name() {
		return this.parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getClass_name() {
		return this.class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
}