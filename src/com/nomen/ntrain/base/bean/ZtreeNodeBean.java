package com.nomen.ntrain.base.bean;
/**
 * 莆田岗位培训_树状节点bean
 * @author 连金亮
 * @dete 
 */
public class ZtreeNodeBean {
	private String id;		//菜单id
	private String pid;		//菜单上级
	private String name;	//菜单名称
	private String rel;     //菜单链接ID
	private String src;		//菜单链接
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
}
