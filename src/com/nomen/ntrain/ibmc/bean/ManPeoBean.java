package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 人员_业主/租户表
 * @author 
 * @date 2015-1-18
 */
public class ManPeoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;       		//主键（id）
    private String ownersign;       //业主标记（1是 0否）
    private String rentsign;       	//租客标记（1是 0否）
    private String name;       		//姓名
    private String sex;       		//性别（0男,1女）
    private String birthday;        //出生年月
    private String nation;       	//民族
    private String idcard;       	//证件号码
    private String telephone;       //联系电话
    private String unitname;       	//工作单位
    private String address;       	//地址
    private String photopath;       //相片（地址）
    private String remark;       	//备注
    private String createdate;      //创建时间
    private String updatedate;      //修改时间
    private String optuserid;       //操作人
    private String pyszm;           //拼音首字母
	
	//辅助字段
    private String optusername;   	//操作人员姓名
    private String photobase64;   	//相片编码64
    private String housename;     	//房产名称
    private String roomname;      	//房间名称
    
	//生成get,set方法
    public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCreatedate() {
		return createdate;
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
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getOptuserid() {
		return optuserid;
	}
	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}
	public String getOwnersign() {
		return ownersign;
	}
	public void setOwnersign(String ownersign) {
		this.ownersign = ownersign;
	}
	public String getPhotopath() {
		return photopath;
	}
	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRentsign() {
		return rentsign;
	}
	public void setRentsign(String rentsign) {
		this.rentsign = rentsign;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getOptusername() {
		return optusername;
	}
	public void setOptusername(String optusername) {
		this.optusername = optusername;
	}
	public String getPhotobase64() {
		return photobase64;
	}
	public void setPhotobase64(String photobase64) {
		this.photobase64 = photobase64;
	}
	public String getHousename() {
		return housename;
	}
	public void setHousename(String housename) {
		this.housename = housename;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
    public String getPyszm() {
		return pyszm;
	}
	public void setPyszm(String pyszm) {
		this.pyszm = pyszm;
	}
}
