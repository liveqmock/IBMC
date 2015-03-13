package com.nomen.ntrain.ibmc.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.ManHouseService;
import com.nomen.ntrain.ibmc.service.SysCommunityService;
import com.nomen.ntrain.util.Constant;

/**
 * @description 房产管理_action层
 * @author 
 * @date 2015-01-21
 */
@SuppressWarnings("all")
public class ManHouseAction extends IbmcAction{
	private SysCommunityService sysCommunityService; //房产表业务处理类
	private ManHouseService     manHouseService; //房产表业务处理类
	private SysCommunityBean    sysCommunityBean;    //房产表信息bean
	private LoginService 		loginService;      	//登录信息业务处理类
	private Map<String,String>	querymap;			//传参map
	private List				dataList;			//结果集
	//========================辅助========================
	private String 				savePath; 			//保存文件的目录路径(通过依赖注入)
	
	
	/*****************************************************************/
	/***************************房产信息维护****************************/
	/*****************************************************************/
	/**
	 * 跳转到房产管理_房产信息列表页面
	 * @return
	 */
	public String toForwardListPage(){
		LoginBean loginBean = this.getLoginSessionBean();
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
			this.querymap.put("needFilterScope", "1");  //需要过滤权限
			this.querymap.put("maxLev", IbmcConstant.COMM_LEV_VILLAGE);   //最末端的级别从村结尾级别（0省 1市 2区 3村 4房产 5房间）
			this.querymap.put("houselev", IbmcConstant.COMM_LEV_HOUSE);   //房产标识
			this.querymap.put("villagelev", IbmcConstant.COMM_LEV_VILLAGE);   //村[社区]
		}
		return SUCCESS;
	}
	
	/**
	 * 查询房产管理_房产信息列表
	 *
	 */
	public void findManHouseListByJq(){
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			String userId = loginBean.getId();    
			//获取登录人员管理的级别
			String userLevSign = loginBean.getLevsign();
			Map map = new HashMap();
			//上级节点路径（可能非直接上级）
			map.put("commpath", func.Trim(this.querymap.get("commpath")));
			map.put("userid", userId);
			map.put("levsign", userLevSign);
			map.put("sortfield",func.Trim(this.sortfield));
			map.put("fields", func.Trim(this.fields));
			map.put("keyword",func.Trim(this.keyword));
			List dataList = this.sysCommunityService.findHouseListByScope(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到房产管理_房产信息维护新增页面
	 * @return
	 */
	public String setManHouse(){
		try {
			if(null == this.sysCommunityBean){
				this.sysCommunityBean = new SysCommunityBean();
			}
			if(func.IsEmpty(this.sysCommunityBean.getId())){
				//暂时无操作
				sysCommunityBean.setUsesign("1");   //设置启用标志
				sysCommunityBean.setCommlev(IbmcConstant.COMM_LEV_HOUSE);   //新增房产[（0省 1市 2区 3村 4房产 5房间）]
			}else{
				String commid = this.sysCommunityBean.getId();
				this.sysCommunityBean = this.sysCommunityService.findSysCommunityBeanById(commid);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 保存房产管理_房产信息维护保存信息
	 * @return
	 */
	public String saveManHouse(){
		String rValue = INPUT;
		try {
			if(this.isValidToken()) {
				//注意这里[未实现]
				this.sysCommunityService.saveSysCommunityBean(sysCommunityBean);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.sysCommunityBean = new SysCommunityBean();
				this.setManHouse();
				this.reloadParentPage2();
			}else{
				this.reloadParentPage();
				return Constant.NO_DATA;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			this.operateError();
		}
		return rValue;
	}
	
	/**
	 * 保存导出[房产信息]导出Excel
	 * @return
	 */
	public String saveManHouseExpExcel(){
		//登录人员信息
		LoginBean loginBean = this.getLoginSessionBean();
		try{
			Map map = new HashMap();
			String userId = loginBean.getId();    
			//获取登录人员管理的级别
			String userLevSign = loginBean.getLevsign();
			//上级节点路径（可能非直接上级）
			map.put("commpath", func.Trim(this.querymap.get("commpath")));
			map.put("userid", userId);
			map.put("levsign", userLevSign);
			//进行参数的补充
			this.manHouseService.saveManHouseExpExcel(map,ServletActionContext.getResponse());
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		return null;
	}
	
	/**
	 * 删除房产,房间列表数据
	 *
	 */
	public void delManHouseByJq(){
		try {
			String id = func.Trim(this.sysCommunityBean.getId());
			String dellev = func.Trim(this.querymap.get("dellev"));  //这个用于判断房产,房间删除判断标识
			this.sysCommunityService.deleteSysCommunity(id,dellev);
			this.print("1");
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 跳转到房产信息维护中[房间]新增,修改页面
	 * @return
	 */
	public String setManHouseRomm(){
		try {
			String id = this.sysCommunityBean.getId();   //获取房产的主键ID
			this.sysCommunityBean = this.sysCommunityService.findSysCommunityBeanById(id);
			if(this.querymap == null) this.querymap = new HashMap();
			this.querymap.put("roomlev", IbmcConstant.COMM_LEV_ROOM);   //房间标识
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 查询房产下面的房间列表
	 *
	 */
	public void findManHouseRoomListByJq(){
		try {
			//查询房产下面的房间列表
			Map map = new HashMap();
			map.put("parentid",func.Trim(this.sysCommunityBean.getId()));
			map.put("sortfield",func.Trim(this.sortfield));
			map.put("fields", func.Trim(this.fields));
			map.put("keyword",func.Trim(this.keyword));
			this.dataList = this.sysCommunityService.findManRoomListByHouseId(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询房产下面的房间信息[bean]通过主键id
	 * @return
	 */
	public void findManHouseRoomBeanByJq(){
		try {
			String id = this.sysCommunityBean.getId();   //获取房间的主键ID
			//修改时
			if(!func.IsEmpty(sysCommunityBean.getId())){
				this.sysCommunityBean = this.sysCommunityService.findSysCommunityBeanById(id);
				this.printBean(sysCommunityBean);
			}
		} catch(Exception ex) {
			this.print("-1");
			ex.printStackTrace();
		}
	}
	
	/**
	 * 保存房产信息维护中的房间信息
	 * @return
	 */
	public void saveManRoomByJq(){
		try {
			//新增时[查询排序号]
			if(func.IsEmpty(sysCommunityBean.getId())){
				String parentid = this.sysCommunityBean.getParentid();
				Map map = new HashMap();
				map.put("parentid", parentid);
				String commorder = this.sysCommunityService.findSysCommunityNextOrder(map);
				sysCommunityBean.setCommorder(commorder);
				sysCommunityBean.setCommlev(IbmcConstant.COMM_LEV_ROOM);
			}
			String commname = URLDecoder.decode(this.sysCommunityBean.getCommname(),"UTF-8");
			String remark = URLDecoder.decode(this.sysCommunityBean.getRemark(),"UTF-8");
			sysCommunityBean.setCommname(commname);
			sysCommunityBean.setRemark(remark);
			this.sysCommunityService.saveSysCommunityBean(sysCommunityBean);
			this.reloadParentPage2();
			this.print("1");
		} catch(Exception ex) {
			this.print("-1");
			ex.printStackTrace();
		}
	}
	
	/**
	 * 查询统计门口机关联房产的个数
	 * @return
	 */
	public void findHouseLinkDoorAndCardCountByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String houseId = req.getParameter("houseId");
			if(!func.IsEmpty(houseId)){
				this.print(this.manHouseService.findHouseLinkDoorAndCardCount(houseId));
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	
	/*****************************************************************/
	/***************************房产信息导入****************************/
	/*****************************************************************/
	
	/**
	 * 保存[验证数据的有效性]房产信息导入到临时表中
	 * @return
	 */
	public String saveManHouseImp(){
		String rValue = "success";  //默认返回的是成功页面
		HttpServletRequest req = ServletActionContext.getRequest();
		//执行文件上传以及解析，若出现错误，则直接返回，并提供解析后的文件下载，若正确，则直接显示预览的链接，并提示操作成功。
		try {
			if(this.isValidToken() && this.upfile != null){
				LoginBean loginBean = this.getLoginSessionBean();
				//上传附件并返回保存名
				String[] saveNameArr = this.execUpload(this.savePath,0);
				//文件路径[]
				String fileFolder = ServletActionContext.getServletContext().getRealPath("/")+this.savePath;
				//获取社区id[parentid]
				String parentid = this.querymap.get("parentid");
				//保存
				Map iMap = new HashMap();
				iMap.put("fileFolder",  fileFolder);
				iMap.put("saveNameArr", saveNameArr);
				iMap.put("parentid",  parentid);      //父亲parentid
				iMap.put("optuserid", loginBean.getId());   //操作人
				String rtn = this.manHouseService.saveManHouseImpExcel(iMap);    //rtn {1：正确 2：错误}
				req.setAttribute("errorsign", rtn);
				if("2".equals(rtn)){
					rValue = "input";   //返回错误提示页面
				}
				this.querymap.put("showsign", "0"); //默认是显示房产,房间信息
			}
			//用于判断页面中房产，房间标识
			req.setAttribute("COMM_LEV_HOUSE", IbmcConstant.COMM_LEV_HOUSE);   //房产标识
			req.setAttribute("COMM_LEV_ROOM", IbmcConstant.COMM_LEV_ROOM);   //房间标识
		} catch (Exception e) {
			this.setActMessage("operate.error");
			e.printStackTrace();
		}
		return rValue;
	}
	
	/**
	 * 导出此次房产信息中通过验证之后的数据
	 *
	 */
	public void expManHouseTempImpData(){
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			//获取社区id[parentid]
			String parentid = this.querymap.get("parentid");
			Map param = new HashMap();
			//保存
			Map iMap = new HashMap();
			iMap.put("parentid",  parentid);      //父亲parentid
			iMap.put("optuserid", loginBean.getId());   //操作人
			this.manHouseService.saveManHouseTempExpExcel(iMap,res);
		} catch (Exception e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
	}
	
	/**
	 * 跳转到房产信息导入页面中的预览页面
	 * @return
	 */
	public String setManHouseImpDataPurview(){
		HttpServletRequest req = ServletActionContext.getRequest();
		req.setAttribute("COMM_LEV_HOUSE", IbmcConstant.COMM_LEV_HOUSE);   //房产标识
		req.setAttribute("COMM_LEV_ROOM", IbmcConstant.COMM_LEV_ROOM);   //房间标识
		return SUCCESS;
	}
	
	/**
	 * 查询此次导入到临时表中的房产信息数据列表
	 *
	 */
	public void findManHouseImpDataListByJq(){
		try {
			String parentid = this.querymap.get("parentid");
			String showsign = this.querymap.get("showsign");   //仅显示导入的房产信息标识
			LoginBean loginBean = this.getLoginSessionBean();
			Map iMap = new HashMap();
			iMap.put("parentid",  parentid);      //父亲parentid
			iMap.put("optuserid", loginBean.getId());   //操作人
			iMap.put("fields", this.fields);   //关键字段
			iMap.put("keyword", this.keyword);   //关键字
			iMap.put("sortfield", "id");   //关键字
			if("1".equals(showsign)){
				//查询房产列表数据
				List list = this.manHouseService.findSysCommTempHouseList(iMap);
				this.printList(list);
			}else{
				//查询房产房间数组列表数据
				List list = this.manHouseService.findSysCommTempList(iMap);
				this.printList(list);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 将此次导入的临时表中的数据导入到正式表中
	 * @return
	 */
	public String saveManHouseByImpTempData(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//执行文件上传以及解析，若出现错误，则直接返回，并提供解析后的文件下载，若正确，则直接显示预览的链接，并提示操作成功。
		try {
			if(this.isValidToken()){
				LoginBean loginBean = this.getLoginSessionBean();
				//获取社区id[parentid]
				String parentid = this.querymap.get("parentid");
				if(!func.IsEmpty(parentid)){
					//保存
					Map iMap = new HashMap();
					iMap.put("parentid",  parentid);      //父亲parentid
					iMap.put("optuserid", loginBean.getId());   //操作人
					this.manHouseService.saveManHouseByImpTempData(iMap);    //rtn {1：正确 2：错误}
					this.setActMessage("operate.success");
				}
			}
		} catch (Exception e) {
			this.setActMessage("operate.error");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/*****************************辅助方法***********************************/
	/**
	 * 查询房产下一个排序号
	 *
	 */
	public void findManHouseNextOrderByJq(){
		try {
			String parentid = this.sysCommunityBean.getParentid();
			Map map = new HashMap();
			map.put("parentid", parentid);
			String commorder = this.sysCommunityService.findSysCommunityNextOrder(map);
			this.print(commorder);
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	//Get和Set方法
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

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public SysCommunityBean getSysCommunityBean() {
		return sysCommunityBean;
	}

	public void setSysCommunityBean(SysCommunityBean sysCommunityBean) {
		this.sysCommunityBean = sysCommunityBean;
	}

	public SysCommunityService getSysCommunityService() {
		return sysCommunityService;
	}

	public void setSysCommunityService(SysCommunityService sysCommunityService) {
		this.sysCommunityService = sysCommunityService;
	}

	public ManHouseService getManHouseService() {
		return manHouseService;
	}

	public void setManHouseService(ManHouseService manHouseService) {
		this.manHouseService = manHouseService;
	}
	
}
