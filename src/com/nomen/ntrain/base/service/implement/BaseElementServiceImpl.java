package com.nomen.ntrain.base.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseElementBean;
import com.nomen.ntrain.base.dao.BaseElementDAO;
import com.nomen.ntrain.base.service.BaseElementService;
import com.nomen.ntrain.util.PubFunc;

public class BaseElementServiceImpl implements BaseElementService {
	private BaseElementDAO baseElementDAO;
	private PubFunc func = new PubFunc();

	public void deleteBaseElement(String ele_id) {
		this.baseElementDAO.deleteBaseElement(ele_id);

	}

	public List<BaseElementBean> findBaseElementList(Map map) {
		return baseElementDAO.findBaseElementList(map);
	}
	
	public BaseElementBean findBaseElementBean(String ele_id) {
		return baseElementDAO.findBaseElementBean(ele_id);
	}

	public void saveBaseElement(BaseElementBean baseElementBean) {
		String express = "";
		express = "<button class=\"btn "+baseElementBean.getStyleclass()+"\" onClick=\""+baseElementBean.getClickevent()+"\">"+
				baseElementBean.getCodename()+"</button>"  ;
		baseElementBean.setExpress(express);
		if(func.IsEmpty(baseElementBean.getId())){
			this.baseElementDAO.insertBaseElement(baseElementBean);
		}else{
			this.baseElementDAO.updateBaseElement(baseElementBean);
		}

	}

	
	//set和get 方法
	public BaseElementDAO getBaseElementDAO() {
		return baseElementDAO;
	}

	public void setBaseElementDAO(BaseElementDAO baseElementDAO) {
		this.baseElementDAO = baseElementDAO;
	}

	

}
