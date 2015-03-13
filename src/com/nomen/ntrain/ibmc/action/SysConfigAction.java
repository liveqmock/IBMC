package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.ibmc.bean.SysConfigBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.SysConfigService;


/**
 * @description 系统管理_系统配置 
 * @author ljl
 * @date 2015-02-28
 */
@SuppressWarnings("all")
public class SysConfigAction extends IbmcAction{
	private SysConfigService		sysConfigService;  
	private SysConfigBean			sysConfigBean;  
	private Map<String,String>		querymap;			//传参map
	private List					dataList;			//结果集
	
	/**
	 * 系统管理_系统配置[有效期标准以及收费列表]
	 */
	public String setSysConfigOfStandar() {
		try {
			if(null==this.querymap){
				this.querymap = new HashMap<String,String>();
			}
			
			//获取系统配置列表
			Map map = new HashMap();
			map.put("commid",  IbmcConstant.CONFIG_COMMID);    //通过COMMID获取有效时间，押金金额
			map.put("configkey", IbmcConstant.CONFIG_KEY_STANDAR);
			this.dataList = this.sysConfigService.findSysConfigList(map);
			if(!func.isEmpty(this.dataList)){
				this.sysConfigBean = (SysConfigBean)this.dataList.get(0);
			}else{
				this.sysConfigBean = new SysConfigBean();
				this.sysConfigBean.setConfigkey(IbmcConstant.CONFIG_KEY_STANDAR);
				this.sysConfigBean.setCommid(IbmcConstant.CONFIG_COMMID);
			}
			HttpServletRequest req=ServletActionContext.getRequest();
			req.setAttribute("configValidList",IbmcConstant.getSysConfigValid());
			
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}

	/**
	 * 系统管理_系统配置[有效期标准以及收费保存]
	 */
	public String saveSysConfigOfStandar() {
		try {
			if(this.isValidToken()) {
				this.sysConfigService.saveSysConfig(this.sysConfigBean);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		return this.setSysConfigOfStandar();
	}
	
	
	//set和get方法
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

	public SysConfigService getSysConfigService() {
		return sysConfigService;
	}

	public void setSysConfigService(SysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}

	public SysConfigBean getSysConfigBean() {
		return sysConfigBean;
	}

	public void setSysConfigBean(SysConfigBean sysConfigBean) {
		this.sysConfigBean = sysConfigBean;
	}
}
