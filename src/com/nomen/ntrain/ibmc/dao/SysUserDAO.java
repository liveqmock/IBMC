package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SysUserBean;

/**
 * @description 系统管理_用户表数据库操作接口
 * @author ljl
 * @date 2015-1-18
 */
public interface SysUserDAO {

	/**
	 * 查找系统管理_用户表列表信息
	 * @param map {
	 *               role_id  角色ID
	 *               fields   关键字查询字段
	 *               keyword  关键字
	 *            }
	 * @return
	 */
	public List<SysUserBean> findSysUserList(Map map);

	/**
	 * 查找系统管理_用户表列表信息[分页]
	 * @param map {
	 *               role_id  角色ID
	 *               fields   关键字查询字段
	 *               keyword  关键字
	 *            }
	 *         tagpage 当前页码
	 *         record  每页显示记录数
	 * @return
	 */
	public List<SysUserBean> findSysUserByPage(Map map,int tagpage,int record);

	/**
	 * 查找系统管理_用户表信息
	 * @param id
	 * @return
	 */
	public SysUserBean findSysUserBeanById(String id);

	/**
	 * 添加系统管理_用户表信息
	 * @param sysUserBean
	 * @return
	 */
	public String insertSysUser(SysUserBean sysUserBean);

	/**
	 * 更新系统管理_用户表信息
	 * @param sysUserBean
	 * @return
	 */
	public void updateSysUser(SysUserBean sysUserBean);

	/**
	 * 删除系统管理_用户表信息
	 * @param id
	 * @return
	 */
	public void deleteSysUserById(String id);
	
	/**
	 * 查找下一个排序号
	 * @param map
	 * @return
	 */
	public String findSysUserOrder(Map map);

}
