package com.nomen.ntrain.sgsdx.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.base.service.LoginService;
import com.nomen.ntrain.sgsdx.bean.SmsItemlistBean;
import com.nomen.ntrain.sgsdx.service.SmsItemlistService;

public class SmsItemlistAction extends SmsAction {
	private SmsItemlistService smsItemlistService;	//短信内容业务接口
	private LoginService loginService;				//登录信息业务处理类
	private Map<String,String> querymap;			//查询Map
	private SmsItemlistBean smsItemlistBean;		//短信内容Bean
	private List dataList;							//结果集
	
	
	/**
	 * 手动发送_[跳转]
	 */
	public String setSmsItemlist(){
		try{
			this.smsItemlistBean = this.smsItemlistService.findSmsItemlistBeanById(this.smsItemlistBean.getId());
			//登录人员信息
			this.getOpraterInfoIntoRequest();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 手动发送_[保存]
	 */
	public void saveSmsItemlist(){
		try{
			//维护人
			LoginBean login = this.getLoginSessionBean();
			this.smsItemlistService.sendSmsItemlistManual(this.smsItemlistBean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ==============================JQuery相关==============================
	 */
	/**
	 * 查询并返回最新发送数据
	 */
	public void findSendSmsDataByJq(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			this.smsItemlistBean = this.smsItemlistService.findSmsItemlistBeanById(this.smsItemlistBean.getId());
			JSONArray json = JSONArray.fromObject(smsItemlistBean);
			response.getWriter().print(json.toString());
		}catch(Exception e){
			this.print("false");
			e.printStackTrace();
		}
	}
	/**
	 * 删除短信内容
	 */
	public void deleteSmsItemlistByJq(){
		try{
			this.smsItemlistService.deleteSmsItemlistByIdStr(this.smsItemlistBean.getId());
			this.print("true");
		}catch(Exception e){
			this.print("false");
			e.printStackTrace();
		}
	}
	/**
	 * 查询短信内容列表
	 */
	public void listSmsItemlistByJq(){
		try{
			
		}catch(Exception e){
			this.print("false");
			e.printStackTrace();
		}
	}
	//set/get方法
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public Map<String, String> getQuerymap() {
		return querymap;
	}
	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}
	public SmsItemlistService getSmsItemlistService() {
		return smsItemlistService;
	}
	public void setSmsItemlistService(SmsItemlistService smsItemlistService) {
		this.smsItemlistService = smsItemlistService;
	}
	public SmsItemlistBean getSmsItemlistBean() {
		return smsItemlistBean;
	}
	public void setSmsItemlistBean(SmsItemlistBean smsItemlistBean) {
		this.smsItemlistBean = smsItemlistBean;
	}
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
