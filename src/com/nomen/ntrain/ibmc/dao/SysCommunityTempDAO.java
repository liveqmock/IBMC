package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;

/**
 * @description 系统管理_社区/房产表临时表
 * @author 
 * @date 2015-03-03
 */
public interface SysCommunityTempDAO {
	
	/**
	 * 应用于：房产导入中先删除操作人员(optuserid)导入的数据
	 * @param map
	 *        optuserid   操作人
	 * 		  parentid  社区id(parentid)
	 * @return
	 */
	public void deleteSysCommTempData(Map map);
	
	/**
	 * 新增房产表临时表
	 * @param sysCommunityBean
	 * @return
	 */
	public String insertSysCommTempBean(SysCommunityBean sysCommunityBean);
	
	/**
	 * 更新临时导入房产信息数据中数据的有效性
	 * @param map
	 *        optuserid   操作人
	 * 		  parentid  社区id(parentid)
	 */
	public void updateSysCommTempData(Map map);
	
	/**
	 * 查询统计导入房产信息数据中存在错误数据的记录总数
	 * @param map
	 * 		  errorflag   错误标识
	 *        optuserid   操作人
	 * 		  parentid  社区id(parentid)
	 * @return
	 */
	public int findSysCommTempErrorCount(Map map);
	
	/**
	 * 保存临时表中的数据导入到正式表中
	 * @param map
	 *        optuserid   操作人
	 * 		  parentid  社区id(parentid)
	 */
	public void saveSysCommTempDataIntoRegular(Map map);
	
	/**
	 * 查询临时表中此次导入的数据列表[预览页面中]
	 * @param map
	 *        optuserid   操作人
	 * 		  parentid  社区id(parentid)
	 *        fields    关键字段
	 *        keyword   关键字
	 *        sortfield 排序号
	 * @return
	 */
	public List<SysCommunityBean> findSysCommTempList(Map map);
	
	/**
	 * 仅查询此次房产导入中房产信息[预览页面中]
	 * @param map
	 *        optuserid   操作人
	 * 		  parentid  社区id(parentid)
	 *        fields    关键字段
	 *        keyword   关键字
	 *        sortfield 排序号
	 * @return
	 */
	public List<SysCommunityBean> findSysCommTempHouseList(Map map);
}
