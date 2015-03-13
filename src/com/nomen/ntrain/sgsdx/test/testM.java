package com.nomen.ntrain.sgsdx.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nomen.ntrain.sgsdx.tool.SmsItem;

public class testM {
	public static void main(String[] args){
		List list = new ArrayList();
		SmsItem sItem = new SmsItem();
		sItem.setId(Long.valueOf("1"));
		SmsItem sItem2 = new SmsItem();
		sItem2.setId(Long.valueOf("2"));
		SmsItem sItem3 = new SmsItem();
		sItem3.setId(Long.valueOf("3"));
		SmsItem sItem4 = new SmsItem();
		sItem4.setId(Long.valueOf("4"));
		SmsItem sItem5 = new SmsItem();
		sItem5.setId(Long.valueOf("5"));
		SmsItem sItem6 = new SmsItem();
		sItem6.setId(Long.valueOf("6"));
		list.add(sItem);
		list.add(sItem2);
		list.add(sItem3);
		list.add(sItem4);
		list.add(sItem5);
		list.add(sItem6);
		Iterator i$ = list.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SmsItem smsItem = (SmsItem)i$.next();
			//sendSms(smsItem.getPhoneNumber(), smsItem.getContent());
			System.out.println(smsItem.getId()+"------------");
		} while (true);
	}
}
