package com.nomen.ntrain.util;

/**
 * @description 数据库方言接口  
 * @author 连金亮
 * @date 2009-09-28
 */
public interface Dialect 
{
	public boolean supportsPage();
	
	/**
	 * 获取MySql分页语句
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public String getMySqlLimit(String sql, int offset, int limit);
	
	/**
	 * 获取Oracle分页语句
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public String getOracleLimit(String sql, int offset, int limit);
}
