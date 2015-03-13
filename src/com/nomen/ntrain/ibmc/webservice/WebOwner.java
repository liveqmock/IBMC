package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerInfo;

public class WebOwner {
	public static void main(String[] args) {
		WebOwner comm = new WebOwner();
		
		try {
			ServiceStub stock=new ServiceStub();
			
			OwnerInfo ownerInfo = new OwnerInfo();
			ownerInfo.setBuildId("2.1");
			ownerInfo.setId(1);
			ownerInfo.setName("业主1");
			ownerInfo.setSex(true);
			
			OwnerAdd ownerAdd = new OwnerAdd();
			ownerAdd.setOwnerInfo(ownerInfo);
			OwnerAddResponse res = stock.ownerAdd(ownerAdd);
			System.out.println("这是新增的返回值:"+res.getRetval());    //获取返回值[0：表示成功，10001表示数据未新增]
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
