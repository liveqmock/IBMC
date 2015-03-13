package com.nomen.ntrain.ibmc.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public final class IbmcConstant {
	public static final String COMM_SUPPER_PARENTID = "803"; 
	public static final String COMM_LEV_PROVINCE = "0"; //省
	public static final String COMM_LEV_CITY = "1";     //市
	public static final String COMM_LEV_COUNTY = "2";  //县/区
	public static final String COMM_LEV_VILLAGE = "3";  //村
	public static final String COMM_LEV_HOUSE = "4";  //房产
	public static final String COMM_LEV_ROOM = "5";  //房间
	

	public static final String CARDTYPE_OWNER = "1"; //业主卡
	public static final String CARDTYPE_TEMP = "2";  //临时卡
	public static final String CARDTYPE_REGULAR = "3";//正式卡
	
	public static final String COMMNUNITY = "COMMNUNITY";
	public static final String ROOMCARD =   "ROOMCARD";
	public static final String DOORHOUSE = "DOORHOUSE";
	
	//用在数据同步中
	public static final String INSERT    = "I";      //新增[用在数据同步中]
	public static final String UPDATE    = "U";      //修改[用在数据同步中]
	public static final String DELETE    = "D";      //删除[用在数据同步中]
	
	//身份证图片保存路径
	public static final String MAN_PER_PHOTO_PATH = "/Ibmc/ManPeo/Photo/";     //市
	//人员性别标识
	public static final String SEX_BOY = "0"; //男
	public static final String SEX_GRIL = "1"; //女
	
	//设备命令
	public static final String EQUIP_RESET = "1";  //重置设备
	public static final String EQUIP_RESTOREFAC = "2";  //重置设备门禁权限
	public static final String EQUIP_RESETUNLOCK = "3"; //恢复出厂设置
	
	//短信发送状态
	public static final String MESSSTATE_WAIT = "0"; //待发送
	public static final String MESSSTATE_SUCC = "1";  //发送成功
	public static final String MESSSTATE_FAIL = "-1";//发送失败

	//系统配置表中configkey常量
	public static final String CONFIG_KEY_STANDAR  = "MONEY_VALID";//卡片有效期以及收费标准

	//通过COMMID获取有效时间，押金金额
	public static final String CONFIG_COMMID  = "-1";

	/**
	 * 所属管理级别
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map getCommLevSign(){
		Map m = new  LinkedHashMap();
		m.put(IbmcConstant.COMM_LEV_PROVINCE, "省");
		m.put(IbmcConstant.COMM_LEV_CITY, "市");
		m.put(IbmcConstant.COMM_LEV_COUNTY, "县/区");
		m.put(IbmcConstant.COMM_LEV_VILLAGE, "村");
		return m;
	}
	
	/**
	 * 卡类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map getCardTypeSign(){
		Map m = new  LinkedHashMap();
		m.put(IbmcConstant.CARDTYPE_OWNER, "业主卡");
		m.put(IbmcConstant.CARDTYPE_TEMP, "临时卡");
		m.put(IbmcConstant.CARDTYPE_REGULAR, "正式卡");
		return m;
	}
	
	/**
	 * 设备命令
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map getEquipCommand(){
		Map m = new  LinkedHashMap();
		m.put(IbmcConstant.EQUIP_RESET, "重置设备");
		//m.put(IbmcConstant.EQUIP_RESTOREFAC, "重置设备门禁权限");
		m.put(IbmcConstant.EQUIP_RESETUNLOCK, "恢复出厂设置");
		return m;
	}
	
	
	/**
	 * 格式化下行社区ID
	 * @param commPath
	 * @return
	 */
	public static final String formatDownCommId(String commPath){
		String commPathArr[] = commPath.split("/");
		//构建ID(规则为XX.XX）
		String downId        = "";
		for(int i=1;i<commPathArr.length;i++){
			downId += commPathArr[i];
			if(i < commPathArr.length -1){
				downId += ".";
			}
		}
		return downId;
	}

	/**
	 * 系统配置卡片有效期列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map getSysConfigValid(){
		Map m = new  LinkedHashMap();
		m.put("3-day", "3天");
		m.put("1-year", "1年");
		m.put("3-year", "3年");
		m.put("5-year", "5年");
		m.put("9999-year", "永久");
		return m;
	}
}
