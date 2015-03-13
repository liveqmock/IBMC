package com.nomen.ntrain.base.service;

import java.util.Map;
import com.nomen.ntrain.annotation.LoginEnums;
import com.nomen.ntrain.base.bean.LoginBean;

/**
 * @description 登录模块  
 * @author 连金亮
 * @date 2009-05-18
 */
public interface LoginService{
	/**
	 * [2010-11-23 主要用来进行登录验证的]
	 * 查询登录人员的信息，并返回LoginBean信息
	 * @param Map
	 * @return Map
	 */
	public Map findLoginUser(Map map); 
	
	
	/**
	 * 记录登录/注销日志
	 * @param loginBean
	 */
	public void insertLoginLog(LoginBean loginBean,LoginEnums logEnums);


	/**
	 * 修改密码
	 * @param loginBean
	 */
	public void updatePassword(LoginBean loginBean);
}
