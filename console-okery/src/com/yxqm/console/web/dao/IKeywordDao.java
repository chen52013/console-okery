package com.yxqm.console.web.dao;

import com.yxqm.console.web.bean.KeywordBean;

import java.util.List;

/**
 * Created by Dell on 2017/2/27.
 */
public interface IKeywordDao {

	List<KeywordBean> queryKeyWordList(KeywordBean keywordBean);

	int queryKeyWordListRows(KeywordBean keywordBean);

	int addKeyword(KeywordBean keywordBean);

	int updateKeyword(KeywordBean keywordBean);
}
