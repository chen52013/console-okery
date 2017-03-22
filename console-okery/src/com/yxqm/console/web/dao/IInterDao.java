package com.yxqm.console.web.dao;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.utils.bean.InitMatchBean;
import com.yxqm.console.utils.bean.InitOddsItem;
import com.yxqm.console.web.bean.FootballMatchBean;
import com.yxqm.console.web.bean.InterBean;
import com.yxqm.console.web.bean.MatchBean;
import com.yxqm.console.web.bean.NBABean;

import java.util.List;
import java.util.Map;


public interface IInterDao {
    /**
     * ����ղ�
     *
     * @param bean
     * @return
     * @throws ConsoleDaoException
     */
    int addInter(InterBean bean) throws ConsoleDaoException;

    int addMatch(MatchBean matchBean)
            throws ConsoleDaoException;
    
    int addNBA(NBABean paramNBABean)
    	    throws ConsoleDaoException;
    
    int addMatchList(InitMatchBean paramInitMatchBean)
        throws ConsoleDaoException;

    /**
     * TODO
     * @param paramInitMatchBean
     * @return
     * @throws ConsoleDaoException
     */
    int addBasketballList(InitMatchBean paramInitMatchBean) throws ConsoleDaoException;

    /**
     * TODO
     * @return
     * @throws ConsoleDaoException
     */
    List<InitMatchBean> selectMaxBasketballMatchTime() throws ConsoleDaoException;

    /**
     * TODO
     * @param paramInitMatchBean
     * @return
     * @throws ConsoleDaoException
     */
    List<InitMatchBean> selectBasketballList(InitMatchBean paramInitMatchBean) throws ConsoleDaoException;
    
    List<InitMatchBean> selectMaxFootballMatchTime() throws ConsoleDaoException;

    int insertInitOdds(InitOddsItem paramInitOddsItem)
        throws ConsoleDaoException;
    
    int insertNBAOdds(InitOddsItem paramInitOddsItem)
    		throws ConsoleDaoException;
    
    List<InitOddsItem> queryOdds(InitOddsItem paramInitOddsItem)
        throws ConsoleDaoException;

    int updateMatchList(InitMatchBean paramInitMatchBean)
        throws ConsoleDaoException;

    int updateNBAMatchList(InitMatchBean paramInitMatchBean)
        throws ConsoleDaoException;

    List<InitMatchBean> selectMatchList(InitMatchBean paramInitMatchBean)
        throws ConsoleDaoException;

    

    int delInter(String[] paramArrayOfString) throws ConsoleDaoException;

    int updateInter(InterBean paramInterBean) throws ConsoleDaoException;

    List<InterBean> queryInterList(InterBean paramInterBean) throws ConsoleDaoException;

    List<MatchBean> queryMatchList(MatchBean paramMatchBean) throws ConsoleDaoException;

    int queryInterListRows(InterBean paramInterBean) throws ConsoleDaoException;

    int queryMatchListRows(MatchBean paramMatchBean) throws ConsoleDaoException;
    
    int queryFootballMatchListRows(FootballMatchBean paramMatchBean) throws ConsoleDaoException;
    
    List<FootballMatchBean> queryFootballMatchList(FootballMatchBean paramMatchBean) throws ConsoleDaoException;

    List<FootballMatchBean> queryMatchScoreList(FootballMatchBean paramMatchBean) throws ConsoleDaoException;

    List<FootballMatchBean> queryMatchTimeList(FootballMatchBean paramMatchBean) throws ConsoleDaoException;

    List<Map<String, Object>> queryMatchScoreTimeList(Map<String, Object> param) throws ConsoleDaoException;

    List<Map<String, Object>> queryMatchKeliList(Map<String, Object> param) throws ConsoleDaoException;



}
