package com.nomen.ntrain.base.dao.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseMenuBean;
import com.nomen.ntrain.base.dao.BaseMenuDAO;
import com.nomen.ntrain.util.Constant;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseMenuDAOImpl extends NsoftBaseDao implements BaseMenuDAO {

	public List<BaseMenuBean> findBaseMenuTreeList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("BaseMenu.findBaseMenuTreeList",map);
	}

	public void deleteBaseMenu(String id) {
		this.getSqlMapClientTemplate().delete("BaseMenu.deleteBaseMenu",id);
	}

	public String findBaseMenuNextSort(String fatherid) {
		return (String)this.getSqlMapClientTemplate().queryForObject("BaseMenu.findBaseMenuNextSort",fatherid);
	}

	public void insertBaseMenu(BaseMenuBean baseMenuBean) {
		this.getSqlMapClientTemplate().insert("BaseMenu.insertBaseMenu",baseMenuBean);
	}

	public void updateBaseMenu(BaseMenuBean baseMenuBean) {
		this.getSqlMapClientTemplate().update("BaseMenu.updateBaseMenu",baseMenuBean);
	}

	public void updateBaseMenuUsesign(BaseMenuBean baseMenuBean) {
		this.getSqlMapClientTemplate().update("BaseMenu.updateBaseMenuUsesign",baseMenuBean);
	}

	public BaseMenuBean findBaseMenuBeanById(String id) {
		return (BaseMenuBean) this.getSqlMapClientTemplate().queryForObject("BaseMenu.findBaseMenuBeanById",id);
	}

	public List<BaseMenuBean> findBaseMenuByUser(String userid,String fatherid,String lev){
		Map map = new HashMap();
		map.put("lev",lev);
		map.put("fatherid",fatherid);
		map.put("userid",userid);
		return this.getSqlMapClientTemplate().queryForList("BaseMenu.findBaseMenuByUser",map);
	}

	public List<BaseMenuBean> findBaseMenuListByThread() {
		return this.getSqlMapClientTemplate().queryForList("BaseMenu.findBaseMenuListByThread");
	}
}
