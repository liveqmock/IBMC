package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysUserBean;
import com.nomen.ntrain.ibmc.dao.SysUserDAO;
import com.nomen.ntrain.util.NsoftBaseDao;
@SuppressWarnings("all")
public class SysUserDAOImpl extends NsoftBaseDao implements SysUserDAO {

	public List<SysUserBean> findSysUserList(Map map){
		return (List<SysUserBean>)this.getSqlMapClientTemplate().queryForList("SysUser.findSysUserList", map);
	}

	public SysUserBean findSysUserBeanById(String id){
		return (SysUserBean)this.getSqlMapClientTemplate().queryForObject("SysUser.findSysUserBeanById", id);
	}

	public String insertSysUser(SysUserBean sysUserBean){
		return (String)this.getSqlMapClientTemplate().insert("SysUser.insertSysUser", sysUserBean);
	}

	public void updateSysUser(SysUserBean sysUserBean){
		this.getSqlMapClientTemplate().update("SysUser.updateSysUser", sysUserBean);
	}

	public void deleteSysUserById(String id){
		this.getSqlMapClientTemplate().delete("SysUser.deleteSysUserById", id);
	}

	public List<SysUserBean> findSysUserByPage(Map map, int tagpage, int record) {
		map.put("total", this.getObjectTotal("SysUser.findSysUserList", map));
		return (List<SysUserBean>)this.getSqlMapClientTemplate().queryForList("SysUser.findSysUserList", map,tagpage,record);
	}

	public String findSysUserOrder(Map map) {
		return (String) this.getSqlMapClientTemplate().queryForObject("SysUser.findSysUserOrder", map);
	}

}
