package com.nomen.ntrain.ibmc.service.implement;

import java.util.Map;

import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManCardTracePhoneBean;
import com.nomen.ntrain.ibmc.dao.ManCardTracePhoneDAO;
import com.nomen.ntrain.ibmc.service.ManCardTracePhoneService;

public class ManCardTracePhoneServiceImpl extends BaseServiceImpl implements ManCardTracePhoneService{
	
	private ManCardTracePhoneDAO manCardTracePhoneDAO;

	public ManCardTracePhoneBean findManCardTracePhoneBean(String cardid) {
		return this.manCardTracePhoneDAO.findManCardTracePhoneBean(cardid);
	}

	public String insertManCardTracePhoneBean(ManCardTracePhoneBean manCardTracePhoneBean) {
		return this.manCardTracePhoneDAO.insertManCardTracePhoneBean(manCardTracePhoneBean);
	}

	public void updateManCardTracePhoneBean(ManCardTracePhoneBean manCardTracePhoneBean) {
		this.manCardTracePhoneDAO.updateManCardTracePhoneBean(manCardTracePhoneBean);
	}
	
	public void saveManCardTracePhoneBean(Map map){
		String cardIdStr = (String) map.get("cardIdStr");   //门卡idStr
		String phone = (String) map.get("phone");           //通知电话号码
		String enddate = (String) map.get("enddate");       //通知截止时间
		LoginBean loginBean = (LoginBean) map.get("loginBean");         //登录人员bean
		if(!func.IsEmpty(cardIdStr)){
			String[] arr = cardIdStr.split(",");
			for(int i=0;i<arr.length;i++){
				String cardid = arr[i];
				//判断该条记录是否存在
				ManCardTracePhoneBean manCardTracePhoneBean = this.manCardTracePhoneDAO.findManCardTracePhoneBean(cardid);
				//当该条记录不存在时，进行新增否则进行修改
				if(null == manCardTracePhoneBean||func.IsEmpty(manCardTracePhoneBean.getTraceid())){
					manCardTracePhoneBean = new ManCardTracePhoneBean();
					manCardTracePhoneBean.setCardid(cardid);
					manCardTracePhoneBean.setPhone(phone);
					manCardTracePhoneBean.setEnddate(enddate);
					manCardTracePhoneBean.setOptuserid(loginBean.getId());
					this.manCardTracePhoneDAO.insertManCardTracePhoneBean(manCardTracePhoneBean);
				}else{
					manCardTracePhoneBean.setPhone(phone);
					manCardTracePhoneBean.setEnddate(enddate);
					this.manCardTracePhoneDAO.updateManCardTracePhoneBean(manCardTracePhoneBean);
				}
			}
		}
	}

	//以下为set get方法
	public ManCardTracePhoneDAO getManCardTracePhoneDAO() {
		return manCardTracePhoneDAO;
	}

	public void setManCardTracePhoneDAO(ManCardTracePhoneDAO manCardTracePhoneDAO) {
		this.manCardTracePhoneDAO = manCardTracePhoneDAO;
	}
}
