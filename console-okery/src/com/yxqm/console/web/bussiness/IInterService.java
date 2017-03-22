package com.yxqm.console.web.bussiness;

import com.yxqm.console.web.bean.FootballMatchBean;
import com.yxqm.console.web.bean.InterBean;
import com.yxqm.console.web.bean.MatchBean;
import com.yxqm.console.web.bean.NBABean;

import java.util.List;
import java.util.Map;


public interface IInterService {
    int addInter(InterBean interBean);

    int addMatch(MatchBean matchBean);

    int addNBA(NBABean nbaBean);

    int delInter(String[] interIds);

    int updateInter(InterBean interBean);

    List<InterBean> queryInterList(InterBean interBean);

    List<MatchBean> queryMatchList(MatchBean matchBean);
    
    List<FootballMatchBean> queryFootballMatchList(FootballMatchBean matchBean);

    List<FootballMatchBean> queryMatchScoreList(FootballMatchBean matchBean);

    List<FootballMatchBean> queryMatchTimeList(FootballMatchBean matchBean);

    List<Map<String, Object>> queryMatchScoreTimeList(Map<String, Object> param);

    List<Map<String, Object>> queryMatchKeliList(Map<String, Object> param);

    int queryInterListRows(InterBean interBean);

    int queryMatchListRows(MatchBean matchBean);
    
    int queryFootballMatchListRows(FootballMatchBean matchBean);



}
