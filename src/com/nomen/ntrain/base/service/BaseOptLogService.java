package com.nomen.ntrain.base.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseOptLogBean;

public interface BaseOptLogService {
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
	 * 删除登录人员
	 * @param id
	 */
	public void deleteBaseOptLogByIdStr(String idStr);
}
