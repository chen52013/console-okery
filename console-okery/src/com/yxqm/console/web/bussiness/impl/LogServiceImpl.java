/**
 * Project Name:console-business
 * File Name:LogServiceImpl.java
 * Package Name:com.yxqm.console.system.log
 * Date:2016Âπ?Êú?6Êó•‰∏ãÂç?:39:27
 * Copyright (c) 2016, Èõ∑ÊπòÂâ?All Rights Reserved.
 *
 */
package com.yxqm.console.web.bussiness.impl;

import com.yxqm.console.exception.ConsoleBusinessException;
import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.ILogDao;
import com.yxqm.console.system.bean.LogBean;
import com.yxqm.console.web.bussiness.ILogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * ClassName:LogServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016Âπ?Êú?6Êó?‰∏ãÂçà1:39:27 <br/>
 *
 * @author Èõ∑ÊπòÂâ?
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("logServiceImpl")
public class LogServiceImpl implements ILogService {
    @Autowired
    @Qualifier("logDaoImpl")
    ILogDao logDaoImpl;

    @Override
    public void insertLog(LogBean logBean) throws ConsoleBusinessException {
        try {
            logDaoImpl.insertLog(logBean);
        } catch (ConsoleDaoException e) {
            throw new ConsoleBusinessException(e);
        }
    }

    @Override
    public List<LogBean> querySysLog(LogBean logBean)
        throws ConsoleBusinessException {
        try {
            List<LogBean> LogBeans = logDaoImpl.querySysLog(logBean);

            return LogBeans;
        } catch (ConsoleDaoException e) {
            throw new ConsoleBusinessException(e);
        }
    }
}
