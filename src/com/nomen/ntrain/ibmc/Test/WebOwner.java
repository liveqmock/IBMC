package com.nomen.ntrain.ibmc.Test;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.nomen.ntrain.ibmc.webservice.ServiceStub;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerInfo;

/**
 * 这张对应的是 dbo.ibmc_owner;表
 * @author Administrator
 *
 */
public class WebOwner {
	public static void main(String[] args) {
		WebOwner comm = new WebOwner();
		try {
			ServiceStub stock=new ServiceStub();
			OwnerInfo ownerInfo = new OwnerInfo();   //业主信息表
			ownerInfo.setBuildId("5.1.1.1");         //设置业主的小区房产ID[注意需要修改]
			ownerInfo.setId(1);              //设置业主的主键ID[这个可设置也可不设置]
			ownerInfo.setName("业主5");       //设置业主的名称
			ownerInfo.setSex(true);          //设置业主的性别
			OwnerAdd ownerAdd = new OwnerAdd();
			ownerAdd.setOwnerInfo(ownerInfo);
			OwnerAddResponse res = stock.ownerAdd(ownerAdd);
			System.out.println("这是新增的返回值:"+res.getRetval());    //获取返回值
			System.out.println("这是新增的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
