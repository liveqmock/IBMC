package com.nomen.ntrain.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.nomen.ntrain.base.bean.BaseRoleUserBean;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.BaseRoleService;
import com.nomen.ntrain.base.service.BaseRoleUserService;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.util.Constant;


public class BaseRoleUserAction  extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(BaseRoleUserAction.class);
	private BaseRoleUserService       baseRoleUserService;//人员角色接口
	private BaseRoleService           baseRoleService;
	private LoginService              loginService;
	private BaseRoleUserBean  		  baseRoleUserBean;	//人员信息bean
	private BaseRoleBean              baseRoleBean;		//角色信息bean
	private List			         dataList;			//结果集合 
	private Map<String,String>       querymap; 	 		//参数集合
	
	/**
	 * 左侧菜单链接跳转
	 */
	public String toForwordRoleUser(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//部门
		LoginBean loginBean = this.getLoginSessionBean();
		//可查看的角色
		Map map = new HashMap();
		map.put("userid", loginBean.getId());
		map.put("expree", ">=");
		List roleList = this.baseRoleService.findBaseRoleByUseridList(map);
		req.setAttribute("roleList",roleList);
		
		//可设置的角色
		Map lM = new HashMap();
		lM.put("userid", loginBean.getId());
		lM.put("expree", ">");
		List<BaseRoleBean> operRoleList = this.baseRoleService.findBaseRoleByUseridList(lM);
		req.setAttribute("operRoleList",operRoleList);
		

		req.setAttribute("loginUserId", loginBean.getId());
		req.setAttribute("estauserid", loginBean.getId());
		//获取登录人员的角色
		String loginRole = "-1";
		if((","+loginRole+",").indexOf(Constant.ROLE_SUPER_ID)>=0){
			req.setAttribute("isSuper", "1");
		}
		return SUCCESS;
	}
	
	/**
	 * 用户管理信息列表
	 * @return
	 */
	public void listBaseRoleUserByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		LoginBean loginBean = this.getLoginSessionBean();
		if(querymap==null){
			querymap =new HashMap<String,String>(); 
		}
	
		Map param = new HashMap();
		param.put("fields", this.getFields());
		param.put("keyword", this.getKeyword());
		param.put("roleid",this.querymap.get("roleid"));
		param.put("deptid",this.querymap.get("id"));
		param.put("userid",loginBean.getId());
		List dataList = this.baseRoleUserService.findBaseRoleUserList(param, func.Cint(this.getTagpage()),func.Cint(this.getRecord()));
		this.print(this.creItemListPage(dataList, String.valueOf(param.get("total"))));
		
	}
	/**
	 * 人员引入【跳转】
	 * 
	 */
	public String setBaseUser(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//部门
		LoginBean loginBean = this.getLoginSessionBean();
		String qUnitId = func.Trim(loginBean.getUnitid());
		List<DataOrganBean> deptList = this.loginService.findDeptListByUnitId(qUnitId);
		req.setAttribute("deptList",deptList);
		//默认第一个部门
		if(querymap==null){
			querymap =new HashMap<String,String>(); 
			this.querymap.put("id", deptList.get(0).getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 人员引入[列表]
	 * @return
	 */
	public void listBaseUserByJq(){ 
		HttpServletRequest req = ServletActionContext.getRequest();
		LoginBean loginBean = this.getLoginSessionBean();
		String userUnitId = loginBean.getUnitid();
		Map<String,String> param = new HashMap<String,String>();
		param.put("deptid", this.querymap.get("id"));
		param.put("fields", this.getFields());
		param.put("keyword", this.getKeyword());
		List dataList = this.baseRoleUserService.findBaseRoleUserList2(param, func.Cint(this.getTagpage()),func.Cint(this.getRecord()));
		this.print(this.creItemListPage(dataList, String.valueOf(param.get("total"))));
	}
	
	/**
	 * 引入人员保存操作
	 * @return
	 */
	public void saveBaseRoleUser(){
		LoginBean loginBean = this.getLoginSessionBean();
		try{
			this.baseRoleUserBean.setMainuser(loginBean.getId());
			this.baseRoleUserBean.setMainusername(loginBean.getUsername());
			String roleIds = baseRoleUserBean.getRoleids();
			roleIds = func.Replace(roleIds, " ", "");
			this.baseRoleUserBean.setRoleids(roleIds);
			this.baseRoleUserService.saveNetRoleUser(baseRoleUserBean, loginBean.getId());
			this.print("1");
			
		}catch(Exception ex){
			this.setActMessage("operate.error");
			LOG.error("权限人员引入成功");
			this.print("-1");
		}
	}
	
	/**
	 * 人员对应的角色设置[跳转]
	 * @return
	 */
	public String setBaseRolePort(){
		HttpServletRequest req =ServletActionContext.getRequest();
		LoginBean loginBean = this.getLoginSessionBean();
		//查询角色列表
		Map map = new HashMap();
		map.put("userid", loginBean.getId());
		map.put("expree", ">");
		List roleList = this.baseRoleService.findBaseRoleByUseridList(map);
		this.printList(roleList);
		return null;
	} 
	
	/**
	 * 保存人员对应的角色
	 * @return
	 */
	public void updateBaseRolePort(){
		LoginBean loginBean = this.getLoginSessionBean();
		try{
			this.baseRoleUserBean.setMainuser(loginBean.getId());
			this.baseRoleUserBean.setMainusername(loginBean.getUsername());
			String roleIds = baseRoleUserBean.getRoleids();
			roleIds = func.Replace(roleIds, " ", "");
			this.baseRoleUserBean.setRoleids(roleIds);
			this.baseRoleUserService.updateBaseRoleUser(baseRoleUserBean);
			this.print("1");
		}catch(Exception ex){
			LOG.error("保存人员对应的角色操作失败");
			this.print("-1");
		}
	}
	
	/**
	 * 删除权限用户信息列表
	 * @return
	 */
	public void deleteBaseRoleUserByJq(){
		try{
			String userid = baseRoleUserBean.getUserid();
			this.baseRoleUserService.deleteBaseRoleUserByUserid(userid);
			this.print("1");
		}catch(Exception ex){
			this.print("-1");
			LOG.error("删除权限用户信息列表失败");
			ex.printStackTrace();
		}
	}

	
	
	//以下为get/set方法


	public BaseRoleUserService getBaseRoleUserService() {
		return baseRoleUserService;
	}

	public void setBaseRoleUserService(BaseRoleUserService baseRoleUserService) {
		this.baseRoleUserService = baseRoleUserService;
	}

	public BaseRoleService getBaseRoleService() {
		return baseRoleService;
	}

	public void setBaseRoleService(BaseRoleService baseRoleService) {
		this.baseRoleService = baseRoleService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public BaseRoleUserBean getBaseRoleUserBean() {
		return baseRoleUserBean;
	}

	public void setBaseRoleUserBean(BaseRoleUserBean baseRoleUserBean) {
		this.baseRoleUserBean = baseRoleUserBean;
	}

	public BaseRoleBean getBaseRoleBean() {
		return baseRoleBean;
	}

	public void setBaseRoleBean(BaseRoleBean baseRoleBean) {
		this.baseRoleBean = baseRoleBean;
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
