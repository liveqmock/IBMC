package com.nomen.ntrain.base.dao.implement;

import com.nomen.ntrain.base.bean.BaseRoleUserBean;
import com.nomen.ntrain.base.dao.BaseRoleUserDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class BaseRoleUserDAOImpl extends NsoftBaseDao implements BaseRoleUserDAO {

	public void deleteBaseRoleUserByUserid(String userid) {
		this.getSqlMapClientTemplate().delete("BaseRoleUser.deleteBaseRoleUserByUserid",userid);
	}

	public String insertBaseRoleUser(BaseRoleUserBean baseRoleUserBean) {
		return (String)this.getSqlMapClientTemplate().insert("BaseRoleUser.insertBaseRoleUser",baseRoleUserBean);
	}

	public void updateBaseRoleUser(BaseRoleUserBean baseRoleUserBean) {
		this.getSqlMapClientTemplate().update("BaseRoleUser.updateBaseRoleUser",baseRoleUserBean);
	}
}
