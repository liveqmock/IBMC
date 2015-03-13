package com.nomen.ntrain.base.bean;

import java.io.Serializable;

public class BaseOptLogBean implements Serializable{
	private String id;			//id(主键)
	private String optremark;	//维护内容
	private String opttype;		//操作类型
	private String argvalues;	//参数
	private String optuserid;	//操作人id
	private String optusername;	//操作人
	private String optdeptname;	//操作人部门
	private String optdeptid;	//操作人部门id 
	private String opttime;		//操作时间
	private String intflag;      //操作类型标识
	
	//set 和get方法  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOptremark() {
		return optremark;
	}
	public void setOptremark(String optremark) {
		this.optremark = optremark;
	}
	public String getOpttype() {
		return opttype;
	}
	public void setOpttype(String opttype) {
		this.opttype = opttype;
	}
	public String getArgvalues() {
		return argvalues;
	}
	public void setArgvalues(String argvalues) {
		this.argvalues = argvalues;
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
	public String getOptdeptname() {
		return optdeptname;
	}
	public void setOptdeptname(String optdeptname) {
		this.optdeptname = optdeptname;
	}
	public String getOptdeptid() {
		return optdeptid;
	}
	public void setOptdeptid(String optdeptid) {
		this.optdeptid = optdeptid;
	}
	public String getOpttime() {
		return opttime;
	}
	public void setOpttime(String opttime) {
		this.opttime = opttime;
	}
	public String getIntflag() {
		return intflag;
	}
	public void setIntflag(String intflag) {
		this.intflag = intflag;
	}
  
	
}
