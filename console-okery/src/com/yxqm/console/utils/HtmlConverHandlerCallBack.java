package com.yxqm.console.utils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

public class HtmlConverHandlerCallBack implements TypeHandlerCallback {
	private String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		String properties = getter.getString();
		if(StringUtils.isEmpty(properties)){
			return properties;
		}
		
        return htmlspecialchars(properties);
	}

	@Override
	public void setParameter(ParameterSetter arg0, Object arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object valueOf(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

