package com.nomen.ntrain.quart.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.nomen.ntrain.sgsdx.service.SmsItemlistService;
import com.nomen.ntrain.util.SpringBeanUtils;

/**
 * 短信发送功能
 * @author 连金亮
 * @date   2015-02-02
 */
public class SmsQuartzJob {
	
	private static final Log    LOG = LogFactory.getLog(SmsQuartzJob.class);
	private SmsItemlistService   smsItemlistService;
	public SmsQuartzJob(){
		this.smsItemlistService = (SmsItemlistService)SpringBeanUtils.getBean("smsItemlistService");
	}
	
	/**
	 * 定时发送短信  默认发送四次，最多尝试发送3次
	 */
	@SuppressWarnings("unchecked")
	public void autoSms(){
		this.smsItemlistService.sendSmsItemlist();
	}
}
