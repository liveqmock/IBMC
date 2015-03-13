package com.nomen.ntrain.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @description 常量类  
 * @author 连金亮
 * @date 2011-05-20
 */

public final class Constant 
{
    //登录后存放信息的session变量
	public final static String LOGIN_PARAM = "loginSession";
	//无权限字符串
	public final static String LOGIN_NOPUR = "nopurview";
	//跳出功能页面，执行脚本
    public final static String NO_DATA = "nodata";
	
	public final static String ROLE_SUPER_ID = "-1";//超级管理员角色ID
    public static final String MENU_FATHERID_LEV1 = "-1";	//菜单列表第一级FATHERID
    
	//全文菜单application 保存名
	public final static String MENU_LIST_NAME   = "menuList";
	//全文角色application 保存名
	public final static String ROLE_LIST_NAME   = "roleList";
	// 编辑日志用到的数据类型 
	public  enum OptType{
		/**新增***/
		INSERT,
		/**修改***/
		UPDATE,
		/**删除***/
		DELETE
	} 
	
	/**
	 * 返回2009年至当前年份的List列表
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static List getYearList() {
		List list = new ArrayList();
		int endyear=PubFunc.getYearValue(new java.util.Date());
		for(int startyear=2010;startyear<=endyear;startyear++) {
			Map map=new HashMap();
			map.put("yearvalue", startyear);
			map.put("yeartext", startyear+"年");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取开始年度到结束年度的List列表
	 * @param startYear 开始年度
	 * @param endYear 结束年度
	 * @param step 年度之间的间隔（负数时表示递减的列表）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getYearList(int startYear,int endYear,int step) {
		List list = new ArrayList();
		for(int i=startYear;startYear<=endYear?i<=endYear:i>=endYear;i=i+step) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("yearvalue", String.valueOf(i));
			map.put("yeartext", String.valueOf(i)+"年");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 返回月份的List列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getMonthList() {
		List list = new ArrayList();
		for(int month=1;month<=12;month++) {
			Map map=new HashMap();
			map.put("monthvalue", month);
			map.put("monthtext", month+"月");
			list.add(map);
		}
		return list;
	}
	 
	
	/**
	 * 返回32位随机字符串
	 * @return
	 */
	public static String getTokenId() {
		//所有字母和数字串
		String radStr = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
		StringBuffer id=new StringBuffer();
		java.util.Random rand=new java.util.Random();
		int length=32;
		for(int i=0;i<length;i++) {
			int randNum = rand.nextInt(62);
			id.append(radStr.substring(randNum, randNum+1));
		}
		return id.toString();
	}
	
	/**
	 * 当clob字段的内容长度在1000和2000之间，进行格式转换
	 * @param strClob clob字符串
	 * @return
	 */
	public static String formatClob(String strClob) {
		if (strClob.length() >= 1000 && strClob.length() <= 2000) {
			strClob = StringUtils.rightPad(strClob, 2009);
		}
		return strClob;
	} 
}
