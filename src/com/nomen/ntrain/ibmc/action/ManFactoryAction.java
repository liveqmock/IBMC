package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.ibmc.bean.ManFacConfigBean;
import com.nomen.ntrain.ibmc.bean.ManFactoryBean;
import com.nomen.ntrain.ibmc.service.ManFactoryService;
import com.nomen.ntrain.util.Constant;

/**
 * @description 设备管理_厂商管理表
 * @author 郑学仕
 * @date 2015-1-20
 */
public class ManFactoryAction extends IbmcAction {
	private ManFactoryService manFactoryService;    //代码列表业务接口
	private LoginService 	  loginService;      	//登录信息业务处理类
	private ManFactoryBean    manFactoryBean;        //代码信息bean
	private ManFacConfigBean  manFacConfigBean; 
	
	private Map<String,String>	querymap;			//传参map
	private List               dataList;
	
	/**
	 * 跳转到[厂商管理]列表页面 
	 * @return
	 */
	public String toForwardFactory(){
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
		}
		return SUCCESS;
	}
	
	/**
	 * 厂商管理表_列表
	 * @return
	 */
	public void listManFactoryByJq(){
		Map map = new HashMap();
		map.put("fields",   func.Trim(this.fields));
		map.put("keyword",  func.Trim(this.keyword));
		this.dataList = this.manFactoryService.findManFactoryList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
		String totalcount = String.valueOf(map.get("total"));
		this.print(this.creItemListPage(dataList,totalcount));
	}
	
	/**
	 *  跳转到[厂商管理]新增,修改页面
	 * @return
	 */
	public String setManFactory(){
		String id = this.manFactoryBean.getId();
		if(func.IsEmpty(id)){
			//新增
			this.manFactoryBean = new ManFactoryBean();
			this.manFacConfigBean = new ManFacConfigBean();
		}else{
			this.manFactoryBean = this.manFactoryService.findManFactoryBean(manFactoryBean);
			this.dataList = this.manFactoryService.findManFacConfigList(id);
		}
		return SUCCESS;
	}
	
	/**
	 *保存（新增/修改）厂商管理信息
	 * @return
	 */
	public String saveManFactory(){
		HttpServletRequest req=ServletActionContext.getRequest();
		 String rValue=SUCCESS;	
		 try {
			if(this.isValidToken()) {
				String[] facModelArr = req.getParameterValues("facmodel");
				String[] modelIdArr = req.getParameterValues("modelId");
				Map map = new HashMap();
				map.put("facModelArr", facModelArr);
				map.put("modelIdArr", modelIdArr);
			   this.manFactoryService.saveManFactoryBean(map,manFactoryBean);
				}
			 this.reloadParentPage();
			 rValue = Constant.NO_DATA;
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return rValue;
	}
	
	/**
	 * 删除 厂商管理信息
	 */
	
	public void deleteManFactoryByJq(){
		 try {
				String idStr = this.querymap.get("idStr");
				if(!func.IsEmpty(idStr)){
					 this.manFactoryService.deleteManFactoryBean(idStr);
					 this.print("1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 删除厂商型号
	 */
	public void deleteManFacConfigByJq(){
		try {
			String id = this.manFacConfigBean.getId();
			if(!func.IsEmpty(id)){
				this.manFactoryService.deleteManFacConfigBean(id);
				this.print("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过厂商ID查询厂商型号
	 */
	public void findManFacModelByJq(){
		try {
			String facId = this.manFactoryBean.getId();
			if(!func.IsEmpty(facId)){
				List modelList = this.manFactoryService.findManFacConfigList(facId);
				this.printList(modelList);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	//set和get 方法
	
	public ManFactoryService getManFactoryService() {
		return manFactoryService;
	}

	public void setManFactoryService(ManFactoryService manFactoryService) {
		this.manFactoryService = manFactoryService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public ManFactoryBean getManFactoryBean() {
		return manFactoryBean;
	}

	public void setManFactoryBean(ManFactoryBean manFactoryBean) {
		this.manFactoryBean = manFactoryBean;
	}

	public Map<String, String> getQuerymap() {
		return querymap;
	}

	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}

	public ManFacConfigBean getManFacConfigBean() {
		return manFacConfigBean;
	}

	public void setManFacConfigBean(ManFacConfigBean manFacConfigBean) {
		this.manFacConfigBean = manFacConfigBean;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	
	
	
}
