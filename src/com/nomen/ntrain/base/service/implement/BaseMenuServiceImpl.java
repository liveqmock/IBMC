package com.nomen.ntrain.base.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.annotation.OptEnums;
import com.nomen.ntrain.annotation.OptResource;
import com.nomen.ntrain.base.bean.BaseMenuBean;
import com.nomen.ntrain.base.bean.ZtreeNodeBean;
import com.nomen.ntrain.base.dao.BaseMenuDAO;
import com.nomen.ntrain.base.service.BaseMenuService;
import com.nomen.ntrain.util.Constant;
public class BaseMenuServiceImpl implements BaseMenuService {
	private BaseMenuDAO       baseMenuDAO;
	public List<BaseMenuBean> findBaseMenuTreeList(Map map) {
		return this.baseMenuDAO.findBaseMenuTreeList(map);
	}
	public List  findUserBigMenuList(String userId) {
		return this.baseMenuDAO.findBaseMenuByUser(userId,Constant.MENU_FATHERID_LEV1,"1");
	}
	
	public List  findUserChildMenuTreeList(String userId,String fatherid) {
		List<BaseMenuBean>  menuList = this.baseMenuDAO.findBaseMenuByUser(userId,fatherid,"");
		List<ZtreeNodeBean> treeList = new ArrayList();
		ZtreeNodeBean zNodeBean = null;
		for(BaseMenuBean m : menuList){
			zNodeBean = new ZtreeNodeBean();
			zNodeBean.setId(m.getId());
			zNodeBean.setPid(m.getFatherid());
			zNodeBean.setName(m.getName());
			zNodeBean.setRel("rel_"+m.getId());
			zNodeBean.setSrc(m.getUrl());  
			treeList.add(zNodeBean);
		}
		return treeList;
	}
	@OptResource(optRemark="菜单设置",optType=OptEnums.DELETE)
	public void deleteBaseMenu(String id) {
		this.baseMenuDAO.deleteBaseMenu(id);
	}
	public String findBaseMenuNextSort(String fatherid) {
		return this.baseMenuDAO.findBaseMenuNextSort(fatherid);
	}
	@OptResource(optRemark="菜单设置",optType=OptEnums.SAVE)
	public void saveBaseMenu(BaseMenuBean baseMenuBean) {
		if(null == baseMenuBean.getId() || "".equals(baseMenuBean.getId())){
			this.baseMenuDAO.insertBaseMenu(baseMenuBean);
		}
		else{
			this.baseMenuDAO.updateBaseMenu(baseMenuBean);
		}
	}
	@OptResource(optRemark="菜单设置-启用|禁用",optType=OptEnums.UPDATE)
	public void updateBaseMenuUsesign(BaseMenuBean baseMenuBean) {
		this.baseMenuDAO.updateBaseMenuUsesign(baseMenuBean);
	}
	
	public BaseMenuBean findBaseMenuBeanById(String id) {
		return this.baseMenuDAO.findBaseMenuBeanById(id);
	}
	public List<BaseMenuBean> findBaseMenuByUser(String lev,String fatherid,String userid) {
		return this.baseMenuDAO.findBaseMenuByUser(lev,fatherid,userid);
	}
	public List<BaseMenuBean> findBaseMenuListByThread() {
		return this.baseMenuDAO.findBaseMenuListByThread();
	}
	
	
	//set和get方法
	public BaseMenuDAO getBaseMenuDAO() {
		return baseMenuDAO;
	}
	public void setBaseMenuDAO(BaseMenuDAO baseMenuDAO) {
		this.baseMenuDAO = baseMenuDAO;
	}
}
