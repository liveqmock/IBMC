package com.nomen.ntrain.base.bean;

import java.io.Serializable;

/**
 * @description 操作日志信息表POJO 
 * @author 连金亮
 * @date 2010-12-26
 */
public class BaseLogBean implements Serializable{
    private String id;			        //ID（主键，操作日志）
    private String content;			    //维护内容
    private String loguser;			    //维护人
    private String sortnum;             //排序号
    private String estauser;			//创建人（发布）
    private String estatime;			//创建时间（发布）
    private String mainuser;			//维护人（发布）
    private String maintime;			//维护时间（发布）
    private String strflag;			    //预留字段（字符串）
    private String intflag;			    //预留字段（整型） 
    
    //以下为set get方法
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEstatime() {
		return estatime;
	}
	public void setEstatime(String estatime) {
		this.estatime = estatime;
	}
	public String getEstauser() {
		return estauser;
	}
	public void setEstauser(String estauser) {
		this.estauser = estauser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntflag() {
		return intflag;
	}
	public void setIntflag(String intflag) {
		this.intflag = intflag;
	}
	public String getLoguser() {
		return loguser;
	}
	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}
	public String getMaintime() {
		return maintime;
	}
	public void setMaintime(String maintime) {
		this.maintime = maintime;
	}
	public String getMainuser() {
		return mainuser;
	}
	public void setMainuser(String mainuser) {
		this.mainuser = mainuser;
	}
	public String getStrflag() {
		return strflag;
	}
	public void setStrflag(String strflag) {
		this.strflag = strflag;
	}
	public String getSortnum() {
		return sortnum;
	}
	public void setSortnum(String sortnum) {
		this.sortnum = sortnum;
	}
    
}