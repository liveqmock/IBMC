package com.nomen.ntrain.ibmc.dao;

import java.util.List;

import com.nomen.ntrain.ibmc.bean.ManFacConfigBean;
/**
 *  @description 设备管理_厂商设备型号配置表
    @author 郑学仕
    @date 2015-1-20
 */

public interface ManFacConfigDAO {
	/**
	 * 查找设备管理_厂商设备型号列表
	 * @param manFacConfigBean
	 * @return
	 */
	public List findManFacConfigList(String id);
	
	/**
	 * 新增设备管理_厂商设备型号
	 * @param manFacConfigBean
	 */
	public void insertManFacConfigBean(ManFacConfigBean manFacConfigBean);
	
	/**
	 * 新增设备管理_厂商设备型号[提供ID]
	 * @param manFacConfigBean
	 */
	public void insertManFacConfigBean2(ManFacConfigBean manFacConfigBean);
	/**
	 * 删除设备管理_厂商设备型号
	 * @param id
	 */
	public void deleteManFacConfigBean(String id);
	/**
	 * 删除  厂商设备型号(删除厂商管理时使用)
	 * @return
	 */
	public void deleteManFacConfigByFacid(String id);
}
