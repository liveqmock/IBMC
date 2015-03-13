package com.nomen.ntrain.base.bean;

import java.io.Serializable;


/**
 * 莆田岗位培训_权限设置
 * @author 郑学仕
 * @date 
 */
public class BaseRoleUserBean implements Serializable{
	private String userid;		//关联人员ID
	private String roleids;		//角色[A,B,C角色]
	private String optuserid;   //维护人
	private String optusername; //维护人
	
	//新增
	private String username;    //关联人员[辅助]
	private String rolename;    //关联人员[辅助]
	
	
	public String getRoleids() {
		return roleids;
	}
	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getOptuserid() {
		return optuserid;
	}
	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}
	public String getOptusername() {
		return optusername;
	}
	public void setOptusername(String optusername) {
		this.optusername = optusername;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
