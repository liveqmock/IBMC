package com.nomen.ntrain.ibmc.enums;

/**
 * 操作枚举
 * @author 连金亮
 * @date 2014-11-20
 */
public enum  SourceEnums{
	/***房产*/
	HOUSE(100,"房产"),
	/***房产门口机关联*/
	HOUSE_DOOR(101,"门口机"),
	/***门卡操作*/
	CARD(102,"门卡");
	
	private int    key;
	private String desc;
	
	/**
	 * 构造方法
	 * @param eV
	 * @param eD
	 */
	private SourceEnums(int key,String desc){
		this.key = key;
		this.desc = desc; 
	}
	
	/**
	 * 类似Integer.valueOf设置方法
	 * @param key
	 * @return
	 */
	public static SourceEnums valueOf(int key){
		SourceEnums[] qT=SourceEnums.values();
		for (SourceEnums cs: qT){
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