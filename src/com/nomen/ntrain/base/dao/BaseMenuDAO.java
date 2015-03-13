package com.nomen.ntrain.base.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseMenuBean;
/**
 * 网考系统[菜单管理]
 * @author 连金亮
 *
 */
public interface BaseMenuDAO {
	/**
	 * 获取菜单列表[菜单维护应用]
	 * @param map
	 * 		usesign
	 * 		fatherid
	 * @return
	 */
	public List<BaseMenuBean> findBaseMenuTreeList(Map map);
	
	/**
	 * 新增菜单
	 * @param BaseMenuBean
	 */
	public void insertBaseMenu(BaseMenuBean BaseMenuBean);
	
	/**
	 * 修改菜单
	 * @param BaseMenuBean
	 */
	public void updateBaseMenu(BaseMenuBean BaseMenuBean);
	
	/**
	 * 删除菜单
	 * @param id
	 */
	public void deleteBaseMenu(String id);
	
	/**
	 * 启用禁用菜单
	 * @param BaseMenuBean [id,usesign]
	 */
	public void updateBaseMenuUsesign(BaseMenuBean BaseMenuBean);
	
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
	public List<BaseMenuBean> findBaseMenuByUser(String userid,String fatherid,String lev);
	
	/**
	 * 获取菜单列表[该方法用来定时加载菜单]
	 * @return
	 */
	public List<BaseMenuBean> findBaseMenuListByThread();
}
