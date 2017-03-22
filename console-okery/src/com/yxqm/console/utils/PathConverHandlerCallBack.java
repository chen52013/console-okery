package com.yxqm.console.utils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

public class PathConverHandlerCallBack implements TypeHandlerCallback {

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		String properties = getter.getString();
		if(StringUtils.isEmpty(properties)){
			return properties;
		}
		
        return StringUtils.replace(properties, "\\", "/");
	}

	@Override
	public void setParameter(ParameterSetter arg0, Object arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object valueOf(String arg0) {
		return arg0;
	}

}
