package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManFacConfigBean;
import com.nomen.ntrain.ibmc.bean.ManFactoryBean;

/**
 * @description 设备管理_厂商管理表
 * @author 郑学仕
 * @date 2015-1-20
 */
public interface ManFactoryService {

	/**
	 * 查找设备管理_厂商管理表列表信息[分页]
	 * @param map
	 * @param page
	 * @param record
	 * @return
	 */
	public List<ManFactoryBean> findManFactoryList(Map map,int page,int record);

	/**
	 * 查找设备管理_厂商管理表列表信息[不分页]
	 * @param map
	 * @return
	 */
	public List<ManFactoryBean> findManFactoryList(Map map);
	/**
	 * 查找设备管理_厂商管理表列表信息Bean
	 * @param manFactoryBean
	 * @return
	 */
	public ManFactoryBean findManFactoryBean(ManFactoryBean manFactoryBean);
	/**
	 * 保存（新增/更新操作）
	 * @param manFactoryBean
	 */
	public void saveManFactoryBean(Map map,ManFactoryBean manFactoryBean);
	/**
	 * 删除设备管理_厂商管理表
	 * @param id
	 */
	public void deleteManFactoryBean(String id);
	
	/**
	 * 查找设备管理_厂商设备型号列表
	 * @param manFacConfigBean
	 * @return
	 */
	public List findManFacConfigList(String id);
	
	/**
	 * 删除设备管理_厂商设备型号
	 * @param id
	 */
	public void deleteManFacConfigBean(String id);
}
