package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 门卡管理_门卡库表POJO 
 * @author ljl
 * @date 2015-1-23
 */
public class ManCardBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String id;        //主键（ID）
    private String cardtype;  //门卡类型（1业主卡 2临时卡 3正式卡）
    private String cardno;    //门卡序列号
    private String cardmac;   //门卡内置ID
    private String houseid;   //房产（ID）
    private String roomid;    //房间ID
    private String ownerid;   //业主ID（存储人员表中的ID）
    private String rentid;    //租户ID（存储人员表中的ID）
    private String startdate; //生效时间
    private String enddate;   //有效时间
    private String operuserid;//操作人
    private String createdate;//创建时间
    private String updatedate;//修改时间
    private String unlockid;  //webservice返回的记录ID
    
	private String operusername;//操作人[辅助辅助	
    private String rentname;    //租客姓名
    private String rentidcard;  //租客身份证号
    private String deletesign;  
    private String ownername;  //业主名称
    private String housename;  //房产名称
    private String roomname;  //房间名称
    private String phone;     //刷卡短信通知号码
    private String traceid;   //短信通知主键ID
    
    
	//Get和Set方法

	public String getDeletesign() {
		return deletesign;
	}
	public void setDeletesign(String deletesign) {
		this.deletesign = deletesign;
	}
	public String getRentidcard() {
		return rentidcard;
	}
	public String getUnlockid() {
		return unlockid;
	}
	public void setUnlockid(String unlockid) {
		this.unlockid = unlockid;
	}
	public void setRentidcard(String rentidcard) {
		this.rentidcard = rentidcard;
	}
    public String getRentname() {
		return rentname;
	}
	public void setRentname(String rentname) {
		this.rentname = rentname;
	}
    public String getId() {
        return id;
    }
    public String getOperuserid() {
		return operuserid;
	}
	public void setOperuserid(String operuserid) {
		this.operuserid = operuserid;
	}
	public String getOperusername() {
		return operusername;
	}
	public void setOperusername(String operusername) {
		this.operusername = operusername;
	}
	public void setId(String id) {
        this.id = id;
    }
    public String getCardtype() {
        return cardtype;
    }
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
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
    public String getHouseid() {
        return houseid;
    }
    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }
    public String getRoomid() {
        return roomid;
    }
    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
    public String getOwnerid() {
        return ownerid;
    }
    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }
    public String getRentid() {
        return rentid;
    }
    public void setRentid(String rentid) {
        this.rentid = rentid;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return enddate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
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
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTraceid() {
		return traceid;
	}
	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}
}
