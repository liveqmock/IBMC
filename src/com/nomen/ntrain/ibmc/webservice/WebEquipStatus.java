package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfdevStatusInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfstring;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevStatusInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevStatusSearch;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevStatusSearchCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.DevStatusSearchResponse;
/**
 * 设备状态查询
 * @author lenovo
 *
 */
public class WebEquipStatus {
	private static final Log LOG = LogFactory.getLog(WebEquipStatus.class);
	
	public class DevStatusBean {
		private String devId;
		private String devStatus;
		
		//以下为set get方法
		public String getDevId() {
			return devId;
		}
		public void setDevId(String devId) {
			this.devId = devId;
		}
		public String getDevStatus() {
			return devStatus;
		}
		public void setDevStatus(String devStatus) {
			this.devStatus = devStatus;
		}
		
		
	}
	
	/**
	 * 设备[门口机]查询
	 */
	public List<DevStatusBean> getDevStatusInfo(String[] devIdArr){
		try {
			ArrayOfstring arrOfDev = new ArrayOfstring();
			if(null != devIdArr){
				for(String devId : devIdArr){
					arrOfDev.addString(devId);
				}
				ServiceStub stock = new ServiceStub();
				DevStatusSearchCond devSearchCond = new DevStatusSearchCond();
				devSearchCond.setDevidArray(arrOfDev);     //设备ID

				DevStatusSearch serach = new DevStatusSearch();
				serach.setDevStatusSearchCond(devSearchCond);
				DevStatusSearchResponse res = stock.devStatusSearch(serach);
				if(res.getRetval() == 0){
					ArrayOfdevStatusInfo arrStatus = res.getDevStatusInfoArray();
					DevStatusInfo[] devStatusInfo = arrStatus.getDevStatusInfo();
					if(null != devStatusInfo){
						DevStatusBean statusBean = null;
						List<DevStatusBean> list = new ArrayList();
						for(DevStatusInfo stInfo : devStatusInfo){
							statusBean = new DevStatusBean();
							statusBean.setDevId(stInfo.getDevId());
							statusBean.setDevStatus(stInfo.getDevStatus()+"");
							list.add(statusBean);
						}
						return list;
					}
				}else{
					LOG.error(res.getErrorDesc()+"--doorId:"+devIdArr);
				}
			}
			
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--doorId:"+devIdArr);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--doorId:"+devIdArr);
		}
		return null;
	}
	

	public static void main(String[] args){
		WebEquipStatus in = new WebEquipStatus();
		String[] ar = new String[]{"H11.16.22.23_21","xxx1"};
		List<DevStatusBean> list = in.getDevStatusInfo(ar);
		for(DevStatusBean b :list){
			System.out.println(b.getDevId()+":"+b.getDevStatus());
		}
	}
}
