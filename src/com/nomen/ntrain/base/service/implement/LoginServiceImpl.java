package com.nomen.ntrain.base.service.implement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nomen.ntrain.annotation.LoginEnums;
import com.nomen.ntrain.base.bean.BaseOptLogBean;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.dao.BaseOptLogDAO;
import com.nomen.ntrain.base.dao.LoginDAO;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.util.Constant;

public class LoginServiceImpl implements LoginService
{
	private LoginDAO         loginDAO;
	private BaseOptLogDAO    baseOptLogDAO ;
	@SuppressWarnings("unchecked")
	public Map findLoginUser(Map map) {
		try {
			List list = this.loginDAO.findLoginUser(map);
			//存在登录人员信息时
			if(list.size()>0) {
				LoginBean login=(LoginBean)list.get(0);
				Map loginMap=new HashMap();
				loginMap.put(Constant.LOGIN_PARAM, login);
				login = null;
				list = null;
				return loginMap;
			}
			return null;
		} catch(Exception ex) {
			return null;
		}
	}

	public void insertLoginLog(LoginBean loginBean, LoginEnums logEnums) {
		if(null == loginBean) return ;
        //插入登录日志
        BaseOptLogBean baseOptLogBean = new BaseOptLogBean(); 
        baseOptLogBean.setOptremark("");
        baseOptLogBean.setOpttype(logEnums.getKey()+"");
        baseOptLogBean.setArgvalues("");
        baseOptLogBean.setOptuserid(loginBean.getId());
        baseOptLogBean.setOptusername(loginBean.getUsername());
        baseOptLogBean.setIntflag("0");
        this.baseOptLogDAO.insertBaseOptLog(baseOptLogBean);
	
}
	public void updatePassword(LoginBean loginBean) {
		this.loginDAO.updatePassword(loginBean);
	}
	
	//以下为get和set方法
	public LoginDAO getLoginDAO() {
		return loginDAO;
	}
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public BaseOptLogDAO getBaseOptLogDAO() {
		return baseOptLogDAO;
	}

	public void setBaseOptLogDAO(BaseOptLogDAO baseOptLogDAO) {
		this.baseOptLogDAO = baseOptLogDAO;
	}

	
}