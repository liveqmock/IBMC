package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.SysCommunityService;
import com.nomen.ntrain.util.Constant;
/**
 * @description 系统管理_社区管理
 * @author ljl
 * @date 2015-1-18
 */
@SuppressWarnings("all")
public class SysCommunityAction extends IbmcAction {
	private SysCommunityService sysCommunityService;
	private SysCommunityBean    sysCommunityBean;
	private Map<String,String>  querymap;
	

	/**
	 *  权限人员所能管理的区域列表(用于构建树状)
	 *  即只加载要求加载的层级
	 */
	public void listRegionByJq(){
		LoginBean loginBean = this.getLoginSessionBean();
		//是否需要过滤区域（否表示查询全部区域数据）
		String needFilterScope = func.Trim(this.querymap.get("needFilterScope"));
		if("1".equals(needFilterScope)){
			//获取登录人员的ID，根据该ID查询其能操作的区域列表
			String userId = loginBean.getId();     
			//获取登录人员管理的级别
			String userLevSign = loginBean.getLevsign();
			String maxLev = func.Trim(this.querymap.get("maxLev"));
			Map map = new HashMap();
			map.put("userid",userId);
			map.put("levsign",userLevSign);
			map.put("maxlev",maxLev);
			this.printList(this.sysCommunityService.findCommRegionListByMap(map));
		}else{
			String parentId = func.Trim(this.querymap.get("parentId"));
			String maxLev = func.Trim(this.querymap.get("maxLev"));
			String queryTree = func.Trim(this.querymap.get("queryTree"));
			if("".equals(parentId)){
				parentId = IbmcConstant.COMM_SUPPER_PARENTID;
			}
			Map map = new HashMap();
			map.put("parentid",parentId);
			map.put("maxlev",maxLev);
			map.put("querytree",queryTree);
			this.printList(this.sysCommunityService.findSysCommunityList(map));
		}
	}
	
	/**
	 * 系统管理 -- 社区管理(村)[列表]
	 * @return
	 */
	public void listVillageByJq(){
		LoginBean loginBean = this.getLoginSessionBean();
		String userId = loginBean.getId();    
		//获取登录人员管理的级别
		String userLevSign = loginBean.getLevsign();
		Map map = new HashMap();
		//上级节点路径（可能非直接上级）
		map.put("commpath", func.Trim(this.querymap.get("commpath")));
		map.put("userid",   userId);
		map.put("levsign",   userLevSign);
		map.put("fields",   func.Trim(this.fields));
		map.put("keyword",  func.Trim(this.keyword));
		List dataList = this.sysCommunityService.findVillageListByScope(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
		String totalcount = String.valueOf(map.get("total"));
		this.print(this.creItemListPage(dataList,totalcount));
	}
	
	/**
	 * 新增[省、市、区----这些接口目前不实现]、村方法
	 * @return
	 */
	public String setSysCommunity(){
		try {
			String pkId = this.sysCommunityBean.getId();
			if(!func.IsEmpty(pkId)){
				this.sysCommunityBean = this.sysCommunityService.findSysCommunityBeanById(pkId);
			}
			String commLev = this.sysCommunityBean.getCommlev();
			if(IbmcConstant.COMM_LEV_VILLAGE.equals(commLev)){
				return "village";
			}
			else{
				this.sysCommunityBean = this.sysCommunityService.findSysCommunityBeanById(pkId);
				return "other";
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return "village";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String saveSysCommunity(){
		String rValue = INPUT;
		try {
			if(this.isValidToken()) {
				//注意这里还需要处理图片问题
				this.sysCommunityService.saveSysCommunityBean(this.sysCommunityBean);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.setSysCommunity();
			}else{
				this.reloadParentPage();
				return Constant.NO_DATA;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rValue;
	}
	

	/**
	 * 删除
	 */
	public void deleteVillageByJq(){
		try {
			String commId = this.sysCommunityBean.getId();
			if(!func.IsEmpty(commId)){
				this.sysCommunityService.deleteSysCommunity(commId,IbmcConstant.COMM_LEV_VILLAGE);
			}
			this.print("1");
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	

	/**
	 * 启用/禁用
	 */
	public void updateSysCommUseSignByJq(){
		try {
			String commId = this.sysCommunityBean.getId();
			String useSign = this.sysCommunityBean.getUsesign();
			if(!func.IsEmpty(commId) && !func.IsEmpty(useSign)){
				this.sysCommunityService.updateSysCommunityUseSign(this.sysCommunityBean);
			}
			this.print("1");
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	//以下为set get方法
	public SysCommunityService getSysCommunityService() {
		return sysCommunityService;
	}

	public void setSysCommunityService(SysCommunityService sysCommunityService) {
		this.sysCommunityService = sysCommunityService;
	}

	public SysCommunityBean getSysCommunityBean() {
		return sysCommunityBean;
	}

	public void setSysCommunityBean(SysCommunityBean sysCommunityBean) {
		this.sysCommunityBean = sysCommunityBean;
	}

	public Map<String, String> getQuerymap() {
		return querymap;
	}

	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}
}
