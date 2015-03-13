package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.dao.SysCommunityDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class SysCommunityDAOImpl extends NsoftBaseDao implements
		SysCommunityDAO {

	public List<SysCommunityBean> findVillageListByScope(Map map, int tagpage,
			int record) {
		map.put("total", this.getObjectTotal("SysCommunity.findVillageListByScope", map));
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findVillageListByScope", map, tagpage, record);
	}
	
	public List<SysCommunityBean> findVillageListByScope(Map map) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findVillageListByScope", map);
	}
	
	public List<SysCommunityBean> findHouseListByScope(Map map, int tagpage,
			int record) {
		map.put("total", this.getObjectTotal("SysCommunity.findHouseListByScope", map));
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findHouseListByScope", map, tagpage, record);
	}
	
	public List<SysCommunityBean> findHouseListByScope(Map map) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findHouseListByScope", map);
	}
	
	public List<SysCommunityBean> findHouseLinkCardByScope(Map map, int tagpage,int record) {
		map.put("total", this.getObjectTotal("SysCommunity.findHouseLinkCardByScope", map));
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findHouseLinkCardByScope", map, tagpage, record);
	}
	
	public List<SysCommunityBean> findHouseLinkCardByScope(Map map) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findHouseLinkCardByScope", map);
	}
	
	public List<SysCommunityBean> findSysCommunityList(Map map){
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findSysCommunityList", map);
	}
	
	public SysCommunityBean findSysCommunityBeanById(String commId){
		return (SysCommunityBean)this.getSqlMapClientTemplate().queryForObject("SysCommunity.findSysCommunityBeanById", commId);
	}
	
	public String insertSysCommunityBean(SysCommunityBean bean) {
		return (String)this.getSqlMapClientTemplate().insert("SysCommunity.insertSysCommunityBean", bean);
	}

	public void updateSysCommunityBean(SysCommunityBean bean) {
		this.getSqlMapClientTemplate().update("SysCommunity.updateSysCommunityBean", bean);
	}

	public void updateSysCommunityUseSign(SysCommunityBean bean) {
		this.getSqlMapClientTemplate().update("SysCommunity.updateSysCommunityUseSign", bean);
	}

	public void updateSysCommDelSignByCommId(String commId) {
		this.getSqlMapClientTemplate().update("SysCommunity.updateSysCommDelSignByCommId", commId);
	}

	public void updateSysCommunityPath(SysCommunityBean bean) {
		this.getSqlMapClientTemplate().update("SysCommunity.updateSysCommunityPath", bean);
	}

	public List<SysCommunityBean> findCommRegionListByMap(Map map) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findCommRegionListByMap", map);
	}

	public String findSysCommunityNextOrder(Map map) {
		return (String)this.getSqlMapClientTemplate().queryForObject("SysCommunity.findSysCommunityNextOrder", map);
	}

	public List<SysCommunityBean> findManRoomListByHouseId(Map map, int tagpage, int record) {
		map.put("total", this.getObjectTotal("SysCommunity.findManRoomListByHouseId", map));
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findManRoomListByHouseId", map, tagpage, record);
	}

	public List<SysCommunityBean> findManRoomListByHouseId(Map map) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findManRoomListByHouseId", map);
	}
	
	public List<SysCommunityBean> findHouseLinkDoorList(Map map, int page, int record) {
		map.put("total", this.getObjectTotal("SysCommunity.findHouseLinkDoorList", map));
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findHouseLinkDoorList", map, page, record);
	}
	
	public List<SysCommunityBean> findSysCommListByCommLevDesc(String commId) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SysCommunity.findSysCommListByCommLevDesc", commId);
	}

	public void deleteSysCommByCommId(String commId) {
		this.getSqlMapClientTemplate().update("SysCommunity.deleteSysCommByCommId", commId);
	}

	public int findSysCommNameIsRepeat(Map map) {
		return Integer.parseInt((String)this.getSqlMapClientTemplate().queryForObject("SysCommunity.findSysCommNameIsRepeat", map));
	}

}
