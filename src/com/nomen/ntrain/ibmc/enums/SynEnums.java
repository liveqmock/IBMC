package com.nomen.ntrain.ibmc.enums;

/**
 * 同步枚举
 * @author 连金亮
 * @date 2014-11-20
 */
public enum  SynEnums{
	/***成功（网络通）*/
	SUCC(1,"成功（网络通）"),
	/***失败（网络通）*/
	FALS(0,"失败（网络通）"),
	/***网络不通*/
	OUTLINE(-1,"网络不通");
	
	private int    key;
	private String desc;
	
	/**
	 * 构造方法
	 * @param eV
	 * @param eD
	 */
	private SynEnums(int key,String desc){
		this.key = key;
		this.desc = desc; 
	}
	
	/**
	 * 类似Integer.valueOf设置方法
	 * @param key
	 * @return
	 */
	public static SynEnums valueOf(int key){
		SynEnums[] qT=SynEnums.values();
		for (SynEnums cs: qT){
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