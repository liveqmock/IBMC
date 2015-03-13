package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.ManDoorHouseService;
import com.nomen.ntrain.ibmc.service.ManDoorService;
import com.nomen.ntrain.ibmc.service.ManFactoryService;
import com.nomen.ntrain.ibmc.service.SysCommunityService;
import com.nomen.ntrain.ibmc.webservice.WebEquipCommand;
import com.nomen.ntrain.ibmc.webservice.WebEquipStatus;
import com.nomen.ntrain.ibmc.webservice.WebEquipStatus.DevStatusBean;
import com.nomen.ntrain.util.Constant;

/**
 * @description 设备管理_门口机管理表action层
 * @author 
 * @date 2015-01-20
 */
@SuppressWarnings("all")
public class ManDoorAction extends IbmcAction{
	private ManDoorService	    manDoorService;	    //设备管理_门口机管理业务接口
	private ManDoorHouseService	manDoorHouseService;//设备管理_房产关联门口机
	private LoginService 		loginService;      	//登录信息业务处理类
	private ManFactoryService   manFactoryService;  //设备管理_厂商管理业务接口
	private SysCommunityService sysCommunityService;//房产业务接口
	private ManDoorBean		    manDoorBean;		//设备管理_门口机管理信息bean
	private ManDoorHouseBean    manDoorHouseBean;   //设备管理_门口机关联配置表
	private Map<String,String>	querymap;			//传参map
	private List				dataList;			//结果集
	//========================辅助========================
	private String 				savePath; 			//保存文件的目录路径(通过依赖注入)
	
	/*********************************************************************/
	/****************************门口机登记*********************************/
	/*********************************************************************/
	/**
	 * 跳转到设备管理_门口机管理列表页面
	 * @return
	 */
	public String toForwardListPage(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
			this.querymap.put("optuserid", this.getLoginSessionBean().getId());
			this.querymap.put("needFilterScope", "1");  //需要过滤权限
			this.querymap.put("maxLev", IbmcConstant.COMM_LEV_VILLAGE);   //最末端的级别从村结尾级别（0省 1市 2区 3村 4房产 5房间）
		}
		req.setAttribute("getEquipCommandMap", IbmcConstant.getEquipCommand());  //设备命令
		req.setAttribute("EQUIP_RESET", IbmcConstant.EQUIP_RESET);  //设备重置
		req.setAttribute("EQUIP_RESTOREFAC", IbmcConstant.EQUIP_RESTOREFAC);  //重置设备门禁权限
		req.setAttribute("EQUIP_RESETUNLOCK", IbmcConstant.EQUIP_RESETUNLOCK);  //恢复出厂设置

		return SUCCESS;
	}
	
	/**
	 * 查询设备管理_门口机管理列表数据
	 *
	 */
	public void findManDoorListByJq(){
		try {
			Map map = new HashMap();
			map.put("optuserid",func.Trim(querymap.get("optuserid")));
			map.put("showsign", func.Trim(querymap.get("showsign")));
			if(null !=manDoorHouseBean && !func.IsEmpty(this.manDoorHouseBean.getHouseid())){
				map.put("houseid", func.Trim(this.manDoorHouseBean.getHouseid()));   //房产houseid[这个参数主要是应对房产门口机关联中过滤已关联的门口机]
			}
			map.put("sortfield",func.Trim(this.sortfield));
			map.put("fields",   func.Trim(this.fields));
			map.put("keyword",  func.Trim(this.keyword));
			this.dataList = this.manDoorService.findManDoorList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到设备管理_门口机管理新增页面
	 * @return
	 */
	public String setManDoor(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			if(null == this.manDoorBean){
				this.manDoorBean = new ManDoorBean();
			}
			if(func.IsEmpty(this.manDoorBean.getId())){
				//新增
				LoginBean loginBean = this.getLoginSessionBean();
				this.manDoorBean.setOptuserid(loginBean.getId());
			}else{
				String id = this.manDoorBean.getId();
				this.manDoorBean = this.manDoorService.findManDoorBeanById(id);
				//查询厂商型号列表
				List modelList = this.manFactoryService.findManFacConfigList(manDoorBean.getFacid());
				req.setAttribute("modelList", modelList);
			}
			//查询厂商列表
			Map map = new HashMap();
			List facList = this.manFactoryService.findManFactoryList(map);
			req.setAttribute("facList", facList);
			this.getOpraterInfoIntoRequest();
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 保存设备管理_门口机管理信息
	 * @return
	 */
	public String saveManDoor(){
		String rValue = INPUT;
		try {
			if(this.isValidToken()) {
				//注意这里
				this.manDoorService.saveManDoorBean(manDoorBean);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.manDoorBean = new ManDoorBean();
				this.setManDoor();
				this.reloadParentPage2();
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
	 * 删除设备管理_门口机列表数据
	 *
	 */
	public void delManDoorByJq(){
		try {
			//注意删除门口机时还需要删除相对应的门口机关联表和门口机关联的卡
			String idStr = this.manDoorBean.getId();
			if(!func.IsEmpty(idStr)){
				this.manDoorService.deleteManDoor(idStr);
				this.print("1");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 跳转到导入门口机页面
	 * @return
	 */
	public String setManDoorImp(){
		
		return SUCCESS;
	}
	
	/**
	 * 门口机登记[验证数据有效性]
	 * @return
	 */
	public String saveManDoorTemp(){
		
		return SUCCESS;
	}
	
	/**
	 * 保存导出[门口机登记]Excel
	 * @return
	 */
	public String saveManDoorExpExcel(){
		//登录人员信息
		LoginBean loginBean = this.getLoginSessionBean();
		try{
			Map map = new HashMap();
			//进行参数的补充
			this.manDoorService.saveManDoorExpExcel(map,ServletActionContext.getResponse());
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		return null;
	}
	
	/**
	 * 查询统计门口机关联房产的个数
	 * @return
	 */
	public void findDoorLinkHouseCountByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String doorId = this.manDoorHouseBean.getDoorid();    //门口机id
			String deletesign = "0";
			if(!func.IsEmpty(doorId)){
				this.print(this.manDoorHouseService.findDoorLinkHouseCount(doorId, deletesign));
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/*********************************************************************/
	/****************************门口机管理*********************************/
	/*********************************************************************/
	
	/**
	 * 通过区域条件获取社区/（村）/房产关联的门口机 列表
	 *
	 */
	public void findManDoorHouseLinkListByJq(){
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
			this.dataList = this.sysCommunityService.findHouseLinkDoorList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
	}
	
	/**
	 * 跳转到房产关联门口机
	 * @return
	 */
	public String setManDoorLinkHouse(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			if(null == this.manDoorHouseBean){
				this.manDoorHouseBean = new ManDoorHouseBean();
			}
			req.setAttribute("showsign", "1"); //仅显示未关联的门口机标识
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 保存房产关联门口机
	 * @return
	 */
	public String saveManDoorLinkHouse(){
		try {
			if(this.isValidToken()) {
				//注意这里
				this.manDoorHouseService.insertManDoorHouseBean(manDoorHouseBean);
			}
			this.reloadParentPage();
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		return Constant.NO_DATA;
	}
	
	/**
	 * 跳转到取消关联门口机页面
	 * @return
	 */
	public String setManDoorCancelLinkHouse(){
		HttpServletRequest req = ServletActionContext.getRequest();
		req.setAttribute("getEquipCommandMap", IbmcConstant.getEquipCommand());  //设备命令
		req.setAttribute("EQUIP_RESET", IbmcConstant.EQUIP_RESET);  //设备重置
		req.setAttribute("EQUIP_RESTOREFAC", IbmcConstant.EQUIP_RESTOREFAC);  //重置设备门禁权限
		req.setAttribute("EQUIP_RESETUNLOCK", IbmcConstant.EQUIP_RESETUNLOCK);  //恢复出厂设置
		try {
			String houseid = this.manDoorHouseBean.getHouseid();
			SysCommunityBean sysCommunityBean = this.sysCommunityService.findSysCommunityBeanById(houseid);
			req.setAttribute("sysCommunityBean", sysCommunityBean);
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 查询房产管理门口机的列表
	 *
	 */
	public void findManHouseLinkDoorListByJq(){
		try {
			String houseid = this.manDoorHouseBean.getHouseid();
			Map map = new HashMap();
			map.put("houseid", houseid);
			map.put("fields", func.Trim(this.fields));
			map.put("keyword",func.Trim(this.keyword));
			this.dataList = this.manDoorHouseService.findManHouseLinkDoorList(map);
			this.printList(dataList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
	}
	
	/**
	 * 保存[取消房产管理门口机]操作
	 * @return
	 */
	public String delManDoorHouse(){
		try {
			if(this.isValidToken()) {
				//注意这里到时候这里还需要处理门口机中的卡操作
				this.manDoorHouseService.deleteManDoorHouseBean(manDoorHouseBean);
			}
			this.reloadParentPage();
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
		return Constant.NO_DATA;
	}
	
	/**
	 * 查询设备状态（直接查询webservice数据，在oracle中不做记录）
	 * @return
	 */
	public void findManDoorStatusByHouseId(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String houseId = req.getParameter("houseId");
			//查询房产对应的门口机列表
			List<ManDoorHouseBean> doorHouseList = this.manDoorHouseService.findDoorListByHouseId(houseId);
			if(null != doorHouseList && doorHouseList.size()>0){
				String devIdStr = "";
				for(ManDoorHouseBean d :doorHouseList){
					devIdStr +=","+d.getEquipid();
				}
				if(devIdStr.startsWith(",")) devIdStr = devIdStr.substring(1);
				String[] devIdArr = devIdStr.split(",");
				WebEquipStatus statusTool = new WebEquipStatus();
			    List<DevStatusBean> list  =  statusTool.getDevStatusInfo(devIdArr); 
			    int succCount = 0;
			    int errCount  = 0;
			    if(null != list){
				    for(DevStatusBean d : list){
				    	if("1".equals(d.getDevStatus())){
				    		succCount ++;
				    	}else{
				    		errCount ++;
				    	}
				    }
			    }
			    if(errCount != 0){
			    	if(succCount != 0){
					    this.print("在线["+succCount+"]--不在线[<span class='fontcolor_red'>"+errCount+"</span>]");
			    	}else{
					    this.print("不在线[<span class='fontcolor_red'>"+errCount+"</span>]");
			    	}
			    }else{
				    this.print("在线["+succCount+"]");
			    }
			}else{
				this.print("");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询设备状态（直接查询webservice数据，在oracle中不做记录）
	 * @return
	 */
	public void findManDoorStatusByEquipId(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String equipId = req.getParameter("equipId");
			//查询房产对应的门口机列表
			if(!func.IsEmpty(equipId)){
				String[] devIdArr = new String[]{equipId};
				WebEquipStatus statusTool = new WebEquipStatus();
			    List<DevStatusBean> list  =  statusTool.getDevStatusInfo(devIdArr); 
			    int succCount = 0;
			    int errCount  = 0;
			    if(null != list){
				    for(DevStatusBean d : list){
				    	if("1".equals(d.getDevStatus())){
				    		succCount ++;
				    	}else{
				    		errCount ++;
				    	}
				    }
			    }
			    if(errCount != 0){
			    	if(succCount != 0){
					    this.print("在线："+succCount+"；不在线:"+errCount);
			    	}else{
					    this.print("不在线:"+errCount);
			    	}
			    }else{
				    this.print("在线："+succCount);
			    }
			}else{
				this.print("");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设备命令操作接口
	 * @return
	 */
	public void setManDoorCommandByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String equipId = req.getParameter("equipId");           //设备ID
			String commandSign = req.getParameter("commandSign");   //获取设备命令标示
			String retval = "";
			//查询房产对应的门口机列表
			if(!func.IsEmpty(equipId)){
				WebEquipCommand command = new WebEquipCommand();
				if(IbmcConstant.EQUIP_RESET.equals(commandSign)){    //设备重置
					retval = command.resetEquip(equipId);
				}else if(IbmcConstant.EQUIP_RESTOREFAC.equals(commandSign)){  //重置设备门禁权限
					retval = command.resetUnlockEquip(equipId);
				}else if(IbmcConstant.EQUIP_RESETUNLOCK.equals(commandSign)){  //恢复出厂设置
					retval = command.restoreFacEquip(equipId);
				}
				this.print(retval);
			}
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

	public ManDoorBean getManDoorBean() {
		return manDoorBean;
	}

	public void setManDoorBean(ManDoorBean manDoorBean) {
		this.manDoorBean = manDoorBean;
	}

	public ManDoorService getManDoorService() {
		return manDoorService;
	}

	public void setManDoorService(ManDoorService manDoorService) {
		this.manDoorService = manDoorService;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public ManFactoryService getManFactoryService() {
		return manFactoryService;
	}

	public void setManFactoryService(ManFactoryService manFactoryService) {
		this.manFactoryService = manFactoryService;
	}

	public SysCommunityService getSysCommunityService() {
		return sysCommunityService;
	}

	public void setSysCommunityService(SysCommunityService sysCommunityService) {
		this.sysCommunityService = sysCommunityService;
	}

	public ManDoorHouseBean getManDoorHouseBean() {
		return manDoorHouseBean;
	}

	public void setManDoorHouseBean(ManDoorHouseBean manDoorHouseBean) {
		this.manDoorHouseBean = manDoorHouseBean;
	}

	public ManDoorHouseService getManDoorHouseService() {
		return manDoorHouseService;
	}

	public void setManDoorHouseService(ManDoorHouseService manDoorHouseService) {
		this.manDoorHouseService = manDoorHouseService;
	}
	
}
