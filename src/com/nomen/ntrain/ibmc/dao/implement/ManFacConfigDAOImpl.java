package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;

import com.nomen.ntrain.ibmc.bean.ManFacConfigBean;
import com.nomen.ntrain.ibmc.dao.ManFacConfigDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManFacConfigDAOImpl extends NsoftBaseDao implements ManFacConfigDAO {

	public void deleteManFacConfigBean(String id) {
		this.getSqlMapClientTemplate().delete("ManFacConfig.deleteManFacConfigBean", id);

	}
	
	public void deleteManFacConfigByFacid(String id) {
		this.getSqlMapClientTemplate().delete("ManFacConfig.deleteManFacConfigByFacid", id);
		
	}

	public List findManFacConfigList(String id) {
		return this.getSqlMapClientTemplate().queryForList("ManFacConfig.findManFacConfigList", id);
	}

	public void insertManFacConfigBean(ManFacConfigBean manFacConfigBean) {
		this.getSqlMapClientTemplate().insert("ManFacConfig.insertManFacConfigBean", manFacConfigBean);

	}
	
	public void insertManFacConfigBean2(ManFacConfigBean manFacConfigBean) {
		this.getSqlMapClientTemplate().insert("ManFacConfig.insertManFacConfigBean2", manFacConfigBean);
	}

	public void deleteManFacConfigById(String id) {
		// TODO Auto-generated method stub
		
	}

}
