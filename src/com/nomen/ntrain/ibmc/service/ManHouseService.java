package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;


/**
 * @description 设备管理_门口机管理表 业务逻辑层
 * @author 
 * @date 2015-01-20
 */
public interface ManHouseService {
	
	/**
	 * 保存导出[房产列表]Excel
	 * @param map
	 */
	public void saveManHouseExpExcel(Map map,HttpServletResponse response);
	
	/**
	 * 删除房产时验证该房产下是否有关联门口机,
	 * 并且房产下面是否有门卡
	 * @param houseId    房产houseId
	 * @return
	 */
	public String findHouseLinkDoorAndCardCount(String houseId);
	
	/**
	 * 房产信息 excel导入
	 * @param map
	 *        {
	 *        saveNameArr：保存文件名
	 *        fileFolder：保存路径
	 *        optuserid  操作人员
	 *        parentid   社区id
	 *        }
	 * @return
	 */
	public String saveManHouseImpExcel(Map map) throws Exception;
	
	/**
	 * 房产信息保存[从临时表中新增到正式表中]
	 * @param map
	 *        {
	 *        optuserid  操作人员
	 *        parentid   社区id
	 *        }
	 * @return
	 */
	public void saveManHouseByImpTempData(Map map);
	
	/**
	 * 查询临时表中此次导入的数据列表
	 * @param map
	 *        optuserid  操作人员
	 *        parentid   社区id
	 *        fields     关键字段
	 *        keyword    关键字
	 *        sortfield 排序号
	 * @return
	 */
	public List<SysCommunityBean> findSysCommTempList(Map map);
	
	/**
	 * 保存导出房产导入中验证不同的数据 Excel
	 * @param map
	 *        optuserid  操作人员
	 *        parentid   社区id
	 */
	public void saveManHouseTempExpExcel(Map map,HttpServletResponse response);
	
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
