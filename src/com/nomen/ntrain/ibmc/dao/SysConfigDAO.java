package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysConfigBean;

/**
 * @description 系统管理_系统配置表数据库操作接口
 * @author ljl
 * @date 2015-02-18
 */
public interface SysConfigDAO {

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
	 * @return
	 */
	public String insertSysConfig(SysConfigBean sysConfigBean);

	/**
	 * 更新系统管理_系统配置信息
	 * @param sysConfigBean
	 * @return
	 */
	public void updateSysConfig(SysConfigBean sysConfigBean);

	/**
	 * 删除系统管理_系统配置信息
	 * @param map {
	 *               configkey  KEY
	 *               commid     社区ID
	 *            }
	 * @return
	 */
	public void deleteSysConfig(Map map);

}
