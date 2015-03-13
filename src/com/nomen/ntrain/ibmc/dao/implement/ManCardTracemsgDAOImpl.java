package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardRecordBean;
import com.nomen.ntrain.ibmc.bean.ManCardTraceMsgBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.dao.ManCardRecordDAO;
import com.nomen.ntrain.ibmc.dao.ManCardTracemsgDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManCardTracemsgDAOImpl extends NsoftBaseDao implements ManCardTracemsgDAO{
	
	public List<ManCardTraceMsgBean> findManCardTracemsgBeanList(Map map,int tagpage, int record) {
		map.put("total", this.getObjectTotal("ManCardTracemsg.findManCardTracemsgBeanList", map));
		return (List<ManCardTraceMsgBean>)this.getSqlMapClientTemplate().queryForList("ManCardTracemsg.findManCardTracemsgBeanList", map,tagpage,record);
	}
	
}
