package com.yxqm.console.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.support.TransactionOperations;

public abstract class AbsSysDao {

	@Autowired
	@Qualifier("console_transactionTemplate")
	protected TransactionOperations console_transactionTemplate;

	@Autowired
	@Qualifier("console_sqlMapClientTemplate")
	protected SqlMapClientTemplate console_sqlMapClientTemplate;
}