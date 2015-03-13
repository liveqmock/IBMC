package com.nomen.ntrain.base.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseElementBean;
import com.nomen.ntrain.base.dao.BaseElementDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseElementDAOImpl extends NsoftBaseDao implements BaseElementDAO {

	public void deleteBaseElement(String ele_id) {
		this.getSqlMapClientTemplate().delete("BaseElement.deleteBaseElement", ele_id);
	}

	public List<BaseElementBean> findBaseElementList(Map map) {
		return this.getSqlMapClientTemplate().queryForList("BaseElement.findBaseElementList", map);
	}
	

	public BaseElementBean findBaseElementBean(String ele_id) {
		return (BaseElementBean) this.getSqlMapClientTemplate().queryForObject("BaseElement.findBaseElementBean", ele_id);
	}

	public void insertBaseElement(BaseElementBean baseElementBean) {
		this.getSqlMapClientTemplate().insert("BaseElement.insertBaseElement", baseElementBean);

	}

	public void updateBaseElement(BaseElementBean baseElementBean) {
		this.getSqlMapClientTemplate().update("BaseElement.updateBaseElement", baseElementBean);
	}


}
