package com.yxqm.console.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/10/2.
 */
public class MethodInvoker extends javax.servlet.http.HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MethodInvoker.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }



    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        this.doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        LOG.info("接收到:{}", JSON.toJSONString(req.getParameterMap()));
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        String dao_name = req.getParameter("dao_name");
        String method_name = req.getParameter("method_name");
        String params = req.getParameter("params");
        String classNameForParams = req.getParameter("paramClass");
        byte[] returnBody = null;



        try {
            if(dao_name == null || method_name == null){
                returnBody = "缺失必填参数".getBytes();
                return;
            }
            Object dao = SpringContextUtil.getBean(dao_name);
            Object param = JSONObject.parseObject(params, Class.forName(classNameForParams));
            if(dao == null || param == null){
                returnBody = "参数错误".getBytes();
                return;
            }
            Method method = dao.getClass().getDeclaredMethod(method_name, Class.forName(classNameForParams));
            Object o = method.invoke(dao, param);
            if(o != null){
                returnBody = JSONObject.toJSONString(o).getBytes();
                LOG.info("返回:{}", JSONObject.toJSONString(o));
            }else{
                returnBody = "执行成功".getBytes();
                LOG.info("返回:执行成功");
            }
        } catch (Exception e) {
            returnBody = String.format("执行错误:%s", e.getMessage()).getBytes();
            LOG.error("调用错误:{}", e.getMessage(), e);
        }finally {
            servletOutputStream.write(returnBody);
            servletOutputStream.flush();
            servletOutputStream.close();

        }
    }
}
