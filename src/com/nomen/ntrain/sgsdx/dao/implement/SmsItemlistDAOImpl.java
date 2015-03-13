package com.nomen.ntrain.sgsdx.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;
import com.nomen.ntrain.sgsdx.dao.SmsItemlistDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

@SuppressWarnings("unchecked")
public class SmsItemlistDAOImpl extends NsoftBaseDao implements SmsItemlistDAO {

	public List<SmsItemlistBean> findSmsItemlistList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("SmsItemlist.findSmsItemlistList",map);
	}

	public void updateSmsItemlist(SmsItemlistBean smsItemlistbean) {
		 this.getSqlMapClientTemplate().update("SmsItemlist.updateSmsItemlist",smsItemlistbean);
	}

	public SmsItemlistBean findSmsItemlistBeanById(String id) {
		return (SmsItemlistBean) this.getSqlMapClientTemplate().queryForObject("SmsItemlist.findSmsItemlistBeanById", id);
	}
}
