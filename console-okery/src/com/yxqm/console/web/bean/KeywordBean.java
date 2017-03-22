package com.yxqm.console.web.bean;

/**
 * Created by Dell on 2017/2/27.
 * 关键字实体类
 */
public class KeywordBean extends SysBaseBean{

	private String id;
	private String type_name;
	private String keyword_name;
	private String title;
	private String url;
	private String crt_time;
	private String mod_time;
	private Integer keyword_status;

	public Integer getKeyword_status() {
		return keyword_status;
	}

	public void setKeyword_status(Integer keyword_status) {
		this.keyword_status = keyword_status;
	}

	public String getCrt_time() {
		return crt_time;
	}

	public void setCrt_time(String crt_time) {
		this.crt_time = crt_time;
	}

	public String getMod_time() {
		return mod_time;
	}

	public void setMod_time(String mod_time) {
		this.mod_time = mod_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getKeyword_name() {
		return keyword_name;
	}

	public void setKeyword_name(String keyword_name) {
		this.keyword_name = keyword_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
