package com.nomen.ntrain.base.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseExceptionBean;


public interface BaseExceptionDAO {
	/**
	 * 错误列表
	 * @param map [year]
	 *  * @param page
	 * @param record
	 * @return
	 */
	public List<BaseExceptionBean> findBaseExceptionList(Map map,int page,int record);
	
	/**
	 * 详细
	 * @param id
	 * @return
	 */
	public BaseExceptionBean   findBaseExceptionBean(String id);
	
	/**
	 * 新增日志
	 * @param bean
	 */
	public void  insertBaseExceptionBean(BaseExceptionBean bean);
	
	/**
	 * 删除日志
	 * @param map [year]
	 * @return
	 */
	public void  deleteBaseException(Map map);
}
