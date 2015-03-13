package com.nomen.ntrain.sgsdx.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;


public interface SmsItemlistDAO {
	
	/**
	 * 查询短信内容列表[无分页]
	 */
	public List<SmsItemlistBean> findSmsItemlistList(Map map);
	/**
	 * 更新短信内容
	 */
	public void updateSmsItemlist(SmsItemlistBean smsItemlistbean);
	
	/**
	 * 获取短信内容Bean
	 */
	public SmsItemlistBean findSmsItemlistBeanById(String id);	
}
