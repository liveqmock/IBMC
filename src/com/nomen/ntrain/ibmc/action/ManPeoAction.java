package com.nomen.ntrain.ibmc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.service.ManPeoService;
import com.nomen.ntrain.util.Constant;

/**
 * @description 人员_业主/租户表action层
 * @author 
 * @date 2015-01-18
 */
@SuppressWarnings("all")
public class ManPeoAction extends IbmcAction{

	private ManPeoService	    manPeoService;	    //人员_业主/租户表业务接口
	private LoginService 		loginService;      	//登录信息业务处理类
	private ManPeoBean		    manPeoBean;		    //人员_业主/租户表信息bean
	private Map<String,String>	querymap;			//传参map
	private List				dataList;			//结果集
	//========================辅助========================
	private String 				savePath; 			//保存文件的目录路径(通过依赖注入)
	private String				ownersign;			//房东标识
	private String				rentsign;			//租客标识
	
	/**
	 * 跳转到人员列表页面
	 * @return
	 */
	public String toForwardListPage(){
		HttpServletRequest req = ServletActionContext.getRequest();
		//初始化
		if(null==this.querymap) {
			this.querymap = new HashMap();
			this.querymap.put("ownersign", ownersign);    //房东管理标识
			this.querymap.put("rentsign", rentsign);    //租客管理标识
		}
		req.setAttribute("SEX_BOY", IbmcConstant.SEX_BOY);  //男
		req.setAttribute("SEX_GRIL", IbmcConstant.SEX_GRIL);//女
		return SUCCESS;
	}
	
	/**
	 * 查询人员列表数据
	 *
	 */
	public void findManPeoListByJq(){
		try {
			Map map = new HashMap();
			map.put("ownersign", func.Trim(this.querymap.get("ownersign")));
			map.put("rentsign", func.Trim(this.querymap.get("rentsign")));
			map.put("sortfield",func.Trim(this.sortfield));
			map.put("fields",   func.Trim(this.fields));
			map.put("keyword",  func.Trim(this.keyword));
			//针对人员查询中的类型
			String type = func.Trim(this.querymap.get("type"));
			if("1".equals(type)){  //房东类型
				map.put("ownersign", "1");
			}else if("2".equals(type)){  //租客类型
				map.put("rentsign", "1");
			}
			this.dataList = this.manPeoService.findManPeoList(map, func.Cint(this.getTagpage()), func.Cint(this.getRecord()));
			String totalcount = String.valueOf(map.get("total"));
			this.print(this.creItemListPage(dataList,totalcount));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到人员新增页面
	 * @return
	 */
	public String setManPeo(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			if(null == this.manPeoBean){
				this.manPeoBean = new ManPeoBean();
			}
			if(func.IsEmpty(this.manPeoBean.getId())){
				//新增
				this.manPeoBean.setOwnersign(func.Trim(ownersign));
				this.manPeoBean.setRentsign(func.Trim(rentsign));
			}else{
				Map map = new HashMap();
				map.put("id", this.manPeoBean.getId());
				this.manPeoBean = this.manPeoService.findManPeoBean(map);
			}
			req.setAttribute("SEX_BOY", IbmcConstant.SEX_BOY);  //男
			req.setAttribute("SEX_GRIL", IbmcConstant.SEX_GRIL);//女
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.setActMessage("operate.error");
		}
		return SUCCESS;
	}
	
	/**
	 * 保存人员新增,修改信息
	 * @return
	 */
	public String saveManPeo(){
		String rValue = INPUT;
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			if(this.isValidToken()) {
				//注意这里还需要处理图片问题
				String fileFolder = ServletActionContext.getServletContext().getRealPath("/")+IbmcConstant.MAN_PER_PHOTO_PATH;
				Map map = new HashMap();
				manPeoBean.setOptuserid(loginBean.getId());   //操作人员信息
				map.put("manPeoBean", manPeoBean);
				map.put("fileFolder", fileFolder);
				this.manPeoService.saveManPeo(map);
			}
			if(func.Trim(this.gosign).equals("1")) {
				this.manPeoBean = new ManPeoBean();
				this.setManPeo();
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
	 * 房产中的业主新增，正式卡中租客新增时
	 * 保存人员新增信息
	 * @return
	 */
	public String saveManPeoMess(){
		try {
			LoginBean loginBean = this.getLoginSessionBean();
			if(this.isValidToken()) {
				//注意这里还需要处理图片问题
				String fileFolder = ServletActionContext.getServletContext().getRealPath("/")+IbmcConstant.MAN_PER_PHOTO_PATH;
				Map map = new HashMap();
				manPeoBean.setOptuserid(loginBean.getId());   //操作人员信息
				map.put("manPeoBean", manPeoBean);
				map.put("fileFolder", fileFolder);
				this.manPeoService.saveManPeo(map);
				this.operateSuccess();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			this.operateError();
		}
		return Constant.NO_DATA;
	}
	
	/**
	 * 删除人员列表数据
	 *
	 */
	public void delManPeoByJq(){
		try {
			Map map = new HashMap();
			map.put("id", this.manPeoBean.getId());
			this.manPeoService.deleteManPeo(map);
			this.print("1");
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 人员查询[导出人员Excel]列表
	 *
	 */
	public void saveManPeoExpExcel(){
		//登录人员信息
		LoginBean loginBean = this.getLoginSessionBean();
		try{
			Map map = new HashMap();
			//进行参数的补充
			//针对人员查询中的类型
			String type = func.Trim(this.querymap.get("type"));
			if("1".equals(type)){  //房东类型
				map.put("ownersign", "1");
			}else if("2".equals(type)){  //租客类型
				map.put("rentsign", "1");
			}
			this.manPeoService.saveManPeoExpExcel(map,ServletActionContext.getResponse());
		} catch(Exception ex) {
			ex.printStackTrace();
			this.setActMessage("operate.error");
		}
	}
	
	/*****************************辅助方法***********************************/
	/**
	 * 查询该人员是否存在记录
	 *
	 */
	public void chkManPeoIsExistsByJq(){
		try {
			String idcard = this.manPeoBean.getIdcard();
			if(!func.IsEmpty(idcard)){
				boolean isfalg = this.manPeoService.findManPeoIsExist(idcard);
				if(isfalg){
					this.print("1");  //表示该人员存在系统中
				}else{
					this.print("0");  //表示该人员不存在系统中
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 根据身份证idcard查询人员信息bean
	 *
	 */
	public void findManPeoBeanByJq(){
		try {
			String idcard = this.manPeoBean.getIdcard();
			if(!func.IsEmpty(idcard)){
				Map map = new HashMap();
				map.put("idcard", idcard);
				this.manPeoBean = this.manPeoService.findManPeoBean(map);
				//当该条人员记录不存在时[系统将会默认将其主键id设置为-1]
				if(null== this.manPeoBean || func.IsEmpty(this.manPeoBean.getId())){
					this.manPeoBean = new ManPeoBean();
					this.manPeoBean.setId("-1");
				}
				this.printBean(manPeoBean);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.print("-1");
		}
	}
	
	/**
	 * 根据门卡序列号cardno查询租客，业主信息
	 */
	public void findManPeoListByCardnoByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String cardno = func.Trim(req.getParameter("cardno"));   //门卡序列号no
			if(!func.IsEmpty(cardno)){
				Map map = new HashMap();
				map.put("cardno", cardno);
				map.put("cardtype", IbmcConstant.CARDTYPE_REGULAR);    //正式卡
				List list = this.manPeoService.findManPeoListByCardno(map);
				this.printList(list);
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
	
	public String getOwnersign() {
		return ownersign;
	}

	public void setOwnersign(String ownersign) {
		this.ownersign = ownersign;
	}

	public String getRentsign() {
		return rentsign;
	}

	public void setRentsign(String rentsign) {
		this.rentsign = rentsign;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public ManPeoBean getManPeoBean() {
		return manPeoBean;
	}

	public void setManPeoBean(ManPeoBean manPeoBean) {
		this.manPeoBean = manPeoBean;
	}

	public ManPeoService getManPeoService() {
		return manPeoService;
	}

	public void setManPeoService(ManPeoService manPeoService) {
		this.manPeoService = manPeoService;
	}
	
}
