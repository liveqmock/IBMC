// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SmsThread.java

package com.nomen.ntrain.sgsdx.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 短信操作入口
 * @author 连金亮
 * @date   2012-01-31
 */
public class SmsSenderThread extends Thread
{
	private static final Log LOG = LogFactory.getLog(SmsSenderThread.class);

	private SmsEngine smsEngine;

	public SmsSenderThread(){
	}

	public void setSmsEngine(SmsEngine smsEngine){
		this.smsEngine = smsEngine;
	}

	public void run()
	{
		LOG.info("sender run................start...");
		if(!smsEngine.getIsStop()){
			try{
				this.wait(20000l);
			}
			catch(Exception e){e.printStackTrace();}
		}
		while (!smsEngine.getIsStop()) 
		{
			try
			{
				SmsItem smsItem = smsEngine.getNextMessageItem();
				if (smsItem != null){
					if(smsEngine.sendSMS(smsItem)){
//						if(smsEngine.getMessageItemCount()==0){
//							smsEngine.setIsStop(true);
//						}
					}
				}
			}
			catch (Exception e)
			{
				smsEngine.deconnectSmsService();
				e.printStackTrace();
			}
		}
		LOG.info("run................end....");
		//smsEngine.deconnectSmsService();
		//smsEngine.deconnectSmsService();
	}
}
