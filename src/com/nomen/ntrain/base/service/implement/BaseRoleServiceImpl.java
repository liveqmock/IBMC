package com.nomen.ntrain.base.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.annotation.OptEnums;
import com.nomen.ntrain.annotation.OptResource;
import com.nomen.ntrain.base.bean.BaseRoleBean;
import com.nomen.ntrain.base.bean.BaseRoleMenuBean;
import com.nomen.ntrain.base.dao.BaseRoleDAO;
import com.nomen.ntrain.base.service.BaseRoleService;

public class BaseRoleServiceImpl implements BaseRoleService {
    private BaseRoleDAO  baseRoleDAO;
    @OptResource(optRemark="角色定义",optType=OptEnums.DELETE)
	public void deleteBaseRoleById(String id) {
		this.baseRoleDAO.deleteBaseRoleById(id);
		this.baseRoleDAO.deleteBaseRoleMenuByRoleId(id);
	}

	public BaseRoleBean findBaseRoleBeanById(String id) {
		return this.baseRoleDAO.findBaseRoleBeanById(id);
	}

	public List<BaseRoleBean> findBaseRoleByUseridList(Map map) {
		return this.baseRoleDAO.findBaseRoleByUseridList(map);
	}

	public String findBaseRoleImpCount(String roleid) {
		return this.baseRoleDAO.findBaseRoleImpCount(roleid);
	}

	public List<BaseRoleBean> findBaseRoleList(Map map) {
		List<BaseRoleBean> rL = this.baseRoleDAO.findBaseRoleList(map);
		//查询出角色对应的菜单ID串，并复制值purview
		for(BaseRoleBean rB : rL){
			String pur = "";
			List<BaseRoleMenuBean> roleMenuList = this.baseRoleDAO.findBaseRoleMenuListByRole(rB.getId());
			for(BaseRoleMenuBean bmB : roleMenuList){
				pur += bmB.getMenuid() +",";
			}  
			rB.setPurview(pur);
		}
		return rL;
	}

	public List<BaseRoleBean> findBaseRoleListByPage(Map map, int tagpage,int record) {
		return this.baseRoleDAO.findBaseRoleListByPage(map, tagpage, record);
	}
	@OptResource(optRemark="角色定义",optType=OptEnums.SAVE)
	public String saveBaseRole(BaseRoleBean baseRoleBean) {
		String pkId = baseRoleBean.getId();
		String purview = baseRoleBean.getPurview();
		baseRoleBean.setPurview("");
		//根据id是否为空，判断是新增、修改
		if(null == pkId || pkId.length() == 0||"".equals(pkId)){
			pkId = this.baseRoleDAO.insertBaseRole(baseRoleBean);
		}
		else{
			this.baseRoleDAO.deleteBaseRoleMenuByRoleId(pkId);
			this.baseRoleDAO.updateBaseRole(baseRoleBean);
		}
		String[] purArr = purview.split(",");
		BaseRoleMenuBean rmBean = null;
		for(String menuId: purArr){
			rmBean = new BaseRoleMenuBean();
			rmBean.setMenuid(menuId);
			rmBean.setRoleid(pkId);
			this.baseRoleDAO.insertBaseRoleMenu(rmBean);
		}
		return pkId;
	}


	public String findBaseRoleMenuByIdStr(String id) {
		List<BaseRoleMenuBean>  roleMenulist = this.baseRoleDAO.findBaseRoleMenuListByRole(id);
		String MenuidStr ="";
		for(BaseRoleMenuBean baseRoleMenuBean : roleMenulist){
			MenuidStr += baseRoleMenuBean.getMenuid()+",";
		}
		return MenuidStr;
	}

	//get和set方法
	public BaseRoleDAO getBaseRoleDAO() {
		return baseRoleDAO;
	}

	public void setBaseRoleDAO(BaseRoleDAO baseRoleDAO) {
		this.baseRoleDAO = baseRoleDAO;
	}

}

   
   
