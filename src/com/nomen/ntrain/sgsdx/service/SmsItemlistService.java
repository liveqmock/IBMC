package com.nomen.ntrain.sgsdx.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;


public interface SmsItemlistService {
	/**
	 * 查询短信内容列表［无分页］
	 */
	public List<SmsItemlistBean> findSmsItemlistListNoPage(Map map);
	/**
	 * 短信发送[自动]
	 */
	public void sendSmsItemlist();
	/**
	 * 短信发送[手动]
	 */
	public void sendSmsItemlistManual(String id);
}
