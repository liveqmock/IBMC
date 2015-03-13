package com.nomen.ntrain.base.bean;

import java.io.Serializable;
/**
 * 异常日志
 * @author 郑学仕
 *
 */
public class BaseExceptionBean implements Serializable{
	private String id;							//主键id
	private String errorclass;					//错误类名
	private String errormethod;					//错误方法
	private String errormsg;					//错误信息
	private String errordate;					//错误日期
	
	
	//get 和 set方法
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getErrorclass() {
		return errorclass;
	}
	public void setErrorclass(String errorclass) {
		this.errorclass = errorclass;
	}
	public String getErrormethod() {
		return errormethod;
	}
	public void setErrormethod(String errormethod) {
		this.errormethod = errormethod;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getErrordate() {
		return errordate;
	}
	public void setErrordate(String errordate) {
		this.errordate = errordate;
	}


}
