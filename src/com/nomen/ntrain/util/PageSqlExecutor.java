package com.nomen.ntrain.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class PageSqlExecutor extends SqlExecutor
{
	private static final Log logger = LogFactory.getLog(PageSqlExecutor.class);
	private Dialect dialect;
	private boolean enablePage = true;
	/**
	 * opersign 操作标志
	 * 0：当前操作的是oracle
	 * 1：当前操作的是MySql  
	 */
	private int opersign=0;
	
	public boolean supportsPage() {
		if(enablePage && dialect!=null) {
			return dialect.supportsPage();
		}
		return false;
	}

	/**
	 * 重写SqlExecutor.executeQuery方法 实现ORACLE的SQL物理分页
	 */
	public void executeQuery(StatementScope statementScope, Connection conn, String sql, 
			Object[] parameters, int skipResults, int maxResults, 
			RowHandlerCallback callback) throws SQLException {
		if (isEnable(sql,skipResults,maxResults)) {
			switch (this.opersign) {
			case 0:
				sql = dialect.getOracleLimit(sql, skipResults, maxResults);
				break;
			case 1:
				sql = dialect.getMySqlLimit(sql, skipResults, maxResults);
				break;
			}
			if(logger.isDebugEnabled()) {
				logger.debug(sql);
			}
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}
		super.executeQuery(statementScope, conn, sql, parameters, skipResults, maxResults, callback);
    }
	
	/**
	 * 是否允许执行分页
	 */
	private boolean isEnable(String sql,int skipResults, int maxResults) {
		return (skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS) 
		&& supportsPage()&&isSelect(sql);
	}
	
	/**
	 * 是否符合oracle分页查询
	 * @param sql
	 * @return
	 */
	private boolean isSelect(String sql) {
		if (sql.toLowerCase().indexOf("select") >= 0) {
			if (sql.toLowerCase().indexOf("rownum") >= 0) {
				System.out.println("分页查询出错：sql语句不能出现rownum关键字！");
            	return false;
            }
            return true;
        }
		System.out.println("分页查询出错：sql语句必须包含select关键字！");
		return false;
	}
	
	//以下为get和set方法
	public Dialect getDialect() {
		return dialect;
	}
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	public int getOpersign() {
		return opersign;
	}
	public void setOpersign(int opersign) {
		this.opersign = opersign;
	}
	public boolean isEnablePage() {
		return enablePage;
	}
	public void setEnablePage(boolean enablePage) {
		this.enablePage = enablePage;
	}
}
