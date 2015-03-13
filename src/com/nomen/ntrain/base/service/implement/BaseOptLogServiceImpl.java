package com.nomen.ntrain.base.service.implement;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.nomen.ntrain.base.bean.BaseOptLogBean;
import com.nomen.ntrain.base.dao.BaseOptLogDAO;
import com.nomen.ntrain.base.service.BaseOptLogService;

public class BaseOptLogServiceImpl implements BaseOptLogService {
    private BaseOptLogDAO baseOptLogDAO;
	public void deleteBaseOptLogByIdStr(String idStr) {
		if(idStr!=null && idStr.length()> 0){
			String[] idArr = StringUtils.split(idStr,",");
			for(String id : idArr){
				this.baseOptLogDAO.deleteBaseOptLog(id);
			}
		   }	

	}

	public BaseOptLogBean findBaseOptLogById(String id) {
		return this.baseOptLogDAO.findBaseOptLogById(id);
	}

	public List findBaseOptLogList(Map map, int page, int record) {
		return this.baseOptLogDAO.findBaseOptLogList(map, page, record);
	}
	
	public void insertBaseOptLog(BaseOptLogBean baseOptLogBean) {
		this.baseOptLogDAO.insertBaseOptLog(baseOptLogBean);		
	}

	
    //set 和 get方法 
	public BaseOptLogDAO getBaseOptLogDAO() {
		return baseOptLogDAO;
	}

	public void setBaseOptLogDAO(BaseOptLogDAO baseOptLogDAO) {
		this.baseOptLogDAO = baseOptLogDAO;
	}


	
	

}
