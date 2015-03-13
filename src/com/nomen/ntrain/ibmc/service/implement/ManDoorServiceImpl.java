package com.nomen.ntrain.ibmc.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManDoorHouseBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManDoorDAO;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.ibmc.excel.IbmcExcelOutForJxlImpl;
import com.nomen.ntrain.ibmc.service.ManDoorService;

public class ManDoorServiceImpl extends BaseServiceImpl implements ManDoorService {
	private ManDoorDAO manDoorDAO;
	private ManDoorHouseDAO manDoorHouseDAO;
	
	public List<ManDoorBean> findManDoorList(Map map, int page, int record) {
		return this.manDoorDAO.findManDoorList(map, page, record);
	}
	
	public List<ManDoorBean> findManDoorList(Map map) {
		return this.manDoorDAO.findManDoorList(map);
	}
	
	public ManDoorBean findManDoorBean(Map map) {
		return this.manDoorDAO.findManDoorBean(map);
	}
	
	public ManDoorBean findManDoorBeanById(String id) {
		Map map = new HashMap();
		map.put("id", id);
		return this.manDoorDAO.findManDoorBean(map);
	}
	
	public String saveManDoorBean(ManDoorBean manDoorBean) {
		String id = manDoorBean.getId();
		if(func.IsEmpty(id)){
			id = this.manDoorDAO.insertManDoorBean(manDoorBean);
		}else{
			this.manDoorDAO.updateManDoorBean(manDoorBean);
		}
		return id;
	}
	
	public String insertManDoorBean(ManDoorBean manDoorBean) {
		return this.manDoorDAO.insertManDoorBean(manDoorBean);
	}

	public void updateManDoorBean(ManDoorBean manDoorBean) {
		this.manDoorDAO.updateManDoorBean(manDoorBean);
	}
	
	public void deleteManDoor(String idStr) {
		//先删除关联的门口机配置表
		String[] dooridArr = idStr.split(",");
		for(int i = 0;i<dooridArr.length;i++){
			String doorid = func.Trim(dooridArr[i]);
			//根据门口机查询需要解除房产和门口机关联的记录
			List<ManDoorHouseBean> list = this.manDoorHouseDAO.findDoorHouseBeanByDoorId(doorid);
			if(!func.isEmpty(list)){
				for(ManDoorHouseBean manDoorHouseBean:list){
					/**
					 * 注意这里的update方法有两种
					 * 1：直接调用updateManDoorHouseDelSignByDoorid(doorid);
					 * 2：是直接调用updateManDoorHouseDelSign(houseId,doorId);
					 * 目前我是调用第一中方法直接在下面进行批量更新
					 */
					//记录需同步的数据[传递房产ID,查询房产对应的路径]
					this.saveSynData(IbmcConstant.DOORHOUSE,manDoorHouseBean.getId(),IbmcConstant.DELETE,"",manDoorHouseBean.getHouseid());
				}
			}
			if(!func.IsEmpty(doorid)){   //门口机ID不为空时
				this.manDoorHouseDAO.updateManDoorHouseDelSignByDoorid(doorid);
			}
		}
		//删除门口机列表
		this.manDoorDAO.deleteManDoor(idStr);
	}
	
	public void saveManDoorExpExcel(Map map, HttpServletResponse response) {
		//数据列表
		List<ManDoorBean> dataList = this.manDoorDAO.findManDoorList(map);
		IbmcExcelOutForJxlImpl ibmcExp = new IbmcExcelOutForJxlImpl();
		ibmcExp.expManDoor(dataList,response);
	}
	
	
	//生成get,set方法
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
