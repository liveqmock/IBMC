package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.util.SynMessage;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfdevInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevModify;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevModifyResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevRemove;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevRemoveResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevSearch;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevSearchCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevSearchResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.SearchRangeType;
/**
 * 设备管理（需要绑定到房屋）
 * @author lenovo
 *
 */
public class WebEquip {
	private static final Log LOG = LogFactory.getLog(WebEquip.class);
	/**
	 * 修改设备绑定信息
	 */
	public SynMessage updateEquip(ManDoorBean doorBean,String equipId){
		SynMessage msg = new SynMessage();
		msg.setSuccess(false);
		try {
			DevInfo info = new DevInfo();
			info.setId(equipId);   				//设置设备ID
			info.setName(doorBean.getName());   //设置设备名称
			info.setMac(doorBean.getDoormac()); //设置设备MAC地址
			info.setIp(doorBean.getDoorip());   //设置设备IP
			info.setMask(doorBean.getSubmask());      //设置设备子网掩码
			info.setGateWay(doorBean.getGateway());   //设置设备网关
			info.setHwVer("");
			info.setSoftVer("");
			
			ServiceStub stock = new ServiceStub();
			DevModify modify = new DevModify();
			modify.setDevInfo(info);
			modify.setSessionId("");        //设置会话ID
			DevModifyResponse res= stock.devModify(modify);
			if(res.getRetval()==0){
				msg.setSuccess(true);
			}else{
				LOG.error(res.getErrorDesc()+"--doorId:"+doorBean.getId());
				msg.setMessage("这是查询的错误详细信息:"+res.getErrorDesc());
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--doorId:"+doorBean.getId());
			msg.setMessage("AxisFault - e"+"--doorId:"+doorBean.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--doorId:"+doorBean.getId());
			msg.setMessage("RemoteException - e"+"--doorId:"+doorBean.getId());
		} 
		return msg;
	}
	
	
	/**
	 * 添加设备绑定信息
	 */
	public SynMessage addEquip(ManDoorBean doorBean,String equipId){
		SynMessage msg = new SynMessage();
		msg.setSuccess(false);
		try {
			DevInfo devInfo = new DevInfo();
			devInfo.setId(equipId);	//设备Id
			devInfo.setName(doorBean.getName());           		//设备名称
			devInfo.setIp(doorBean.getDoorip());         		//设置IP
			devInfo.setMac(doorBean.getDoormac());   			//设置MAC
			devInfo.setGateWay(doorBean.getGateway());     		//设置网关
			devInfo.setMask(doorBean.getSubmask());      		//设置子网掩码
			devInfo.setHwVer("");
			devInfo.setSoftVer("");
			DevAdd devAdd = new DevAdd();
			devAdd.setDevInfo(devInfo);
			ServiceStub stock=new ServiceStub();
			DevAddResponse res = stock.devAdd(devAdd);
			if(res.getRetval()==0){
				msg.setSuccess(true);
			}else{
				LOG.error(res.getErrorDesc()+"--doorId:"+doorBean.getId());
				msg.setMessage("这是查询的错误详细信息:"+res.getErrorDesc());
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--doorId:"+doorBean.getId());
			msg.setMessage("AxisFault - e"+"--doorId:"+doorBean.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--doorId:"+doorBean.getId());
			msg.setMessage("RemoteException - e"+"--doorId:"+doorBean.getId());
		} 
		return msg;
	}
	
	/**
	 * 设备[门口机]查询
	 */
	public SynMessage isExistsEquip(String equipId){
		SynMessage msg = new SynMessage();
		msg.setSuccess(false);
		try {
			ServiceStub stock = new ServiceStub();
			DevSearch devSearch = new DevSearch();
			DevSearchCond devSearchCond = new DevSearchCond();
			devSearchCond.setId(equipId);     //设备ID
			
			//以下参数必须设置
			devSearchCond.setFlagId(2);  	//设备ID查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			devSearchCond.setFlagName(1);	//设备名称查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			devSearchCond.setFlagMac(1); 	//mac地址查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			devSearchCond.setFlagIp(1);  	//ip查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			devSearchCond.setFlagMask(1);   //子网掩码查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			devSearchCond.setFlagGateWay(1);//网关查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			devSearchCond.setFlagHwVer(1);
			devSearchCond.setFlagSoftVer(1);
			
			SearchRangeType searchRangeType = new SearchRangeType(); //查询记录范围
			searchRangeType.setStart(0);   //开锁记录索引
			searchRangeType.setEnd(1);     //结束记录索引
			searchRangeType.setOnlyCount(true);  //是否只显示条数
			devSearchCond.setSearchRange(searchRangeType);  //查询记录范围
			devSearch.setDevSearchCond(devSearchCond);
			
			DevSearchResponse res = stock.devSearch(devSearch);
			if(res.getRetval() == 0){
				if(res.getCount()>0){
					msg.setSuccess(true);
					msg.setCount(res.getCount());
				}else{
					msg.setSuccess(true);
					msg.setCount(0);
					LOG.error(res.getErrorDesc()+"--doorId:"+equipId);
					msg.setMessage("这是查询的错误详细信息:"+res.getErrorDesc());
				}
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--doorId:"+equipId);
			msg.setMessage("AxisFault - e"+"--doorId:"+equipId);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--doorId:"+equipId);
			msg.setMessage("RemoteException - e"+"--doorId:"+equipId);
		}
		return msg;
	}
	

	/**
	 * 新增/修改设备（房产关联）
	 * 若返回-1 则表示操作失败,可能原因是webservice不通等
	 */
	public String saveEquip(ManDoorBean doorBean,String commPath){
		//房产路径
		String housePath = IbmcConstant.formatDownCommId(commPath);
		//设备id
		String equipId   = "H"+housePath+"_"+doorBean.getId();
		//判断该ID是否存在
		SynMessage eM = this.isExistsEquip(equipId);
		if(eM.isSuccess()){
			if(eM.getCount()>0){
					//存在该设备
					SynMessage uM = this.updateEquip(doorBean,equipId);
					if(!uM.isSuccess()){
						equipId = "-1";
					}
			}else{
				//新增数据
				SynMessage sM = this.addEquip(doorBean,equipId);
				if(!sM.isSuccess()){
					equipId = "-1";
				}
			}
		}
		return equipId;
	}
	
	/**
	 * 删除设备
	 */
	public String deleteEquip(String equipId){
		try {
			DevInfo devInfo = new DevInfo();
			devInfo.setId(equipId);	//设备Id
			DevRemove devRemove = new DevRemove();
			devRemove.setDevInfo(devInfo);
			ServiceStub stock=new ServiceStub();
			DevRemoveResponse res = stock.devRemove(devRemove);
			if(res.getRetval()==0){
				return "1";
			}else{
				LOG.error(res.getErrorDesc()+"--equipId:"+equipId);
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--equipId:"+equipId);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
		} 
		return "-1";
	}
	

	public static void main(String[] args){
		WebEquip in = new WebEquip();
		in.deleteEquip("H11.16.22.23_1");
	}
}
