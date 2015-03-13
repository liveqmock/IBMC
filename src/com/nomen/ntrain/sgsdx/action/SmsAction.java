package com.nomen.ntrain.sgsdx.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.common.CommonAction;

public class SmsAction extends CommonAction {
	protected String showValue;				//显示导航菜单
	protected String fun;					//操作标志：新增（1），修改（2）
	protected String taksign="0";  			//记录操作成功后是否保存bean数据标志，1保存，0不保存
	protected String gosign;				//继续添加标识
	protected String fields;        		//关键字字段名称
	protected String keyword;       		//关键字内容
	protected String fatherid;   			//父级类别ID
	protected String sortfield; 			//排序的字段名
	protected String showfield;  			//公用列显示的字段名 
	protected String hfsign;                //是否过滤回复
	
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
	public String getShowValue() {
		return showValue;
	}
	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}
	public String getTaksign() {
		return taksign;
	}
	public void setTaksign(String taksign) {
		this.taksign = taksign;
	}
	public String getShowfield() {
		return showfield;
	}
	public void setShowfield(String showfield) {
		this.showfield = showfield;
	}
	public String getSortfield() {
		return sortfield;
	}
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}

	public String getHfsign() {
		return hfsign;
	}

	public void setHfsign(String hfsign) {
		this.hfsign = hfsign;
	}

	public String getFatherid() {
		return fatherid;
	}

	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	
}
