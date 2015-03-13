package com.nomen.ntrain.quart.dao;

import java.util.List;
import java.util.Map;
import com.nomen.ntrain.quart.bean.NetQuartBean;

/**
 * @description 调度器数据库操作接口
 * @author 梁桂钊
 * @date 2013-09-16
 */
public interface NetQuartDAO {

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
     * 获取调度器任务实体列表
     * 
     * @param map
     * 		jobstatus 状态
     * @return
     */
	public List findNetQuartList(Map map);

	/**
	 * 添加调度器任务实体Bean
	 * 
	 * @param netQuartBean
	 * @return
	 */
	public String insertNetQuart(NetQuartBean netQuartBean);
	
    /**
     * 更新调度器任务实体Bean
     * 
     * @param netQuartBean
     */
    public void updateNetQuart(NetQuartBean netQuartBean);
    
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
}
