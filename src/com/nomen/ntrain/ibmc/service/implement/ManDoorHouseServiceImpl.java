package com.nomen.ntrain.ibmc.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.ibmc.dao.SynDataDAO;
import com.nomen.ntrain.ibmc.dao.SysCommunityDAO;
import com.nomen.ntrain.ibmc.service.ManDoorHouseService;
import com.nomen.ntrain.ibmc.util.SynWebServiceUtil;

public class ManDoorHouseServiceImpl extends BaseServiceImpl implements ManDoorHouseService {
	
	private ManDoorHouseDAO manDoorHouseDAO;
	//private SynWebServiceUtil synWebServiceUtil;
	private SysCommunityDAO  sysCommunityDAO;
	
	public List<ManDoorBean> findManHouseLinkDoorList(Map map) {
		return this.manDoorHouseDAO.findManHouseLinkDoorList(map);
	}

	public List<ManDoorHouseBean> findDoorListByHouseId(String houseId) {
		return this.manDoorHouseDAO.findDoorListByHouseId(houseId);
	}
	
	public void insertManDoorHouseBean(ManDoorHouseBean dhBean) {
		String houseId = dhBean.getHouseid();
		String dooridstr = dhBean.getDoorid();
		String[] dooridArr = dooridstr.split(",");
		if(null != dooridArr && dooridArr.length>0){
			//判断该房产是否已经有关联门口机
			List<ManDoorHouseBean> list = this.manDoorHouseDAO.findDoorListByHouseId(houseId);
			if(null != list && list.size()>0){
				return ;
			}
			ManDoorHouseBean tempBean = null;
			for(String doorid:dooridArr){
				dhBean.setDoorid(doorid);
				//判断是否已经存在关联数据
				tempBean = this.manDoorHouseDAO.findDoorHouseBean(dhBean);
				if(tempBean==null || tempBean.getId().equals("")){
					//表示不存在
					String tId = this.manDoorHouseDAO.insertManDoorHouseBean(dhBean);

					//记录需同步的数据[传递房产ID,查询房产对应的路径]
					this.saveSynData(IbmcConstant.DOORHOUSE,tId,IbmcConstant.INSERT,"",houseId);
				}
			}
			//查询出房屋对应的路径
			//SysCommunityBean commBean = this.sysCommunityDAO.findSysCommunityBeanById(manDoorHouseBean.getHouseid());
			//synWebServiceUtil.saveSysDoorToWebServiceByUp(dooridArr,commBean.getId(),commBean.getCommpath());
		}
	}

	public void deleteManDoorHouseBean(ManDoorHouseBean manDoorHouseBean) {
		String doorIdStr = manDoorHouseBean.getDoorid();
		String houseId = manDoorHouseBean.getHouseid();
		if(!func.IsEmpty(houseId)&&!func.IsEmpty(doorIdStr)){   
			//房产Id和门口机idstr不能为空
			String[] doorIdArr = doorIdStr.split(",");
			if(doorIdArr!=null){
				ManDoorHouseBean tempBean = null;
				for(String doorId:doorIdArr){
					if(!func.IsEmpty(doorId)){
						//查询出其对应的关联主键ID（该主键记录在数据同步表中）
						tempBean = this.manDoorHouseDAO.findDoorHouseBean(houseId,doorId);
						this.manDoorHouseDAO.updateManDoorHouseDelSign(houseId,doorId);

						//记录需同步的数据[传递房产ID,查询房产对应的路径]
						this.saveSynData(IbmcConstant.DOORHOUSE,tempBean.getId(),IbmcConstant.DELETE,"",houseId);
					}
				}
			}
		}
	}
	
	public String findDoorLinkHouseCount(String doorId, String deletesign) {
		return this.manDoorHouseDAO.findDoorLinkHouseCount(doorId, deletesign);
	}
	
	public String findHouseLinkDoorAndCardCount(String houseId) {
		return this.manDoorHouseDAO.findHouseLinkDoorAndCardCount(houseId);
	}
	
	//生成get,set方法
	public ManDoorHouseDAO getManDoorHouseDAO() {
		return manDoorHouseDAO;
	}

	public void setManDoorHouseDAO(ManDoorHouseDAO manDoorHouseDAO) {
		this.manDoorHouseDAO = manDoorHouseDAO;
	}
	
	/**
	public SynWebServiceUtil getSynWebServiceUtil() {
		return synWebServiceUtil;
	}

	public void setSynWebServiceUtil(SynWebServiceUtil synWebServiceUtil) {
		this.synWebServiceUtil = synWebServiceUtil;
	}
	**/
	public SysCommunityDAO getSysCommunityDAO() {
		return sysCommunityDAO;
	}

	public void setSysCommunityDAO(SysCommunityDAO sysCommunityDAO) {
		this.sysCommunityDAO = sysCommunityDAO;
	}
	
}
