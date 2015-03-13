package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;

/**
 * @description 设备管理_门口机管理表 业务逻辑层
 * @author 
 * @date 2015-01-23
 */
public interface ManDoorHouseService {
	
	/**
	 * 查找 设备管理_房屋关联门口机 列表信息[不分页]
	 * @param map
	 *        houseid 房产id
	 *        fields
	 *        keyword
	 * @return
	 */
	public List<ManDoorBean> findManHouseLinkDoorList(Map map);
	
	
	/**
	 * 查找 房产对应的门口机列表[同步使用]
	 *           houseId 房产id
	 * @return
	 */
	public List<ManDoorHouseBean> findDoorListByHouseId(String houseId);
	
	/**
	 * 添加设备管理_门口机关联配置表信息
	 * @param manDoorHouseBean 门口机管理表Bean
	 *        houseid  房产id
	 *        dooridstr 门口机id[多个以,隔开]
	 * @return
	 */
	public void insertManDoorHouseBean(ManDoorHouseBean manDoorHouseBean);
	
	/**
	 * 删除设备管理_门口机关联配置表信息
	 * @param manDoorHouseBean 门口机管理表Bean
	 *        houseid  房产id
	 *        dooridstr 门口机id[多个以,隔开]
	 */
	public void deleteManDoorHouseBean(ManDoorHouseBean manDoorHouseBean);
	
	/**
	 * 查找统计设备管理_门口机关联房产的个数
	 * @param doorId    门口机Id
	 * @param deletesign 删除标识
	 * @return
	 */
	public String findDoorLinkHouseCount(String doorId,String deletesign);

	/**
	 * 删除房产时验证该房产下是否有关联门口机,
	 * 并且房产下面是否有门卡
	 * @param houseId    房产houseId
	 * @return
	 */
	public String findHouseLinkDoorAndCardCount(String houseId);
	
}
