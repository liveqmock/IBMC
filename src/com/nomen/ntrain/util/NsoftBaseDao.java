package com.nomen.ntrain.util;

/**
 * Dao 底层实现类父类
 * @author 连金亮
 * @date 2011-05-20
 */

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate; 

public abstract class NsoftBaseDao extends SqlMapClientDaoSupport
{
	private SqlExecutor sqlExecutor; 

	public void setEnableLimit(boolean enableLimit) {
		if (sqlExecutor instanceof PageSqlExecutor) {
			((PageSqlExecutor)sqlExecutor).setEnablePage(enableLimit);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initialize() throws Exception {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
			if(sqlMapClient instanceof com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient){
				ReflectUtil.setFieldValue(((com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient)sqlMapClient)
						.getDelegate(), "sqlExecutor", SqlExecutor.class,
						sqlExecutor);
			}
		}
	}
	
	public long getObjectTotal(String selectQuery, Object parameterObject){
		prepareCountQuery(selectQuery);
		return (Long)getSqlMapClientTemplate()
		.queryForObject(CountStatementUtil.getCountStatementId(selectQuery),parameterObject);
	}
	
	public long getObjectTotal(String selectQuery) {
		prepareCountQuery(selectQuery);
		return (Long)getSqlMapClientTemplate()
		.queryForObject(CountStatementUtil.getCountStatementId(selectQuery));
	}
	
	@SuppressWarnings("deprecation")
	protected void prepareCountQuery(String selectQuery) {
		String countQuery = CountStatementUtil.getCountStatementId(selectQuery);
		if(logger.isDebugEnabled()) {
			logger.debug("Convert " + selectQuery + " to " + countQuery);
		}
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		if (sqlMapClient instanceof com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient){
			SqlMapExecutorDelegate delegate = ((com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient) sqlMapClient) .getDelegate();
			try {
				delegate.getMappedStatement(countQuery);
			}catch(SqlMapException ex) {
				delegate.addMappedStatement(CountStatementUtil
						.createCountStatement(delegate.getMappedStatement(selectQuery)));
			}
		}
	} 

	
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}
	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
}
