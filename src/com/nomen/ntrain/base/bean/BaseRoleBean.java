package com.nomen.ntrain.base.bean;

import java.io.Serializable;

/**
 * 莆田岗位培训_角色定义
 * @author 郑学仕
 * @dete 
 */

public class BaseRoleBean implements Serializable{
	private String id;			//角色id
	private String rolename;	//角色名称
	private String rgroup;      //角色所在组（0用户组 1系统组）
	private String remark;		//备注
	private String purview;		//权限
	private String mainuser;	//维护人员
	private String weight;      //权值（权值越小，权限越大）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPurview() {
		return purview;
	}
	public void setPurview(String purview) {
		this.purview = purview;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getMainuser() {
		return mainuser;
	}
	public void setMainuser(String mainuser) {
		this.mainuser = mainuser;
	}
	public String getRgroup() {
		return rgroup;
	}
	public void setRgroup(String rgroup) {
		this.rgroup = rgroup;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
}
