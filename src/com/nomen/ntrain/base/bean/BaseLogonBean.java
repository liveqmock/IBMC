package com.nomen.ntrain.base.bean;

import java.io.Serializable;

/**
 * @description 登录日志表 
 * @author lianjinliang
 * @date   2010-11-12
 */
public class BaseLogonBean implements Serializable{
	private String id;        //id(主键)
	private String unitid;    //单位id
	private String userid;    //人员id
	private String username;  //人员姓名
	private String deptname;  //部门名称
	private String intime;    //登录时间
	private String outtime;   //退出时间
	private String strflag;   //预留字段（字符串）
	private String intflag;   //预留字段（整型）
	
	//以下为set get方法
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntflag() {
		return intflag;
	}
	public void setIntflag(String intflag) {
		this.intflag = intflag;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getStrflag() {
		return strflag;
	}
	public void setStrflag(String strflag) {
		this.strflag = strflag;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}
