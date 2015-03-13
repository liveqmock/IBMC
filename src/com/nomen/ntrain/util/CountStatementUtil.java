package com.nomen.ntrain.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.AutoResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.ExecuteListener;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.mapping.statement.SelectStatement;
import com.ibatis.sqlmap.engine.scope.ErrorContext;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class CountStatementUtil
{
	public static MappedStatement createCountStatement(MappedStatement selectStatement) {
		return new CountStatement((SelectStatement) selectStatement);
	}
	
	public static String getCountStatementId(String selectStatementId) {
		return "__" + selectStatementId + "Count__";
	}
}

class CountStatement extends SelectStatement {
	@SuppressWarnings("deprecation")
	public CountStatement(SelectStatement selectStatement) {
		super();
		setId(CountStatementUtil.getCountStatementId(selectStatement.getId()));
		setResultSetType(selectStatement.getResultSetType());
		setFetchSize(1);
		setParameterMap(selectStatement.getParameterMap());
		setParameterClass(selectStatement.getParameterClass());
		setSql(selectStatement.getSql());
		setSqlMapClient(selectStatement.getSqlMapClient());
		setTimeout(selectStatement.getTimeout());
		setResource(selectStatement.getResource());
		
		List executeListeners = (List)ReflectUtil.getFieldValue(selectStatement, "executeListeners");
		if (executeListeners != null) {
			for (Object listener : executeListeners) {
				addExecuteListener((ExecuteListener) listener);
			}
		}
		
		ResultMap resultMap = new AutoResultMap(
				((com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient)getSqlMapClient()).getDelegate(), false);
		resultMap.setId(getId() + "-AutoResultMap");
		resultMap.setResultClass(Long.class);
		resultMap.setResource(getResource());
		setResultMap(resultMap);
	}
	
	protected void executeQueryWithCallback(StatementScope statementScope,
			Connection conn, Object parameterObject, Object resultObject,
			RowHandler rowHandler, int skipResults, int maxResults) throws SQLException {
		ErrorContext errorContext = statementScope.getErrorContext();
		errorContext.setActivity("preparing the mapped statement for execution");
		errorContext.setObjectId(this.getId());
		errorContext.setResource(this.getResource());
		try {
			parameterObject = validateParameter(parameterObject);
			Sql sql = getSql();
			
			errorContext.setMoreInfo("Check the parameter map.");
			ParameterMap parameterMap = sql.getParameterMap(statementScope,parameterObject);
			
			errorContext.setMoreInfo("Check the result map.");
			ResultMap resultMap = getResultMap(statementScope, parameterObject, sql);
			
			statementScope.setResultMap(resultMap);
			statementScope.setParameterMap(parameterMap);
			
			errorContext.setMoreInfo("Check the parameter map.");
			Object[] parameters = parameterMap.getParameterObjectValues(statementScope, parameterObject);
			
			errorContext.setMoreInfo("Check the SQL statement.");
			String sqlString = getSqlString(statementScope, parameterObject, sql);
			
			errorContext.setActivity("executing mapped statement");
			errorContext.setMoreInfo("Check the SQL statement or the result map.");
			RowHandlerCallback callback = new RowHandlerCallback(resultMap,
					resultObject, rowHandler);
			sqlExecuteQuery(statementScope, conn, sqlString, parameters, skipResults,maxResults, callback); 
			errorContext.setMoreInfo("Check the output parameters.");
			
			if(parameterObject != null) {
				postProcessParameterObject(statementScope, parameterObject, parameters);
			}
			
			errorContext.reset();
			sql.cleanup(statementScope);
			notifyListeners();
		} catch (SQLException e) {
			errorContext.setCause(e);
			throw new NestedSQLException(errorContext.toString(), e.getSQLState(), e.getErrorCode(), e);
		} catch (Exception ex) {
			errorContext.setCause(ex);
			throw new NestedSQLException(errorContext.toString(), ex);
		}
	}
	
	private String getSqlString(StatementScope request, Object parameterObject, Sql sql) {
		String sqlString = sql.getSql(request, parameterObject);
		sqlString = "select count(*) as c from (" + sqlString+")";
		return sqlString;
	}
	
	private ResultMap getResultMap(StatementScope request,Object parameterObject, Sql sql) {
		return getResultMap();
	} 
}
