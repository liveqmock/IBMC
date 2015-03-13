package com.nomen.ntrain.base.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.nomen.ntrain.base.bean.BaseRoleMenuBean;
import com.nomen.ntrain.base.dao.BaseRoleDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseRoleDAOImpl extends NsoftBaseDao implements BaseRoleDAO {

	public void deleteBaseRoleById(String id) {
		this.getSqlMapClientTemplate().delete("BaseRole.deleteBaseRoleById",id);
	}

	public BaseRoleBean findBaseRoleBeanById(String id) {
		return (BaseRoleBean) this.getSqlMapClientTemplate().queryForObject("BaseRole.findBaseRoleBeanById",id);
	}

	public List<BaseRoleBean> findBaseRoleByUseridList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("BaseRole.findBaseRoleByUseridList", map);
	}

	public String findBaseRoleImpCount(String roleid) {
		return (String) this.getSqlMapClientTemplate().queryForObject("BaseRole.findBaseRoleImpCount",roleid);
	}

	public List<BaseRoleBean> findBaseRoleList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("BaseRole.findBaseRoleList", map);
	}

	public List<BaseRoleBean> findBaseRoleListByPage(Map map, int tagpage,
			int record) {
		map.put("total", this.getObjectTotal("BaseRole.findBaseRoleList", map));
		return this.getSqlMapClientTemplate().queryForList("BaseRole.findBaseRoleList", map, tagpage, record);
	}

	public String insertBaseRole(BaseRoleBean baseRoleBean) {
		return (String) this.getSqlMapClientTemplate().insert("BaseRole.insertBaseRole",baseRoleBean);
	}

	public void updateBaseRole(BaseRoleBean baseRoleBean) {
		this.getSqlMapClientTemplate().update("BaseRole.updateBaseRole",baseRoleBean);

	}

	public void deleteBaseRoleMenuByRoleId(String id) {
		this.getSqlMapClientTemplate().delete("BaseRole.deleteBaseRoleMenuByRoleId",id);

	}

	public String insertBaseRoleMenu(BaseRoleMenuBean baseRoleMenuBean) {
		return (String) this.getSqlMapClientTemplate().insert("BaseRole.insertBaseRoleMenu", baseRoleMenuBean);
	}

	public List <BaseRoleMenuBean> findBaseRoleMenuListByRole(String id) {
		return this.getSqlMapClientTemplate().queryForList("BaseRole.findBaseRoleMenuListByRole", id);
	}
}
