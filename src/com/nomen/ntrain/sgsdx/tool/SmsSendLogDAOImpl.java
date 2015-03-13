package com.nomen.ntrain.sgsdx.tool;

import java.util.List;
import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;
import com.nomen.ntrain.sgsdx.dao.SmsItemlistDAO;

public class SmsSendLogDAOImpl extends BaseServiceImpl implements SmsSendLogDAO {
	
	private SmsItemlistDAO smsItemlistDAO;
	private String oldId="";
	private String sendSign="1";
	private int sendCount=0;
	private List<SmsItem> failSmsList;
	
	public void saveSmsItemListAndLog(SmsItem smsItem,int oldsmsItem) {
		String id = String.valueOf(smsItem.getId());
		String sendSignTemp = String.valueOf(smsItem.getSendSign());
		String content = smsItem.getContent();
		String sendMode = smsItem.getSendMode();
		boolean failSign = !"1".equals(sendSignTemp);
		//更新短信内容表
		SmsItemlistBean bean = new SmsItemlistBean();
		bean.setId(String.valueOf(smsItem.getId()));
		bean.setSendsign(sendSignTemp);
		bean.setSendcount("true");
		if("2".equals(sendMode)){
			//手动发送更新短信内容和号码
			bean.setSms(content);
			bean.setPhone(smsItem.getPhoneNumber());
		}
//		if(content.length()>70){
			//自动发送
			if(id.equals(oldId)&&smsItem.getSendCount()<sendCount){
				bean.setSendcount("");		//同一条记录(超过70个字)不更新短信内容表的发送次数(失败短信一条终止接来同一记录不再发送)
				bean.setSms("");
				bean.setPhone("");				
//			if(failSign)
//				sendSign = sendSignTemp;
//			bean.setSendsign(sendSign);	//同一条记录(超过70个字)只要有一条短信失败就标记为失败 
			}
//			if("2".equals(sendMode)){
//				bean.setSendcount("");
//			}
//		}
		this.smsItemlistDAO.updateSmsItemlist(bean);
		sendCount = smsItem.getSendCount()+1;
		oldId = id;	
	}

	//set/get方法
	public SmsItemlistDAO getSmsItemlistDAO() {
		return smsItemlistDAO;
	}

	public void setSmsItemlistDAO(SmsItemlistDAO smsItemlistDAO) {
		this.smsItemlistDAO = smsItemlistDAO;
	}
}
