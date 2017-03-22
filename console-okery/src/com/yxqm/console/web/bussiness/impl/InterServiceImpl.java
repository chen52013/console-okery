package com.yxqm.console.web.bussiness.impl;

import com.yxqm.console.web.bean.FootballMatchBean;
import com.yxqm.console.web.bean.InterBean;
import com.yxqm.console.web.bean.MatchBean;
import com.yxqm.console.web.bean.NBABean;
import com.yxqm.console.web.bussiness.IInterService;
import com.yxqm.console.web.dao.IInterDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("interService")
public class InterServiceImpl implements IInterService {
    @Autowired
    @Qualifier("interDao")
    protected IInterDao interDao;

    @Override
    public int addNBA(NBABean nbaBean) {
        return interDao.addNBA(nbaBean);
    }

    @Override
    public int addInter(InterBean interBean) {
        return interDao.addInter(interBean);
    }

    @Override
    public int addMatch(MatchBean matchBean) {
        return interDao.addMatch(matchBean);
    }

    @Override
    public int delInter(String[] interIds) {
        return interDao.delInter(interIds);
    }

    @Override
    public int updateInter(InterBean interBean) {
        return interDao.updateInter(interBean);
    }

    @Override
    public List<InterBean> queryInterList(InterBean interBean) {
        return interDao.queryInterList(interBean);
    }

    public int queryInterListRows(InterBean interBean) {
        return interDao.queryInterListRows(interBean);
    }

    @Override
    public List<MatchBean> queryMatchList(MatchBean matchBean) {
        return interDao.queryMatchList(matchBean);
    }

    public int queryMatchListRows(MatchBean matchBean) {
        return interDao.queryMatchListRows(matchBean);
    }
    
    public int queryFootballMatchListRows(FootballMatchBean matchBean) {
    	return interDao.queryFootballMatchListRows(matchBean);
    }
    
    @Override
    public List<FootballMatchBean> queryFootballMatchList(FootballMatchBean matchBean) {
        return interDao.queryFootballMatchList(matchBean);
    }

    @Override
    public List<FootballMatchBean> queryMatchScoreList(FootballMatchBean matchBean) {
        return interDao.queryMatchScoreList(matchBean);
    }

    @Override
    public List<FootballMatchBean> queryMatchTimeList(FootballMatchBean matchBean) {
        return interDao.queryMatchTimeList(matchBean);
    }

    @Override
    public List<Map<String, Object>> queryMatchScoreTimeList(Map<String, Object> param) {
        return interDao.queryMatchScoreTimeList(param);
    }

    @Override
    public List<Map<String, Object>> queryMatchKeliList(Map<String, Object> param) {
        return interDao.queryMatchKeliList(param);
    }



}
