package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 设备管理_门口机管理表
 * @author 
 * @date 2015-1-20
 */
public class ManDoorBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String id;              //主键（id）
    private String name;            //门口机名称
    private String facid;           //厂商id
    private String confid;          //门口机型号
    private String doorip;          //门口机ip
    private String doormac;         //门口机mac
    private String gateway;         //门口机网关
    private String submask;         //门口机子网掩码
    private String doortype;        //门口机进出门类型（1进门 2出门）
	private String softver;         //门口机软件版本[上行修改]
    private String hardver;         //门口机硬件版本[上行修改]
    private String remark;          //备注
    private String optuserid;       //操作人
    private String createdate;      //创建时间
    private String updatedate;      //修改时间
    
    //辅助字段
    private String optusername;   	 //操作人员姓名
    private String facname;     	 //门口机厂商
    private String facmodel;         //门口机型号
    private String equipid;          //webservice返回的ID
    
	//生成get,set方法
	public String getEquipid() {
		return equipid;
	}
	public void setEquipid(String equipid) {
		this.equipid = equipid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOptuserid() {
		return optuserid;
	}
	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getConfid() {
		return confid;
	}
	public void setConfid(String confid) {
		this.confid = confid;
	}
	public String getDoorip() {
		return doorip;
	}
	public void setDoorip(String doorip) {
		this.doorip = doorip;
	}
	public String getDoormac() {
		return doormac;
	}
	public void setDoormac(String doormac) {
		this.doormac = doormac;
	}
	public String getFacid() {
		return facid;
	}
	public void setFacid(String facid) {
		this.facid = facid;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getHardver() {
		return hardver;
	}
	public void setHardver(String hardver) {
		this.hardver = hardver;
	}
	public String getSoftver() {
		return softver;
	}
	public void setSoftver(String softver) {
		this.softver = softver;
	}
	public String getSubmask() {
		return submask;
	}
	public void setSubmask(String submask) {
		this.submask = submask;
	}
	public String getOptusername() {
		return optusername;
	}
	public void setOptusername(String optusername) {
		this.optusername = optusername;
	}
	public String getFacmodel() {
		return facmodel;
	}
	public void setFacmodel(String facmodel) {
		this.facmodel = facmodel;
	}
	public String getFacname() {
		return facname;
	}
	public void setFacname(String facname) {
		this.facname = facname;
	}
    public String getDoortype() {
		return doortype;
	}
	public void setDoortype(String doortype) {
		this.doortype = doortype;
	}
}
