package com.nomen.ntrain.sgsdx.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SmsSendingLogDAO.java
 
public class SmsSendingLogDAO
{
	private static final Log LOG = LogFactory.getLog(SmsSendingLogDAO.class);

	public SmsSendingLogDAO()
	{
	}

	public void save(SmsSendingLog smsSendingLog)
	{
		//dao.save(smsSendingLog);
		LOG.info("保存发送日志信息...");
	}

	public SmsSendingLog get(Long id)
	{
		LOG.info("获取发送日志信息...");
		return null;//(SmsSendingLog)dao.get(SmsSendingLog.class, id);
	}
}
