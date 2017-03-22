package com.yxqm.console.web.dao.impl;

import com.yxqm.console.exception.ConsoleDaoException;
import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.utils.bean.InitMatchBean;
import com.yxqm.console.utils.bean.InitOddsItem;
import com.yxqm.console.web.bean.FootballMatchBean;
import com.yxqm.console.web.bean.InterBean;
import com.yxqm.console.web.bean.MatchBean;
import com.yxqm.console.web.bean.NBABean;
import com.yxqm.console.web.dao.IInterDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.util.List;
import java.util.Map;

@Service("interDao")
public class InterDaoImpl extends AbsSysDao implements IInterDao {
	@Override
	public int addInter(final InterBean bean) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object inter_id = console_sqlMapClientTemplate.insert("inter.addInter", bean);

				if (null != inter_id) {
					return Integer.parseInt(inter_id.toString());
				}

				status.setRollbackOnly();

				return 0;
			}
		});

		return resultCode;
	}

	@Override
	public int addMatch(final MatchBean bean) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object match_id = console_sqlMapClientTemplate.insert("match.addMatch", bean);

				if (null != match_id) {
					return Integer.parseInt(match_id.toString());
				}

				status.setRollbackOnly();

				return 0;
			}
		});

		return resultCode;
	}

	public int addMatchList(final InitMatchBean bean) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object match_id = InterDaoImpl.this.console_sqlMapClientTemplate.insert("match.addMatchList", bean);
				if (null != match_id) {
					return Integer.valueOf(Integer.parseInt(match_id.toString()));
				}
				status.setRollbackOnly();
				return Integer.valueOf(0);
			}
		});
		return resultCode.intValue();
	}

	public int addBasketballList(final InitMatchBean bean) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object match_id = InterDaoImpl.this.console_sqlMapClientTemplate.insert("match.addBasketballList", bean);
				if (null != match_id) {
					return Integer.valueOf(Integer.parseInt(match_id.toString()));
				}
				status.setRollbackOnly();
				return Integer.valueOf(0);
			}
		});
		return resultCode.intValue();
	}

	public int insertInitOdds(final InitOddsItem initOddsItem) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object odds_id = InterDaoImpl.this.console_sqlMapClientTemplate.insert("match.insertInitOdds",
						initOddsItem);
				if (null != odds_id) {
					return Integer.valueOf(Integer.parseInt(odds_id.toString()));
				}
				status.setRollbackOnly();
				return Integer.valueOf(0);
			}
		});
		return resultCode.intValue();
	}

	public int insertNBAOdds(final InitOddsItem initOddsItem) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object odds_id = InterDaoImpl.this.console_sqlMapClientTemplate.insert("match.insertNBAOdds",
						initOddsItem);
				if (null != odds_id) {
					return Integer.valueOf(Integer.parseInt(odds_id.toString()));
				}
				status.setRollbackOnly();
				return Integer.valueOf(0);
			}
		});
		return resultCode.intValue();
	}

	public List<InitOddsItem> queryOdds(InitOddsItem initOddsItem) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryOdds", initOddsItem);
	}

	public int updateMatchList(final InitMatchBean bean) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object match_id = Integer
						.valueOf(InterDaoImpl.this.console_sqlMapClientTemplate.update("match.updateMatchList", bean));
				if (null != match_id) {
					return Integer.valueOf(Integer.parseInt(match_id.toString()));
				}
				status.setRollbackOnly();
				return Integer.valueOf(0);
			}
		});
		return resultCode.intValue();
	}

	public int updateNBAMatchList(final InitMatchBean bean) throws ConsoleDaoException {
		Integer resultCode = console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object match_id = Integer.valueOf(
						InterDaoImpl.this.console_sqlMapClientTemplate.update("match.updateNBAMatchList", bean));
				if (null != match_id) {
					return Integer.valueOf(Integer.parseInt(match_id.toString()));
				}
				status.setRollbackOnly();
				return Integer.valueOf(0);
			}
		});
		return resultCode.intValue();
	}

	public int addNBA(final NBABean bean) throws ConsoleDaoException {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object nba_id = console_sqlMapClientTemplate.insert("nba.addNBA", bean);

				if (null != nba_id) {
					return Integer.parseInt(nba_id.toString());
				}

				status.setRollbackOnly();

				return 0;
			}
		});

		return resultCode;
	}

	@Override
	public int delInter(final String[] privilegeCodes) throws ConsoleDaoException {

		return 0;
	}

	@Override
	public int updateInter(final InterBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.update("inter.updateInter", bean);
	}

	@Override
	public List<InterBean> queryInterList(InterBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("inter.queryInterList", bean);
	}

	@Override
	public int queryInterListRows(InterBean bean) throws ConsoleDaoException {
		return (Integer) console_sqlMapClientTemplate.queryForObject("inter.queryInterListRows", bean);
	}

	@Override
	public List<InitMatchBean> selectMaxBasketballMatchTime() throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.selectMaxBasketballMatchTime");
	}

	@Override
	public List<InitMatchBean> selectMaxFootballMatchTime() throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.selectMaxFootballMatchTime");
	}

	public List<InitMatchBean> selectMatchList(InitMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.selectMatchList", bean);
	}

	public List<InitMatchBean> selectBasketballList(InitMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.selectBasketballList", bean);
	}

	public List<MatchBean> queryMatchList(MatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryMatchList", bean);
	}

	public int queryMatchListRows(MatchBean bean) throws ConsoleDaoException {
		return (Integer) console_sqlMapClientTemplate.queryForObject("match.queryMatchListRows", bean);
	}

	@Override
	public int queryFootballMatchListRows(FootballMatchBean bean) throws ConsoleDaoException {
		return (Integer) console_sqlMapClientTemplate.queryForObject("match.queryFootballMatchListRows", bean);
	}

	@Override
	public List<FootballMatchBean> queryFootballMatchList(FootballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryFootballMatchList", bean);
	}

	@Override
	public List<FootballMatchBean> queryMatchScoreList(FootballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryMatchScoreList", bean);
	}

	@Override
	public List<FootballMatchBean> queryMatchTimeList(FootballMatchBean bean) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryMatchTimeList", bean);
	}

	@Override
	public List<Map<String, Object>> queryMatchScoreTimeList(Map<String, Object> param) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryMatchScoreTimeList", param);
	}

	@Override
	public List<Map<String, Object>> queryMatchKeliList(Map<String, Object> param) throws ConsoleDaoException {
		return console_sqlMapClientTemplate.queryForList("match.queryMatchKeliList", param);
	}



}
