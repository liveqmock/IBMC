package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 系统管理_用户表POJO 
 * @author ljl
 * @date 2015-1-18
 */
public class SysUserBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String id;    	  //主键（ID）
    private String username;  //用户名
    private String telephone; //联系电话
    private String phone; 	  //手机号码
    private String userpsd;   //密码（MD5加密）
    private String userorder; //排序号
    private String remark;	  //备注
    private String levsign;   //所属管理级别
    private String createdate;//创建时间
    private String updatedate;//修改时间
    
    private String rolename;  //辅助字段[角色]
    private String roleid;    //辅助字段[角色ID]

	//Get和Set方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserpsd() {
		return userpsd;
	}
	public void setUserpsd(String userpsd) {
		this.userpsd = userpsd;
	}
	public String getUserorder() {
		return userorder;
	}
	public void setUserorder(String userorder) {
		this.userorder = userorder;
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
	public String getLevsign() {
		return levsign;
	}
	public void setLevsign(String levsign) {
		this.levsign = levsign;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
}
