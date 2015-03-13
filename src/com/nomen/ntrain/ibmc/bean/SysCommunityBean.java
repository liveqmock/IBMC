package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 系统管理_社区/房产表
 * @author ljl
 * @date 2015-1-18
 */
public class SysCommunityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;			//主键（id）
	private String parentid;	//父级（id）
	private String commdetail;	//区域/社区详细
	private String commname;	//区域名称/社区名称/房产地址/房间名称
	private String ownerid;		//业主id（存储人员表中的id）
	private String commtype;	//类型（1 独立业主 2共有业主）
	private String commlev;		//级别（0省 1市 2区 3村 4房产 5房间）
	private String usesign;		//启用状态（1启用 0禁用）
	private String commorder;	//排序号
	private String remark;		//备注
	private String commzip;		//邮编
	private String commpath;	//代码路径
	private String createdate;	//创建时间
	private String updatedate;	//修改时间
	private String downsign;    //数据下行状态(0待下行  1成功 -1失败)
	private String downdate;    //数据下行时间
	private String tasksign;    //状态
	
	public String getTasksign() {
		return tasksign;
	}
	public void setTasksign(String tasksign) {
		this.tasksign = tasksign;
	}
	private String childnum;    //子节点数量[辅助]
	private String parentname;  //上级名称[辅助]
	private String ownername;   //业主名称[辅助]
	private String owneridcard;   //业主身份证号码[辅助]
	private String mcardnum;    //主卡数量[辅助]
	private String tcardnum;    //临时卡数量[辅助]
	private String rcardnum;    //正式卡数量[辅助]
	private String doorcount;   //门口机数量
	
	//以下三个字段是用临时表中的字段
	private String optuserid;   //操作人
	private String errorflag;   //错误标识（0：成功，1：错误）
	private String errorstr;   //错误提示字符串
	private String telephone;  //联系电话
	
	
	public String getMcardnum() {
		return mcardnum;
	}
	public void setMcardnum(String mcardnum) {
		this.mcardnum = mcardnum;
	}
	public String getTcardnum() {
		return tcardnum;
	}
	public void setTcardnum(String tcardnum) {
		this.tcardnum = tcardnum;
	}
	public String getRcardnum() {
		return rcardnum;
	}
	public void setRcardnum(String rcardnum) {
		this.rcardnum = rcardnum;
	}

	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getChildnum() {
		return childnum;
	}
	public void setChildnum(String childnum) {
		this.childnum = childnum;
	}
	//生成get,set方法
	public String getParentid() {
		return parentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getCommdetail() {
		return commdetail;
	}
	public void setCommdetail(String commdetail) {
		this.commdetail = commdetail;
	}
	public String getCommname() {
		return commname;
	}
	public void setCommname(String commname) {
		this.commname = commname;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public String getCommtype() {
		return commtype;
	}
	public void setCommtype(String commtype) {
		this.commtype = commtype;
	}
	public String getCommlev() {
		return commlev;
	}
	public void setCommlev(String commlev) {
		this.commlev = commlev;
	}
	public String getUsesign() {
		return usesign;
	}
	public void setUsesign(String usesign) {
		this.usesign = usesign;
	}
	public String getCommorder() {
		return commorder;
	}
	public void setCommorder(String commorder) {
		this.commorder = commorder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCommzip() {
		return commzip;
	}
	public void setCommzip(String commzip) {
		this.commzip = commzip;
	}
	public String getCommpath() {
		return commpath;
	}
	public void setCommpath(String commpath) {
		this.commpath = commpath;
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
	public String getDoorcount() {
		return doorcount;
	}
	public void setDoorcount(String doorcount) {
		this.doorcount = doorcount;
	}
	public String getDownsign() {
		return downsign;
	}
	public void setDownsign(String downsign) {
		this.downsign = downsign;
	}
	public String getDowndate() {
		return downdate;
	}
	public void setDowndate(String downdate) {
		this.downdate = downdate;
	}
	public String getOwneridcard() {
		return owneridcard;
	}
	public void setOwneridcard(String owneridcard) {
		this.owneridcard = owneridcard;
	}
	public String getErrorflag() {
		return errorflag;
	}
	public void setErrorflag(String errorflag) {
		this.errorflag = errorflag;
	}
	public String getErrorstr() {
		return errorstr;
	}
	public void setErrorstr(String errorstr) {
		this.errorstr = errorstr;
	}
	public String getOptuserid() {
		return optuserid;
	}
	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
