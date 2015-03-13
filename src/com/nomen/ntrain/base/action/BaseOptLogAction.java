package com.nomen.ntrain.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.annotation.LoginEnums;
import com.nomen.ntrain.annotation.OptEnums;
import com.nomen.ntrain.base.bean.BaseOptLogBean;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.BaseOptLogService;
import com.nomen.ntrain.base.service.LoginService;

public class BaseOptLogAction extends BaseAction{
	private static Log LOG = LogFactory.getLog(BaseOptLogAction.class);
	private BaseOptLogService      baseOptLogService; 
	private LoginService           loginService;
	private BaseOptLogBean          baseOptLogBean;         //日志管理信息bean
	private List                    dataList;              //结果集合
	private Map<String,String>      querymap;              //参数集合
	
	 /**
	  * 菜单链接跳转[登录日志]
	  */  
	
	public String toForwardOptLog(){
		HttpServletRequest req = ServletActionContext.getRequest();		
		//操作类型
		req.setAttribute("logEnums", LoginEnums.values());
		req.setAttribute("optEnums", OptEnums.values());
		
		return SUCCESS;
	}
	
	/**
	 * 登录日志 列表
	 * 操作日志 列表
	 * @return
	 */
	public void listBaseOptLogByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();		
		Map map = new HashMap();
		map.put("opttype", this.querymap.get("opttype"));
		map.put("fields", this.getFields());
		map.put("keyword", this.getKeyword());
		map.put("intflag", this.querymap.get("intflag"));
		List dataList = this.baseOptLogService.findBaseOptLogList(map, func.Cint(this.getTagpage()),func.Cint(this.getRecord()));
		this.print(this.creItemListPage(dataList, String.valueOf(map.get("total"))));
	}
	/**
	 * 操作日志内容列表
	 * @return
	 */
	public String listBaseOptDesc(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String id = this.querymap.get("id");
		if(!func.IsEmpty(id)){
			this.baseOptLogBean = this.baseOptLogService.findBaseOptLogById(id);
		}
		req.setAttribute("optEnums", OptEnums.values());
		return SUCCESS;
	}
	
	
	
	/**
	 * 登录日志 删除
	 * 操作日志 删除
	 * @return
	 */
	public void deleteBaseOptLogByJq(){
		try {
			String idStr = this.querymap.get("idStr");
			this.baseOptLogService.deleteBaseOptLogByIdStr(idStr);
			this.print("1");
		} catch (Exception e) {
			this.print("-1");
			LOG.error("删除登录人员失败");
			e.printStackTrace();
		}
	}
	
	
	
	//set和get方法
   
	public BaseOptLogService getBaseOptLogService() {
		return baseOptLogService;
	}
	public void setBaseOptLogService(BaseOptLogService baseOptLogService) {
		this.baseOptLogService = baseOptLogService;
	}
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public BaseOptLogBean getBaseOptLogBean() {
		return baseOptLogBean;
	}
	public void setBaseOptLogBean(BaseOptLogBean baseOptLogBean) {
		this.baseOptLogBean = baseOptLogBean;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public Map<String, String> getQuerymap() {
		return querymap;
	}
	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}

	

}
