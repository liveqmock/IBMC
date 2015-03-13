package com.nomen.ntrain.quart.dao.impl;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.quart.bean.NetQuartBean;
import com.nomen.ntrain.quart.dao.NetQuartDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class NetQuartDAOImpl extends NsoftBaseDao implements NetQuartDAO {

	public NetQuartBean findNetQuartBean(String id) {
		return (NetQuartBean) this.getSqlMapClientTemplate().queryForObject("NetQuart.findNetQuartBean", id);
	}

	public List findNetQuartList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("NetQuart.findNetQuartList", map);
	}
	
	public List findNetQuartList(Map map, int page, int record) {
		map.put("total", this.getObjectTotal("NetQuart.findNetQuartList", map));
		return (List<ManDoorBean>)this.getSqlMapClientTemplate().queryForList("NetQuart.findNetQuartList", map, page, record);
	}
	
	public String insertNetQuart(NetQuartBean netQuartBean) {
		return (String) this.getSqlMapClientTemplate().insert("NetQuart.insertNetQuart",netQuartBean);
	}

	public void updateNetQuart(NetQuartBean netQuartBean) {
		this.getSqlMapClientTemplate().update("NetQuart.updateNetQuart", netQuartBean);
	}
	
	public void deleteNetQuart(String id) {
		this.getSqlMapClientTemplate().delete("NetQuart.deleteNetQuart",id);
	}

	public void deleteNetQuartByDate(String date) {
		this.getSqlMapClientTemplate().delete("NetQuart.deleteNetQuartByDate",date);
	}
	
}
