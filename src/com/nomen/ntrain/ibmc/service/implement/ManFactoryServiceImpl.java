package com.nomen.ntrain.ibmc.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManFacConfigBean;
import com.nomen.ntrain.ibmc.bean.ManFactoryBean;
import com.nomen.ntrain.ibmc.dao.ManFacConfigDAO;
import com.nomen.ntrain.ibmc.dao.ManFactoryDAO;
import com.nomen.ntrain.ibmc.service.ManFactoryService;

public class ManFactoryServiceImpl extends BaseServiceImpl implements ManFactoryService {
    private  ManFactoryDAO manFactoryDAO;
    private  ManFacConfigDAO manFacConfigDAO;
    
	public void deleteManFactoryBean(String id) {
		this.manFactoryDAO.deleteManFactoryBean(id);
		this.manFacConfigDAO.deleteManFacConfigByFacid(id);
	}

	public List<ManFactoryBean> findManFactoryList(Map map, int page, int record) {
		return this.manFactoryDAO.findManFactoryList(map, page, record);
	}

	public List<ManFactoryBean> findManFactoryList(Map map) {
		return this.manFactoryDAO.findManFactoryList(map);
	}
	
	public ManFactoryBean findManFactoryBean(ManFactoryBean manFactoryBean) {
		return this.manFactoryDAO.findManFactoryBean(manFactoryBean);
	}

	public void saveManFactoryBean(Map map,ManFactoryBean manFactoryBean) {
		String[] facModelArr = (String[]) map.get("facModelArr");
		String[] modelIdArr = (String[]) map.get("modelIdArr");
		String id = manFactoryBean.getId();
		if(func.IsEmpty(id)){
			//获取主记录ID
			id = getManFacConfigOfId(manFactoryBean);
			if(facModelArr!=null){
				for(String facmodel:facModelArr){
					ManFacConfigBean manFacConfigBean = new ManFacConfigBean();
					manFacConfigBean.setFacid(id);
					manFacConfigBean.setFacmodel(facmodel);
					this.manFacConfigDAO.insertManFacConfigBean(manFacConfigBean);
				}
			}
		}else{
			this.manFactoryDAO.updateManFactoryBean(manFactoryBean);
			//清除配置表信息
			this.manFacConfigDAO.deleteManFacConfigByFacid(id);
			//重构配置表信息
			if(facModelArr!=null){
				for(int i = 0; i<facModelArr.length;i++){
						if(func.IsEmpty(modelIdArr[i])){
							ManFacConfigBean manFacConfigBean = new ManFacConfigBean();
							manFacConfigBean.setFacid(id);
							manFacConfigBean.setFacmodel(facModelArr[i]);
							this.manFacConfigDAO.insertManFacConfigBean(manFacConfigBean);
						}else{
							ManFacConfigBean manFacConfigBean = new ManFacConfigBean();
							manFacConfigBean.setId(modelIdArr[i]);
							manFacConfigBean.setFacid(id);
							manFacConfigBean.setFacmodel(facModelArr[i]);
							this.manFacConfigDAO.insertManFacConfigBean2(manFacConfigBean);
						}
				}
			}
		}
	}
	
   public String getManFacConfigOfId(ManFactoryBean manFactoryBean){
		String id = this.manFactoryDAO.insertManFactoryBean(manFactoryBean);
	   return id;
   }
   
	public void deleteManFacConfigBean(String id) {
		this.manFacConfigDAO.deleteManFacConfigBean(id);
		
	}

	public List findManFacConfigList(String id) {
		return this.manFacConfigDAO.findManFacConfigList(id);
	}
   
	//set和get方法
	public ManFactoryDAO getManFactoryDAO() {
		return manFactoryDAO;
	}

	public void setManFactoryDAO(ManFactoryDAO manFactoryDAO) {
		this.manFactoryDAO = manFactoryDAO;
	}

	public ManFacConfigDAO getManFacConfigDAO() {
		return manFacConfigDAO;
	}

	public void setManFacConfigDAO(ManFacConfigDAO manFacConfigDAO) {
		this.manFacConfigDAO = manFacConfigDAO;
	}
	
	

}
