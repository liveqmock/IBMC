package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.dao.ManDoorDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManDoorDAOImpl extends NsoftBaseDao implements ManDoorDAO {

	public List<ManDoorBean> findManDoorList(Map map, int page, int record) {
		map.put("total", this.getObjectTotal("ManDoor.findManDoorList", map));
		return (List<ManDoorBean>)this.getSqlMapClientTemplate().queryForList("ManDoor.findManDoorList", map, page, record);
	}
	
	public List<ManDoorBean> findManDoorList(Map map) {
		return (List<ManDoorBean>)this.getSqlMapClientTemplate().queryForList("ManDoor.findManDoorList", map);
	}

	public ManDoorBean findManDoorBean(Map map) {
		return (ManDoorBean)this.getSqlMapClientTemplate().queryForObject("ManDoor.findManDoorBean", map);
	}

	public String insertManDoorBean(ManDoorBean manDoorBean) {
		return (String)this.getSqlMapClientTemplate().insert("ManDoor.insertManDoorBean", manDoorBean);
	}

	public void updateManDoorBean(ManDoorBean manDoorBean) {
		this.getSqlMapClientTemplate().update("ManDoor.updateManDoorBean", manDoorBean);
	}
	
	public void deleteManDoor(String idStr) {
		this.getSqlMapClientTemplate().delete("ManDoor.deleteManDoor", idStr);
	}
	
}
