package com.nomen.ntrain.ibmc.dao.implement;

import com.nomen.ntrain.ibmc.bean.ManCardTracePhoneBean;
import com.nomen.ntrain.ibmc.dao.ManCardTracePhoneDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManCardTracePhoneDAOImpl extends NsoftBaseDao implements ManCardTracePhoneDAO{

	public ManCardTracePhoneBean findManCardTracePhoneBean(String cardid) {
		return (ManCardTracePhoneBean) this.getSqlMapClientTemplate().queryForObject("ManCardTracePhone.findManCardTracePhoneBean", cardid);
	}

	public String insertManCardTracePhoneBean(ManCardTracePhoneBean manCardTracePhoneBean) {
		return (String) this.getSqlMapClientTemplate().insert("ManCardTracePhone.insertManCardTracePhoneBean", manCardTracePhoneBean);
	}

	public void updateManCardTracePhoneBean(ManCardTracePhoneBean manCardTracePhoneBean) {
		this.getSqlMapClientTemplate().update("ManCardTracePhone.updateManCardTracePhoneBean", manCardTracePhoneBean);
	}
	
	
}
