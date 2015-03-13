package com.nomen.ntrain.annotation;

/**
 * 操作枚举
 * @author 连金亮
 * @date 2014-11-20
 */
public enum  LoginEnums{
	
	/***登录*/
	LOGIN(200,"登录"),
	/***注销*/
	LOGOUT(201,"注销");
	private int    key;
	private String desc;
	
	/**
	 * 构造方法
	 * @param eV
	 * @param eD
	 */
	private LoginEnums(int key,String desc){
		this.key = key;
		this.desc = desc; 
	}
	
	/**
	 * 类似Integer.valueOf设置方法
	 * @param key
	 * @return
	 */
	public static LoginEnums valueOf(int key){
		LoginEnums[] qT=LoginEnums.values();
		for (LoginEnums cs: qT){
			if (cs.getKey()==key){
				return cs;
			}
		}
		return null;
	}

	//set get方法
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
