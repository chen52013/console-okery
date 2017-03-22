package com.yxqm.console.system.resource;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.system.IResourceDao;
import com.yxqm.console.system.bean.PrivilegeResourceRefBean;
import com.yxqm.console.system.bean.ResourceBean;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import org.springframework.stereotype.Service;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("resourceDao")
@SuppressWarnings({"deprecation",
    "unchecked",
    "rawtypes"
})
public class ResourceDaoImpl extends AbsSysDao implements IResourceDao {
    public List<ResourceBean> queryResources(HashMap<String, Object> params)
        throws ConsoleDaoException {
        return console_sqlMapClientTemplate.queryForList("resources.queryResources",
            params);
    }

    public List<PrivilegeResourceRefBean> queryPrivilegeResourcesRef(
        PrivilegeResourceRefBean roleResourceRefBean)
        throws ConsoleDaoException {
        return console_sqlMapClientTemplate.queryForList("user.queryPrivilegeResourcesRef",
            roleResourceRefBean);
    }

    @Override
    public int queryRows(Object object) throws ConsoleDaoException {
        Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("resources.queryResourceRows",
                (ResourceBean) object);

        if (null != totalNum) {
            return totalNum.intValue();
        }

        return 0;
    }

    @Override
    public List<Map<String, Object>> queryList(Object object)
        throws ConsoleDaoException {
        return console_sqlMapClientTemplate.queryForList("resources.queryResourceList",
            (ResourceBean) object);
    }

    @Override
    public int addResource(final ResourceBean resourceBean)
        throws ConsoleDaoException {
        Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
                    public Integer doInTransaction(TransactionStatus status) {
                        Object resource_id = console_sqlMapClientTemplate.insert("resources.addResource",
                                resourceBean);

                        if (null != resource_id) {
                            return Integer.parseInt(resource_id.toString());
                        }

                        status.setRollbackOnly();

                        return 0;
                    }
                });

        return resultCode;
    }

    @Override
    public ResourceBean queryResourceById(int resourceId)
        throws ConsoleDaoException {
        return (ResourceBean) console_sqlMapClientTemplate.queryForObject("resources.queryResourceById",
            String.valueOf(resourceId));
    }

    @Override
    public int updateResource(final ResourceBean resourceBean)
        throws ConsoleDaoException {
        Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
                    public Integer doInTransaction(TransactionStatus status) {
                        Object resource_id = console_sqlMapClientTemplate.update("resources.updateResource",
                                resourceBean);

                        if (null != resource_id) {
                            return Integer.parseInt(resource_id.toString());
                        }

                        status.setRollbackOnly();

                        return 0;
                    }
                });

        return resultCode;
    }

    @Override
    public int deleteResource(final List<ResourceBean> resourceBeanList)
        throws ConsoleDaoException {
        Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
                    public Integer doInTransaction(TransactionStatus status) {
                        Object obj = console_sqlMapClientTemplate.execute(new SqlMapClientCallback() {
                                    public Object doInSqlMapClient(
                                        SqlMapExecutor executor)
                                        throws SQLException {
                                        executor.startBatch();

                                        for (ResourceBean resourceBean : resourceBeanList) {
                                            executor.delete("resources.deleteResource",
                                                resourceBean);

                                            executor.delete("resources.deletePrivilegeResourceRef",
                                                resourceBean);
                                        }

                                        return executor.executeBatch();
                                    }
                                });

                        return Integer.valueOf(obj.toString());
                    }
                });

        return resultCode.intValue();
    }

    @Override
    public int queryResourceDataChecked(ResourceBean resourceBean)
        throws ConsoleDaoException {
        Integer totalNum = (Integer) console_sqlMapClientTemplate.queryForObject("resources.queryResourceDataChecked",
                resourceBean);

        if (null != totalNum) {
            return totalNum.intValue();
        }

        return 0;
    }

    @Override
    public List<HashMap<String, Object>> queryResourceListByPrivilegeCode(
        HashMap<String, Object> params) throws ConsoleDaoException {
        return console_sqlMapClientTemplate.queryForList("resources.queryResourceListByPrivilegeCode",
            params);
    }

    @Override
    public List<Map<String, Object>> queryResourceUrlList(
        Map<String, Object> params) throws ConsoleDaoException {
        return console_sqlMapClientTemplate.queryForList("resources.queryResourceUrlList",
            params);
    }

    @Override
    public int insertResource(Map<String, Object> params)
        throws ConsoleDaoException {
        Object obj = console_sqlMapClientTemplate.insert("resources.insertResource",
                params);

        if (null != obj) {
            return Integer.parseInt(obj.toString());
        }

        return 1;
    }
}
