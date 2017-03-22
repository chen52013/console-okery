package com.yxqm.console.web.bussiness.impl;

import com.yxqm.console.system.IPrivilegeDao;
import com.yxqm.console.system.bean.PrivilegeBean;
import com.yxqm.console.system.bean.RoleBean;
import com.yxqm.console.system.exception.ConsoleSystemException;
import com.yxqm.console.web.bussiness.IPrivilegeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.List;


@Service("privilegeService")
public class PrivilegeServiceImpl implements IPrivilegeService {
    @Autowired
    @Qualifier("privilegeDao")
    protected IPrivilegeDao privilegeDao;

    @Override
    public int add(PrivilegeBean privilegeBean) {
        return privilegeDao.add(privilegeBean);
    }

    @Override
    public int del(String[] privilegeIds) {
        return privilegeDao.del(privilegeIds);
    }

    @Override
    public int update(PrivilegeBean privilegeBean) {
        return privilegeDao.update(privilegeBean);
    }

    @Override
    public List<PrivilegeBean> list(PrivilegeBean privilegeBean) {
        return privilegeDao.list(privilegeBean);
    }

    @Override
    public int listRows(PrivilegeBean privilegeBean) {
        // TODO Auto-generated method stub
        return privilegeDao.listRows(privilegeBean);
    }

    @Override
    public List<PrivilegeBean> queryPrivilegeByRole(RoleBean roleBean)
        throws ConsoleSystemException {
        return privilegeDao.queryPrivilegeByRole(roleBean);
    }

    @Override
    public int privilegeDataChecked(PrivilegeBean privilegeBean)
        throws ConsoleSystemException {
        try {
            return privilegeDao.privilegeDataChecked(privilegeBean);
        } catch (ConsoleSystemException ex) {
            throw new ConsoleSystemException("权限数据�?��异常", ex);
        }
    }
}
