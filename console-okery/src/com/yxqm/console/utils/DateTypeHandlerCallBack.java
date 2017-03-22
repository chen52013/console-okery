package com.yxqm.console.utils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeHandlerCallBack implements TypeHandlerCallback {

	public static final String DATE_FORMAT_STANDARD="yyyy-MM-dd HH:mm:ss";
	
	static ThreadLocal<SimpleDateFormat> sdfLocal = new ThreadLocal<SimpleDateFormat>(){
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT_STANDARD); 
		};
	};
	
	public Object getResult(ResultGetter getter) throws SQLException {
		String properties = getter.getString();
		if(StringUtils.isEmpty(properties)){
			return properties;
		}
		Timestamp date = Timestamp.valueOf(properties);
        return sdfLocal.get().format(new Date(date.getTime()));
	}

	public void setParameter(ParameterSetter arg0, Object arg1)
			throws SQLException {
	}

	public Object valueOf(String arg0) {
		return arg0;
	}

}
