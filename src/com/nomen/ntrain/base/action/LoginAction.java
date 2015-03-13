package com.nomen.ntrain.base.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.nomen.ntrain.annotation.LoginEnums;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.BaseMenuService;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.common.CommonAction;
import com.nomen.ntrain.util.Constant;
import com.nomen.ntrain.util.MD5;
import com.opensymphony.xwork2.ActionContext;

/**
 * @description 登录模块  
 * @author lianjinliang
 * @date 2010-11-23
 */

public class LoginAction extends CommonAction{
	private static final Log  LOG = LogFactory.getLog(LoginAction.class);
	private static final long serialVersionUID=200905183423L;
	private LoginService loginService;
	private BaseMenuService baseMenuService;
	private LoginBean loginbean;
	
	/**
	 * 进入登录页面
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String listLogin() { 
		return SUCCESS;
	} 
	
	/**
	 * 验证登录信息
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String saveLoginIn() {
		Map map=new HashMap();
		if(null == this.loginbean){
			this.loginbean = new LoginBean();
		}
		//将登录条件写入参数集合
		map.put("username", func.Trim(this.loginbean.getUsername()));   //身份证/工号
		String userpsd = func.Trim(this.loginbean.getUserpsd());  //密码
		map.put("userpsd", MD5.EncryptData(userpsd));
		//判断登录信息
		Map loginSession=this.loginService.findLoginUser(map);
		HttpServletRequest req=ServletActionContext.getRequest();
		if(loginSession!=null) {
			this.loginbean = (LoginBean)loginSession.get(Constant.LOGIN_PARAM);
			ActionContext ctx= ActionContext.getContext();
			Map session= ctx.getSession();
			session.put(Constant.LOGIN_PARAM, this.loginbean);
			//记录操作日志
			this.loginService.insertLoginLog(loginbean, LoginEnums.LOGIN);
			//查询正在启用的大类菜单
			List menuList = this.baseMenuService.findUserBigMenuList(this.loginbean.getId());
			req.setAttribute("menuList",menuList);
			return SUCCESS;
		} else {
			String htmlError = "<script language=\"javascript\">jBox.tip(\""+this.getText("login.error")
			                +"\");setTimeout(function(){window.top.location=\"listlogin.shtml\"},3000);</script>";
			req.setAttribute("htmlError", htmlError);
			return "login_error";
		}
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	public String saveLoginOut() {
		LoginBean login = this.getLoginSessionBean();
		if(null!=login){
			//记录操作日志
			this.loginService.insertLoginLog(login, LoginEnums.LOGOUT);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.invalidate();  
		LOG.info("注销成功！");
		return SUCCESS;
	}
	
	
	public String setPassWordByMD5(){
		try
		{
			//将明码转成MD5
			HttpServletRequest req = ServletActionContext.getRequest();
			String password = func.Trim(req.getParameter("password"));
			if("1".equals(func.Trim(this.getText("md5pass.sign")))){
				password = MD5.EncryptData(password);
			}  
			this.print(password);
			return null;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	} 
	

	/**
	 * 左侧菜单
	 * @return
	 */
	public void leftSubMenu(){
		String userId = this.getLoginSessionBean().getId();
		HttpServletRequest req = ServletActionContext.getRequest();
		String fatherid = func.Trim(req.getParameter("fatherid"));
		this.printList(this.baseMenuService.findUserChildMenuTreeList(userId,fatherid));
	}
	
	
	//以下为get和set方法
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public BaseMenuService getBaseMenuService() {
		return baseMenuService;
	}

	public void setBaseMenuService(BaseMenuService baseMenuService) {
		this.baseMenuService = baseMenuService;
	}

	public LoginBean getLoginbean() {
		return loginbean;
	}
	public void setLoginbean(LoginBean loginbean) {
		this.loginbean = loginbean;
	}
}