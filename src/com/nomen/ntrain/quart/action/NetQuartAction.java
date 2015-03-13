package com.nomen.ntrain.quart.action;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.action.LoginAction;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.common.CommonAction;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.quart.bean.NetQuartBean;
import com.nomen.ntrain.quart.service.NetQuartService;
import com.nomen.ntrain.quart.util.CommonUtils;
import com.nomen.ntrain.quart.util.Dom4jHelper;
import com.nomen.ntrain.util.Constant;
import com.nomen.ntrain.util.ReflectUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.quartz.CronExpression;
import org.springframework.util.ReflectionUtils;
//import org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl;

/**
 * @description 调度器任务实体action层
 * @author 梁桂钊
 * @date 2013-09-16
 */

@SuppressWarnings("all")
public class NetQuartAction extends CommonAction {
	private static final Log LOG = LogFactory.getLog(NetQuartAction.class);
    private NetQuartService netQuartService; 
	private NetQuartBean netQuartBean;
	protected String gosign;      //是否继续新标示        
	protected String fields;      //关键字字段名称
	protected String keyword;     //关键字内容
	
	/**
	 * 跳转到调度器列表页面
	 * @return
	 */
	public String toForwardListPage(){
		return SUCCESS;
	}
	
    /**
     * 调度器任务实体 列表
     * @return
     */
	public String findNetQuartListByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		Map map = new HashMap();
		map.put("fields",   func.Trim(this.fields));
		map.put("keyword",  func.Trim(this.keyword));
		List dataList = this.netQuartService.findNetQuartList(map,func.Cint(this.getTagpage()), func.Cint(this.getRecord()));		
		String totalcount = String.valueOf(map.get("total"));
		this.print(this.creItemListPage(dataList,totalcount));
		return null;
	} 
	
	/**
	 * 调度器任务实体 跳转
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String setNetQuart() throws Exception{
		// 从xml中获取任务名
		HttpServletRequest req = ServletActionContext.getRequest();
		String filename = getClass().getResource("/com/nomen/ntrain/quart/task").getFile();
		List<String> fileList = CommonUtils.getFileNames(filename);
		fileList.remove("QuartzJobBean");
		// 获取所有的文件名
		req.setAttribute("fileList", fileList);
		if(netQuartBean==null || func.IsEmpty(netQuartBean.getId())){
			this.netQuartBean = new NetQuartBean();
			this.netQuartBean.setJobstatus("0");
		}else{
			this.netQuartBean = this.netQuartService.findNetQuartBean(netQuartBean.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 调度器任务实体 保存
	 * 
	 * @return
	 */
	public String saveNetQuart(){
		String rValue = INPUT;
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			if(this.isValidToken()) {
				this.netQuartService.saveNetQuart(netQuartBean);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.netQuartBean = new NetQuartBean();
				this.setNetQuart();
				this.reloadParentPage2();
			}else{
				this.reloadParentPage();
				return Constant.NO_DATA;
			}
		} catch(Exception ex) {
			this.setActMessage("operate.error");
			ex.printStackTrace();
		}
		return rValue;
	}
	
	/***************************************************/
	/******************* JQUERY  ***********************/
	/***************************************************/
	
	/**
	 * 调度器任务实体 删除
	 * @return
	 */
	public String deleteNetQuartByJq(){	
		try {
			String id = this.netQuartBean.getId();
			if(null != id || id.length() != 0){
				this.netQuartService.deleteNetQuart(id);
				this.print("1");
				LOG.info("调度器删除成功！");
			}
		} catch(Exception ex) {
			LOG.error("调度器删除失败！");
			this.print("-1");
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 调度器任务实体 启用/禁用
	 * @return
	 */
	public String updateNetQuartByJq(){
		try {
			this.netQuartService.updateNetQuartJobstatus(netQuartBean);
			this.print("1");
			LOG.info("调度器启用/禁用成功！");
		} catch (Exception e) {
			LOG.error("调度器启用/禁用失败！");
			this.print("-1");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 调度器任务实体 获取执行方法[根据JOBID]
	 * @return
	 */
	public String checkNetQuartMethodsByJq(){
		try {
			// 获取jobid中的所有方法名
			List<String> list = ReflectUtil.getMethodsWithoutObject("com.nomen.ntrain.quart.task."+netQuartBean.getJobid());
			this.printList(list);
		} catch (Exception e) {	
			e.printStackTrace();
			LOG.error("调度器获取执行方法失败！");
		}
		return null;
	}
	
	/**
	 * 核查运行表达式是否正确
	 * @return
	 */
	public String checkNetQuartCronexprByJq(){
		try {
			String cronexpr = netQuartBean.getCronexpr();		
			if(CronExpression.isValidExpression(cronexpr)){
				this.print("true");
			}
			else{
				this.print("false");
			}
			LOG.info("核查运行表达式是否正确成功！");
		} catch (Exception e) {
			LOG.error("核查运行表达式是否正确失败！");
			e.printStackTrace();
			this.print("err");
		}
		return null;
	}
	
	// Get和Set方法
	public NetQuartBean getNetQuartBean() {
		return netQuartBean;
	}

	public void setNetQuartBean(NetQuartBean netQuartBean) {
		this.netQuartBean = netQuartBean;
	}

	public NetQuartService getNetQuartService() {
		return netQuartService;
	}

	public void setNetQuartService(NetQuartService netQuartService) {
		this.netQuartService = netQuartService;
	}

	public String getGosign() {
		return gosign;
	}

	public void setGosign(String gosign) {
		this.gosign = gosign;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
