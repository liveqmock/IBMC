package com.nomen.ntrain.ibmc.Test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.nomen.ntrain.ibmc.webservice.ServiceStub;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoAddResponse;

/**
 * 这张对应的是 dbo.ibmc_unlock_ic;表
 * @author Administrator
 *
 */
public class WebUnlockPersonInfo {
	public static void main(String[] args) {
		WebUnlockPersonInfo comm = new WebUnlockPersonInfo();
		try {
			ServiceStub stock=new ServiceStub();
			UnlockPersonRelaInfo unlockPersonRelaInfo = new UnlockPersonRelaInfo();
			//创建 Calendar 对象
		    Calendar calendar = Calendar.getInstance();
		    // 初始化 Calendar 对象，但并不必要，除非需要重置时间
		    calendar.setTime(new Date());
		    unlockPersonRelaInfo.setUpdateTime(calendar);         //更新时间
		    calendar.add(Calendar.DAY_OF_MONTH, 3);
		    unlockPersonRelaInfo.setExpiredTime(calendar);        //设置过期时间
		    unlockPersonRelaInfo.setOwnerIndex(9);                //设置业主的主键ID[注意需要修改]
		    unlockPersonRelaInfo.setIsOwner(true);                //设置是否是业主
		    unlockPersonRelaInfo.setPersonId("5.1.1.1.1");   //设置业主的房间ID[注意需要修改]关联dbo.ibmc_community
		    unlockPersonRelaInfo.setUnlockId(1);              //设置门卡的主键ID[]
		    unlockPersonRelaInfo.setUnlockKey("2bb4ec21");     //设置门卡的物理序列号
		    unlockPersonRelaInfo.setUnlockType(1);            //设置门卡的类型
		    unlockPersonRelaInfo.setUpdateUserid(9);          //设置更新操作门卡的人员userid[注意需要修改]
			
			UnlockPersonRelaInfoAdd unlockPersonRelaInfoAdd = new UnlockPersonRelaInfoAdd();
			unlockPersonRelaInfoAdd.setUnlockPersonRelaInfo(unlockPersonRelaInfo);
			UnlockPersonRelaInfoAddResponse res = stock.unlockPersonRelaInfoAdd(unlockPersonRelaInfoAdd);
			System.out.println("这是新增的返回值:"+res.getRetval());    //获取返回值
			System.out.println("这是新增的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 添加小区节点
	 */
	public void buildAdd(BuildInfo buildInfo){
		try {
			ServiceStub stock=new ServiceStub();
			BuildAdd buildAdd = new BuildAdd();
			buildAdd.setBuildInfo(buildInfo);   //小区信息
			buildAdd.setSessionId("");      //设置会话ID
			BuildAddResponse buildRes = stock.buildAdd(buildAdd);   //获取返回结果集
			System.out.println("这是新增的返回值:"+buildRes.getRetval());    //获取返回值[0：表示成功，10001表示数据未新增]
			System.out.println("这是新增的错误详细信息:"+buildRes.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}
	
}
