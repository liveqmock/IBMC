package com.nomen.ntrain.base.dao.implement;

import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.nomen.ntrain.base.bean.BaseLogonBean;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.dao.LoginDAO;

public class LoginDAOImpl extends SqlMapClientDaoSupport implements LoginDAO 
{
	public List findLoginUser(Map map) {
		return this.getSqlMapClientTemplate().queryForList("Login.getLoginByUser", map);
	}
	
	public String insertBaseLogon(BaseLogonBean baseLogonBean) { 
		return (String)this.getSqlMapClientTemplate().insert("Login.insertBaseLogon", baseLogonBean); 
	}

	public void updateBaseLogon(String id) { 
		this.getSqlMapClientTemplate().update("Login.updateBaseLogon", id); 
	}

	public void updatePassword(LoginBean loginBean) {
		this.getSqlMapClientTemplate().update("Login.updatePassword",loginBean);
	}
}
