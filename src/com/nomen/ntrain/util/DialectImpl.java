package com.nomen.ntrain.util;

public class DialectImpl implements Dialect 
{
	protected static final String SQL_END_DELIMITER = ";";
	
	public String getMySqlLimit(String sql, int offset, int limit) {
		StringBuffer sb = new StringBuffer(sql.length() + 20);
		sb.append(trim(sql));
		
		if(offset > 0) {
			sb.append(" limit ").append(offset).append(',').append(limit).append(SQL_END_DELIMITER);
		} else {
			sb.append(" limit ").append(limit).append(SQL_END_DELIMITER);
		}
		return sb.toString();
	}
	
	public String getOracleLimit(String sql, int offset, int limit) {
		if(offset < 1) {
			offset=1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (select t.*,rownum rn from (");
		sb.append(trim(sql));
		sb.append(") t where rownum <= ");
		sb.append(limit*offset);
		sb.append(") where rn > ");
		sb.append((offset-1)*limit);
		return sb.toString();
	}
	
	public boolean supportsPage() {
		return true;
	}
	
	/**
	 * 去掉当前SQL后分号
	 * @param sql
	 * @return
	 */
	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length()-1-SQL_END_DELIMITER.length());
		}
		return sql;
	}
}
