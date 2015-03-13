package com.nomen.ntrain.base.dao.implement;

import com.nomen.ntrain.base.bean.BaseOptLogBean; 
import com.nomen.ntrain.base.dao.BaseDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseDAOImpl extends NsoftBaseDao implements BaseDAO{
	/**
	 * 操作日志
	 * @param tableName
	 * @param pkId
	 * @return
	 */
	public boolean insertOperLog(BaseOptLogBean optLogBean){ 
		this.getSqlMapClientTemplate().insert("BaseLog.insertOperLog",optLogBean);
		return true;
	}
}
