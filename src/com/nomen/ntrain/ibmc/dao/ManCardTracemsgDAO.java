package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardTraceMsgBean;

/**
 * @description 刷卡短信查询列表
 * @author 
 * @date 
 */
public interface ManCardTracemsgDAO {
	
	/**
	 * 查询刷卡短信列表
	 * @param map
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<ManCardTraceMsgBean> findManCardTracemsgBeanList(Map map,int tagpage,int record);

}
