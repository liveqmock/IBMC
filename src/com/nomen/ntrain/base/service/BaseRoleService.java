package com.nomen.ntrain.base.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.nomen.ntrain.base.bean.BaseRoleMenuBean;

public interface BaseRoleService {
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
	 * @param id
	 */
	public void deleteBaseRoleById(String id);
	
	/**
	 * 新增/修改 角色管理
	 * @param NetRoleBean
	 * @return
	 */
	public String saveBaseRole(BaseRoleBean baseRoleBean);
	
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
	 * @param id
	 * @return
	 */
	public String findBaseRoleMenuByIdStr(String id);
}
