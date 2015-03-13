package com.nomen.ntrain.ibmc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.ibmc.bean.ManCardBean;
import com.nomen.ntrain.ibmc.bean.ManCardRecordBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.bean.SysConfigBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.ManCardRecordService;
import com.nomen.ntrain.ibmc.service.ManCardService;
import com.nomen.ntrain.ibmc.service.ManCardTracePhoneService;
import com.nomen.ntrain.ibmc.service.ManCardTracemsgService;
import com.nomen.ntrain.ibmc.service.SysCommunityService;
import com.nomen.ntrain.ibmc.service.SysConfigService;
import com.nomen.ntrain.ibmc.webservice.WebUnlockRecord;
import com.nomen.ntrain.sgsdx.service.SmsItemlistService;
import com.nomen.ntrain.util.Constant;

/**
 * @description 门卡管理_门卡库表action层
 * @author ljl
 * @date 2015-1-23
 */
@SuppressWarnings("all")
public class ManCardAction extends IbmcAction{
	private SysCommunityService  	sysCommunityService;
	private ManCardService       	manCardService;  			//代码列表业务接口
	private ManCardRecordService 	manCardRecordService; 		//房产表业务处理类
	private ManCardTracemsgService 	manCardTracemsgService; 	//刷卡短信通知详情
	private ManCardTracePhoneService manCardTracePhoneService;
	private SmsItemlistService   	smsItemlistService;
	private SysConfigService        sysConfigService;           //系统配置
	private ManCardRecordBean    	manCardRecordBean;    		//房产表信息bean
	private ManCardBean          	manCardBean;     			//岗位类别代码信息表
	private Map<String,String>   	querymap;        			//传参map
	private String               	cardtype;        			//卡类型
	
	/**
	 * 门卡管理 -- 业主卡/临时卡/正式卡[列表]
	 * @return
	 */
	public void listManCardsByJq(){
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
		List dataList = this.sysCommunityService.findHouseLinkCardByScope(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
		String totalcount = String.valueOf(map.get("total"));
		this.print(this.creItemListPage(dataList,totalcount));
	}

	/**
	 * 门卡管理 -- 业主卡/临时卡/正式卡[列表跳转]
	 * @return
	 */
	public String listManCard(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String houseId = this.querymap.get("houseid");
		//获取业主信息
		SysCommunityBean houseBean = this.sysCommunityService.findSysCommunityBeanById(houseId);
		req.setAttribute("houseBean", houseBean);
		
		req.setAttribute("cardTypeMap",IbmcConstant.getCardTypeSign());
		
		//根据卡类型构建卡片失效时间
		SysConfigBean configBean = this.sysConfigService.findSysConfigOfStandar(IbmcConstant.CONFIG_COMMID);
		if(null == configBean){configBean = new SysConfigBean();}
		if(IbmcConstant.CARDTYPE_OWNER.equals(cardtype)){
			req.setAttribute("defEndDate",configBean._getOwnerCardValid());
		}else if(IbmcConstant.CARDTYPE_REGULAR.equals(cardtype)){
			req.setAttribute("defEndDate",configBean._getRegularCardValid());
		}else{
			req.setAttribute("defEndDate","");
		}
		this.getOpraterInfoIntoRequest();
		return SUCCESS;
	}

	/**
	 * 门卡管理 -- 业主卡/临时卡/正式卡[列表]
	 * @return
	 */
	public void listManCardByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String houseId = this.querymap.get("houseid");
		Map map = new HashMap();
		//上级节点路径（可能非直接上级）
		map.put("houseid",  houseId);
		map.put("cardtype", this.cardtype);
		map.put("showsign", func.Trim(req.getParameter("showsign")));
		map.put("fields",   func.Trim(this.fields));
		map.put("keyword",  func.Trim(this.keyword));
		map.put("sortfield","roomid,rentid,id");
		List dataList = this.manCardService.findManCardListByPage(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
		String totalcount = String.valueOf(map.get("total"));
		this.print(this.creItemListPage(dataList,totalcount));
	}
	

	/**
	 * 门卡详细
	 * @return
	 */
	public void findManCardBeanByJq(){
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			String cardId = this.manCardBean.getId();
			ManCardBean cardBean = this.manCardService.findManCardBeanById(cardId);
			if(null == cardBean){
				cardBean = new ManCardBean();
				cardBean.setId("-1");
			}
			this.printBean(cardBean);
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}

	/**
	 * 门卡管理 -- 业主卡/临时卡/正式卡[保存]
	 * @return
	 */
	public void saveManCardByJq(){
		//需要判断门卡类型
		String cardType = this.manCardBean.getCardtype();
		//房屋ID
		String houseId = this.manCardBean.getHouseid();
		//门卡NO + MAC
		String cardNo = this.manCardBean.getCardno();
		if(func.IsEmpty(cardType) || func.IsEmpty(houseId) || func.IsEmpty(cardNo)){
			this.print("-1");
		}else{
			try{
				LoginBean loginBean = this.getLoginSessionBean();
				this.manCardBean.setOperuserid(loginBean.getId());
				this.manCardService.saveManCardBean(this.manCardBean);
				this.print("1");
			}catch(Exception ex){
				this.print("-2");
			}
		}
	}

	/**
	 * 通过门卡(序列号)查询门卡所属类型(业主卡/临时卡/正式卡)
	 * @return
	 */
	public void findManCardByCardNo(){
		try{
			HttpServletRequest req = ServletActionContext.getRequest();
			String cardNo = manCardBean.getCardno();
			if(!func.IsEmpty(cardNo)){
				ManCardBean manCardBean = this.manCardService.findManCardByCardNo(cardNo);
				if(null == manCardBean) manCardBean = new ManCardBean();
				this.printBean(manCardBean);
			}else{
				this.printBean(new ManCardBean());
			}
		}catch(Exception ex){
		}
	}
	
	/**
	 * 注销卡
	 */
	public void deleteManCardByJq(){
		try{
			HttpServletRequest req = ServletActionContext.getRequest();
			String cardId = manCardBean.getId();
			if(!func.IsEmpty(cardId)){
				this.manCardService.deleteManCardById(cardId);
			}
			this.print("1");
		}catch(Exception ex){
			this.print("-1");
		}
	}

	/**
	 * 重置临时卡
	 */
	public void resetManCardByJq(){
		try{
			HttpServletRequest req = ServletActionContext.getRequest();
			String cardId = manCardBean.getId();
			if(!func.IsEmpty(cardId)){
				LoginBean loginBean = this.getLoginSessionBean();
				this.manCardService.updateManCardValidDate(cardId,loginBean.getId());
			}
			this.print("1");
		}catch(Exception ex){
			this.print("-1");
		}
	}
	
	/**
	 * 正式卡一键延期
	 * @return
	 * 1:表示当前操作成功,0表示参数错误,-1表示操作失败
	 */
	public void delayManCardByJq(){
		try {
			String cardIdStr = func.Trim(querymap.get("cardidstr"));
			String houseIdStr = func.Trim(querymap.get("houseidstr"));
			if(!this.func.IsEmpty(cardIdStr)&&!this.func.IsEmpty(houseIdStr)){
				this.manCardService.updateManCardBeanByCardidStr(cardIdStr,houseIdStr);
				this.print("1");
			}else{
				this.print("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 在门卡管理--正式门卡中
	 * 查询房间列表
	 */
	public void findManRoomListByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String houseId = req.getParameter("houseid");
			//判断是否是正式卡列表，若是则要求查询出房间列表
			Map rM = new HashMap();
			rM.put("parentid",houseId);
			rM.put("sortfield","ID");
			List roomList = this.sysCommunityService.findManRoomListByHouseId(rM);
			this.printList(roomList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/*****************************************************************/
	/***************************刷卡记录查询****************************/
	/*****************************************************************/
	
	/**
	 * 跳转到刷卡记录列表页面
	 * @return
	 */
	public String toForwardListPage(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
			this.querymap.put("needFilterScope", "1");  //需要过滤权限
			this.querymap.put("maxLev", IbmcConstant.COMM_LEV_VILLAGE);   //最末端的级别从村结尾级别（0省 1市 2区 3村 4房产 5房间）
		}
		req.setAttribute("CARDTYPE_OWNER",IbmcConstant.CARDTYPE_OWNER);   //业主卡
		req.setAttribute("CARDTYPE_TEMP",IbmcConstant.CARDTYPE_TEMP);   //临时卡
		req.setAttribute("CARDTYPE_REGULAR",IbmcConstant.CARDTYPE_REGULAR);   //正式卡
		//卡类别
		req.setAttribute("cardTypeMap",IbmcConstant.getCardTypeSign());
		return SUCCESS;
	}
	
	/**
	 * 查询刷卡记录查询信息列表
	 *
	 */
	public void findManCardRecordListByJq(){
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			String userId = loginBean.getId();    
			//获取登录人员管理的级别
			String userLevSign = loginBean.getLevsign();
			Map map = new HashMap();
			map.put("commpath", func.Trim(querymap.get("commpath")));  //市/区/村/房产 
			map.put("cardtype", func.Trim(querymap.get("cardtype")));  //1业主卡 2临时卡 3正式卡
			map.put("fields", func.Trim(this.fields));
			map.put("keyword",func.Trim(this.keyword));
			map.put("sortfield", "touchdate desc");
			List dataList = this.manCardRecordService.findManCardRecordList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到刷卡记录详细信息
	 * @return
	 */
	public String setManCardRecordDesc(){
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			//跳转到刷卡记录查询详细信息
			String id = this.manCardRecordBean.getId();
			if(!func.IsEmpty(id)){
				this.manCardRecordBean = this.manCardRecordService.findManCardRecordBeanById(id);
				if(!func.IsEmpty(manCardRecordBean.getCardid())){
					ManPeoBean manPeoBean = this.manCardRecordService.findManPeoBeanByCardId(manCardRecordBean.getCardid());
					req.setAttribute("manPeoBean", manPeoBean);
				}
			}
			req.setAttribute("CARDTYPE_OWNER",IbmcConstant.CARDTYPE_OWNER);   //业主卡
			req.setAttribute("CARDTYPE_TEMP",IbmcConstant.CARDTYPE_TEMP);   //临时卡
			req.setAttribute("CARDTYPE_REGULAR",IbmcConstant.CARDTYPE_REGULAR);   //正式卡
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *
	 */
	public void findCardRecordImageByJq(){
		//需要判断门卡类型
		//String cardtype = this.manCardBean.getCardtype();
		HttpServletRequest req = ServletActionContext.getRequest();
		String imagePath = func.Trim(req.getParameter("touchimg"));
		HttpServletResponse response =  ServletActionContext.getResponse();
		OutputStream os = null;   //输出流
		response.reset();
		response.setContentType("image/*");
		WebUnlockRecord in = new WebUnlockRecord();
		try {
			os = response.getOutputStream();
			byte[] buffer = in.getImage(imagePath);
			os.write(buffer);
			os.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(os != null) {
					os.close();
				}
			}catch (IOException e) {}
		}
	}
	
	/*********************************************************************/
	/*************************短信发送记录查询*******************************/
	/*********************************************************************/
	
	/**
	 * 跳转到查询短信放记录列表页面
	 * @return
	 */
	public String toForwardCardMessListPage(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
			this.querymap.put("optuserid", this.getLoginSessionBean().getId());
			this.querymap.put("needFilterScope", "1");  //需要过滤权限
			this.querymap.put("maxLev", IbmcConstant.COMM_LEV_VILLAGE);   //最末端的级别从村结尾级别（0省 1市 2区 3村 4房产 5房间）
		}
		req.setAttribute("CARDTYPE_OWNER",IbmcConstant.CARDTYPE_OWNER);   //业主卡
		req.setAttribute("CARDTYPE_TEMP",IbmcConstant.CARDTYPE_TEMP);   //临时卡
		req.setAttribute("CARDTYPE_REGULAR",IbmcConstant.CARDTYPE_REGULAR);   //正式卡
		//卡类别
		req.setAttribute("cardTypeMap",IbmcConstant.getCardTypeSign());
		return SUCCESS;
	}
	
	/**
	 * 查询短信发送记录列表数据
	 *
	 */
	public void findManCardLinkNoticeMessListByJq(){
		try {
			Map map = new HashMap();
			//上级节点路径（可能非直接上级）
			map.put("commpath", func.Trim(this.querymap.get("commpath")));
			map.put("startdate",func.Trim(querymap.get("startdate")));
			map.put("enddate", func.Trim(querymap.get("enddate")));
			map.put("fields",   func.Trim(this.fields));
			map.put("keyword",  func.Trim(this.keyword));
			List dataList = this.manCardService.findManCardLinkNoticeMessList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到短信发送详细信息页面
	 * @return
	 */
	public String setManCardLinkNoticeMess(){
		HttpServletRequest req = ServletActionContext.getRequest();
		req.setAttribute("MESSSTATE_WAIT", IbmcConstant.MESSSTATE_WAIT);  //待发送
		req.setAttribute("MESSSTATE_SUCC", IbmcConstant.MESSSTATE_SUCC);  //发送成功
		req.setAttribute("MESSSTATE_FAIL", IbmcConstant.MESSSTATE_FAIL);  //发送失败
		return SUCCESS;
	}
	
	/**
	 * 查询刷卡短信通知列表
	 */
	public void findManCardNoticeMessListByJq(){
		try {
			Map map = new HashMap();
			map.put("traceid",  func.Trim(querymap.get("traceid")));
			map.put("fields",  func.Trim(this.fields));
			map.put("keyword", func.Trim(this.keyword));
			map.put("sortfield", "touchdate desc");
			List dataList = this.manCardTracemsgService.findManCardTracemsgBeanList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到门卡短信通知引入页面
	 * @return
	 */
	public String setManCardInMessImp(){
		HttpServletRequest req = ServletActionContext.getRequest();
		if(null==this.querymap) {
			this.querymap = new HashMap();
			this.querymap.put("userid", this.getLoginSessionBean().getId());
			this.querymap.put("needFilterScope", "1");  //需要过滤权限
			this.querymap.put("maxLev", IbmcConstant.COMM_LEV_VILLAGE);   //最末端的级别从村结尾级别（0省 1市 2区 3村 4房产 5房间）
		}
		req.setAttribute("CARDTYPE_OWNER",IbmcConstant.CARDTYPE_OWNER);   //业主卡
		req.setAttribute("CARDTYPE_TEMP",IbmcConstant.CARDTYPE_TEMP);   //临时卡
		req.setAttribute("CARDTYPE_REGULAR",IbmcConstant.CARDTYPE_REGULAR);   //正式卡
		req.setAttribute("cardTypeMap",IbmcConstant.getCardTypeSign());  //卡类别
		return SUCCESS;
	}
	
	/**
	 * 查询短信通知中引入的门卡列表
	 */
	public void findManCardListInMessImpByCommIdByJq(){
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			String userId = loginBean.getId();
			//获取登录人员管理的级别
			String userLevSign = loginBean.getLevsign();
			Map map = new HashMap();
			map.put("commpath", func.Trim(querymap.get("commpath")));  //市/区/村/房产 
			map.put("cardtype", func.Trim(querymap.get("cardtype")));  //1业主卡 2临时卡 3正式卡
			map.put("userid", func.Trim(querymap.get("userid")));  //人员userid
			map.put("commlev", func.Trim(loginBean.getLevsign()));  //层级
			map.put("fields", func.Trim(this.fields));
			map.put("keyword",func.Trim(this.keyword));
			map.put("sortfield", "ownerid,rentid,cardtype,roomid");
			List dataList = this.manCardService.findManCardListInMessImpByCommId(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存短信通知引入门卡
	 */
	public String saveManCardMessImp(){
		try {
			if(this.isValidToken()){
				LoginBean loginBean = this.getLoginSessionBean();
				String cardIdStr = func.Trim(this.querymap.get("cardidstr"));
				String phone = func.Trim(this.querymap.get("phone"));
				String enddate = func.Trim(this.querymap.get("enddate"));
				Map map = new HashMap();
				map.put("cardIdStr", cardIdStr);
				map.put("phone", phone);
				map.put("enddate", enddate);
				map.put("loginBean", loginBean);
				this.manCardTracePhoneService.saveManCardTracePhoneBean(map);
			}
			this.reloadParentPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.NO_DATA;
	}
	
	/**
	 * 手动发送_[保存]
	 */
	public void sentSmsByManu(){
		try{
			HttpServletRequest req = ServletActionContext.getRequest();
			String id = func.Trim(req.getParameter("id"));
			if(!func.IsEmpty(id)){
				this.smsItemlistService.sendSmsItemlistManual(id);
			}
			this.print("1");
		}catch(Exception e){
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	//Get和Set方法
	public ManCardService getManCardService() {
		return manCardService;
	}
	public void setManCardService(ManCardService manCardService) {
		this.manCardService = manCardService;
	}
	public ManCardBean getManCardBean() {
		return manCardBean;
	}
	public void setManCardBean(ManCardBean manCardBean) {
		this.manCardBean = manCardBean;
	}
	public Map<String, String> getQuerymap() {
		return querymap;
	}
	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}
	public SysCommunityService getSysCommunityService() {
		return sysCommunityService;
	}

	public void setSysCommunityService(SysCommunityService sysCommunityService) {
		this.sysCommunityService = sysCommunityService;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public ManCardRecordBean getManCardRecordBean() {
		return manCardRecordBean;
	}

	public void setManCardRecordBean(ManCardRecordBean manCardRecordBean) {
		this.manCardRecordBean = manCardRecordBean;
	}

	public ManCardRecordService getManCardRecordService() {
		return manCardRecordService;
	}

	public void setManCardRecordService(ManCardRecordService manCardRecordService) {
		this.manCardRecordService = manCardRecordService;
	}

	public ManCardTracemsgService getManCardTracemsgService() {
		return manCardTracemsgService;
	}

	public void setManCardTracemsgService(
			ManCardTracemsgService manCardTracemsgService) {
		this.manCardTracemsgService = manCardTracemsgService;
	}
	
	public SmsItemlistService getSmsItemlistService() {
		return smsItemlistService;
	}

	public void setSmsItemlistService(SmsItemlistService smsItemlistService) {
		this.smsItemlistService = smsItemlistService;
	}

	public ManCardTracePhoneService getManCardTracePhoneService() {
		return manCardTracePhoneService;
	}

	public void setManCardTracePhoneService(
			ManCardTracePhoneService manCardTracePhoneService) {
		this.manCardTracePhoneService = manCardTracePhoneService;
	}

	public SysConfigService getSysConfigService() {
		return sysConfigService;
	}

	public void setSysConfigService(SysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}
}
