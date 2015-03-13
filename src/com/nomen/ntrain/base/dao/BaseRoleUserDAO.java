package com.nomen.ntrain.base.dao;


import com.nomen.ntrain.base.bean.BaseRoleUserBean;

public interface BaseRoleUserDAO {
	
	/**
	 * 删除用户信息
	 * @param userid
	 */
	public void deleteBaseRoleUserByUserid(String userid);
	
	/**
	 * 新增用户信息
	 * @param netRoleUserBean
	 * @return
	 */
	public String insertBaseRoleUser(BaseRoleUserBean baseRoleUserBean);
	
	/**
	 * 修改用户信息
	 * @param userid
	 */
	public void updateBaseRoleUser(BaseRoleUserBean baseRoleUserBean);
}
