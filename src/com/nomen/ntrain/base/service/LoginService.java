package com.nomen.ntrain.base.service;

import java.util.Map;
import com.nomen.ntrain.annotation.LoginEnums;
import com.nomen.ntrain.base.bean.LoginBean;

/**
 * @description ��¼ģ��  
 * @author ������
 * @date 2009-05-18
 */
public interface LoginService{
	/**
	 * [2010-11-23 ��Ҫ�������е�¼��֤��]
	 * ��ѯ��¼��Ա����Ϣ��������LoginBean��Ϣ
	 * @param Map
	 * @return Map
	 */
	public Map findLoginUser(Map map); 
	
	
	/**
	 * ��¼��¼/ע����־
	 * @param loginBean
	 */
	public void insertLoginLog(LoginBean loginBean,LoginEnums logEnums);


	/**
	 * �޸�����
	 * @param loginBean
	 */
	public void updatePassword(LoginBean loginBean);
}
