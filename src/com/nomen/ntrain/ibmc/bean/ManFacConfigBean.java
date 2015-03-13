package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 设备管理_厂商设备型号配置表
 * @author  郑学仕
 * @date 2015-1-20
 */
public class ManFacConfigBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;						//主键（id）
	private String facid;					//厂商id
	private String facmodel;				//型号
	
	
	//set和get方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFacid() {
		return facid;
	}
	public void setFacid(String facid) {
		this.facid = facid;
	}
	public String getFacmodel() {
		return facmodel;
	}
	public void setFacmodel(String facmodel) {
		this.facmodel = facmodel;
	}
	
	

}
