/**  
 * Project Name:console-web  
 * File Name:HttpUtils.java  
 * Package Name:com.yxqm.console.web.utils  
 * Date:2015年9月11日下午3:22:25  
 * Copyright (c) 2015, 吴江平 All Rights Reserved.  
 *  
*/  
  
package com.yxqm.console.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**  
 * ClassName:HttpUtils <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年9月11日 下午3:22:25 <br/>  
 * @author   吴江平  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class HttpUtils {

	/**
     * 从输入流中读取数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
    
    /**
     * 通过HttpURLConnection模拟post表单提交
     * 
     * @param path
     * @param params 例如"name=wujiangping&age=25"
     * @return
     * @throws Exception
     */
    public static byte[] sendPostRequestByForm(String path, String params) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        byte[] bypes = params.getBytes("UTF-8");
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();
        return readInputStream(inStream);
    }
}
  
