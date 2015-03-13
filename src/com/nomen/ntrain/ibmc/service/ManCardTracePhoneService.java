package com.nomen.ntrain.ibmc.service;

import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardTracePhoneBean;

/**
 * @description 刷卡短信通知配置表
 * @author 
 * @date 
 */
public interface ManCardTracePhoneService {
	
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
	
	/**
	 * 保存刷卡短信通知配置表信息
	 * @param map
	 *        loginBean  登录人员bean
	 *        cardIdStr  门卡idStr
	 *        phone      手机号码
	 */
	public void saveManCardTracePhoneBean(Map map);
	
}
