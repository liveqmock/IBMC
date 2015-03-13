package com.nomen.ntrain.ibmc.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManCardDAO;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.ibmc.dao.ManPeoDAO;
import com.nomen.ntrain.ibmc.dao.SysCommunityDAO;
import com.nomen.ntrain.ibmc.service.SysCommunityService;
import com.nomen.ntrain.util.PubFunc;

public class SysCommunityServiceImpl extends BaseServiceImpl implements SysCommunityService {

	private SysCommunityDAO sysCommunityDAO;
	private ManDoorHouseDAO manDoorHouseDAO;
	private ManCardDAO      manCardDAO;
	private ManPeoDAO       manPeoDAO;
	
	//private SynWebServiceUtil synWebServiceUtil;
	
	public List<SysCommunityBean> findVillageListByScope(Map map, int tagpage,
			int record) {
		return this.sysCommunityDAO.findVillageListByScope(map,tagpage,record);
	}

	public List<SysCommunityBean> findVillageListByScope(Map map) {
		return this.sysCommunityDAO.findVillageListByScope(map);
	}
	
	public List<SysCommunityBean> findHouseListByScope(Map map, int tagpage,
			int record) {
		return this.sysCommunityDAO.findHouseListByScope(map,tagpage,record);
	}

	public List<SysCommunityBean> findHouseListByScope(Map map) {
		return this.sysCommunityDAO.findHouseListByScope(map);
	}
	
	public List<SysCommunityBean> findHouseLinkCardByScope(Map map, int tagpage,
			int record) {
		return this.sysCommunityDAO.findHouseLinkCardByScope(map,tagpage,record);
	}

	public List<SysCommunityBean> findHouseLinkCardByScope(Map map) {
		return this.sysCommunityDAO.findHouseLinkCardByScope(map);
	}
	
	public List<SysCommunityBean> findSysCommunityList(Map map){
		return this.sysCommunityDAO.findSysCommunityList(map);
	}
	
	public String saveSysCommunityBean(SysCommunityBean bean) {
		String commId = bean.getId();
		String parentId = bean.getParentid();
		boolean isUpdate = false;
		if(null == commId || "".equals(commId)){
			bean.setUsesign("1");
			commId = this.sysCommunityDAO.insertSysCommunityBean(bean);
		}else{
			isUpdate = true;
			this.sysCommunityDAO.updateSysCommunityBean(bean);
		}
		String commPath = "";
		String commLev = bean.getCommlev();
		if(IbmcConstant.COMM_LEV_PROVINCE.equals(commLev)){
			commPath = commId;
		}else if(IbmcConstant.COMM_LEV_HOUSE.equals(commLev)){
			//对房产信息中的房东进行更新操作
			ManPeoBean manPeoBean = new ManPeoBean();
			manPeoBean.setId(bean.getOwnerid());
			manPeoBean.setOwnersign("1");
			this.manPeoDAO.updateManPeoBean(manPeoBean);
			SysCommunityBean parentCommBean = this.sysCommunityDAO.findSysCommunityBeanById(parentId);
			String parentCommPath = parentCommBean.getCommpath();
			commPath = parentCommPath+"/"+commId;
		}else{
			SysCommunityBean parentCommBean = this.sysCommunityDAO.findSysCommunityBeanById(parentId);
			String parentCommPath = parentCommBean.getCommpath();
			commPath = parentCommPath+"/"+commId;
		}
		bean.setCommpath(commPath);
		this.sysCommunityDAO.updateSysCommunityPath(bean);

		//记录需同步的数据
		if(isUpdate){
			this.saveSynData(IbmcConstant.COMMNUNITY,commId,IbmcConstant.UPDATE,commPath,"");
		}else{
			this.saveSynData(IbmcConstant.COMMNUNITY,commId,IbmcConstant.INSERT,commPath,"");
		}
		//调用房产数据下行接口（包括市、区、村、房产）
		//this.synWebServiceUtil.saveSysCommToWebServiceByUp(commId);
		return commId;
	}

	public void updateSysCommunityUseSign(SysCommunityBean bean) {
		this.sysCommunityDAO.updateSysCommunityUseSign(bean);
	}
	
	
	public void deleteSysCommunity(String commIds,String dellev) {
		PubFunc func = new PubFunc();
		if(!func.IsEmpty(commIds)){
			String[] commIdArr = commIds.split(",");
			for(String commId : commIdArr){
				//删除卡
				this.manCardDAO.updateManCardDelSignByCommId(commId);
				//删除刷卡记录
				//------------------------
				//删除房产同门口机的关联数据
				this.manDoorHouseDAO.updateManDoorHouseDelSignByCommid(commId);
				//删除（市/区/村/房产/房间）
				this.sysCommunityDAO.updateSysCommDelSignByCommId(commId);
				//记录需同步的数据[传递ID,查询对应的路径]
				this.saveSynData(IbmcConstant.COMMNUNITY,commId,IbmcConstant.DELETE,"",commId);
			}
		}
	}

	public List<SysCommunityBean> findCommRegionListByMap(Map map){
		map.put("parentid",IbmcConstant.COMM_SUPPER_PARENTID);
		return this.sysCommunityDAO.findCommRegionListByMap(map);
	}

	public SysCommunityBean findSysCommunityBeanById(String id) {
		return this.sysCommunityDAO.findSysCommunityBeanById(id);
	}
	
	public String findSysCommunityNextOrder(Map map) {
		return this.sysCommunityDAO.findSysCommunityNextOrder(map);
	}
	
	public List<SysCommunityBean> findManRoomListByHouseId(Map map, int tagpage, int record) {
		return this.sysCommunityDAO.findManRoomListByHouseId(map, tagpage, record);
	}
	
	public List<SysCommunityBean> findManRoomListByHouseId(Map map) {
		return this.sysCommunityDAO.findManRoomListByHouseId(map);
	}

	public List<SysCommunityBean> findHouseLinkDoorList(Map map, int page, int record) {
		return this.sysCommunityDAO.findHouseLinkDoorList(map, page, record);
	}
	
	//以下为 set get 方法
	public SysCommunityDAO getSysCommunityDAO() {
		return sysCommunityDAO;
	}

	public void setSysCommunityDAO(SysCommunityDAO sysCommunityDAO) {
		this.sysCommunityDAO = sysCommunityDAO;
	}
	/***
	public SynWebServiceUtil getSynWebServiceUtil() {
		return synWebServiceUtil;
	}

	public void setSynWebServiceUtil(SynWebServiceUtil synWebServiceUtil) {
		this.synWebServiceUtil = synWebServiceUtil;
	}
	 **/

	public ManCardDAO getManCardDAO() {
		return manCardDAO;
	}

	public void setManCardDAO(ManCardDAO manCardDAO) {
		this.manCardDAO = manCardDAO;
	}

	public ManDoorHouseDAO getManDoorHouseDAO() {
		return manDoorHouseDAO;
	}

	public void setManDoorHouseDAO(ManDoorHouseDAO manDoorHouseDAO) {
		this.manDoorHouseDAO = manDoorHouseDAO;
	}

	public ManPeoDAO getManPeoDAO() {
		return manPeoDAO;
	}

	public void setManPeoDAO(ManPeoDAO manPeoDAO) {
		this.manPeoDAO = manPeoDAO;
	}

	public int findSysCommNameIsRepeat(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
