package com.nomen.ntrain.base.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseLogonBean;
import com.nomen.ntrain.base.bean.LoginBean;

/**
 * @description 登录模块  
 * @author 钱新红
 * @date 2009-05-18
 */

public interface LoginDAO 
{
	/**
	 * 查询登录人员信息
	 * @param map
	 * @return
	 */
	public List findLoginUser(Map map);
	
	/**
	 * 记录登录日志
	 * @param baseLogonBean
	 * @return
	 */
	public String insertBaseLogon(BaseLogonBean baseLogonBean);
	
	/**
	 * 记录注销日志
	 * @param baseLogonBean
	 * @return
	 */
	public void updateBaseLogon(String id);
	
	/**
	 * 修改密码
	 * @param loginBean
	 */
	public void updatePassword(LoginBean loginBean);
}
