package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManFactoryBean;
import com.nomen.ntrain.ibmc.dao.ManFactoryDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManFactoryDAOImpl extends NsoftBaseDao implements ManFactoryDAO {

	public void deleteManFactoryBean(String id) {
		this.getSqlMapClientTemplate().delete("ManFactory.deleteManFactoryBean", id);

	}

	public List<ManFactoryBean> findManFactoryList(Map map, int page, int record) {
		map.put("total", this.getObjectTotal("ManFactory.findManFactoryList", map));
		return this.getSqlMapClientTemplate().queryForList("ManFactory.findManFactoryList", map, page, record);
	}
	
	public List<ManFactoryBean> findManFactoryList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("ManFactory.findManFactoryList", map);
	}
	
	public ManFactoryBean findManFactoryBean(ManFactoryBean manFactoryBean) {
		return (ManFactoryBean) this.getSqlMapClientTemplate().queryForObject("ManFactory.findManFactoryBean", manFactoryBean);
	}

	public String insertManFactoryBean(ManFactoryBean manFactoryBean) {
		return (String) this.getSqlMapClientTemplate().insert("ManFactory.insertManFactoryBean", manFactoryBean);

	}

	public void updateManFactoryBean(ManFactoryBean manFactoryBean) {
		this.getSqlMapClientTemplate().update("ManFactory.updateManFactoryBean", manFactoryBean);

	}

}
