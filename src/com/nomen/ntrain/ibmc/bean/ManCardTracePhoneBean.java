package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * 刷卡短信通知配置表
 * @author 
 *
 */
public class ManCardTracePhoneBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String traceid;		//主键（id）
	private String cardid;		//门卡id
	private String phone;		//通知手机号码
	private String optuserid;		//操作人
	private String createdate;		//创建时间
	private String updatedate;		//修改时间
	private String enddate;		//通知截止时间

	//以下为set get方法
	
	public String getTraceid() {
		return traceid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getOptuserid() {
		return optuserid;
	}
	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
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
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
