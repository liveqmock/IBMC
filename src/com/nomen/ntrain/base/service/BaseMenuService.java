package com.nomen.ntrain.base.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseMenuBean;

public interface BaseMenuService {
	/**
	 * 获取菜单列表[菜单维护应用]
	 * @param map
	 * 		usesign
	 * 		fatherid
	 * @return
	 */
	public List<BaseMenuBean> findBaseMenuTreeList(Map map);
	
	
	/**
	 * 根据登录人员获取大类菜单
	 * @param map：userid：人员ID
	 * @return
	 */
	public List findUserBigMenuList(String userId);
	
	/**
	 * 根据登录人员以及大类菜单ID获取子菜单
	 * @param map：userid：人员ID
	 * @return
	 */
	public List findUserChildMenuTreeList(String userId,String fatherid);
	
	/**
	 * 保存菜单
	 * @param BaseMenuBean
	 */
	public void saveBaseMenu(BaseMenuBean baseMenuBean);
	
	/**
	 * 删除菜单
	 * @param id
	 */
	public void deleteBaseMenu(String id);
	/**
	 * 启用禁用菜单
	 * @param BaseMenuBean [id,usesign]
	 */
	public void updateBaseMenuUsesign(BaseMenuBean baseMenuBean);
	
	/**
	 * 获取下一个菜单排序号
	 * @param fatherid
	 */
	public String findBaseMenuNextSort(String fatherid);
	
	/**
	 * 根据id查找记录
	 * @param id
	 * @return
	 */
	public BaseMenuBean findBaseMenuBeanById(String id);

	/**
	 * 获取登录人员具有的菜单
	 * @param 
	 * @return
	 */
	public List<BaseMenuBean> findBaseMenuByUser(String lev,String fatherid,String userid);
	
	/**
	 * 获取菜单列表[该方法用来定时加载菜单]
	 * @return
	 */
	public List<BaseMenuBean> findBaseMenuListByThread();
}
