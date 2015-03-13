package com.nomen.ntrain.ibmc.action;

import com.nomen.ntrain.common.CommonAction;

public class IbmcAction extends CommonAction {
	private static final long serialVersionUID = -6567483905381234844L;
	protected String fun;                         //操作标志：新增（1），修改（2）
	protected String taksign="0";                 //记录操作成功后是否保存bean数据标志，1保存，0不保存
	protected String gosign;                      //继续添加标识
	protected String fields;                      //关键字字段名称
	protected String keyword;                     //关键字内容
	protected String sortfield;                   //排序的字段名
	
	public String toForwardListPage(){
		return SUCCESS;
	}
	//以下为get和set方法
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getFun() {
		return fun;
	}
	public void setFun(String fun) {
		this.fun = fun;
	}
	public String getGosign() {
		return gosign;
	}
	public void setGosign(String gosign) {
		this.gosign = gosign;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSortfield() {
		return sortfield;
	}
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}
	public String getTaksign() {
		return taksign;
	}
	public void setTaksign(String taksign) {
		this.taksign = taksign;
	}
}