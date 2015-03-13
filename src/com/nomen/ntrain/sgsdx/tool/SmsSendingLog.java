package com.nomen.ntrain.sgsdx.tool;

// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SmsSendingLog.java
 

import java.util.Date;

public class SmsSendingLog
{

	private Long id;
	private String receiverName;
	private String receiverPhone;
	private String content;
	private Date sendDate;
	private String smsSendStatus; //5端口连接不上

	public SmsSendingLog()
	{
	}

	public String getSmsSendStatus()
	{
		return smsSendStatus;
	}

	public void setSmsSendStatus(String smsSendStatus)
	{
		this.smsSendStatus = smsSendStatus;
	}

	public Date getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(Date sendDate)
	{
		this.sendDate = sendDate;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public String getReceiverPhone()
	{
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone)
	{
		this.receiverPhone = receiverPhone;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
