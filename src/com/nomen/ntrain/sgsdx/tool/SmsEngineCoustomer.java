// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SmsEngineSender.java

package com.nomen.ntrain.sgsdx.tool;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 短信发送/接受者
 * @author 连金亮
 * @date   2012-01-31
 */
public class SmsEngineCoustomer
{
	private static final Log LOG = LogFactory.getLog(SmsEngineCoustomer.class);
	private SmsSendLogDAO smsSendLogDAO;
	
	class SendSmsCallBack implements SmsCallBack{
		final SmsEngineCoustomer this$0;

		public void doCallBack(SmsItem smsItem, int status)
		{
			Long id = smsItem.getId();
			if (id == null)
				return ;
//			SmsSendingLogDAO smsSendingLogDAO = new SmsSendingLogDAO();
//			SmsSendingLog smsSendingLog = smsSendingLogDAO.get(id);  
//			if (smsSendingLog == null)
//				return;
//			if (status > 3)
//				status = 3;
//			smsSendingLog.setSmsSendStatus(Integer.toString(status));
//			smsSendingLog.setSendDate(new Date());
//			smsSendingLogDAO.save(smsSendingLog);
			//更新短信内容表 和 添加短信日志
			smsItem.setSendSign(status);
			smsSendLogDAO.saveSmsItemListAndLog(smsItem,smsEngine.getMessageItemCount());
			
		}

		SendSmsCallBack()
		{
			this$0 = SmsEngineCoustomer.this; 
		}
	}


	private static SendSmsCallBack sendSmsCallBack;
	private SmsEngine smsEngine;
	
	//private SmsSendingLogDAO smsSendingLogDAO;

	public SmsEngineCoustomer()
	{
		sendSmsCallBack = new SendSmsCallBack();
		//if (SpringUtil.getServletContext() != null)
		//{
		//	Object obj = SpringUtil.getServletContext().getAttribute("smsEngine");
		//	if (obj != null)
		//		smsEngine = (SmsEngine)obj;
		//}
		try {
			//smsEngine = new SmsEngine();//注释掉?
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSmsSendingLogDAO(SmsSendingLogDAO smsSendingLogDAO)
	{
		//this.smsSendingLogDAO = smsSendingLogDAO;
	}

	public SmsSendingLog beforSendSms(String mobile, String message)
	{
		SmsSendingLog smsSendingLog = new SmsSendingLog();
		smsSendingLog.setReceiverPhone(mobile);
		smsSendingLog.setContent(message);
		smsSendingLog.setSendDate(new Date());
//		smsSendingLogDAO = new SmsSendingLogDAO();
//		smsSendingLogDAO.save(smsSendingLog);
		return smsSendingLog;
	}

	public void sendSms(String phoneNumber, String content)
	{
		SmsSendingLog smsSendingLog = beforSendSms(phoneNumber, content);
		if (smsEngine == null)
		{
			return;
		} else
		{
			SmsItem smsItem = new SmsItem();
			smsItem.setId(smsSendingLog.getId());
			smsItem.setPhoneNumber(phoneNumber);
			//String appendTailContent = (new StringBuilder()).append(content).append("[平台短信]").toString();
			String appendTailContent = (new StringBuilder()).append(content).toString();
			smsItem.setContent(appendTailContent);
			smsItem.setSmsCallBack(sendSmsCallBack); 
			smsEngine.addMessageItem(smsItem);
			return;
		}
	}

	public void sendSms(List smsItemList)
	{
		Iterator i$ = smsItemList.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SmsItem smsItem = (SmsItem)i$.next();
			//sendSms(smsItem.getPhoneNumber(), smsItem.getContent());
			sendSms(smsItem);
		} while (true);
	}

	public void sendSms(SmsItem smsItem)
	{
		if (smsEngine == null) return;
		
		//String appendTailContent = (new StringBuilder()).append(smsItem.getContent()).append("[平台短信]").toString();
		String appendTailContent = (new StringBuilder()).append(smsItem.getContent()).toString();
		smsItem.setContent(appendTailContent);
		smsItem.setSmsCallBack(sendSmsCallBack); //引入回调函数
		smsEngine.addMessageItem(smsItem);		
		return;
	}

	public SmsEngine getSmsEngine() {
		return smsEngine;
	}

	public void setSmsEngine(SmsEngine smsEngine) {
		this.smsEngine = smsEngine;
	}

	public SmsSendLogDAO getSmsSendLogDAO() {
		return smsSendLogDAO;
	}

	public void setSmsSendLogDAO(SmsSendLogDAO smsSendLogDAO) {
		this.smsSendLogDAO = smsSendLogDAO;
	}
	
	
 
}
