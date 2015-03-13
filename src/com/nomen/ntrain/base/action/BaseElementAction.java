package com.nomen.ntrain.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.BaseElementBean;
import com.nomen.ntrain.base.constant.BaseConstant;
import com.nomen.ntrain.base.service.BaseElementService;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.ibmc.action.IbmcAction;
import com.nomen.ntrain.util.Constant;

/**
 * @description 系统管理_全局按钮表
    @author 郑学仕
    @date 2015-01-19
 */
public class BaseElementAction extends IbmcAction
{
	private static final long serialVersionUID = -2123844639199920594L;
	
	private Map<String,String>  querymap;           //参数集合
	
	private BaseElementService baseElementService;
	private LoginService       loginService;
	private BaseElementBean    baseElementBean;
	
	
	
	 //全局控件按钮链接跳转
    public String toForwardElement(){
    	if(this.querymap==null){
			this.querymap = new HashMap<String,String>();
		}
    	return SUCCESS;
    }
	
	/**
	 * 全局按钮表列表
	 */
    public void listBaseElement(){
    	Map map = new HashMap();
    	List dataList = this.baseElementService.findBaseElementList(map);
    	this.printList(dataList);
    }
    
	/**
	 * 跳转
	 */
    public String setBaseElement(){
    	HttpServletRequest req=ServletActionContext.getRequest();
    	if("1".equals(this.fun)){
    		this.baseElementBean = new BaseElementBean();
    	}else{
    	    this.baseElementBean = this.baseElementService.findBaseElementBean(this.baseElementBean.getId());
    	}
    	//设置按钮样式列表
    	req.setAttribute("classList",BaseConstant.btnClassList());
    	return SUCCESS;
    }
    /**
     * 保存
     */
     public String saveBaseElement(){
    	 String rValue=SUCCESS;
    	 try {
			if(this.isValidToken()) {
				 this.baseElementService.saveBaseElement(baseElementBean);
				 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.reloadParentPage();
		rValue = Constant.NO_DATA;
		return rValue;
     }
     
     /**
      * 删除
      * @return
      */
     public void deleteBaseElement(){
    	 
    	 try {
			String id = this.baseElementBean.getId();

			if(!func.IsEmpty(id )){
				 this.baseElementService.deleteBaseElement(id);
				 this.print("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
    
    //set和get方法

	public Map<String, String> getQuerymap() {
		return querymap;
	}

	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}

	public BaseElementService getBaseElementService() {
		return baseElementService;
	}

	public void setBaseElementService(BaseElementService baseElementService) {
		this.baseElementService = baseElementService;
	}

	public BaseElementBean getBaseElementBean() {
		return baseElementBean;
	}

	public void setBaseElementBean(BaseElementBean baseElementBean) {
		this.baseElementBean = baseElementBean;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
    
    
    
}
