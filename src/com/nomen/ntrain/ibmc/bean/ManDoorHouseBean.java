package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 设备管理_门口机关联房产配置表
 * @author 
 * @date 2015-1-20
 */
public class ManDoorHouseBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String id;               //主键（id）
    private String doorid;           //门口机（ID）
    private String houseid;          //房产（ID）
    private String createdate;       //创建时间
    private String updatedate;       //修改时间
    
    private String equipid;     	 //webserivce返回 门口机+房产外键ID
    private String deletesign;       //删除标记
    
	//生成get,set方法
    
	public String getDeletesign() {
		return deletesign;
	}
	public void setDeletesign(String deletesign) {
		this.deletesign = deletesign;
	}
	public String getCreatedate() {
		return createdate;
	}
	public String getEquipid() {
		return equipid;
	}
	public void setEquipid(String equipid) {
		this.equipid = equipid;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getDoorid() {
		return doorid;
	}
	public void setDoorid(String doorid) {
		this.doorid = doorid;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	
}
