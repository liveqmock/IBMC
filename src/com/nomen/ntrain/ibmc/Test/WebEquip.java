package com.nomen.ntrain.ibmc.Test;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.nomen.ntrain.ibmc.webservice.ServiceStub;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevModify;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevModifyResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevRemove;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevRemoveResponse;

/**
 * 这张对应的是 dbo.ibmc_equipment;表
 * String id;/设备ID           equ_id
 * String name;//设备名称       equ_name
 * String mac;//mac地址
 * String ip;//ip地址
 * String mask;//子网掩码
 * String gateway;//网关
 * String softver;//软件版本
 * String hwver;//硬件版本
 *
 */
public class WebEquip {
	public static void main(String[] args) {
		WebEquip comm = new WebEquip();
		
		DevInfo devInfo = new DevInfo();
		devInfo.setId("H4_1");     //设备equ_id
		devInfo.setName("门口机5_1");      //设备名称
		devInfo.setIp("192.168.1.66");   //设置IP
		devInfo.setMac("14-14-4B-51-94-90");    //设置MAC
		devInfo.setGateWay("192.168.1.1");     //设置网关
		devInfo.setMask("255.255.255.0");      //设置子网掩码
		devInfo.setHwVer("11");
		devInfo.setSoftVer("11");
		comm.devAdd(devInfo);
		
		devInfo = new DevInfo();
		devInfo.setId("H4_1");     //设备equ_id
		devInfo.setName("门口机5_1");      //设备名称
		devInfo.setIp("192.168.1.66");   //设置IP
		devInfo.setMac("14-14-4B-51-94-90");    //设置MAC
		devInfo.setGateWay("192.168.1.1");     //设置网关
		devInfo.setMask("255.255.255.0");      //设置子网掩码
		devInfo.setHwVer("11");
		devInfo.setSoftVer("11");
		comm.devModify(devInfo);
		
		
	}
	
	/**
	 * 设备[门口机]新增
	 *
	 */
	public void devAdd(DevInfo devInfo){
		try {
			ServiceStub stock = new ServiceStub();
			DevAdd devAdd = new DevAdd();
			devAdd.setDevInfo(devInfo);
			DevAddResponse res = stock.devAdd(devAdd);
			System.out.println("这是新增的返回值:"+res.getRetval());    //获取返回值
			System.out.println("这是新增的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设备[门口机]修改
	 *
	 */
	public void devModify(DevInfo devInfo){
		try {
			ServiceStub stock = new ServiceStub();
			DevModify devModify = new DevModify();
			devModify.setDevInfo(devInfo);
			DevModifyResponse res = stock.devModify(devModify);
			System.out.println("这是修改的返回值:"+res.getRetval());    //获取返回值
			System.out.println("这是修改的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设备[门口机]删除
	 *
	 */
	public void devRemove(DevInfo devInfo){
		try {
			ServiceStub stock = new ServiceStub();
			DevRemove devRemove = new DevRemove();
			devRemove.setDevInfo(devInfo);
			DevRemoveResponse res = stock.devRemove(devRemove);
			System.out.println("这是删除的返回值:"+res.getRetval());    //获取返回值
			System.out.println("这是删除的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设备[门口机]查询
	 *
	 */
	public void devSearch(){
		
	}
	
	
	
}
