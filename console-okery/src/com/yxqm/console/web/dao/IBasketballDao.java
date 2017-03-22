package com.yxqm.console.web.dao;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.web.bean.BasketballMatchBean;
import com.yxqm.console.web.bean.FootballMatchBean;

import java.util.List;

/**
 * Created by Dell on 2017/3/16.
 */
public interface IBasketballDao {

	List<BasketballMatchBean> queryBasketBallLeagueList(BasketballMatchBean paramMatchBean) throws ConsoleDaoException;

	int queryBasketballMatchListRows(BasketballMatchBean paramMatchBean) throws ConsoleDaoException;

	int editBasketballMatch(BasketballMatchBean paramMatchBean) throws ConsoleDaoException;

	List<BasketballMatchBean> queryBasketballMatchList(BasketballMatchBean paramMatchBean) throws ConsoleDaoException;

	List<BasketballMatchBean> queryTeamDetailList(BasketballMatchBean paramMatchBean) throws ConsoleDaoException;

	List<BasketballMatchBean> queryBasketballTeamList(BasketballMatchBean paramMatchBean) throws ConsoleDaoException;
}
