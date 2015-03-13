package com.nomen.ntrain.ibmc.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManCardTraceMsgBean;
import com.nomen.ntrain.ibmc.dao.ManCardTracemsgDAO;
import com.nomen.ntrain.ibmc.service.ManCardTracemsgService;

public class ManCardTracemsgServiceImpl extends BaseServiceImpl implements ManCardTracemsgService{
	
	private ManCardTracemsgDAO manCardTracemsgDAO;
	
	public List<ManCardTraceMsgBean> findManCardTracemsgBeanList(Map map, int tagpage, int record) {
		return this.manCardTracemsgDAO.findManCardTracemsgBeanList(map, tagpage, record);
	}
	
	//以下为set get方法
	public ManCardTracemsgDAO getManCardTracemsgDAO() {
		return manCardTracemsgDAO;
	}

	public void setManCardTracemsgDAO(ManCardTracemsgDAO manCardTracemsgDAO) {
		this.manCardTracemsgDAO = manCardTracemsgDAO;
	}
	
}
