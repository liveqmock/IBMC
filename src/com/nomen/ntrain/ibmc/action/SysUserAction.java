package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.BaseRoleService;
import com.nomen.ntrain.ibmc.bean.SysUserAreaBean;
import com.nomen.ntrain.ibmc.bean.SysUserBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.SysUserService;
import com.nomen.ntrain.util.Constant;
import com.nomen.ntrain.util.MD5;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;


/**
 * @description 系统管理_用户管理 
 * @author ljl
 * @date 2015-1-18
 */
@SuppressWarnings("all")
public class SysUserAction extends IbmcAction{

	private SysUserService			sysUserService;  
	private BaseRoleService         baseRoleService;
	
	private SysUserBean				sysUserBean;  
	private Map<String,String>		querymap;			//传参map
	private List					dataList;			//结果集

	/**
	 * 跳转到[用户管理]列表页面 
	 */
	public String toForwardSysUser() {
		HttpServletRequest req = ServletActionContext.getRequest();
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
		}
		//获取登录人员信息
		LoginBean loginBean = this.getLoginSessionBean();
		Map param = new HashMap();
		List roleList = this.baseRoleService.findBaseRoleList(param);
		req.setAttribute("roleList", roleList);
		return SUCCESS;
	}
	
	/**
	 * [用户管理]列表
	 */
	public void listSysUserByJq() {
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			if(null==this.querymap){
				this.querymap = new HashMap<String,String>();
			}
			
			//获取用户列表
			Map map = new HashMap();
			
			map.put("areaid",func.Trim(this.querymap.get("id")));//获取管理区域邮编
			map.put("roleid", func.Trim(this.querymap.get("roleid")));
			map.put("fields",   func.Trim(this.fields));
			map.put("keyword",  func.Trim(this.keyword));
			this.dataList=this.sysUserService.findSysUserByPage(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
	}

	/**
	 * @description跳转 [用户管理]
	 */
	public String setSysUser() {
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String id = func.Trim(this.sysUserBean.getId());
			if(func.IsEmpty(id)) {
				this.sysUserBean = new SysUserBean();
				//Map map = new HashMap();
				//map.put("roleid", this.querymap.get("roleid"));
				//String userOrder = this.sysUserService.findSysUserOrder(map);
				//sysUserBean.setUserorder(userOrder);

			} else {
				this.sysUserBean = this.sysUserService.findSysUserBeanById(id);
				String areaIdStr = this.sysUserService.findSysUserAreaIdstr(id);  //查找原有管理区域ID串
				req.setAttribute("areaIdStr", areaIdStr);
			}
			//角色下拉列表
			LoginBean loginBean = this.getLoginSessionBean();
			Map param = new HashMap();
			List roleList = this.baseRoleService.findBaseRoleList(param);
			req.setAttribute("roleList", roleList);
			
			req.setAttribute("CommLevSign", IbmcConstant.getCommLevSign()); //所属管理级别
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		this.getOpraterInfoIntoRequest();
		return SUCCESS;
	}

	/**
	 * @description保存 [用户管理]
	 */
	public String saveSysUser() {
		 String rValue=SUCCESS;	
		try {
			if(this.isValidToken()) {
				HttpServletRequest req = ServletActionContext.getRequest();
				String areaIdStr = req.getParameter("areaIdStr"); //管理区域ID串
				LoginBean loginBean = this.getLoginSessionBean();
				String roleid = func.Trim(this.sysUserBean.getRoleid());
				Map map = new HashMap();
				map.put("roleid",roleid);
				map.put("optuserid", loginBean.getId());
				map.put("optusername",loginBean.getUsername());
				map.put("areaIdStr",areaIdStr);
				this.sysUserService.saveSysUser(map,this.sysUserBean);
			}
			 this.reloadParentPage();
			 rValue = Constant.NO_DATA;
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}

		 return rValue;
	}

	/**
	 * @description删除 [用户管理]
	 */
	public void deleteSysUserByJq() {
		try {
			String idStr = this.querymap.get("idStr");
			if(!func.IsEmpty(idStr)){
				this.sysUserService.deleteSysUserById(idStr);
				this.print("1");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			this.print("-1");
			this.setActMessage("operate.error");
		}
	}
    /**
     * 排序号
     */
	public void findSysUserOrderByJq(){
		try {
			Map map = new HashMap();
			map.put("roleid", this.sysUserBean.getRoleid());
			String userOrder = this.sysUserService.findSysUserOrder(map);
			this.print(userOrder);
		} catch (Exception e) {
			e.printStackTrace();
			this.print("-1");
		}
		
	}
	
	/**
	 * 跳转到用户密码设置
	 * @return
	 */
	public String setSysUserPwd(){
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			String sysUserId = func.Trim(loginBean.getId());
			if (!func.IsEmpty(sysUserId)) {
				this.sysUserBean = this.sysUserService.findSysUserBeanById(sysUserId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 验证输入的密码是否与原来的密码一致
	 * 1:表示密码一致允许修改
	 * 2:表示密码不一致不允许修改
	 * -1:操作错误出现异常
	 */
	public void chkSysUserPwdIsTrueByJq(){
		try {
			String sysUserId = func.Trim(this.sysUserBean.getId());
			String oldPwd = func.Trim(this.sysUserBean.getUserpsd());
			if (!func.IsEmpty(sysUserId)) {
				SysUserBean sysUserBean = this.sysUserService.findSysUserBeanById(sysUserId);
				if(!func.IsEmpty(oldPwd)){
					if(MD5.EncryptData(oldPwd).equals(sysUserBean.getUserpsd())){
						this.print("1");
					}else{
						this.print("2");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 保存用户密码修改操作
	 * 1：表示当前修改密码操作成功
	 * -1：表示当前修改密码操作失败
	 */
	public void saveSysUserPwdByJq(){
		try {
			String sysUserId = func.Trim(this.sysUserBean.getId());
			if(!func.IsEmpty(sysUserId)){
				this.sysUserBean.setUserpsd(MD5.EncryptData(this.sysUserBean.getUserpsd()));
				this.sysUserService.updateSysUser(sysUserBean);
				this.print("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 重置该用户的初始化密码
	 * 1：表示当前修改密码操作成功
	 * -1：表示当前修改密码操作失败
	 */
	public void saveSysUserInitPwdByJq(){
		try {
			String sysUserId = func.Trim(this.sysUserBean.getId());
			if(!func.IsEmpty(sysUserId)){
				this.sysUserBean.setUserpsd(MD5.EncryptData("a_123456"));  //初始化密码为a_123456
				this.sysUserService.updateSysUser(sysUserBean);
				this.print("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	
	//set和get方法
	public SysUserBean getSysUserBean() {
		return sysUserBean;
	}
	public void setSysUserBean(SysUserBean sysUserBean) {
		this.sysUserBean = sysUserBean;
	}
	public SysUserService getSysUserService() {
		return sysUserService;
	}
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
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

	public BaseRoleService getBaseRoleService() {
		return baseRoleService;
	}

	public void setBaseRoleService(BaseRoleService baseRoleService) {
		this.baseRoleService = baseRoleService;
	}

}
