package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.ibmc.bean.SynDataBean;
import com.nomen.ntrain.ibmc.bean.SynDataBeanBean;
import com.nomen.ntrain.ibmc.service.SynDataService;

/**
 * @description 记录操作数据_用于同webservice同步action层
 * @author ljl
 * @date 2015-1-29
 */
@SuppressWarnings("all")
public class SynDataBeanAction extends IbmcAction{

private SynDataService      synDataBeanService;  //代码列表业务接口
private SynDataBean         synDataBean;     //岗位类别代码信息表
private Map<String,String>   parammap;         //传参map

	/**
	 * @description列表信息
	 */
	public String listSynDataBean() {
		this.showValue=this.getMenu();
		//权限判断【是否有该功能权限】
//		if(this.testPurview("ibmc_system","ibmc_system"))return Constant.LOGIN_NOPUR;
		HttpServletRequest request = ServletActionContext.getRequest();
		Map param = new HashMap();
		request.setAttribute("datalist", this.synDataBeanService.findSynDataBeanList(param));
		return SUCCESS;
	}

	/**
	 * @description新增、修改跳转
	 */
	public String setSynDataBean() {
		this.showValue=this.getMenu();
		//不保留用户记录的信息
		if("0".equals(this.taksign)) {
			if("1".equals(this.fun)) {
				//清空bean中角色和角色信息的内容
				this.gosign = "1";
			} else {
				this.synDataBeanBean = this.synDataBeanService.findSynDataBeanBean(map);
			}
		}
		return SUCCESS;
	}

	/**
	 * @description新增信息
	 */
	public String insertSynDataBean() {
		String rValue=INPUT;
		try {
			if(this.isValidToken()) {
				this.synDataBeanService.insertSynDataBean(this.synDataBeanBean);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.setSynDataBean();
			} else {
				rValue=this.listSynDataBean();
			}
		} catch(Exception ex) {
			this.setActother("insert.error");
			this.taksign="1";
			this.setSynDataBean();
		}
		return rValue;
	}

	/**
	 * @description更新信息
	 */
	public String updateSynDataBean() {
		String rValue=INPUT;
		try {
			if(this.isValidToken()) {
				this.synDataBeanService.updateSynDataBean(this.synDataBeanBean);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.setSynDataBean();
			} else {
				rValue=this.listSynDataBean();
			}
		} catch(Exception ex) {
			this.setActother("update.error");
			this.taksign="1";
			this.setSynDataBean();
		}
		return rValue;
	}

	/**
	 * @description删除信息
	 */
	public String deleteSynDataBean() {
		try {
			this.synDataBeanService.deleteSynDataBean(this.parammap);
		} catch(Exception ex) {
			this.setActother("delete.error");
			this.setSynDataBean();
		}
		return SUCCESS;
	}

	//Get和Set方法
	public SynDataBeanBean getSynDataBeanBean() {
		return synDataBeanBean;
	}
	public void setSynDataBeanBean(SynDataBeanBean synDataBeanBean) {
		this.synDataBeanBean = synDataBeanBean;
	}
	public SynDataService getSynDataBeanService() {
		return synDataBeanService;
	}
	public void setSynDataBeanService(SynDataService synDataBeanService) {
		this.synDataBeanService = synDataBeanService;
	}
	public Map<String, String> getParammap() {
		return parammap;
	}
	public void setParammap(Map<String, String> parammap) {
		this.parammap = parammap;
	}
}
