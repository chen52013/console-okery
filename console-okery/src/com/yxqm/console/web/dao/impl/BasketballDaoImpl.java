package com.yxqm.console.web.dao.impl;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.utils.bean.InitMatchBean;
import com.yxqm.console.utils.bean.InitOddsItem;
import com.yxqm.console.web.bean.*;
import com.yxqm.console.web.dao.IBasketballDao;
import com.yxqm.console.web.dao.IInterDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.util.List;
import java.util.Map;

@Service("basketballDao")
public class BasketballDaoImpl extends AbsSysDao implements IBasketballDao {

	@Override
	public List<BasketballMatchBean> queryBasketBallLeagueList(BasketballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryBasketBallLeagueList", bean);
	}

	@Override
	public int queryBasketballMatchListRows(BasketballMatchBean bean) throws ConsoleDaoException {
		return (Integer) console_sqlMapClientTemplate.queryForObject("match.queryBasketballMatchListRows", bean);
	}

	@Override
	public List<BasketballMatchBean> queryBasketballMatchList(BasketballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryBasketballMatchList", bean);
	}

	@Override
	public List<BasketballMatchBean> queryTeamDetailList(BasketballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryTeamDetailList", bean);
	}

	@Override
	public List<BasketballMatchBean> queryBasketballTeamList(BasketballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryBasketballTeamList", bean);
	}

	@Override
	public int editBasketballMatch(BasketballMatchBean bean) throws ConsoleDaoException {
		Object obj = console_sqlMapClientTemplate.update("match.editBasketballMatch", bean);
		return Integer.parseInt(obj.toString());
	}

}
