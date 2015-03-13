package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysConfigBean;
/**
 * @description 系统管理_系统配置业务逻辑层
 * @author ljl
 * @date 2015-1-23
 */
public interface SysConfigService {

	/**
	 * 查找系统管理_系统配置信息
	 * @param map {
	 *               configkey  KEY
	 *               commid     社区ID
	 *            }
	 * @return
	 */
	public List<SysConfigBean> findSysConfigList(Map map); 

	/**
	 * 添加系统管理_系统配置信息
	 * @param sysConfigBean
	 */
	public void saveSysConfig(SysConfigBean sysConfigBean);
	
	/**
	 * 删除系统管理_系统配置信息
	 * @param map {
	 *               configkey  KEY
	 *               commid     社区ID
	 *            }
	 * @return
	 */
	public void deleteSysConfig(Map map);
	
	/**
	 * 获取正式卡、临时卡、业主卡 对应的有效期限以及押金金额
	 * @param   commId 
	 * @return
	 */
	public SysConfigBean findSysConfigOfStandar(String commId);
}
