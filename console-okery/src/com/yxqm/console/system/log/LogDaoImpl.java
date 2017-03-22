package com.yxqm.console.system.log;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.system.ILogDao;
import com.yxqm.console.system.bean.LogBean;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("logDaoImpl")
public class LogDaoImpl extends AbsSysDao implements ILogDao {
    public void insertLog(LogBean logBean) throws ConsoleDaoException {
        try {
            console_sqlMapClientTemplate.insert("syslog.insertLog", logBean);
        } catch (Exception e) {
            throw new ConsoleDaoException(e);
        }
    }

    public List<LogBean> querySysLog(LogBean logBean)
        throws ConsoleDaoException {
        try {
            return console_sqlMapClientTemplate.queryForList("syslog.querySysLog",
                logBean);
        } catch (Exception e) {
            throw new ConsoleDaoException(e);
        }
    }
}
