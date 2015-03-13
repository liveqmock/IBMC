package com.nomen.ntrain.sgsdx.service.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;
import com.nomen.ntrain.sgsdx.dao.SmsItemlistDAO;
import com.nomen.ntrain.sgsdx.service.SmsItemlistService;
import com.nomen.ntrain.sgsdx.tool.SmsEngineCoustomer;
import com.nomen.ntrain.sgsdx.tool.SmsItem;


public class SmsItemlistServiceImpl extends BaseServiceImpl  implements SmsItemlistService {
	
	private SmsItemlistDAO smsItemlistDAO;
	private SmsEngineCoustomer smsEngineCoustomer;
	
	public List<SmsItemlistBean> findSmsItemlistListNoPage(Map map){
		return this.smsItemlistDAO.findSmsItemlistList(map);
	}

	public void sendSmsItemlist() {
		System.out.println("________________进入自动发送_____________");
		Map map = new HashMap();
		map.put("had_send", "1");	//已发送 1
		map.put("sendcount", "4");	//发送次数
		map.put("rownum", "50");	//获取50条
		List<SmsItemlistBean> list = this.smsItemlistDAO.findSmsItemlistList(map);
		List smsItemList = new ArrayList();
		//未发送或发送失败但发送次数小于４次的短信进行发送
		for(SmsItemlistBean bean:list){
			SmsItem sItem = new SmsItem();
			sItem.setId(Long.valueOf(bean.getId()));
			sItem.setContent(bean.getSms()); 
			sItem.setPhoneNumber(bean.getPhone());
			sItem.setSendCount(Integer.valueOf(bean.getSendcount()));
			sItem.setSendMode("1");	//发送方式 1 自动 2 手动
			smsItemList.add(sItem);
		}
		if(list.size()>0){
			smsEngineCoustomer.sendSms(smsItemList);
		}
	}
	
	public void sendSmsItemlistManual(String idStr) {		
		String idArr[] = idStr.split(",");
		List smsItemList = new ArrayList();
		SmsItemlistBean bean = null;
		SmsItem sItem = null;
		for(String id : idArr){
			//通过ID查询记录
			bean = this.smsItemlistDAO.findSmsItemlistBeanById(id);
			if(null != bean && !bean.getId().equals("")){
				sItem = new SmsItem(); 
				sItem.setId(Long.valueOf(bean.getId()));
				sItem.setContent(bean.getSms()); 
				sItem.setPhoneNumber(bean.getPhone());
				sItem.setSendCount(Integer.valueOf(bean.getSendcount()));
				sItem.setMainuser(null);
				sItem.setSendMode("2");	//发送方式 1 自动 2 手动
				smsItemList.add(sItem);	
			}
		}
		smsEngineCoustomer.sendSms(smsItemList);
	}
	
	//set/get方法
	public SmsItemlistDAO getSmsItemlistDAO() {
		return smsItemlistDAO;
	}

	public void setSmsItemlistDAO(SmsItemlistDAO smsItemlistDAO) {
		this.smsItemlistDAO = smsItemlistDAO;
	}

	public SmsEngineCoustomer getSmsEngineCoustomer() {
		return smsEngineCoustomer;
	}

	public void setSmsEngineCoustomer(SmsEngineCoustomer smsEngineCoustomer) {
		this.smsEngineCoustomer = smsEngineCoustomer;
	}
}
