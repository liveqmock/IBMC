package com.nomen.ntrain.ibmc.util;

public class SynMessage {	
	private boolean success;  //状态码(true成功 false失败)
	private String  message;  //提示信息
	private Object  obj;      //其他返回值(如ID等)
	private int  count;    
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
