package com.nomen.ntrain.base.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseOptLogBean;
import com.nomen.ntrain.base.dao.BaseOptLogDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseOptLogDAOImpl extends NsoftBaseDao implements BaseOptLogDAO {

	public void deleteBaseOptLog(String id) {
		this.getSqlMapClientTemplate().delete("BaseOptLog.deleteBaseOptLog", id);

	}

	public BaseOptLogBean findBaseOptLogById(String id) {
		return (BaseOptLogBean)this.getSqlMapClientTemplate().queryForObject("BaseOptLog.findBaseOptLogById",id);
	}

	public List findBaseOptLogList(Map map, int page, int record) {
		map.put("total", this.getObjectTotal("BaseOptLog.findBaseOptLogList", map));
		return this.getSqlMapClientTemplate().queryForList("BaseOptLog.findBaseOptLogList", map, page, record);
	}

	public void insertBaseOptLog(BaseOptLogBean baseOptLogBean) {
		this.getSqlMapClientTemplate().insert("BaseOptLog.insertBaseOptLog",baseOptLogBean);

	}

}
