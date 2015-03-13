package com.nomen.ntrain.quart.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.quart.bean.NetQuartBean;

/**
 * @description 调度器逻辑层
 * @author 梁桂钊
 * @date 2013-09-16
 */
public interface NetQuartService {
	/**
     * 获取调度器任务实体Bean
     * 
     * @param id
     * @return
     */
	public NetQuartBean findNetQuartBean(String id);
	
    /**
     * 获取调度器任务实体列表[分页列表]
     * @param map
     * 		jobstatus 状态
     *      page 
     *      record
     * @return
     */
	public List findNetQuartList(Map map,int page,int record);
	
    /**
     * 获取调度器任务实体列表[不分页]
     * 
     * @param map
     * 		jobstatus 状态
     * @return
     */
	public List findNetQuartList(Map map);

	/**
	 * 保存调度器任务实体
	 * 
	 * @param netQuartBean
	 * @return
	 */
	public String saveNetQuart(NetQuartBean netQuartBean);
    
	/**
	 * 删除调度器任务实体Bean
	 * 
	 * @param id
	 * @return
	 */
	public void deleteNetQuart(String id); 
	
	/**
	 * 删除调度器任务实体Bean(根据任务维护时间)
	 * 
	 * @param id
	 * @return
	 */
	public void deleteNetQuartByDate(String date); 
	
	/**
	 * 启用\禁用 更新
	 * @param netQuartBean
	 */
	public void updateNetQuartJobstatus(NetQuartBean netQuartBean);
	
	
	/**
	 * 项目启动时加载所有启动的任务
	 */
	public void IniLoadClass();
}
