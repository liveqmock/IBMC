package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.dao.ManPeoDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManPeoDAOImpl extends NsoftBaseDao implements ManPeoDAO {
	
	public List<ManPeoBean> findManPeoList(Map map,int page,int record) {
		map.put("total", this.getObjectTotal("ManPeo.findManPeoList", map));
		return (List<ManPeoBean>)this.getSqlMapClientTemplate().queryForList("ManPeo.findManPeoList", map, page, record);
	}
	
	public List<ManPeoBean> findManPeoList(Map map) {
		return (List<ManPeoBean>)this.getSqlMapClientTemplate().queryForList("ManPeo.findManPeoList", map);
	}
	
	public ManPeoBean findManPeoBean(Map map) {
		return (ManPeoBean)this.getSqlMapClientTemplate().queryForObject("ManPeo.findManPeoBean", map);
	}
	
	public String insertManPeoBean(ManPeoBean manPeoBean) {
		return (String)this.getSqlMapClientTemplate().insert("ManPeo.insertManPeoBean", manPeoBean);
	}

	public void updateManPeoBean(ManPeoBean manPeoBean) {
		this.getSqlMapClientTemplate().update("ManPeo.updateManPeoBean", manPeoBean);
	}
	
	public boolean findManPeoIsExist(String idcard) {
		int result = Integer.parseInt((String)this.getSqlMapClientTemplate().queryForObject("ManPeo.findManPeoIsExist", idcard));
		return result==0?false:true;
	}
	
	public void deleteManPeo(Map map) {
		this.getSqlMapClientTemplate().delete("ManPeo.deleteManPeo", map);
	}

	public void deleteManPeoByIdCard(String idcard) {
		this.getSqlMapClientTemplate().delete("ManPeo.deleteManPeoByIdCard", idcard);
	}
	
	public List<ManPeoBean> findManPeoListByCardno(Map map) {
		return (List<ManPeoBean>)this.getSqlMapClientTemplate().queryForList("ManPeo.findManPeoListByCardno", map);
	}
	
}
