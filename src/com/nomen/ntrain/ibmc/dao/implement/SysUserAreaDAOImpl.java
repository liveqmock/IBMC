package com.nomen.ntrain.ibmc.dao.implement;

import com.nomen.ntrain.ibmc.bean.SysUserAreaBean;
import com.nomen.ntrain.ibmc.dao.SysUserAreaDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class SysUserAreaDAOImpl  extends NsoftBaseDao implements SysUserAreaDAO {

	public void deleteSysUserAreaBean(String userid) {
		this.getSqlMapClientTemplate().delete("SysUserArea.deleteSysUserAreaBean", userid);
	}

	public String findSysUserAreaIdstr(String userid) {
		return (String) this.getSqlMapClientTemplate().queryForObject("SysUserArea.findSysUserAreaIdstr", userid);
	}

	public void insertSysUserAreaBean(SysUserAreaBean sysUserAreaBean) {
		this.getSqlMapClientTemplate().insert("SysUserArea.insertSysUserAreaBean", sysUserAreaBean);

	}

}
