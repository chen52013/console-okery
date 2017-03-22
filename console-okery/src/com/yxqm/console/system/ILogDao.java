package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.bean.LogBean;

import java.util.List;


/**
 * ClassName:ILogDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2016�?�?6�?下午1:54:44 <br/>
 * @author  雷湘�?
 * @version
 * @since    JDK 1.7
 * @see
 */
public interface ILogDao {
    /**
     *
     * methodName: insertLog
     *
     * 记录操作日志.<br/>
     *
     * @param logBean
     */
    void insertLog(LogBean logBean) throws ConsoleDaoException;

    /**
     *
     * methodName: querySysLog
     *
     * 查询操作日志.<br/>
     *
     * @param logBean
     * @return
     */
    List<LogBean> querySysLog(LogBean logBean) throws ConsoleDaoException;
}
