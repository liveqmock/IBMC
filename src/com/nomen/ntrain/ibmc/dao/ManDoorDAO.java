package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;

/**
 * @description 设备管理_门口机管理表数据库操作接口
 * @author 
 * @date 2015-01-20
 */
public interface ManDoorDAO {

	/**
	 * 查找 设备管理_门口机管理表 列表信息[分页]
	 * @param map：id，主键ID；facid，厂商ID ；confid，厂商型号ID；
	 * 			  optuserid，操作人；fields，关键字段；keyword，关键字内容；
	 * 			  sortfield，排序字段；
	 * @return
	 */
	public List<ManDoorBean> findManDoorList(Map map,int page,int record);
	
	/**
	 * 查找 设备管理_门口机管理表 列表信息[不分页]
	 * @param map：id，主键ID；facid，厂商ID ；confid，厂商型号ID；
	 * 			  optuserid，操作人；fields，关键字段；keyword，关键字内容；
	 * 			  sortfield，排序字段；
	 * @return
	 */
	public List<ManDoorBean> findManDoorList(Map map);

	/**
	 * 查找 设备管理_门口机管理表Bean信息  
	 * @param map 
	 *  		id，主键ID ；doormac，门口机mac；
	 * @return
	 */
	public ManDoorBean findManDoorBean(Map map);

	/**
	 * 添加 设备管理_门口机管理表Bean信息  
	 * @param 
	 * 		 manDoorBean 门口机管理表Bean
	 * @return
	 */
	public String insertManDoorBean(ManDoorBean manDoorBean);

	/**
	 * 更新 设备管理_门口机管理表Bean信息
	 * @param 
	 *       manDoorBean 门口机管理表Bean
	 * @return
	 */
	public void updateManDoorBean(ManDoorBean manDoorBean);
	
	/**
	 * 删除人员_业主/租户表信息[根据主键ID]
	 * @param 
	 *        idStr 主键idstr [必传参数]
	 */
	public void deleteManDoor(String idStr);
	
}
