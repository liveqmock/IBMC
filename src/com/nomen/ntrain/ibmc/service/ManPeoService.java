package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.nomen.ntrain.ibmc.bean.ManPeoBean;

/**
 * @description 人员_业主/租户表业务逻辑层
 * @author 
 * @date 2015-01-18
 */
public interface ManPeoService {

	/**
	 * 查找 人员_业主/租户表 列表信息[分页]
	 * @param map：owner_sign，是否是业主标识 ；rent_sign，是否是租客标识；
	 * 			  peo_idcard，身份证号；fields，关键字段；keyword，关键字内容；
	 * 			  sortfield，排序字段；
	 * @return
	 */
	public List<ManPeoBean> findManPeoList(Map map,int page,int record);

	/**
	 * 查找 人员_业主/租户表 列表信息[不分页]
	 * @param map：owner_sign，是否是业主标识 ；rent_sign，是否是租客标识；
	 * 			  peo_idcard，身份证号；fields，关键字段；keyword，关键字内容；
	 * 			  sortfield，排序字段；
	 * @return
	 */
	public List<ManPeoBean> findManPeoList(Map map);
	
	/**
	 * 查找 人员_业主/租户表Bean信息[通过主键ID]  
	 * @param  peo_id，主键ID 
	 * @return
	 */
	public ManPeoBean findManPeoBeanById(String id);
	
	/**
	 * 查找 人员_业主/租户表Bean信息  
	 * @param map 
	 *  		peo_id，主键ID ；peo_idcard，身份证号；
	 * @return
	 */
	public ManPeoBean findManPeoBean(Map map);

	/**
	 * 添加 人员_业主/租户表Bean信息  
	 * @param 
	 * 			ManPeoBean
	 * @return
	 */
	public String insertManPeoBean(ManPeoBean manPeoBean);

	/**
	 * 更新 人员_业主/租户表Bean信息
	 * @param manPeoBean
	 * @return
	 */
	public void updateManPeoBean(ManPeoBean manPeoBean);
	
	/**
	 * 保存 人员_业主/租户表Bean信息
	 * @param map
	 *        manPeoBean  人员_业主/租户表Bean信息
	 *        fileFolder  保存图片路径
	 * @return 
	 */
	public String saveManPeo(Map map);
	
	/**
	 * 	查询人员_业主/租户表中是否存在该人员
	 * @param idcard 身份证号码
	 * @return  false 表示不存在
	 *          true  表示系统中存在该人员
	 */
	public boolean findManPeoIsExist(String idcard);

	/**
	 * 删除人员_业主/租户表表信息[根据身份证号码]
	 * @param idcard 身份证号码
	 * @return
	 */
	public void deleteManPeoByIdCard(String idcard);

	/**
	 * 删除人员_业主/租户表信息[根据主键ID]
	 * @param map
	 *        peoid 主键id [必传参数]
	 *        idcard 身份证号码
	 */
	public void deleteManPeo(Map map);
	
	/**
	 * 人员查询[导出人员Excel]列表
	 * @param map
	 * @param response
	 */
	public void saveManPeoExpExcel(Map map,HttpServletResponse response);

	/**
	 * 根据门卡序列号cardno查询租客，业主信息
	 * @param map：cardtype 门卡类型
	 *             cardno   门卡序列号cardno
	 * @return
	 */
	public List<ManPeoBean> findManPeoListByCardno(Map map);
	
}
