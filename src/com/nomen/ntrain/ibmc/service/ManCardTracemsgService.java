package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardRecordBean;
import com.nomen.ntrain.ibmc.bean.ManCardTraceMsgBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockRecordInfo;

/**
 * @description 刷卡历史
 * @author 
 * @date 2015-2-7
 */
public interface ManCardTracemsgService {
	
	/**
	 * 查询刷卡短信列表
	 * @param map
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<ManCardTraceMsgBean> findManCardTracemsgBeanList(Map map,int tagpage,int record);
	
}
