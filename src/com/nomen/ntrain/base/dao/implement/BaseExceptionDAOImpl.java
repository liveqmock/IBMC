package com.nomen.ntrain.base.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseExceptionBean;
import com.nomen.ntrain.base.dao.BaseExceptionDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseExceptionDAOImpl extends NsoftBaseDao implements BaseExceptionDAO {

	public void deleteBaseException(Map map) {
		this.getSqlMapClientTemplate().delete("BaseException.deleteBaseException",map);

	}

	public BaseExceptionBean findBaseExceptionBean(String id) {
		return (BaseExceptionBean)this.getSqlMapClientTemplate().queryForObject("BaseException.findBaseExceptionBean",id);
	}

	public List<BaseExceptionBean> findBaseExceptionList(Map map, int page, int record) {
		map.put("total", this.getObjectTotal("BaseException.findBaseExceptionList", map));
		return this.getSqlMapClientTemplate().queryForList("BaseException.findBaseExceptionList",map,page,record);
	}

	public void insertBaseExceptionBean(BaseExceptionBean bean) {
		this.getSqlMapClientTemplate().insert("BaseException.insertBaseExceptionBean",bean);

	}

}
