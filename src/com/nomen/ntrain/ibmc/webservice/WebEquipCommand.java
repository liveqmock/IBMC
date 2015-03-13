package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.util.SynMessage;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.Reset;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ResetResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ResetUnlock;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ResetUnlockResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.RestoreFac;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.RestoreFacResponse;

/**
 * 设备命令（）
 * @author 
 *
 */
public class WebEquipCommand {
	private static final Log LOG = LogFactory.getLog(WebEquipCommand.class);
	
	/**
	 * 重置设备
	 * @param equipId 设备id
	 * @return  1:表示当前命令操作成功，-1表示当前命令操作失败
	 */
	public String resetEquip(String equipId){
		String retval = "-1";
		try {
			ServiceStub stock = new ServiceStub();
			Reset reset = new Reset();
			reset.setEqument_id(equipId);   //设置设备主键id
			reset.setSessionId("");         //设置会话sessionid
			ResetResponse res = stock.reset(reset);
			if(res.getRetval()==0){
				retval = "1";
			}else{
				LOG.error(res.getErrorDesc()+"--equipId:"+equipId);
				LOG.error("这是重置设备命令的错误详细信息:"+res.getErrorDesc());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
		}
		return retval;
	}
	
	/**
	 * 设备恢复出厂设置
	 * @param equipId 设备id
	 * @return  1:表示当前命令操作成功，-1表示当前命令操作失败
	 */
	public String restoreFacEquip(String equipId){
		String retval = "-1";
		try {
			ServiceStub stock = new ServiceStub();
			RestoreFac restoreFac = new RestoreFac();
			restoreFac.setEqument_id(equipId);   //设置设备主键id
			restoreFac.setSessionId("");         //设置会话sessionid
			RestoreFacResponse res = stock.restoreFac(restoreFac);
			if(res.getRetval()==0){
				retval = "1";
			}else{
				LOG.error(res.getErrorDesc()+"--equipId:"+equipId);
				LOG.error("这是恢复出厂设置设备命令的错误详细信息:"+res.getErrorDesc());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
		}
		return retval;
	}
	
	/**
	 * 重置设备门禁锁（该功能需要后台服务支持）
	 * @param equipId 设备id
	 * @return  1:表示当前命令操作成功，-1表示当前命令操作失败
	 */
	public String resetUnlockEquip(String equipId){
		String retval = "-1";
		try {
			ServiceStub stock = new ServiceStub();
			ResetUnlock resetUnlock = new ResetUnlock();
			resetUnlock.setEqument_id(equipId);   //设置设备主键id
			resetUnlock.setSessionId("");         //设置会话sessionid
			ResetUnlockResponse res = stock.resetUnlock(resetUnlock);
			if(res.getRetval()==0){
				retval = "1";
			}else{
				LOG.error(res.getErrorDesc()+"--equipId:"+equipId);
				LOG.error("这是重置设备门禁权限命令的错误详细信息:"+res.getErrorDesc());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
			LOG.error("RemoteException - e"+"--equipId:"+equipId);
		}
		return retval;
	}
	
	public static void main(String[] args){
		WebEquipCommand in = new WebEquipCommand();
		
	}
}
