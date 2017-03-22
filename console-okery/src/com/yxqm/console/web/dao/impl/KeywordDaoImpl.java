package com.yxqm.console.web.dao.impl;

import com.yxqm.console.system.AbsSysDao;
import com.yxqm.console.web.bean.KeywordBean;
import com.yxqm.console.web.dao.IKeywordDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.util.List;

/**
 * Created by Dell on 2017/2/27.
 */
@Service("keywordDao")
public class KeywordDaoImpl extends AbsSysDao implements IKeywordDao {

	@Override
	public List<KeywordBean> queryKeyWordList(KeywordBean keywordBean) {
		return console_sqlMapClientTemplate.queryForList("keyword.queryKeyWordList", keywordBean);
	}

	@Override
	public int queryKeyWordListRows(KeywordBean keywordBean) {
		return (Integer)console_sqlMapClientTemplate.queryForObject("keyword.queryKeyWordListRows", keywordBean);
	}

	@Override
	public int addKeyword(final KeywordBean keywordBean) {
		Integer resultCode = this.console_transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				Object keyword_id = console_sqlMapClientTemplate.insert("keyword.addKeyword", keywordBean);
				if (null != keyword_id) {
					return Integer.parseInt(keyword_id.toString());
				}
				status.setRollbackOnly();
				return 0;
			}
		});
		return resultCode;
	}

	@Override
	public int updateKeyword(KeywordBean keywordBean) {
		return console_sqlMapClientTemplate.update("keyword.updateKeyword", keywordBean);
	}
}
