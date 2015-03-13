package com.nomen.ntrain.ibmc.dao;

import com.nomen.ntrain.ibmc.bean.ManCardTracePhoneBean;


/**
 * @description 刷卡短信通知配置表
 * @author 
 * @date 
 */
public interface ManCardTracePhoneDAO {
	
	/**
	 * 刷卡短信通知配置表信息
	 * @param string cardid 门卡id
	 * @return
	 */
	public ManCardTracePhoneBean findManCardTracePhoneBean(String cardid);
	
	/**
	 * 添加刷卡短信通知配置表信息
	 * @param manCardTracePhoneBean
	 * @return
	 */
	public String insertManCardTracePhoneBean(ManCardTracePhoneBean manCardTracePhoneBean);

	/**
	 * 更新刷卡短信通知配置表信息
	 * @param manCardTracePhoneBean
	 * @return
	 */
	public void updateManCardTracePhoneBean(ManCardTracePhoneBean manCardTracePhoneBean);
	
}
