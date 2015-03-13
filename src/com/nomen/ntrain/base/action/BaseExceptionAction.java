package com.nomen.ntrain.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.BaseExceptionBean;
import com.nomen.ntrain.base.service.BaseExceptionService;
import com.nomen.ntrain.util.Constant;

public class BaseExceptionAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private BaseExceptionService baseExceptionService;
	private BaseExceptionBean    baseExceptionBean;
	private Map<String,String>  querymap;
	private List<BaseExceptionBean> dataList;
	
	/*
	 * 菜单链接跳转
	 */
	public String toForwardException(){
		HttpServletRequest req = ServletActionContext.getRequest();	
		req.setAttribute("yearList", Constant.getYearList());
		req.setAttribute("monthList", Constant.getMonthList());
		
		return SUCCESS;
	}
	
	/**
	 * 错误日志列表
	 * @return
	 */
	public void listBaseExceptionByJq(){
	
		if(null == this.querymap){
			this.querymap = new HashMap<String,String>();
			this.querymap.put("year", func.getYear()+"");
		}
		List dataList = this.baseExceptionService.findBaseExceptionList(this.querymap,func.Cint(this.getTagpage()),func.Cint(this.getRecord()));
		this.print(this.creItemListPage(dataList, String.valueOf(this.querymap.get("total"))));
	}

	/**
	 * 错误日志详细页
	 * @return
	 */
	public String setBaseException(){
		if(!func.IsEmpty(this.baseExceptionBean.getId())){
			this.baseExceptionBean = this.baseExceptionService.findBaseExceptionBean(this.baseExceptionBean.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 删除错误日志
	 * @return
	 */
	public void deleteBaseExceptionByJq(){
		if(!func.IsEmpty(this.querymap.get("year"))){
			this.baseExceptionService.deleteBaseException(this.querymap);
		}
	}

	//set 和get方法
	public BaseExceptionService getBaseExceptionService() {
		return baseExceptionService;
	}

	public void setBaseExceptionService(BaseExceptionService baseExceptionService) {
		this.baseExceptionService = baseExceptionService;
	}

	public BaseExceptionBean getBaseExceptionBean() {
		return baseExceptionBean;
	}

	public void setBaseExceptionBean(BaseExceptionBean baseExceptionBean) {
		this.baseExceptionBean = baseExceptionBean;
	}

	public Map<String, String> getQuerymap() {
		return querymap;
	}

	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}

	public List<BaseExceptionBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<BaseExceptionBean> dataList) {
		this.dataList = dataList;
	}
	
	
}
