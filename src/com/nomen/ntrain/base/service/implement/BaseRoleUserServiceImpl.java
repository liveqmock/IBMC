package com.nomen.ntrain.base.service.implement;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.nomen.ntrain.annotation.OptEnums;
import com.nomen.ntrain.annotation.OptResource;
import com.nomen.ntrain.base.bean.BaseRoleUserBean;
import com.nomen.ntrain.base.dao.BaseRoleUserDAO;
import com.nomen.ntrain.base.service.BaseRoleUserService;
import com.nomen.ntrain.util.PubFunc;

public class BaseRoleUserServiceImpl implements BaseRoleUserService {
	
	private BaseRoleUserDAO baseRoleUserDAO;
	@OptResource(optRemark="权限设置",optType=OptEnums.DELETE)
	public void deleteBaseRoleUserByUserid(String userid) {
		this.baseRoleUserDAO.deleteBaseRoleUserByUserid(userid);
	}

	public List<BaseRoleUserBean> findBaseRoleUserList(Map map, int page,int record) {
		return this.baseRoleUserDAO.findBaseRoleUserList(map, page, record);
	}
	
	public List findBaseRoleUserList2(Map map, int page, int record) {
		return this.baseRoleUserDAO.findBaseRoleUserList2(map, page, record);
	}

	public boolean isSuperManager(String userId) {
		return this.baseRoleUserDAO.isSuperManager(userId);
	}
	@OptResource(optRemark="权限设置",optType=OptEnums.SAVE)
	public void saveNetRoleUser(BaseRoleUserBean baseRoleUserBean,
			String mainUserId) {
		String useridStr = baseRoleUserBean.getUserid();
		String[] useridArr=StringUtils.split(useridStr,",");
		PubFunc func = new PubFunc();
		for(String userid:useridArr){
			String count ="";
			count = this.baseRoleUserDAO.findBaseRoleUserCount(userid);
			if(func.Cint(count)==0){
				baseRoleUserBean.setUserid(userid);
				baseRoleUserBean.setMainuser(mainUserId);
				this.baseRoleUserDAO.insertBaseRoleUser(baseRoleUserBean);
			}else{
				baseRoleUserBean.setUserid(userid);
				baseRoleUserBean.setMainuser(mainUserId);
				this.baseRoleUserDAO.updateBaseRoleUser(baseRoleUserBean);
			}
		}

	}
	@OptResource(optRemark="权限设置",optType=OptEnums.UPDATE)
	public void updateBaseRoleUser(BaseRoleUserBean baseRoleUserBean) {
		this.baseRoleUserDAO.updateBaseRoleUser(baseRoleUserBean);
	}
	
  //set和get

	public BaseRoleUserDAO getBaseRoleUserDAO() {
		return baseRoleUserDAO;
	}

	public void setBaseRoleUserDAO(BaseRoleUserDAO baseRoleUserDAO) {
		this.baseRoleUserDAO = baseRoleUserDAO;
	}


	
}
