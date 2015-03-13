package com.nomen.ntrain.ibmc.dao;

import com.nomen.ntrain.ibmc.bean.SysUserAreaBean;

/**
 * @description 系统管理_管理区域配置表
	@author 郑学仕
	@date 2015-1-23
 */
public interface SysUserAreaDAO {
	/**
	 * 查找系统管理_管理区域ID串
	 * @param userid
	 * @return
	 */
	public String findSysUserAreaIdstr(String userid);
	/**
	 *  新增系统管理_管理区域配置
	 * @param sysUserAreaBean
	 */
	public void insertSysUserAreaBean(SysUserAreaBean sysUserAreaBean);
	/**
	 * 删除系统管理_管理区域配置
	 * @param userid
	 */
	public void deleteSysUserAreaBean(String userid);
}
