package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;

/**
 * @description 系统管理_系统管理_社区/房产表数据库操作接口
 * @author ljl
 * @date 2015-1-18
 */
public interface SysCommunityDAO {
	/**
	 * 应用于：系统管理_社区管理
	 * 通过区域条件获取社区/（村）列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 [fields]
	 *                 [keyword]
	 *             }
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<SysCommunityBean> findVillageListByScope(Map map,int tagpage,int record);
	
	/**
	 * 应用于：系统管理_社区管理
	 * 通过区域条件获取社区/（村）列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 [fields]
	 *                 [keyword]
	 *             }
	 * @return
	 */
	public List<SysCommunityBean> findVillageListByScope(Map map);
	

	/**
	 * 应用于：房产管理_房产信息维护
	 * 通过区域条件获取房产列表
	 * @param map  {
	 *                 commpath  根据该path查询社区/村的列表数据（该path可能是村的上级或者上上级）
	 *                 [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 [fields]
	 *                 [keyword]
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
	 *                 [usesign]   启用/禁用（若无该条件默认查询启用的数据）
	 *                 userid    人员ID
	 *                 [fields]
	 *                 [keyword]
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
	 * 查询区域列表（无条件过滤））
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
	 * @param commId
	 * @return
	 */
	public SysCommunityBean findSysCommunityBeanById(String commId);
	
	/**
	 * 新增
	 * @param bean
	 */
	public String insertSysCommunityBean(SysCommunityBean bean);
	
	/**
	 * 修改
	 * @param bean
	 */
	public void updateSysCommunityBean(SysCommunityBean bean);
	
	/**
	 * 修改路径
	 * @param bean {commid commpath}
	 */
	public void updateSysCommunityPath(SysCommunityBean bean);
	
	/**
	 * 修改启用/禁用状态
	 * @param bean
	 */
	public void updateSysCommunityUseSign(SysCommunityBean bean);
	
	/**
	 * 删除[虚拟删除]
	 * @param commId
	 */
	public void updateSysCommDelSignByCommId(String commId);
	
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
	 * 根据commId 倒序查询 房间,房产,社区,区树状图 列表
	 * @param string
	 *        commId  主键Id
	 * @return
	 */
	public List<SysCommunityBean> findSysCommListByCommLevDesc(String commId);
	
	/**
	 * 删除[真实删除]
	 * @param commId
	 */
	public void deleteSysCommByCommId(String commId);
	
	/**
	 * 查询本社区中的房产地址是否重复
	 * @param map
	 *        parentid 父级id[必传]
	 *        commname 房产地址名称[必传]
	 */
	public int findSysCommNameIsRepeat(Map map);

}
