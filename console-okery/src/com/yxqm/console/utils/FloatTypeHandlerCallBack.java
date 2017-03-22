package com.yxqm.console.utils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

/**
 * 
 * ClassName: FloatTypeHandlerCallBack <br/>  
 * Function: 浮点数转换器. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2015年9月16日 下午12:03:10 <br/>  
 *  
 * @author 雷湘剑  
 * @version   
 * @since JDK 1.7
 */
public class FloatTypeHandlerCallBack implements TypeHandlerCallback {
	
	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		Double properties = getter.getDouble();
		if(null == properties){
			return properties;
		}
		String oddsV  =String.format("%.2f",properties);
		return oddsV;
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
