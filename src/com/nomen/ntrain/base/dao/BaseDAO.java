package com.nomen.ntrain.base.dao;


import com.nomen.ntrain.base.bean.BaseOptLogBean;



public interface BaseDAO{
	/**
	 * 插入编辑日志
	 * @param optLogBean 编辑日志bean
	 * @return
	 */
	public boolean insertOperLog(BaseOptLogBean optLogBean);
}