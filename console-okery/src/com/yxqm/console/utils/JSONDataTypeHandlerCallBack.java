/**  
 * Project Name:console-dao  
 * File Name:JSONDataTypeHandlerCallBack.java  
 * Package Name:com.yxqm.console.dao.util  
 * Date:2015年8月16日下午2:36:06  
 * Copyright (c) 2015, 雷湘剑 All Rights Reserved.  
 *  
*/  
  
package com.yxqm.console.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

/**  
 * ClassName:JSONDataTypeHandlerCallBack <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年8月16日 下午2:36:06 <br/>  
 * @author  雷湘剑 
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class JSONDataTypeHandlerCallBack implements TypeHandlerCallback {
	public Object getResult(ResultGetter getter) throws SQLException {
		String properties = getter.getString();
		if(StringUtils.isEmpty(properties)){
			return properties;
		}
		JSONObject jsonObj = JSON.parseObject(properties);
		
        return jsonObj;
	}

	public void setParameter(ParameterSetter arg0, Object arg1)
			throws SQLException {
	}

	public Object valueOf(String arg0) {
		return arg0;
	}

}