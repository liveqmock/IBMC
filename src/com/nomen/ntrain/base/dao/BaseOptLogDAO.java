package com.nomen.ntrain.base.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseOptLogBean;

/**
 * 操作日志
 * @author 郑学仕
 * @date 2014-11-20
 */
public interface BaseOptLogDAO {
	/**
	 * 列表[分页]
	 * @param map  
	 *            deptid:部门ID
	 * @param page
	 * @param record
	 * @return
	 */
	public List findBaseOptLogList(Map map,int page,int record);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public BaseOptLogBean findBaseOptLogById(String id);
	
	/**
	 * 新增
	 * @param baseOptLogBean
	 */
	public void insertBaseOptLog(BaseOptLogBean baseOptLogBean);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteBaseOptLog(String id);
	
}
