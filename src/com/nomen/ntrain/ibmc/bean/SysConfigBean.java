package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description 系统管理_系统配置表
 * @author ljl
 * @date 2015-02-28
 */
public class SysConfigBean implements Serializable {
	private static final long   serialVersionUID = 1L;
	private String commid;     //社区ID（预留）
	private String configkey;  //key
	private String configval1; //值1（业主卡有效期限-月）
	private String configval2; //值2（临时卡有效期限-月）
	private String configval3; //值3（正式卡有效期限-月）
	private String configval4; //值4（业主卡押金-元）
	private String configval5; //值5（临时卡押金-元）
	private String configval6; //值6（正式卡押金-元）
	
	//以下为set get方法
	public String getConfigkey() {
		return configkey;
	}
	public void setConfigkey(String configkey) {
		this.configkey = configkey;
	}
	public String getConfigval1() {
		return configval1;
	}
	public void setConfigval1(String configval1) {
		this.configval1 = configval1;
	}
	public String getConfigval2() {
		return configval2;
	}
	public String getCommid() {
		return commid;
	}
	public void setCommid(String commid) {
		this.commid = commid;
	}
	public void setConfigval2(String configval2) {
		this.configval2 = configval2;
	}
	public String getConfigval3() {
		return configval3;
	}
	public void setConfigval3(String configval3) {
		this.configval3 = configval3;
	}
	public String getConfigval4() {
		return configval4;
	}
	public void setConfigval4(String configval4) {
		this.configval4 = configval4;
	}
	public String getConfigval5() {
		return configval5;
	}
	public void setConfigval5(String configval5) {
		this.configval5 = configval5;
	}
	public String getConfigval6() {
		return configval6;
	}
	public void setConfigval6(String configval6) {
		this.configval6 = configval6;
	}
	
	/**
	 * 获取业主卡有效期
	 * @return
	 */
	public String _getOwnerCardValid(){
		return _getCardValidTool(this.configval1);
	}
	
	/**
	 * 获取临时卡有效期[临时卡数据新增时不需要配置失效时间，失效时间是在第一次刷卡后的N天失效]
	 * @return
	 */
	public String _getTempCardValid(){
		return "";
	}
	
	/**
	 * 获取正式卡有效期
	 * @return
	 */
	public String _getRegularCardValid(){
		return _getCardValidTool(this.configval3);
	}

	private String _getCardValidTool(String configVal){
		try{
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			if(null != configVal && !"".equals(configVal)){
				String[] arr = configVal.split("-");
				if(2 == arr.length){
					if(arr[0].equals("9999") && "year".equals(arr[1])){
						Date rtnDate = format.parse("9999-12-30");
						return format.format(rtnDate);
					}else if(!arr[0].equals("9999") && "year".equals(arr[1])){
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						cal.add(Calendar.YEAR,1);
						Date rtnDate=cal.getTime(); 
						return format.format(rtnDate);
					}
				}
			}
		}catch(Exception ex){
			
		}
		return "";
	}
	
	/**
	 * 获取业主卡押金
	 * @return
	 */
	public String _getOwnerCardCash(){
		return this.configval4;
	}
	
	/**
	 * 获取临时卡押金
	 * @return
	 */
	public String _getTempCardCash(){
		return this.configval5;
	}
	
	/**
	 * 获取正式卡押金
	 * @return
	 */
	public String _getRegularCardCash(){
		return this.configval6;
	}
}
