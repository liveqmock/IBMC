package com.nomen.ntrain.util;

import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.action.LoginAction;
import com.nomen.ntrain.base.bean.LoginBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @description 登录拦截器  
 * @author 连金亮
 * @date 2011-05-20
 */

public class CheckInterceptor extends AbstractInterceptor
{
	public static final long serialVersionUID=20090504L;
	public static final String LOGIN_KEY  = com.nomen.ntrain.util.Constant.LOGIN_PARAM;
	public static final String LOGIN_PAGE = "global_login";
	private AuthCheckUtil    authCheckUtil;
	
	public void destroy() {  
		System.out.println("==============================================================");
		System.out.println("=============      拦截器加载开始销毁      =======================");
		System.out.println("==============================================================");
	}
	
	public void init() { 
		this.authCheckUtil = new AuthCheckUtil();
		System.out.println("==============================================================");
		System.out.println("=============      拦截器加载开始加载      =======================");
		System.out.println("==============================================================");
	}	
	
	public String intercept(ActionInvocation actionInvocation) throws Exception 
	{
		Object action = actionInvocation.getAction();
		if (action instanceof LoginAction) {
			return actionInvocation.invoke();
		}
		
		//确认Session中是否存在LOGIN
		Map session = actionInvocation.getInvocationContext().getSession();
		LoginBean login = (LoginBean) session.get(LOGIN_KEY);
		if (login != null) {
			//权限判断
			if(this.authCheckUtil.checkAccess(actionInvocation,login.getRoleids())){
				return actionInvocation.invoke();
			}
			else{
				ActionContext actionContext = actionInvocation.getInvocationContext();
				HttpServletRequest request= (HttpServletRequest)actionContext.get(ServletActionContext.HTTP_REQUEST);
				ResourceBundle bundle = ResourceBundle.getBundle("globalMessages");
				String htmlError = bundle.getString("nopurview.message");
				request.setAttribute("htmlError", htmlError);
				return Constant.LOGIN_NOPUR;
			}
		} else {
			//终止后续操作，返回LOGIN
			ActionContext actionContext = actionInvocation.getInvocationContext();
			HttpServletRequest request= (HttpServletRequest)actionContext.get(ServletActionContext.HTTP_REQUEST);
			ResourceBundle bundle = ResourceBundle.getBundle("globalMessages");
			String loginError = bundle.getString("login.fail");
			String htmlError = "<script language=\"javascript\">alert(\""+loginError
			                +"\");window.top.location=\""+ServletActionContext.getRequest().getContextPath()+"/"+"\"</script>";
			request.setAttribute("htmlError", htmlError);
			return LOGIN_PAGE;
		}
	}
	
	public void showMsg(ActionInvocation actionInvocation){
		/***********************************操作地址提示*****************************************/
		//获取session中的用户 
		Object object=actionInvocation.getInvocationContext().getSession().get("loginSession");
		String path=ServletActionContext.getRequest().getServletPath(); 
		String actionpath =actionInvocation.getAction().toString();
        String name = actionInvocation.getInvocationContext().getName(); // action 的名称，在xml中配置的  
        LoginBean loginBean = (LoginBean)object;
		System.out.println("---"+loginBean.getUsername()+"---"+(new Date())+"地址：" + path+"    \n类：  "+actionpath+" \n方法对应名称：["+name+"]"); 	
		/***********************************操作地址提示*****************************************/
		
	}
}