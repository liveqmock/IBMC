package com.nomen.ntrain.sgsdx.tool;

import java.util.ArrayList;
import java.util.List;

public class smsTest {
	public static void main(String[] args){
		SmsEngineCoustomer customer = new SmsEngineCoustomer();
		//单条发送
		//sender.sendSms("13950311430","new info");
		customer.sendSms("13950311430","new info");
		
		//实现群发
		List smsItemList = new ArrayList();
		//SmsItem sItem = new SmsItem();
		//sItem.setId(1l);
		//sItem.setContent("测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！测试信息！"); 
		//sItem.setContent("abc");
		//sItem.setPhoneNumber("13950311430");
		//smsItemList.add(sItem);
		/*
		sItem = new SmsItem();
		sItem.setId(2l);
		sItem.setContent("测试信息！"); 
		sItem.setPhoneNumber("13489948281");
		smsItemList.add(sItem);
		
		sItem = new SmsItem();
		sItem.setId(3l);
		sItem.setContent("测试信息！"); 
		sItem.setPhoneNumber("15959043118");
		smsItemList.add(sItem);
		
		sItem = new SmsItem();
		sItem.setId(4l);
		sItem.setContent("测试信息！"); 
		sItem.setPhoneNumber("15860292278");
		smsItemList.add(sItem);*/
		customer.sendSms(smsItemList);
	}
}
