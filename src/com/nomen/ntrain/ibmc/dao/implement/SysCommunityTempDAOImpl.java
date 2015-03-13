package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.dao.SysCommunityTempDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class SysCommunityTempDAOImpl extends NsoftBaseDao implements SysCommunityTempDAO {

	public void deleteSysCommTempData(Map map) {
		this.getSqlMapClientTemplate().update("SysCommunityTemp.deleteSysCommTempData", map);
	}

	public String insertSysCommTempBean(SysCommunityBean sysCommunityBean) {
		return (String) this.getSqlMapClientTemplate().insert("SysCommunityTemp.insertSysCommTempBean", sysCommunityBean);
	}

	public void updateSysCommTempData(Map map) {
		this.getSqlMapClientTemplate().update("SysCommunityTemp.updateSysCommTempData", map);
	}
	
	public int findSysCommTempErrorCount(Map map) {
		return Integer.parseInt((String)this.getSqlMapClientTemplate().queryForObject("SysCommunityTemp.findSysCommTempErrorCount", map));
	}

	public void saveSysCommTempDataIntoRegular(Map map) {
		this.getSqlMapClientTemplate().update("SysCommunityTemp.saveSysCommTempDataIntoRegular", map);
	}

	public List<SysCommunityBean> findSysCommTempList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("SysCommunityTemp.findSysCommTempList", map);
	}

	public List<SysCommunityBean> findSysCommTempHouseList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("SysCommunityTemp.findSysCommTempHouseList", map);
	}
	
}
