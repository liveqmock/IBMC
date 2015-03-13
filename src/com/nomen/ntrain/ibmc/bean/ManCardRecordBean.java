package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * 刷卡历史记录表
 * @author lenovo
 *
 */
public class ManCardRecordBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;		//主键（id）
	private String cardid;	//门卡ID
	private String cardno;	//门卡序列号
	private String cardmac; //门卡内置id
	private String roomid;	//房间id
	private String roomname; //房间名称
	private String rentname;//租户姓名
	private String houseid; //房产id
	private String housename;//房产名称
	private String equipname;//门口机名称
	private String equipid;//门口机id
	private String touchdate;//刷卡时间
	private String touchimg;//抓拍图片地址
	private String synid;   //webservice端ID
	private String cardtype;//门卡类型
	private String ownername;//业主名称
	
	//以下为set get方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getCardmac() {
		return cardmac;
	}
	public void setCardmac(String cardmac) {
		this.cardmac = cardmac;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getRentname() {
		return rentname;
	}
	public void setRentname(String rentname) {
		this.rentname = rentname;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getHousename() {
		return housename;
	}
	public void setHousename(String housename) {
		this.housename = housename;
	}
	public String getEquipname() {
		return equipname;
	}
	public void setEquipname(String equipname) {
		this.equipname = equipname;
	}
	public String getEquipid() {
		return equipid;
	}
	public void setEquipid(String equipid) {
		this.equipid = equipid;
	}
	public String getTouchdate() {
		return touchdate;
	}
	public void setTouchdate(String touchdate) {
		this.touchdate = touchdate;
	}
	public String getTouchimg() {
		return touchimg;
	}
	public void setTouchimg(String touchimg) {
		this.touchimg = touchimg;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getSynid() {
		return synid;
	}
	public void setSynid(String synid) {
		this.synid = synid;
	}
}
