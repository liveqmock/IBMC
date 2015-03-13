package com.nomen.ntrain.base.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.nomen.ntrain.base.bean.BaseRoleMenuBean;

/**
 * @description 角色定义 
 * @author 郑学仕
 * @date 2014-11-14
 */
public interface BaseRoleDAO {
	/**
	 * 角色管理 列表[分页]
	 * @param map
	 *            mainuser 维护人员
	 * @param tagpage
	 * @param record
	 * @return
	 */
	public List<BaseRoleBean> findBaseRoleListByPage(Map map,int tagpage,int record);
	
	/**
	 * 角色管理 列表[非分页]
	 * @param map
	 * @return
	 */
	public List<BaseRoleBean> findBaseRoleList(Map map);
	
	/**
	 * 查询单条记录[通过id获取详细]
	 * @param id
	 * @return
	 */
	public BaseRoleBean findBaseRoleBeanById(String id);
	
	/**
	 * 删除一条记录[通过id删除]
	 * @param 
	 */
	public void deleteBaseRoleById(String id);

	/**
	 * 新增 角色管理
	 * @param BaseRoleBean
	 * @return
	 */
	public String insertBaseRole(BaseRoleBean baseRoleBean);

	/**
	 * 修改 角色管理
	 * @param BaseRoleBean
	 * @return
	 */
	public void updateBaseRole(BaseRoleBean baseRoleBean);
	
	/**
	 * 查询登录人员中角色管理权值列表
	 * @param map
	 *        userid 登录人员
	 * @return
	 */
	public List<BaseRoleBean> findBaseRoleByUseridList(Map map);
	
	/**
	 * 查询角色被引入的数量
	 * @param roleid
	 * @return
	 */
	public String findBaseRoleImpCount(String roleid);
	
	
	/**
	 * 角色菜单定义 列表
	 * @param baseRoleMenuBean
	 * @return
	 */
	public List<BaseRoleMenuBean> findBaseRoleMenuListByRole(String id);
	
	/**
	 * 删除[通过id删除]角色菜单定义
	 * @param id
	 */
	public void deleteBaseRoleMenuByRoleId(String id);

	/**
	 * 新增 角色菜单定义
	 * @param BaseRoleMenuBean
	 * @return
	 */
	public String insertBaseRoleMenu(BaseRoleMenuBean baseRoleMenuBean);
}
