package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.excel.IbmcExcelOutForJxlImpl;
/**
 * @description 系统管理_系统管理_社区/房产表业务逻辑层
 * @author ljl
 * @date 2015-1-18
 */
public interface SysCommunityService {
	/**
	 * 应用于：系统管理_社区管理
	 * 通过区域条件获取社区（村）列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 usesign   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 fields
	 *                 keyword
	 *             }
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findVillageListByScope(Map map,int tagpage,int record);

	/**
	 * 应用于：系统管理_社区管理
	 * 通过区域条件获取社区（村）列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 usesign   启用/禁用（若无该条件默认查询启用的数据）
	 *                 fields
	 *                 keyword
	 *             }
	 * @return
	 */
	public List<SysCommunityBean> findVillageListByScope(Map map);
	
	/**
	 * 应用于：房产管理_房产信息维护
	 * 通过区域条件获取房产列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 usesign   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 fields
	 *                 keyword
	 *             }
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findHouseListByScope(Map map,int tagpage,int record);

	/**
	 * 应用于：房产管理_房产信息维护
	 * 通过区域条件获取房产列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 usesign   启用/禁用（若无该条件默认查询启用的数据）
	 *                 fields
	 *                 keyword
	 *             }
	 * @return
	 */
	public List<SysCommunityBean> findHouseListByScope(Map map);
	

	/**
	 * 应用于：门卡管理_各种卡管理列表
	 * 通过区域条件获取业主房产列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 usesign   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 fields
	 *                 keyword
	 *             }
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findHouseLinkCardByScope(Map map,int tagpage,int record);

	/**
	 * 应用于：门卡管理_各种卡管理列表
	 * 通过区域条件获取业主房产列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 usesign   启用/禁用（若无该条件默认查询启用的数据）
	 *                 fields
	 *                 keyword
	 *             }
	 * @return
	 */
	public List<SysCommunityBean> findHouseLinkCardByScope(Map map);

	/**
	 * 查询区域列表（无条件过滤）
	 * @param map  {
	 *                parentid
	 *                maxlev
	 *                querytree
	 *             }
	 * @return
	 */
	public List<SysCommunityBean> findSysCommunityList(Map map);
	
	/**
	 * 详细
	 * @param id
	 * @return
	 */
	public SysCommunityBean findSysCommunityBeanById(String id);
	
	/**
	 * 新增/修改
	 * @param bean
	 */
	public String saveSysCommunityBean(SysCommunityBean bean);
	
	/**
	 * 修改启用/禁用状态
	 * @param bean
	 */
	public void updateSysCommunityUseSign(SysCommunityBean bean);
	
	/**
	 * 删除(虚拟删除)
	 * @param idStr 房产,房间主键idStr
	 *        dellev 房产,房间删除标识[4:房产，5:房间]
	 */
	public void deleteSysCommunity(String commIds,String dellev);
	
	/**
	 * 查询人员可管理的区域列表
	 * @param map  {
	 *                userid
	 *                levsign 数据的开始级别
	 *                maxlev  数据的结束级别
	 *             }
	 * @return
	 */
	public List<SysCommunityBean> findCommRegionListByMap(Map map);
	
	/**
	 * 查询社区房产下一个节点的排序号
	 * @param map
	 *        parentid 父级（ID）[必传]
	 *        ownerid  业主ID
	 */
	public String findSysCommunityNextOrder(Map map);
	
	/**
	 * 应用于：房产管理_房产信息维护_房间列表
	 * 通过房产查询房产下面的房间列表
	 * @param map  {
	 *                 parentid  父亲ID[房产主键ID]
	 *                 [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *                 [sortfield] 排序号
	 *                 [fields]
	 *                 [keyword]
	 *             }
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findManRoomListByHouseId(Map map,int tagpage,int record);

	/**
	 * 应用于：房产管理_房产信息维护_房间列表
	 * 通过房产查询房产下面的房间列表
	 * @param map  {
	 *                 parentid  父亲ID[房产主键ID]
	 *                 [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *                 [sortfield] 排序号
	 *                 [fields]
	 *                 [keyword]
	 *             }
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findManRoomListByHouseId(Map map);
	
	/**
	 * 应用于：设备管理_门口机管理
	 * 通过区域条件获取房产关联的门口机 列表
	 * @param map
	 *        commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *        [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *        userid    人员ID
	 *        [fields]
	 *        [keyword]
	 * @param page
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findHouseLinkDoorList(Map map, int page, int record);
	
	/**
	 * 查询本社区中的房产地址是否重复
	 * @param map
	 *        parentid 父级id[必传]
	 *        commname 房产地址名称[必传]
	 */
	public int findSysCommNameIsRepeat(Map map);
}
