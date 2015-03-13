package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysConfigBean;
import com.nomen.ntrain.ibmc.bean.SysUserBean;
import com.nomen.ntrain.ibmc.dao.SysConfigDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class SysConfigDAOImpl extends NsoftBaseDao implements SysConfigDAO {

	public List<SysConfigBean> findSysConfigList(Map map) {
		return (List<SysConfigBean>)this.getSqlMapClientTemplate().queryForList("SysConfig.findSysConfigList", map);
	}

	public String insertSysConfig(SysConfigBean sysConfigBean) {
		this.getSqlMapClientTemplate().insert("SysConfig.insertSysConfig",sysConfigBean);
		return "1";
	}

	public void updateSysConfig(SysConfigBean sysConfigBean) {
		this.getSqlMapClientTemplate().update("SysConfig.updateSysConfig",sysConfigBean);
	}

	public void deleteSysConfig(Map map) {
		this.getSqlMapClientTemplate().delete("SysConfig.deleteSysConfig",map);
	}

}
