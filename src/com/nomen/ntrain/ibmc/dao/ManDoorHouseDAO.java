package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;

/**
 * @description 设备管理_门口机管理表数据库操作接口
 * @author 
 * @date 2015-01-23
 */
public interface ManDoorHouseDAO {
	
	/**
	 * 查找 设备管理
	 * @param  
	 * @return
	 */
	public ManDoorHouseBean findDoorHouseBeanById(String id);
	
	/**
	 * 查找 设备管理_房屋关联门口机 列表信息[不分页]
	 * @param map
	 *           houseid 房产id
	 *           fields
	 *           keyword
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
	 * 查找 房产对应的门口机列表[通过门口机doorid]
	 * @param doorId
	 * @return
	 */
	public List<ManDoorHouseBean> findDoorHouseBeanByDoorId(String doorId);
	
	/**
	 * 通过房产ID和门口机ID查询[非已删除的记录]
	 * @param  
	 * @return
	 */
	public ManDoorHouseBean findDoorHouseBean(ManDoorHouseBean manDoorHouseBean);
	
	/**
	 * 通过房产ID和门口机ID查询[非已删除的记录]
	 * @param  
	 * @return
	 */
	public ManDoorHouseBean findDoorHouseBean(String houseId,String doorId);
	
	/**
	 * 添加设备管理_门口机关联配置表信息
	 * @param 
	 * 		 manDoorBean 门口机管理表Bean
	 * @return
	 */
	public String insertManDoorHouseBean(ManDoorHouseBean manDoorHouseBean);
	
	/**
	 * 删除房产对应的某一个门口机
	 */
	public void updateManDoorHouseDelSign(String houseId,String doorId);
	
	/**
	 * 删除门口机对应的房产配置表数据
	 * @param doorid   门口机id
	 */
	public void updateManDoorHouseDelSignByDoorid(String doorid);

	/**
	 * 删除房产对应的门口机
	 * @param houseid   房产Id
	 */
	public void updateManDoorHouseDelSignByHouseid(String houseid);

	/**
	 * 删除门口机同房产关联数据（commId可能为村、区等）
	 * @param commId
	 */
	public void updateManDoorHouseDelSignByCommid(String commId);
	
	/**
	 * 更新房屋+门口机对应返回的webservice记录ID
	 * @param houseId
	 * @param doorId
	 * @param webServiceEquipId
	 */
	public void updateDoorHouseEquipId(String houseId,String doorId,String webServiceEquipId);
	
	/**
	 * 删除房产同门口机配置表[真实删除]
	 * @param id
	 */
	public void deleteDoorHouseById(String id);
	
	/**
	 * 通过[省/市/区/村/房产]查找 门口机关联列表
	 * @param   commId 省/市/区/村/房产ID
	 * @return
	 */
	public List<ManDoorHouseBean> findDoorHouseListByCommId(String commId);
	
	/**
	 * 删除房产同门口机配置表[真实删除]
	 * @param id
	 */
	public void deleteDoorHouseByCommId(String commId);
	
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
