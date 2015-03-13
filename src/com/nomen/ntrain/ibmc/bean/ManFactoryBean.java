package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 设备管理_厂商管理表
 * @author 郑学仕
 * @date 2015-1-20
 */
public class ManFactoryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;								//主键（id）
	private String facname;							//厂商名称
	private String contact;							//厂商联系人
	private String telephone;						//联系电话
	private String address;							//地址
	private String remark;							//备注
	private String createdate;						//创建时间
	private String updatedate;						//修改时间
	
	
	 //set和get方法
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFacname() {
		return facname;
	}
	public void setFacname(String facname) {
		this.facname = facname;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}


}
