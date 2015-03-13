package com.nomen.ntrain.base.bean;

import java.io.Serializable;
/**
 * 角色对应菜单表
 * @author 郑学仕
 *
 */

public class BaseRoleMenuBean implements Serializable{
	private String roleid;   //角色ID
	private String menuid;   //菜单ID
	
	
	//set和get
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}


}
