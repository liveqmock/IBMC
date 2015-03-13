package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 系统管理_管理区域配置表
 * @author 郑学仕
 * @date 2015-1-23
 */
public class SysUserAreaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;                  //用户id
	private String areaid;                  //管理区域id（叶子节点到根节点都需要记录）
	private String createdate;              //创建时间
	private String updatedate;              //修改时间
	
	private String areaidstr;               //ID串【辅助】
	

	//set和get
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
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
	public String getAreaidstr() {
		return areaidstr;
	}
	public void setAreaidstr(String areaidstr) {
		this.areaidstr = areaidstr;
	}
     
}
