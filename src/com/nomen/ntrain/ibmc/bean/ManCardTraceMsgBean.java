package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * 短信通知详情表
 * @author lenovo
 *
 */
public class ManCardTraceMsgBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;		//主键id
	private String traceid;		//跟踪记录id
	private String touchdate;   //刷卡时间
	private String senddate;    //发送时间
	private String sendcount;	//已发送（最大次数为3次）
	private String sendtxt;		//发送内容
	private String phonenum;	//通知手机号
	private String rentname;	//租户姓名 
	private String iffee;		//是否付费（1系统付费 0用户付费）
	private String status;		//发送状态（0待发送 1发送成功 -1发送失败）
	
	private String phone;		//通知手机号码
	
	//以下为set get方法
	public String getIffee() {
		return iffee;
	}
	public void setIffee(String iffee) {
		this.iffee = iffee;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getRentname() {
		return rentname;
	}
	public void setRentname(String rentname) {
		this.rentname = rentname;
	}
	public String getSendcount() {
		return sendcount;
	}
	public void setSendcount(String sendcount) {
		this.sendcount = sendcount;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getSendtxt() {
		return sendtxt;
	}
	public void setSendtxt(String sendtxt) {
		this.sendtxt = sendtxt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTouchdate() {
		return touchdate;
	}
	public void setTouchdate(String touchdate) {
		this.touchdate = touchdate;
	}
	public String getTraceid() {
		return traceid;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
