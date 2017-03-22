package com.yxqm.console.web.bussiness.impl;

import com.yxqm.console.web.bean.*;
import com.yxqm.console.web.bussiness.IBasketballService;
import com.yxqm.console.web.bussiness.IInterService;
import com.yxqm.console.web.dao.IBasketballDao;
import com.yxqm.console.web.dao.IInterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("basketballService")
public class BasketballServiceImpl implements IBasketballService {
    @Autowired
    @Qualifier("basketballDao")
    protected IBasketballDao basketballDao;

    @Override
    public List<BasketballMatchBean> queryBasketBallLeagueList(BasketballMatchBean matchBean) {
    	return basketballDao.queryBasketBallLeagueList(matchBean);
    }

    public int queryBasketballMatchListRows(BasketballMatchBean matchBean) {
        return basketballDao.queryBasketballMatchListRows(matchBean);
    }

    @Override
    public List<BasketballMatchBean> queryBasketballMatchList(BasketballMatchBean matchBean) {
        return basketballDao.queryBasketballMatchList(matchBean);
    }

    @Override
    public List<BasketballMatchBean> queryTeamDetailList(BasketballMatchBean matchBean) {
        return basketballDao.queryTeamDetailList(matchBean);
    }

    @Override
    public List<BasketballMatchBean> queryBasketballTeamList(BasketballMatchBean matchBean) {
        return basketballDao.queryBasketballTeamList(matchBean);
    }

    public int editBasketballMatch(BasketballMatchBean matchBean) {
        return basketballDao.editBasketballMatch(matchBean);
    }
}
