package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.bean.ManCardBean;
import com.nomen.ntrain.ibmc.enums.SynEnums;
import com.nomen.ntrain.ibmc.util.SynMessage;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfstring;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockDevList;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockDevListResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoRemove;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoRemoveResponse;

public class WebUnlockDevRela {
	private static final Log LOG = LogFactory.getLog(WebUnlockDevRela.class);
	
	/**
	 * 添加设备门卡关联列表
	 * @param cardBean
	 * @param roomIdPath
	 * @return
	 */
	public SynMessage addUnlockDevRelaInfo(String cardNo,int unlockId,String newDevId){
		SynMessage msg = new SynMessage();
		msg.setSuccess(false);
		try {
			ServiceStub stock=new ServiceStub();
			
			UnlockDevRelaInfo devInfo = new UnlockDevRelaInfo();
			devInfo.setUnlockId(unlockId);
			devInfo.setUnlockKey(cardNo);
			devInfo.setUnlockType(1);
			ArrayOfstring arr = new ArrayOfstring();
			if(newDevId != null && !"".equals(newDevId)){
				arr.addString(newDevId);
			}
			devInfo.setDevIdArray(arr);
			UnlockDevRelaInfoAdd devAdd = new UnlockDevRelaInfoAdd();
			devAdd.setUnlockDevRelaInfo(devInfo);
			UnlockDevRelaInfoAddResponse res = stock.unlockDevRelaInfoAdd(devAdd);
			if(res.getRetval()==0){
				msg.setSuccess(true);
			}else{
				LOG.error(res.getErrorDesc()+"--cardNo:"+cardNo);
				msg.setMessage("这是查询的错误详细信息:"+res.getErrorDesc());
			}
		}catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--cardNo:"+cardNo);
			msg.setMessage("AxisFault - e"+"--cardNo:"+cardNo);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--cardNo:"+cardNo);
			msg.setMessage("RemoteException - e"+"--cardNo:"+cardNo);
		} 
		return msg;
	}
	
	/**
	 * 修改设备门卡关联列表 --- 尚未使用[待修改]
	 */
	public void updateUnlockDevRelaInfo(ManCardBean cardBean,String roomIdPath){
		try {
			ServiceStub stock=new ServiceStub();
			
			UnlockDevRelaInfo devInfo = new UnlockDevRelaInfo();
			devInfo.setUnlockId(5);
			devInfo.setUnlockKey("2bb4ec21");
			devInfo.setUnlockType(1);
			ArrayOfstring arr = new ArrayOfstring();
			arr.addString("H2_1");
			devInfo.setDevIdArray(arr);
			
			//System.out.println("这是新增的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 通过门卡id查询出对应的设别列表
	 */
	public String[] getUnlockDevArrByUnLockId(int unLockId){
		try {
			ServiceStub stock = new ServiceStub();
			GetUnlockDevList getUnlockDevList = new GetUnlockDevList();
			getUnlockDevList.setUnlockId(unLockId); //设置门卡上的主键ID[注意需要修改]关联dbo.ibmc_unlock_ic表中的主键ID
			getUnlockDevList.setUnlockType(1);      //设置门卡的类型1
			getUnlockDevList.setSessionId("");      //设置sessionID      
			GetUnlockDevListResponse res = stock.getUnlockDevList(getUnlockDevList);
			if(res.getRetval() == 0){
				ArrayOfstring arrOf = res.getDevIdArray();                  //根据卡查询出来的门口机设备ID数组
				String[] devIdArr = arrOf.getString();    //H打头的记录
				return devIdArr;
			}
			System.out.println("这是查询的返回值:"+res.getRetval());         //获取返回值
			System.out.println("这是查询的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加/修改设备门卡关联列表
	 * @param cardBean
	 * @param roomIdPath
	 * @return
	 */
	public String saveUnlockDevRelaInfo(String cardNo,int unlockId,String devId){
		//获取该卡对应已设置的设备列表
		String[] okDevIdArr = this.getUnlockDevArrByUnLockId(unlockId);
		//判断新的门口机记录是否存在,若不存在则新增
		boolean exists = false;
		if(okDevIdArr != null){
			for(int i=0;i<okDevIdArr.length;i++){
				if(!exists && okDevIdArr[i].equals(devId)){
					//已存在
					exists = true;
				}
			}
			if(!exists){
				//新增
				SynMessage msg = this.addUnlockDevRelaInfo(cardNo,unlockId,devId);
				if(msg.isSuccess()){
					return "1";
				}
			}
		}else{
			//新增
			SynMessage msg = this.addUnlockDevRelaInfo(cardNo,unlockId,devId);
			if(msg.isSuccess()){
				return "1";
			}
		}
		return "-1";
	}
	
	/**
	 * 删除卡同门口机关联数据[该卡对应的所有门口机]
	 */
	public String deleteUnlockDevRelaInfoByUnlockId(int unLockId){
		try {
			ServiceStub stock=new ServiceStub();
			UnlockDevRelaInfo devInfo = new UnlockDevRelaInfo();
			devInfo.setUnlockId(unLockId);
			devInfo.setUnlockType(1);
			
			//获取该卡对应已设置的设备列表
			ArrayOfstring arr = new ArrayOfstring();
			String[] okDevIdArr = this.getUnlockDevArrByUnLockId(unLockId);
			if(okDevIdArr != null){
				for(int i=0;i<okDevIdArr.length;i++){
					arr.addString(okDevIdArr[i]);
				}
			}
			devInfo.setDevIdArray(arr);
			
			UnlockDevRelaInfoRemove devRemove = new UnlockDevRelaInfoRemove();
			devRemove.setUnlockDevRelaInfo(devInfo);
			UnlockDevRelaInfoRemoveResponse res = stock.unlockDevRelaInfoRemove(devRemove);
			if(res.getRetval()==0){
				return "1";
			}else{
				LOG.error(res.getErrorDesc()+"--unLockId:"+unLockId);
			}
		}catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--unLockId:"+unLockId);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--unLockId:"+unLockId);
		} 
		return "-1";
	}
	

	/**
	 * 删除卡对应特定门口机关联数据[特定门口机]
	 */
	public String deleteUnlockDevRelaInfoByUnlockId(int unLockId,String[] delDevArr){
		try {
			ServiceStub stock=new ServiceStub();
			UnlockDevRelaInfo devInfo = new UnlockDevRelaInfo();
			devInfo.setUnlockId(unLockId);
			devInfo.setUnlockType(1);
			
			//获取该卡对应已设置的设备列表
			ArrayOfstring arr = new ArrayOfstring();
			if(delDevArr != null){
				for(int i=0;i<delDevArr.length;i++){
					arr.addString(delDevArr[i]);
				}
			}
			devInfo.setDevIdArray(arr);
			
			UnlockDevRelaInfoRemove devRemove = new UnlockDevRelaInfoRemove();
			devRemove.setUnlockDevRelaInfo(devInfo);
			UnlockDevRelaInfoRemoveResponse res = stock.unlockDevRelaInfoRemove(devRemove);
			if(res.getRetval()==0){
				return "1";
			}else{
				LOG.error(res.getErrorDesc()+"--unLockId:"+unLockId);
			}
		}catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--unLockId:"+unLockId);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--unLockId:"+unLockId);
		} 
		return "-1";
	}
	public static void main(String[] args){
		WebUnlockDevRela in = new WebUnlockDevRela();
		in.deleteUnlockDevRelaInfoByUnlockId(69);
	}
}
