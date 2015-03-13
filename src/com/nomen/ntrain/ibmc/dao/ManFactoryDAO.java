package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManFactoryBean;


/**
 * @description 设备管理_厂商管理表
 * @author 郑学仕
 * @date 2015-1-20
 */
public interface ManFactoryDAO {
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
	 * 新增设备管理_厂商管理表
	 * @param manFactoryBean
	 */
	public String insertManFactoryBean(ManFactoryBean manFactoryBean);
	/**
	 * 更新设备管理_厂商管理表
	 * @param manFactoryBean
	 */
	public void updateManFactoryBean(ManFactoryBean manFactoryBean);
	/**
	 * 删除设备管理_厂商管理表
	 * @param id
	 */
	public void deleteManFactoryBean(String id);

}
