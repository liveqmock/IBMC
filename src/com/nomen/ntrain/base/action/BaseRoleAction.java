package com.nomen.ntrain.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.BaseMenuService;
import com.nomen.ntrain.base.service.BaseRoleService;
import com.nomen.ntrain.util.Constant;


public class BaseRoleAction extends BaseAction {

		private static final long serialVersionUID = 1L;
		private static final Log LOG = LogFactory.getLog(BaseRoleAction.class);
		protected String fun;					           //操作标志：新增（1），修改（2）
		private String                 taksign;             //记录操作成功后是否保存bean数据标志，1保存，0不保存
		private BaseRoleService       baseRoleService;      //角色管理业务接口
		//private BaseRoleUserService    baseRoleUserService;  //角色人员业务接口
		private BaseMenuService        baseMenuService;      //菜单管理业务接口
		private BaseRoleBean          baseRoleBean;         //角色管理Bean
		private List                  dataList;            //结果集合
		private Map<String,String>    querymap;            //参数集合
		
		
		
		/**
		 * 左侧菜单链接跳转
		 */
		public String toForwordRole(){
			HttpServletRequest req = ServletActionContext.getRequest();
			return SUCCESS;
		}
		
		/***
		 * 角色管理 列表[分页]
		 * @return
		 */
		public void listBaseRoleByJq(){
			LoginBean loginBean = this.getLoginSessionBean();
			Map<String,String> rMap = new HashMap<String,String>();
			rMap.put("mainuser", loginBean.getId());
			List dataList = this.baseRoleService.findBaseRoleListByPage(rMap, func.Cint(this.getTagpage()),func.Cint(this.getRecord()));
			this.print(this.creItemListPage(dataList,String.valueOf(rMap.get("total"))));
		}
		
		/***
		 * 角色管理 新增/修改[跳转]
		 * @return
		 */
		public String setBaseRole(){
			try {
				if(this.fun.equals("1")){
					this.baseRoleBean = new BaseRoleBean();
					this.baseRoleBean.setRgroup("0");//默认为用户组
				}else{
					//修改，根据id查询该记录
					this.baseRoleBean = this.baseRoleService.findBaseRoleBeanById(this.baseRoleBean.getId());
					String pur  = this.baseRoleService.findBaseRoleMenuByIdStr(this.baseRoleBean.getId());;
					this.baseRoleBean.setPurview(pur);
				}
				
			} catch (Exception e) {
				LOG.error("角色管理 新增/修改[跳转]查询失败");
				e.printStackTrace();
			}
			return SUCCESS;
		}
		
		/**
		 * 菜单角色列表
		 */
		public void listBaseRoleMenuTreeByJq(){
			LoginBean loginBean = this.getLoginSessionBean();
			//获取菜单
			Map<String,String> map = new HashMap<String,String>();
			map.put("fatherid", Constant.MENU_FATHERID_LEV1);
			map.put("userid", loginBean.getId());
			map.put("usesign", "1");
			map.put("purfilter", "1");
			
			//查询全部所有的菜单列表
			List dataList = this.baseMenuService.findBaseMenuTreeList(map);
			this.printList(dataList);
		}
		
		
		
		/**
		 * 角色管理 新增/修改[保存]
		 * @return
		 */
		public String addBaseRole(){
			HttpServletRequest req = ServletActionContext.getRequest();
			LoginBean loginBean = this.getLoginSessionBean();
			String chkPurViewStr = req.getParameter("ChkPurView");
			this.baseRoleBean.setPurview(chkPurViewStr);
			
			String rValue=SUCCESS;
			try {
				if(this.isValidToken()){
					this.baseRoleBean.setMainuser(loginBean.getId());
				    this.baseRoleService.saveBaseRole(baseRoleBean);//调用service修改方法
				}
			} catch (Exception ec) {
				ec.printStackTrace();
				LOG.error("角色管理 新增/修改[保存]失败");
				//记录操作成功后是否保存bean数据标志，1保存，0不保存
				this.taksign="1";
			}
			this.reloadParentPage();
			rValue = Constant.NO_DATA;
			return rValue;
		}
		
		
		/**
		 * 删除
		 * @return
		 */
		
		public void deleteBaseRoleByJq(){
			try{
				this.baseRoleService.deleteBaseRoleById(this.baseRoleBean.getId());
				this.print("1");
			}catch(Exception ex){
				this.print("-1");
				LOG.error("角色管理 删除失败");
				ex.printStackTrace();
			}
		}
		
		/***************************************************/
		/******************* JQUERY  ***********************/
		/***************************************************/
		/**
		 * 查询角色被引入的数量
		 */
		public void findBaseRoleImpCountByJq(){
			try {
				HttpServletRequest req = ServletActionContext.getRequest();
				String roleid = req.getParameter("roleid");
				String total = this.baseRoleService.findBaseRoleImpCount(roleid);
				this.print(total);
			} catch (RuntimeException e) {
				this.print("-1");
				e.printStackTrace();
			}
		}
		
		//以下为get 和 set方法

		public String getFun() {
			return fun;
		}

		public void setFun(String fun) {
			this.fun = fun;
		}

		public String getTaksign() {
			return taksign;
		}

		public void setTaksign(String taksign) {
			this.taksign = taksign;
		}

		public BaseRoleService getBaseRoleService() {
			return baseRoleService;
		}

		public void setBaseRoleService(BaseRoleService baseRoleService) {
			this.baseRoleService = baseRoleService;
		}

		public BaseMenuService getBaseMenuService() {
			return baseMenuService;
		}

		public void setBaseMenuService(BaseMenuService baseMenuService) {
			this.baseMenuService = baseMenuService;
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
