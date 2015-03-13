// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SmsCallBack.java

package com.nomen.ntrain.sgsdx.tool;

/**
 * 短信发送回调函数
 * @author nomen
 */

public interface SmsCallBack
{ 
	public abstract void doCallBack(SmsItem smsitem, int i);
}
