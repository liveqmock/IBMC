package com.nomen.ntrain.ibmc.Test;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.nomen.ntrain.ibmc.webservice.ServiceStub;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfstring;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoAddResponse;
/**
 * 这张对应的是 dbo.ibmc_equipment_unlock;表
 * @author Administrator
 *
 */
public class WebUnlockPersonRela {
	public static void main(String[] args) {
		WebUnlockPersonRela comm = new WebUnlockPersonRela();
		try {
			ServiceStub stock=new ServiceStub();
			UnlockDevRelaInfo unlockDevRelaInfo = new UnlockDevRelaInfo();
			unlockDevRelaInfo.setUnlockId(10);               //设置门卡上的主键ID[注意需要修改]关联dbo.ibmc_unlock_ic表中的主键ID
			unlockDevRelaInfo.setUnlockKey("2bb4ec21");     //设置门卡的物理序列号
			unlockDevRelaInfo.setUnlockType(1);          //设置门卡的类型
			ArrayOfstring arr = new ArrayOfstring();
			arr.addString("H5_1");                      //设置门卡关联的门口机[注意需要修改]关联dbo.ibmc_community;表中的equ_id
			unlockDevRelaInfo.setDevIdArray(arr);
			UnlockDevRelaInfoAdd unlockDevRelaInfoAdd = new UnlockDevRelaInfoAdd();
			unlockDevRelaInfoAdd.setUnlockDevRelaInfo(unlockDevRelaInfo);
			UnlockDevRelaInfoAddResponse res = stock.unlockDevRelaInfoAdd(unlockDevRelaInfoAdd);
			System.out.println("这是新增的返回值:"+res.getRetval());    //获取返回值
			System.out.println("这是新增的错误详细信息:"+res.getErrorDesc());  //获取错误详细信息
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
