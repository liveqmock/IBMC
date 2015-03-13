package com.nomen.ntrain.base.bean;

import java.io.Serializable;

/**
 * 莆田岗位培训_系统菜单
 * @author 连金亮
 * @dete 
 */
public class BaseMenuBean implements Serializable{
	private String id;		//菜单id
	private String name;	//菜单名称
	private String lev;		//菜单级别
	private String rel;     //菜单链接ID
	private String url;		//菜单链接
	private String pur;		//该菜单是否需要权限
	private String sortnum;	//菜单排序号
	private String specsign;//特殊标记[1只出现在权限，不出现在菜单]
	private String fatherid;
	private String usesign; //启用、禁用标记
	public String getUsesign() {
		return usesign;
	}
	public void setUsesign(String usesign) {
		this.usesign = usesign;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPur() {
		return pur;
	}
	public void setPur(String pur) {
		this.pur = pur;
	}
	public String getSpecsign() {
		return specsign;
	}
	public void setSpecsign(String specsign) {
		this.specsign = specsign;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSortnum() {
		return sortnum;
	}
	public void setSortnum(String sortnum) {
		this.sortnum = sortnum;
	}
	public String getFatherid() {
		return fatherid;
	}
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
