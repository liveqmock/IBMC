package com.nomen.ntrain.ibmc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardBean;
import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;
import com.nomen.ntrain.ibmc.bean.SynDataBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManCardDAO;
import com.nomen.ntrain.ibmc.dao.ManDoorDAO;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.ibmc.dao.SynDataDAO;
import com.nomen.ntrain.ibmc.dao.SynWebServiceDAO;
import com.nomen.ntrain.ibmc.dao.SysCommunityDAO;
import com.nomen.ntrain.ibmc.webservice.WebCommunity;
import com.nomen.ntrain.ibmc.webservice.WebEquip;
import com.nomen.ntrain.ibmc.webservice.WebUnlockDevRela;
import com.nomen.ntrain.ibmc.webservice.WebUnlockPersonInfo;

public class SynWebServiceUtil {
	private SynWebServiceDAO synWebServiceDAO;
	private ManDoorDAO       manDoorDAO;
	private SynDataDAO       synDataDAO;
	private ManDoorHouseDAO  manDoorHouseDAO;
	private ManCardDAO       manCardDAO;
	private SysCommunityDAO  sysCommunityDAO;
	
	/**
	 * 对特定区域的数据[房产、房产关联、门卡等]进行同步
	 *   间隔1分钟执行一次
	 */
	public void synDataToWebService(String commId){
		//查询该区域是否存在需要同步的数据
		if(this.synDataDAO.findNeedSynDataCount(commId)==0){
			return ;
		}
		//查询该区域 同步任务的状态--任务调度状态（0未开始 1完成 -1正在进行）
		String taskSign = this.synDataDAO.findSysCommTaskSignById(commId);
		if("0".equals(taskSign) || "1".equals(taskSign)||"-1".equals(taskSign)){
			//更新任务状态为 -1 正在进行
			this.synDataDAO.updateSysCommTaskSignById("-1",commId);
			
			List<SynDataBean> dataList = this.synDataDAO.findNeedSynDataList(commId);
			for(SynDataBean sD : dataList){
				//判断数据所属模块
				if(IbmcConstant.COMMNUNITY.equals(sD.getModesign())){
					//分为新增/修改/删除
					SynMessage msg = null;
					if(IbmcConstant.INSERT.equals(sD.getOpttype()) || IbmcConstant.UPDATE.equals(sD.getOpttype())){
						msg = this.saveSysCommToWebServiceByUp(sD.getOptid());
						//更新
						sD.setDownsign(msg.isSuccess()?"1":"-1");
						this.synDataDAO.updateSynDataBean(sD);
					}else if(IbmcConstant.DELETE.equals(sD.getOpttype())){
						String retVal = this.deleteCommunityToWebService(sD.getOptid(),sD.getCommpath());
						sD.setDownsign(retVal);
						this.synDataDAO.updateSynDataBean(sD);
					}
				}else if(IbmcConstant.ROOMCARD.equals(sD.getModesign())){
					//分为新增/修改/删除
					if(IbmcConstant.INSERT.equals(sD.getOpttype()) || IbmcConstant.UPDATE.equals(sD.getOpttype())){
						ManCardBean cardBean = this.manCardDAO.findManCardBeanById(sD.getOptid());
						if((null!=cardBean)&&(!("").equals(cardBean.getId()))){
							//调用webservice中的方法
							//查询该记录对应的oldunlockid
							String oldUnLockId = cardBean.getUnlockid();
							//查询房间节点路径(若为业主卡/临时卡则 roomid存储的值为房产ID)
							SysCommunityBean commBean = null;
							if(cardBean.getCardtype().equals(IbmcConstant.CARDTYPE_REGULAR)){
								commBean = this.sysCommunityDAO.findSysCommunityBeanById(cardBean.getRoomid());
							}else{
								commBean = this.sysCommunityDAO.findSysCommunityBeanById(cardBean.getHouseid());
							}
							cardBean.setId(sD.getOptid());
							cardBean.setUnlockid(oldUnLockId);
							//调用webservice下行数据
							String newUnlockId = this.saveRoomCardToWebService(cardBean,commBean.getCommpath());
							
							//修改webservice返回的ID
							cardBean.setUnlockid(newUnlockId);
							this.manCardDAO.updateManCardUnLockId(cardBean);
							//更新
							sD.setDownsign("-1".equals(newUnlockId) ? "-1":"1");
						}else{    
							//更新
							sD.setDownsign("1");
						}
						this.synDataDAO.updateSynDataBean(sD);
					}else if(IbmcConstant.DELETE.equals(sD.getOpttype())){
						//查询webservice对应的id
						String oldUnLockId = this.manCardDAO.findManCardUnLockId(sD.getOptid());
						String downSign = "1";
						if(null != oldUnLockId && !"".equals(oldUnLockId)){
							int unLockId = Integer.parseInt(oldUnLockId);
							downSign = this.deleteRoomCardToWebService(unLockId);
						}
						
						//更新状态
						sD.setDownsign(downSign);
						this.synDataDAO.updateSynDataBean(sD);
						
						if("1".equals(downSign)){
							//删除oracle库中的记录
							this.manCardDAO.deleteManCardByCardId(sD.getOptid());
						}
					}
					
					
				}else if(IbmcConstant.DOORHOUSE.equals(sD.getModesign())){
					//分为新增/修改/删除
					if(IbmcConstant.INSERT.equals(sD.getOpttype())){
						ManDoorHouseBean manDoorHouseBean = this.manDoorHouseDAO.findDoorHouseBeanById(sD.getOptid());
						
						//查询出房屋对应的路径
						SysCommunityBean commBean = this.sysCommunityDAO.findSysCommunityBeanById(manDoorHouseBean.getHouseid());
						String retval = this.saveSysDoorToWebServiceByUp(manDoorHouseBean.getDoorid(),commBean.getId(),commBean.getCommpath());
						
						//更新
						sD.setDownsign("-1".equals(retval)?"-1":"1");
						this.synDataDAO.updateSynDataBean(sD);
					}else if(IbmcConstant.DELETE.equals(sD.getOpttype())){
						//删除该门口机同房产下门卡的配置信息以及门口机同房产关联表
						String retval = this.deleteDoorHouseToWebService(sD.getOptid());
						//更新
						sD.setDownsign("-1".equals(retval)?"-1":"1");
						this.synDataDAO.updateSynDataBean(sD);
						
						//删除门口机同房产关联数据
						this.manDoorHouseDAO.deleteDoorHouseById(sD.getOptid());
					}
				}
			}
			//更新任务状态为 1完成  
			this.synDataDAO.updateSysCommTaskSignById("1",commId);
		}else if("-1".equals(taskSign)){
			//等待下次任务
			return ;
		}
	}
	
	/**
	 * 对单条的需要上行的数据进行上行
	 * 立即执行
	 * @return downsign 下发标识（1：下发成功,0：下发失败）
	 */
	public String synDataToWebServiceById(String synDataId){
		SynDataBean sD = this.synDataDAO.findSynDataBeanById(synDataId);
		//判断数据所属模块
		if(IbmcConstant.COMMNUNITY.equals(sD.getModesign())){
			//分为新增/修改/删除
			SynMessage msg = null;
			if(IbmcConstant.INSERT.equals(sD.getOpttype()) || IbmcConstant.UPDATE.equals(sD.getOpttype())){
				msg = this.saveSysCommToWebServiceByUp(sD.getOptid());
				//更新
				sD.setDownsign(msg.isSuccess()?"1":"-1");
				this.synDataDAO.updateSynDataBean(sD);
			}else if(IbmcConstant.DELETE.equals(sD.getOpttype())){
				String retVal = this.deleteCommunityToWebService(sD.getOptid(),sD.getCommpath());
				sD.setDownsign(retVal);
				this.synDataDAO.updateSynDataBean(sD);
			}
			
		}else if(IbmcConstant.ROOMCARD.equals(sD.getModesign())){
			//分为新增/修改/删除
			if(IbmcConstant.INSERT.equals(sD.getOpttype()) || IbmcConstant.UPDATE.equals(sD.getOpttype())){
				ManCardBean cardBean = this.manCardDAO.findManCardBeanById(sD.getOptid());
				//查询该记录对应的oldunlockid
				String oldUnLockId = cardBean.getUnlockid();
				//查询房间节点路径(若为业主卡/临时卡则 roomid存储的值为房产ID)
				SysCommunityBean commBean = null;
				if(cardBean.getCardtype().equals(IbmcConstant.CARDTYPE_REGULAR)){
					commBean = this.sysCommunityDAO.findSysCommunityBeanById(cardBean.getRoomid());
				}else{
					commBean = this.sysCommunityDAO.findSysCommunityBeanById(cardBean.getHouseid());
				}
				cardBean.setId(sD.getOptid());
				cardBean.setUnlockid(oldUnLockId);
				//调用webservice下行数据
				String newUnlockId = this.saveRoomCardToWebService(cardBean,commBean.getCommpath());
				
				//修改webservice返回的ID
				cardBean.setUnlockid(newUnlockId);
				this.manCardDAO.updateManCardUnLockId(cardBean);
				
				//更新
				sD.setDownsign("-1".equals(newUnlockId) ? "-1":"1");
				this.synDataDAO.updateSynDataBean(sD);
			}else if(IbmcConstant.DELETE.equals(sD.getOpttype())){
				//查询webservice对应的id
				String oldUnLockId = this.manCardDAO.findManCardUnLockId(sD.getOptid());
				String downSign = "1";
				if(null != oldUnLockId && !"".equals(oldUnLockId)){
					int unLockId = Integer.parseInt(oldUnLockId);
					downSign = this.deleteRoomCardToWebService(unLockId);
				}
				
				//更新状态
				sD.setDownsign(downSign);
				this.synDataDAO.updateSynDataBean(sD);
				
				if("1".equals(downSign)){
					//删除oracle库中的记录
					this.manCardDAO.deleteManCardByCardId(sD.getOptid());
				}
			}
			
		}else if(IbmcConstant.DOORHOUSE.equals(sD.getModesign())){
			//分为新增/修改/删除
			if(IbmcConstant.INSERT.equals(sD.getOpttype())){
				ManDoorHouseBean manDoorHouseBean = this.manDoorHouseDAO.findDoorHouseBeanById(sD.getOptid());
				
				//查询出房屋对应的路径
				SysCommunityBean commBean = this.sysCommunityDAO.findSysCommunityBeanById(manDoorHouseBean.getHouseid());
				String retval = this.saveSysDoorToWebServiceByUp(manDoorHouseBean.getDoorid(),commBean.getId(),commBean.getCommpath());
				
				//更新
				sD.setDownsign("-1".equals(retval)?"-1":"1");
				this.synDataDAO.updateSynDataBean(sD);
			}else if(IbmcConstant.DELETE.equals(sD.getOpttype())){
				//删除该门口机同房产下门卡的配置信息以及门口机同房产关联表
				String retval = this.deleteDoorHouseToWebService(sD.getOptid());
				//更新
				sD.setDownsign("-1".equals(retval)?"-1":"1");
				this.synDataDAO.updateSynDataBean(sD);
				
				//删除门口机同房产关联数据
				this.manDoorHouseDAO.deleteDoorHouseById(sD.getOptid());
			}
		}
		return sD.getDownsign();
	}
	
	
	
	/**
	 * 向上同步数据（通过房间、房屋、村、区、市 --->）
	 */
	public SynMessage saveSysCommToWebServiceByUp(String commId){
		SynMessage msg = new SynMessage();
		//向上倒查村、区、市 向下查询 设备、业主+租客 以及门卡
		List<SysCommunityBean> dataList = synWebServiceDAO.findSysCommParentListByCommId(commId);
		String downId = "";
		WebCommunity service = new WebCommunity();
		boolean isSuccess = true;
		String errorId = "";
		for(SysCommunityBean commBean : dataList){
			if(IbmcConstant.COMM_LEV_PROVINCE.equals(commBean.getCommlev())){
				//省				
			}else if(IbmcConstant.COMM_LEV_CITY.equals(commBean.getCommlev())){
				//市（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_COUNTY.equals(commBean.getCommlev())){
				//区（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_VILLAGE.equals(commBean.getCommlev())){
				//村（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_HOUSE.equals(commBean.getCommlev())){
				//房产（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_ROOM.equals(commBean.getCommlev())){
				//房间（同步数据）
				downId = service.saveBulid(commBean);
			}

			if("-1".equals(downId)){
				isSuccess = false;
				errorId += commBean.getId()+",";
				commBean.setDownsign("-1");//失败
				this.synWebServiceDAO.updateSysCommDownSign(commBean);
			}else{
				commBean.setDownsign("1");//成功
				this.synWebServiceDAO.updateSysCommDownSign(commBean);
			}
		}
		msg.setSuccess(isSuccess);
		msg.setMessage(errorId);
		return msg;
	}
	
	/**
	 * 向上同步数据（通过房间、房屋、村、区、市 --->）
	 */
	public void saveSysCommToWebServiceByDown(String commId){
		//向上倒查村、区、市 向下查询 设备、业主+租客 以及门卡
		List<SysCommunityBean> dataList = synWebServiceDAO.findSysCommChildsListByCommId(commId);
		String downId = "";
		WebCommunity service = new WebCommunity();
		for(SysCommunityBean commBean : dataList){
			if(IbmcConstant.COMM_LEV_PROVINCE.equals(commBean.getCommlev())){
				//省				
			}else if(IbmcConstant.COMM_LEV_CITY.equals(commBean.getCommlev())){
				//市（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_COUNTY.equals(commBean.getCommlev())){
				//区（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_VILLAGE.equals(commBean.getCommlev())){
				//村（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_HOUSE.equals(commBean.getCommlev())){
				//房产（同步数据）
				downId = service.saveBulid(commBean);
			}else if(IbmcConstant.COMM_LEV_ROOM.equals(commBean.getCommlev())){
				//房间（同步数据）
				downId = service.saveBulid(commBean);
			}
			
			if("-1".equals(downId)){
				commBean.setDownsign("-1");//失败
				this.synWebServiceDAO.updateSysCommDownSign(commBean);
			}else{
				commBean.setDownsign("1");//成功
				this.synWebServiceDAO.updateSysCommDownSign(commBean);
			}
		}
	}
	

	/**
	 * 同步门口机同房屋管理数据
	 *  1、同步门口机同房屋配置表
	 *  2、新建/修改门口机同房屋下门卡的关联
	 */
	public String saveSysDoorToWebServiceByUp(String doorId,String houseId,String commPath){
		WebEquip service = new WebEquip();
		ManDoorBean doorBean = null;
		Map dM = new HashMap();
		dM.put("id",doorId);
		doorBean = this.manDoorDAO.findManDoorBean(dM);
		String webServiceEquipId = service.saveEquip(doorBean,commPath);
		this.manDoorHouseDAO.updateDoorHouseEquipId(houseId,doorId,webServiceEquipId);
		
		//查询出房产下可用的门卡列表并同门口机建立管理
		List<ManCardBean> dataList = this.manCardDAO.findManCardListByHouseId(houseId);
		
		if(null != dataList){
			//查询房间节点路径(若为业主卡/临时卡则 roomid存储的值为房产ID)
			SysCommunityBean commBean = null;
			for(ManCardBean cardBean : dataList){
				if(!"1".equals(cardBean.getDeletesign())){
					if(cardBean.getCardtype().equals(IbmcConstant.CARDTYPE_REGULAR)){
						commBean = this.sysCommunityDAO.findSysCommunityBeanById(cardBean.getRoomid());
					}else{
						commBean = this.sysCommunityDAO.findSysCommunityBeanById(cardBean.getHouseid());
					}
					this.saveRoomCardToWebService(cardBean,commBean.getCommpath(),webServiceEquipId);
				}
			}
		}
		return webServiceEquipId;
	}
	

	/**
	 * 同步门卡列表[按卡同步至所有门口机]
	 */
	public String saveRoomCardToWebService(ManCardBean cardBean,String roomIdPath){
		WebUnlockPersonInfo service = new WebUnlockPersonInfo();
		WebUnlockDevRela service2 = new WebUnlockDevRela();
		//更新门卡记录
		String unLockId = service.saveUnlockPersonInfo(cardBean,roomIdPath);
		//通过房产查询门口机列表
		String houseId = cardBean.getHouseid();
		//更新门卡同设备列表
		List<ManDoorHouseBean> doorList= this.manDoorHouseDAO.findDoorListByHouseId(houseId);
		if(null != doorList){
			for(ManDoorHouseBean dhB : doorList){
				//获取设备同房产关联后 webservice返回的ID
				service2.saveUnlockDevRelaInfo(cardBean.getCardno(),Integer.parseInt(unLockId),dhB.getEquipid());
			}
		}
		return unLockId;
	}
	
	/**
	 * 同步门卡列表[按卡同步至特定门口机]
	 */
	public String saveRoomCardToWebService(ManCardBean cardBean,String roomIdPath,String equipId){
		WebUnlockPersonInfo service = new WebUnlockPersonInfo();
		WebUnlockDevRela service2 = new WebUnlockDevRela();
		//更新门卡记录
		String unLockId = service.saveUnlockPersonInfo(cardBean,roomIdPath);
		//通过房产查询门口机列表
		String houseId = cardBean.getHouseid();
		//获取设备同房产关联后 webservice返回的ID
		service2.saveUnlockDevRelaInfo(cardBean.getCardno(),Integer.parseInt(unLockId),equipId);
		return unLockId;
	}
	
	/**
	 * 删除门卡列表
	 *   1、删除门卡同门口机关联配置表
	 *   2、删除门卡数据
	 */
	public String deleteRoomCardToWebService(int unLockId){
		WebUnlockPersonInfo service = new WebUnlockPersonInfo();
		WebUnlockDevRela service2 = new WebUnlockDevRela();
		//删除卡同门口机关联数据
		String rtn = service2.deleteUnlockDevRelaInfoByUnlockId(unLockId);
		//删除门卡
		String rtn2 = service.deleteUnlockPersonInfoByUnlockId(unLockId);
		if("1".equals(rtn) && "1".equals(rtn2)){
			return "1";
		}
		return "-1";
	}

	/**
	 * 解除房产下特定的门口机数据
	 */
	public String deleteDoorHouseToWebService(String synDataOptId){
		String rtnVal = "1";
		//查询出门口机同房产的关联记录
		ManDoorHouseBean doorHouseBean = this.manDoorHouseDAO.findDoorHouseBeanById(synDataOptId);
		if(null == doorHouseBean) return rtnVal;
		String houseId = doorHouseBean.getHouseid();
		String equipId = doorHouseBean.getEquipid();
		if(!"".equals(equipId)){
			//查询出该房产下所有的门卡
			List<ManCardBean> dataList = this.manCardDAO.findManCardListByHouseId(houseId);
			for(ManCardBean cardBean : dataList){
				if(null != cardBean.getUnlockid() && !"".equals(cardBean.getUnlockid())){
					//删除卡同门口机关联数据
					WebUnlockDevRela service = new WebUnlockDevRela();
					//删除该门卡具体要清楚的门口id 1、清楚门卡+门口机配置
					service.deleteUnlockDevRelaInfoByUnlockId(Integer.parseInt(cardBean.getUnlockid()),new String[]{equipId});
				}
			}
			//删除门口机同房产关联数据[webservice]
			WebEquip service2 = new WebEquip();
			service2.deleteEquip(equipId);
		}
		
		return rtnVal;
	}
	
	public String deleteCommunityToWebService(String commId,String commPath){
		//1、通过commId查询其对应的门卡列表
		List<ManCardBean> cardList = this.manCardDAO.findManCardListByCommId(commId);
		if(null != cardList){
			for(ManCardBean cardBean : cardList ){
				if(!"".equals(cardBean.getUnlockid()) && !"-1".equals(cardBean.getUnlockid())){
					this.deleteRoomCardToWebService(Integer.parseInt(cardBean.getUnlockid()));
				}
			}
		}
		
		//2、通过commId查询出门口机同房产关联表[要注意,若删除的是房间 ，则跳过该流程]
		SysCommunityBean commBean = this.sysCommunityDAO.findSysCommunityBeanById(commId);
		if(!IbmcConstant.COMM_LEV_ROOM.equals(commBean.getCommlev())){
			List<ManDoorHouseBean> doorHouseList = this.manDoorHouseDAO.findDoorHouseListByCommId(commId);
			if(null != doorHouseList){
				WebEquip service2 = new WebEquip();
				for(ManDoorHouseBean dbBean : doorHouseList ){
					if(!"".equals(dbBean.getEquipid()) && !"-1".equals(dbBean.getEquipid())){
						//删除门口机同房产关联数据[webservice]
						service2.deleteEquip(dbBean.getEquipid());
					}
				}
				//删除门口机同房产关联 - oracle真实数据
				this.manDoorHouseDAO.deleteDoorHouseByCommId(commId);
			}
		}
		
		//删除[省/市/区/村/房产/房间ID]门卡数据
		List<SysCommunityBean> commList = this.sysCommunityDAO.findSysCommListByCommLevDesc(commId);
		if(null != commList){
			WebCommunity webComm = new WebCommunity();
			for(SysCommunityBean cM : commList ){
				webComm.deleteBuild(cM.getCommpath());
			}
		}		
		//删除oracle数据
		this.sysCommunityDAO.deleteSysCommByCommId(commId);
		this.manCardDAO.deleteManCardByCommId(commId);
		return null;
	}
	
	
	//以下为set get方法
	
	
	public SynDataDAO getSynDataDAO() {
		return synDataDAO;
	}

	public void setSynDataDAO(SynDataDAO synDataDAO) {
		this.synDataDAO = synDataDAO;
	}

	public ManCardDAO getManCardDAO() {
		return manCardDAO;
	}

	public void setManCardDAO(ManCardDAO manCardDAO) {
		this.manCardDAO = manCardDAO;
	}

	public SysCommunityDAO getSysCommunityDAO() {
		return sysCommunityDAO;
	}

	public void setSysCommunityDAO(SysCommunityDAO sysCommunityDAO) {
		this.sysCommunityDAO = sysCommunityDAO;
	}

	public SynWebServiceDAO getSynWebServiceDAO() {
		return synWebServiceDAO;
	}

	public void setSynWebServiceDAO(SynWebServiceDAO synWebServiceDAO) {
		this.synWebServiceDAO = synWebServiceDAO;
	}

	public ManDoorDAO getManDoorDAO() {
		return manDoorDAO;
	}

	public void setManDoorDAO(ManDoorDAO manDoorDAO) {
		this.manDoorDAO = manDoorDAO;
	}

	public ManDoorHouseDAO getManDoorHouseDAO() {
		return manDoorHouseDAO;
	}

	public void setManDoorHouseDAO(ManDoorHouseDAO manDoorHouseDAO) {
		this.manDoorHouseDAO = manDoorHouseDAO;
	}
	
}
