package com.yxqm.console.web.bussiness;

import com.yxqm.console.web.bean.BasketballMatchBean;

import java.util.List;

/**
 * Created by Dell on 2017/3/16.
 */
public interface IBasketballService {

	List<BasketballMatchBean> queryBasketBallLeagueList(BasketballMatchBean matchBean);

	List<BasketballMatchBean> queryBasketballMatchList(BasketballMatchBean matchBean);

	int queryBasketballMatchListRows(BasketballMatchBean matchBean);

	List<BasketballMatchBean> queryBasketballTeamList(BasketballMatchBean matchBean);

	List<BasketballMatchBean> queryTeamDetailList(BasketballMatchBean matchBean);

	int editBasketballMatch(BasketballMatchBean basketballMatchBean);
}
