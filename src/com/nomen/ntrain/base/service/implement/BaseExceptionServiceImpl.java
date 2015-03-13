package com.nomen.ntrain.base.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseExceptionBean;
import com.nomen.ntrain.base.dao.BaseExceptionDAO;
import com.nomen.ntrain.base.service.BaseExceptionService;

public class BaseExceptionServiceImpl implements BaseExceptionService {
    
	private BaseExceptionDAO baseExceptionDAO;
	
	public void deleteBaseException(Map map) {
		this.baseExceptionDAO.deleteBaseException(map);

	}

	public BaseExceptionBean findBaseExceptionBean(String id) {
		return this.baseExceptionDAO.findBaseExceptionBean(id);
	}

	public List<BaseExceptionBean> findBaseExceptionList(Map map, int page, int record) {
		return this.baseExceptionDAO.findBaseExceptionList(map,page,record);
	}

	public void insertBaseExceptionBean(BaseExceptionBean bean) {
		this.baseExceptionDAO.insertBaseExceptionBean(bean);
	}

	
	//set和get
	
	public BaseExceptionDAO getBaseExceptionDAO() {
		return baseExceptionDAO;
	}

	public void setBaseExceptionDAO(BaseExceptionDAO baseExceptionDAO) {
		this.baseExceptionDAO = baseExceptionDAO;
	}

}
