package com.nomen.ntrain.base.bean;

import java.io.Serializable;

/**
 * @description 登录信息表
 * @author 连金亮
 * @date 2015-01-21
 */

public class LoginBean implements Serializable{
	private String id;              //人员主键ID
	private String username;        //用户名称
    private String roleids;         //人员对应的角色
    private String userpsd;
    private String levsign;         //人员所管理的级别
	private String userlogonid;     //记录登录人员登录日志在日志表中的id[辅助]
    
	//以下为set get方法
    public String getLevsign() {
		return levsign;
	}
	public void setLevsign(String levsign) {
		this.levsign = levsign;
	}
	public String getRoleids() {
		return roleids;
	}
	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserpsd() {
		return userpsd;
	}
	public void setUserpsd(String userpsd) {
		this.userpsd = userpsd;
	}
	public String getUserlogonid() {
		return userlogonid;
	}
	public void setUserlogonid(String userlogonid) {
		this.userlogonid = userlogonid;
	}
	
}