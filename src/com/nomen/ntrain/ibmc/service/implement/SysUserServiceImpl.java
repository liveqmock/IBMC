package com.nomen.ntrain.ibmc.service.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseOptLogBean;
import com.nomen.ntrain.base.bean.BaseRoleUserBean;
import com.nomen.ntrain.base.dao.BaseRoleUserDAO;
import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.SysUserAreaBean;
import com.nomen.ntrain.ibmc.bean.SysUserBean;
import com.nomen.ntrain.ibmc.dao.SysUserAreaDAO;
import com.nomen.ntrain.ibmc.dao.SysUserDAO;
import com.nomen.ntrain.ibmc.service.SysUserService;
import com.nomen.ntrain.util.MD5;
@SuppressWarnings("all")
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

	private SysUserDAO sysUserDAO;
	private BaseRoleUserDAO baseRoleUserDAO;
	private SysUserAreaDAO sysUserAreaDAO;
	public List<SysUserBean> findSysUserList(Map map){
		return sysUserDAO.findSysUserList(map);
	}

	public List<SysUserBean> findSysUserByPage(Map map, int tagpage, int record) {
		return sysUserDAO.findSysUserByPage(map,tagpage,record);
	}

	public SysUserBean findSysUserBeanById(String id){
		return sysUserDAO.findSysUserBeanById(id);
	}

	public void saveSysUser(Map map,SysUserBean sysUserBean){
	
		String id = sysUserBean.getId();
		String roleid = (String) map.get("roleid"); //角色id
		String optuserid = (String) map.get("optuserid");
		String optusername = (String)map.get("optusername");
		String areaIdStr = (String)map.get("areaIdStr");
		if(func.IsEmpty(id)){
			//设置初始化用户密码a_123456
			sysUserBean.setUserpsd(MD5.EncryptData("a_123456"));
			id = sysUserDAO.insertSysUser(sysUserBean);
			this.insertSysUserData(id, roleid, areaIdStr, optuserid, optusername);//新增管理区域与关联角色
		}else{
			sysUserDAO.updateSysUser(sysUserBean);
			this.baseRoleUserDAO.deleteBaseRoleUserByUserid(id);
		     this.sysUserAreaDAO.deleteSysUserAreaBean(id);
//			if(!func.IsEmpty(roleid)){
//				BaseRoleUserBean bean = new BaseRoleUserBean();
//				bean.setUserid(id);
//				bean.setRoleids(roleid);
//				this.baseRoleUserDAO.updateBaseRoleUser(bean);
//			}
			this.insertSysUserData(id, roleid, areaIdStr, optuserid, optusername);//新增管理区域与关联角色
			
			
		}
		
		
		
	}
	/**
	 * 新增管理区域与关联角色(id:主键Id；roleid：角色Id；areaIdStr：区域ID串)
	 */
    private void insertSysUserData(String id,String roleid,String areaIdStr,String optuserid,String optusername){
    	if(!func.IsEmpty(roleid)){
			//新增角色
			BaseRoleUserBean bean = new BaseRoleUserBean();
			bean.setUserid(id);
			bean.setRoleids(roleid);
			bean.setOptuserid(optuserid);
			bean.setOptusername(optusername);
			this.baseRoleUserDAO.insertBaseRoleUser(bean);
		}
		if(!func.IsEmpty(areaIdStr)){
			//新增管理区域
			String[] areaIdArr = areaIdStr.split(",");
			for(String areaId:areaIdArr){
				SysUserAreaBean abean = new SysUserAreaBean();
				abean.setAreaid(areaId);
				abean.setUserid(id);
				this.sysUserAreaDAO.insertSysUserAreaBean(abean);
			}
		}
    	
    }
	public void deleteSysUserById(String idStr){
		String[] idArr = idStr.split(",");
		for(String id : idArr){
			sysUserDAO.deleteSysUserById(id);
			this.baseRoleUserDAO.deleteBaseRoleUserByUserid(id);
			this.sysUserAreaDAO.deleteSysUserAreaBean(id);
		}
	}

	public String findSysUserOrder(Map map){
	   return this.sysUserDAO.findSysUserOrder(map);
	}
	
	public String findSysUserAreaIdstr(String userid){
		return this.sysUserAreaDAO.findSysUserAreaIdstr(userid);
	}

	public void updateSysUser(SysUserBean sysUserBean) {
		this.sysUserDAO.updateSysUser(sysUserBean);
	}
	
	//Get和Set方法
	public SysUserDAO getSysUserDAO() {
		return sysUserDAO;
	}
	public void setSysUserDAO(SysUserDAO sysUserDAO) {
		this.sysUserDAO = sysUserDAO;
	}

	public BaseRoleUserDAO getBaseRoleUserDAO() {
		return baseRoleUserDAO;
	}

	public void setBaseRoleUserDAO(BaseRoleUserDAO baseRoleUserDAO) {
		this.baseRoleUserDAO = baseRoleUserDAO;
	}

	public SysUserAreaDAO getSysUserAreaDAO() {
		return sysUserAreaDAO;
	}

	public void setSysUserAreaDAO(SysUserAreaDAO sysUserAreaDAO) {
		this.sysUserAreaDAO = sysUserAreaDAO;
	}
	
}
