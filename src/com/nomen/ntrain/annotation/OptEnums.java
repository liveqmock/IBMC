package com.nomen.ntrain.annotation;

/**
 * 操作枚举
 * @author 连金亮
 * @date 2014-11-20
 */
public enum  OptEnums{
	/***新增*/
	INSERT(100,"新增"),
	/***修改*/
	UPDATE(101,"修改"),
	/***保存*/
	SAVE(102,"保存"),
	/***删除*/
	DELETE(103,"删除");
	
	private int    key;
	private String desc;
	
	/**
	 * 构造方法
	 * @param eV
	 * @param eD
	 */
	private OptEnums(int key,String desc){
		this.key = key;
		this.desc = desc; 
	}
	
	/**
	 * 类似Integer.valueOf设置方法
	 * @param key
	 * @return
	 */
	public static OptEnums valueOf(int key){
		OptEnums[] qT=OptEnums.values();
		for (OptEnums cs: qT){
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