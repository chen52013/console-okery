package com.yxqm.console.web.bussiness.impl;

import com.yxqm.console.web.bean.KeywordBean;
import com.yxqm.console.web.bussiness.IKeywordService;
import com.yxqm.console.web.dao.IKeywordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dell on 2017/2/27.
 */
@Service("keywordService")
public class KeywordServiceImpl implements IKeywordService {

	@Autowired
	@Qualifier("keywordDao")
	protected IKeywordDao keywordDao;

	@Override
	public List<KeywordBean> queryKeyWordList(KeywordBean keywordBean) {
		return keywordDao.queryKeyWordList(keywordBean);
	}

	@Override
	public int queryKeyWordListRows(KeywordBean keywordBean) {
		return keywordDao.queryKeyWordListRows(keywordBean);
	}

	@Override
	public int addKeyword(KeywordBean keywordBean) {
		return keywordDao.addKeyword(keywordBean);
	}

	@Override
	public int updateKeyword(KeywordBean keywordBean) {
		return keywordDao.updateKeyword(keywordBean);
	}
}
