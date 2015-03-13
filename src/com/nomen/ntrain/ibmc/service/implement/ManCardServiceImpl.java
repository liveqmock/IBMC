package com.nomen.ntrain.ibmc.service.implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManCardBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManCardDAO;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.ibmc.dao.ManPeoDAO;
import com.nomen.ntrain.ibmc.dao.SysCommunityDAO;
import com.nomen.ntrain.ibmc.service.ManCardService;
@SuppressWarnings("all")
public class ManCardServiceImpl extends BaseServiceImpl implements ManCardService {

	private ManCardDAO manCardDAO;
	private SysCommunityDAO sysCommunityDAO;
	private ManDoorHouseDAO manDoorHouseDAO;
	private ManPeoDAO manPeoDAO;
	//private SynWebServiceUtil synWebServiceUtil;

	public ManCardBean findManCardBeanById(String cardId){
		return manCardDAO.findManCardBeanById(cardId);
	}

	public List<ManCardBean> findManCardListByPage(Map map,int tagpage,int record){
		return manCardDAO.findManCardListByPage(map,tagpage,record);
	}

	public String saveManCardBean(ManCardBean bean){
		String pkId = bean.getId();
		String cardType = func.Trim(bean.getCardtype());
		String cardnoStr = func.Trim(bean.getCardno());    
		//正式卡中存在IC卡，身份证卡一起共存的情况
		if(cardnoStr.indexOf(",")>0){         //判断cardno[门卡序列号]是否是以,隔开
			String[] cardnoArr = cardnoStr.split(",");
			for(String cardno:cardnoArr){
				bean.setCardno(cardno);
				bean.setId("");  //需要将主键id清除,防止在此循环是使用此id进行更新数据记录
				pkId = saveCardBeanMess(bean);
			}
		}else{
			pkId = saveCardBeanMess(bean);
		}
		return pkId;
	}
	
	private String saveCardBeanMess(ManCardBean bean){
		String pkId = bean.getId();
		String cardType = func.Trim(bean.getCardtype());
		
		if(IbmcConstant.CARDTYPE_REGULAR.equals(cardType)){   
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			bean.setStartdate(format.format(new Date()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.YEAR,1);
			Date date=cal.getTime(); 
			//bean.setEnddate(format.format(date));   //失效时间从后台获取
			
			/**
			 * 当正式卡中房间是字符串时需要进行新增
			 */
			String roomid = bean.getRoomid();
			String roomname = bean.getRoomname()+"房间";     //构造房间名称
			String parentid = bean.getHouseid();      //房产主键id
			
			if(func.IsEmpty(roomid)&&!func.IsEmpty(roomname)){
				//新增房产中的房间操作
				SysCommunityBean sysCommunityBean = new SysCommunityBean();
				sysCommunityBean.setParentid(parentid);
				sysCommunityBean.setCommname(roomname);
				Map map = new HashMap();
				map.put("parentid", parentid);
				String commorder = this.sysCommunityDAO.findSysCommunityNextOrder(map);
				sysCommunityBean.setCommorder(commorder);
				sysCommunityBean.setCommlev(IbmcConstant.COMM_LEV_ROOM);
				sysCommunityBean.setUsesign("1");
				String commId = this.sysCommunityDAO.insertSysCommunityBean(sysCommunityBean);
				SysCommunityBean parentCommBean = this.sysCommunityDAO.findSysCommunityBeanById(parentid);  //获取房产中的path
				String parentCommPath = parentCommBean.getCommpath();
				String commPath = parentCommPath+"/"+commId;
				
				sysCommunityBean.setCommpath(commPath);
				sysCommunityBean.setId(commId);
				this.sysCommunityDAO.updateSysCommunityPath(sysCommunityBean);
				//记录需要同步的数据记录
				this.saveSynData(IbmcConstant.COMMNUNITY,commId,IbmcConstant.INSERT,commPath,"");
				
				bean.setRoomid(commId);    //记录房间的主键id
			}
			
			/**
			 * 修改正式门卡中人员库中的租客标识
			 */
			ManPeoBean manPeoBean = new ManPeoBean();
			manPeoBean.setId(bean.getRentid());
			manPeoBean.setRentsign("1");
			this.manPeoDAO.updateManPeoBean(manPeoBean);
			
		}else if(IbmcConstant.CARDTYPE_OWNER.equals(cardType)){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			bean.setStartdate(format.format(new Date()));
			try {
				Date date = format.parse("9999-12-30");
				//bean.setEnddate(format.format(date));     //失效时间从后台获取
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//以下为身份证号缺陷修订功能
		if(func.Trim(bean.getCardno()).length()==15){
			bean.setCardno(bean.getCardno()+"0");
		}
		
		//获取业主ID
	    SysCommunityBean ownBean = this.sysCommunityDAO.findSysCommunityBeanById(bean.getHouseid());
		if(null == pkId || "".equals(pkId)){
			bean.setOwnerid(ownBean.getOwnerid());
			pkId = manCardDAO.insertManCardBean(bean);
			//判断该门卡关联的房产houseid是否有关联门口机,如果没有关联门口机则无需新增这条记录,否则需要对这条记录进行下发
			List doorHouseList = this.manDoorHouseDAO.findDoorListByHouseId(bean.getHouseid());
			if(!func.isEmpty(doorHouseList)){
				this.saveSynData(IbmcConstant.ROOMCARD,pkId,IbmcConstant.INSERT,"",bean.getHouseid());
			}
		}else{
			bean.setOwnerid(ownBean.getOwnerid());
			manCardDAO.updateManCardBean(bean);
			//判断该门卡关联的房产houseid是否有关联门口机,如果没有关联门口机则无需新增这条记录,否则需要对这条记录进行下发
			List doorHouseList = this.manDoorHouseDAO.findDoorListByHouseId(bean.getHouseid());
			if(!func.isEmpty(doorHouseList)){
				this.saveSynData(IbmcConstant.ROOMCARD,pkId,IbmcConstant.UPDATE,"",bean.getHouseid());
			}
			//查询该记录对应的oldunlockid
			//oldUnLockId = this.manCardDAO.findManCardUnLockId(pkId);
		}
		
		//对租客
		if(IbmcConstant.CARDTYPE_REGULAR.equals(cardType)){
			ManPeoBean manPeoBean = new ManPeoBean();
			manPeoBean.setId(bean.getRentid());
			manPeoBean.setRentsign("1");
			this.manPeoDAO.updateManPeoBean(manPeoBean);
		}
		
		/**
		String oldUnLockId = "";
		//查询房间节点路径(若为业主卡/临时卡则 roomid存储的值为房产ID)
		SysCommunityBean commBean = null;
		if(bean.getCardtype().equals(IbmcConstant.CARDTYPE_REGULAR)){
			commBean = this.sysCommunityDAO.findSysCommunityBeanById(bean.getRoomid());
		}else{
			commBean = this.sysCommunityDAO.findSysCommunityBeanById(bean.getHouseid());
		}
		bean.setId(pkId);
		bean.setUnlockid(oldUnLockId);
		//调用webservice下行数据
		String newUnlockId = synWebServiceUtil.saveRoomCardToWebService(bean,commBean.getCommpath());
		//修改webservice返回的ID
		bean.setUnlockid(newUnlockId);
		this.manCardDAO.updateManCardUnLockId(bean);
		**/
		return pkId;
	}

	public void deleteManCardById(String id){
		String idArr[] = id.split(",");
		ManCardBean cardBean = null;
		if(idArr != null){
			for(String dId : idArr){
				//查询该卡信息
				cardBean = this.manCardDAO.findManCardBeanById(dId);
				this.saveSynData(IbmcConstant.ROOMCARD,dId,IbmcConstant.DELETE,"",cardBean.getHouseid());
				manCardDAO.updateManCardDelSignById(dId);
			}
		}
	}
	
	public ManCardBean findManCardByCardNo(String cardNo){
		return manCardDAO.findManCardByCardNo(cardNo);
	}
	
	public void updateManCardValidDate(String id,String operUserId){
		String idArr[] = id.split(",");
		if(idArr != null){
			ManCardBean bean = null;
			ManCardBean cardBean = null;
			for(String dId : idArr){
				//查询该卡信息
				cardBean = this.manCardDAO.findManCardBeanById(dId);
				
				bean = new	ManCardBean();
				bean.setId(dId);
				bean.setOperuserid(operUserId);
				manCardDAO.updateManCardValidDate(bean);
				this.saveSynData(IbmcConstant.ROOMCARD,dId,IbmcConstant.UPDATE,"",cardBean.getHouseid());
			}
		}
	}
	
	public List<ManCardBean> findManCardLinkNoticeMessList(Map map,int tagpage, int record) {
		return this.manCardDAO.findManCardLinkNoticeMessList(map, tagpage, record);
	}

	public List<ManCardBean> findManCardListInMessImpByCommId(Map map,int tagpage, int record) {
		return this.manCardDAO.findManCardListInMessImpByCommId(map, tagpage, record);
	}
	
	public void updateManCardBeanByCardidStr(String cardidstr,String houseidStr) {
		ManCardBean manCardBean = new ManCardBean();
		if(!func.IsEmpty(cardidstr)){
			manCardBean.setId(cardidstr);
			this.manCardDAO.updateManCardBeanByCardidStr(manCardBean);
			String[] cardidArr = cardidstr.split(",");
			String[] houseidArr = houseidStr.split(",");
			for(int i = 0;i<cardidArr.length;i++){
				this.saveSynData(IbmcConstant.ROOMCARD, cardidArr[i], IbmcConstant.UPDATE, "",houseidArr[i]);
			}
		}
	}
	
	//Get和Set方法
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
	/**
	public SynDataDAO getSynDataDAO() {
		return synDataDAO;
	}

	public void setSynDataDAO(SynDataDAO synDataDAO) {
		this.synDataDAO = synDataDAO;
	}
	**/
	/**
	public SynWebServiceUtil getSynWebServiceUtil() {
		return synWebServiceUtil;
	}

	public void setSynWebServiceUtil(SynWebServiceUtil synWebServiceUtil) {
		this.synWebServiceUtil = synWebServiceUtil;
	}
	**/

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
	
}
