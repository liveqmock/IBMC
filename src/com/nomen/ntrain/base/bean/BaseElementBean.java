package com.nomen.ntrain.base.bean;
/**
 * @description 系统管理_全局按钮表
    @author 郑学仕
    @date 2015-01-19
 */

public class BaseElementBean {

	private String  id;	                   //主键（ID）
	private String  codevalue;	               //页面元素编码
	private String  codename;	               //页面元素显示名称
	private String  clickevent;	               //页面元素对应的function
	private String  styleclass;	               //页面元素对应的样式
	private String  express;	               //生成后的表达式
	private String  createdate;	           //创建时间
	private String  updatedate;	           //修改时间
	

	//set和get 方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public String getClickevent() {
		return clickevent;
	}
	public void setClickevent(String clickevent) {
		this.clickevent = clickevent;
	}
	public String getStyleclass() {
		return styleclass;
	}
	public void setStyleclass(String styleclass) {
		this.styleclass = styleclass;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
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
