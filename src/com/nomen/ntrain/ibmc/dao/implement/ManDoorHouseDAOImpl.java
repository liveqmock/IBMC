package com.nomen.ntrain.ibmc.dao.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManDoorHouseDAOImpl extends NsoftBaseDao  implements ManDoorHouseDAO {
	
	public ManDoorHouseBean findDoorHouseBeanById(String id) {
		return (ManDoorHouseBean)this.getSqlMapClientTemplate().queryForObject("ManDoorHouse.findDoorHouseBeanById", id);
	}
	
	public List<ManDoorBean> findManHouseLinkDoorList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("ManDoorHouse.findManHouseLinkDoorList", map);
	}

	public List<ManDoorHouseBean> findDoorListByHouseId(String houseId) {
		return this.getSqlMapClientTemplate().queryForList("ManDoorHouse.findDoorListByHouseId", houseId);
	}
	
	public List<ManDoorHouseBean> findDoorHouseBeanByDoorId(String doorId) {
		return this.getSqlMapClientTemplate().queryForList("ManDoorHouse.findDoorHouseBeanByDoorId", doorId);
	}
	
	public String insertManDoorHouseBean(ManDoorHouseBean manDoorHouseBean) {
		return (String)this.getSqlMapClientTemplate().insert("ManDoorHouse.insertManDoorHouseBean", manDoorHouseBean);
	}
	
	public ManDoorHouseBean findDoorHouseBean(ManDoorHouseBean manDoorHouseBean) {
		if(manDoorHouseBean.getHouseid()==null || "".equals(manDoorHouseBean.getHouseid())) manDoorHouseBean.setHouseid("-1") ;
		if(manDoorHouseBean.getDoorid()==null || "".equals(manDoorHouseBean.getDoorid())) manDoorHouseBean.setDoorid("-1");
		return (ManDoorHouseBean)this.getSqlMapClientTemplate().queryForObject("ManDoorHouse.findDoorHouseBean", manDoorHouseBean);
	}
	
	public ManDoorHouseBean findDoorHouseBean(String houseId,String doorId) {
		if(houseId==null || "".equals(houseId)) houseId = ("-1") ;
		if(doorId==null || "".equals(doorId)) doorId = ("-1");
		ManDoorHouseBean manDoorHouseBean = new ManDoorHouseBean();
		manDoorHouseBean.setHouseid(houseId);
		manDoorHouseBean.setDoorid(doorId);
		return (ManDoorHouseBean)this.getSqlMapClientTemplate().queryForObject("ManDoorHouse.findDoorHouseBean", manDoorHouseBean);
	}
	
	public void updateManDoorHouseDelSign(Map map) {
		this.getSqlMapClientTemplate().update("ManDoorHouse.updateManDoorHouseDelSign", map);
	}
	
	public void updateManDoorHouseDelSign(String houseId,String doorId){
		if(houseId==null || "".equals(houseId)) houseId = "-1";
		if(doorId==null || "".equals(doorId)) doorId = "-1";
		Map map = new HashMap();
		map.put("houseid", houseId);
		map.put("doorid", doorId);
		this.updateManDoorHouseDelSign(map);
	}
	
	public void updateManDoorHouseDelSignByDoorid(String doorid) {
		if(doorid==null || "".equals(doorid)) doorid = "-1";
		Map map = new HashMap();
		map.put("doorid", doorid);
		this.updateManDoorHouseDelSign(map);
	}

	public void updateManDoorHouseDelSignByHouseid(String houseid) {
		if(houseid==null || "".equals(houseid)) houseid = "-1";
		Map map = new HashMap();
		map.put("houseid", houseid);
		this.updateManDoorHouseDelSign(map);

	}
	public void updateManDoorHouseDelSignByCommid(String commId){
		this.getSqlMapClientTemplate().delete("ManDoorHouse.updateManDoorHouseDelSignByCommid", commId);
	}

	public void updateDoorHouseEquipId(String houseId, String doorId,
			String webServiceEquipId) {
		ManDoorHouseBean manDoorHouseBean = new ManDoorHouseBean();
		manDoorHouseBean.setHouseid(houseId);
		manDoorHouseBean.setDoorid(doorId);
		manDoorHouseBean.setEquipid(webServiceEquipId);
		this.getSqlMapClientTemplate().delete("ManDoorHouse.updateDoorHouseEquipId", manDoorHouseBean);
		
	}

	public void deleteDoorHouseById(String id) {
		this.getSqlMapClientTemplate().delete("ManDoorHouse.deleteDoorHouseById", id);
	}

	public List<ManDoorHouseBean> findDoorHouseListByCommId(String commId) {
		return this.getSqlMapClientTemplate().queryForList("ManDoorHouse.findDoorHouseListByCommId", commId);
	}

	public void deleteDoorHouseByCommId(String commId) {
		this.getSqlMapClientTemplate().delete("ManDoorHouse.deleteDoorHouseByCommId", commId);
	}

	public String findDoorLinkHouseCount(String doorId, String deletesign) {
		Map map = new HashMap();
		map.put("doorid", doorId);
		map.put("deletesign", deletesign);
		return (String) this.getSqlMapClientTemplate().queryForObject("ManDoorHouse.findDoorLinkHouseCount", map);
	}

	public String findHouseLinkDoorAndCardCount(String houseId) {
		return (String) this.getSqlMapClientTemplate().queryForObject("ManDoorHouse.findHouseLinkDoorAndCardCount", houseId);
	}	
}
