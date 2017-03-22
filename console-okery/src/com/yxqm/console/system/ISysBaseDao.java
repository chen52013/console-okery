package com.yxqm.console.system;

import com.yxqm.console.exception.ConsoleDaoException;

import java.util.List;
import java.util.Map;


public interface ISysBaseDao {
    /**
     * ��ѯ��¼����
     *
     * @param object
     * @return
     * @throws ConsoleDaoException
     */
    public int queryRows(Object object) throws ConsoleDaoException;

    /**
     * ��ѯ����
     *
     * @param object
     * @return
     * @throws ConsoleDaoException
     */
    public List<Map<String, Object>> queryList(Object object)
        throws ConsoleDaoException;
}
