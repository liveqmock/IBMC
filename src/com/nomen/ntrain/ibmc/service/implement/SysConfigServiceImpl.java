package com.nomen.ntrain.ibmc.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysConfigBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.SysConfigDAO;
import com.nomen.ntrain.ibmc.service.SysConfigService;

public class SysConfigServiceImpl implements SysConfigService {
	private SysConfigDAO sysConfigDAO;
	
	public List<SysConfigBean> findSysConfigList(Map map) {
		return this.sysConfigDAO.findSysConfigList(map);
	}

	public void saveSysConfig(SysConfigBean sysConfigBean) {
		//判断记录是否存在，存在则修改，否则新增
		Map map = new HashMap();
		map.put("commid",sysConfigBean.getCommid());
		map.put("configkey",sysConfigBean.getConfigkey());
		List sysConfigList = this.sysConfigDAO.findSysConfigList(map);
		if(null == sysConfigList || sysConfigList.size() == 0){
			this.sysConfigDAO.insertSysConfig(sysConfigBean);
		}else{
			this.sysConfigDAO.updateSysConfig(sysConfigBean);
		}
	}

	public void deleteSysConfig(Map map) {
		this.sysConfigDAO.deleteSysConfig(map);
	}

	//以下为set get方法

	public SysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(SysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public SysConfigBean findSysConfigOfStandar(String commId) {
		Map map = new HashMap();
		map.put("commid",   commId);
		map.put("configkey",IbmcConstant.CONFIG_KEY_STANDAR);
		List sysConfigList = this.sysConfigDAO.findSysConfigList(map);
		SysConfigBean configBean = null;
		if(null != sysConfigList && sysConfigList.size() > 0){
			configBean =  (SysConfigBean)sysConfigList.get(0);
		}
		return configBean;
	}
}
