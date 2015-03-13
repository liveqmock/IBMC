package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.bean.ManCardBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfunlockPersonRelaInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockPersonRelaInfoList;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockPersonRelaInfoListResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoModify;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoModifyResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoRemove;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoRemoveResponse;
/**
 * 人员发卡管理
 * @author lenovo
 *
 */
public class WebUnlockPersonInfo {
	private static final Log LOG = LogFactory.getLog(WebUnlockPersonInfo.class);


	/**
	 * 添加人员卡信息
	 */
	public String addUnlockPersonRelaInfo(ManCardBean cardBean,String roomPath){
		//默认返回的卡ID
		String  rtnUnlockId = "-1";         
		try {
			ServiceStub stock=new ServiceStub();
			int unLockType = 1;
			boolean isOwner = true;
			int ownerIndex = 0;
			//判断该门卡是否已存在
			UnlockPersonRelaInfo[] relaInfoArr = this.getUnlockIdArrByPesonId(roomPath);
			boolean isExists = false;
			if(null != relaInfoArr){
				for(UnlockPersonRelaInfo info : relaInfoArr){
					if((info.getIsOwner() == isOwner) 
							&& info.getUnlockType()== unLockType
							&& info.getOwnerIndex()==ownerIndex
							&& info.getPersonId().equals(roomPath)
							&& info.getUnlockKey().equals(cardBean.getCardno())){
						isExists = true;
						return info.getUnlockId()+"";
					}
				}
			}
			
			UnlockPersonRelaInfo relaInfo = new UnlockPersonRelaInfo();
			//创建 Calendar 对象
		    Calendar calendar = Calendar.getInstance();
		    // 初始化 Calendar 对象，但并不必要，除非需要重置时间
		    calendar.setTime(new Date());
		    //relaInfo.setUnlockId(); //门禁卡ID---系统自动生成
		    relaInfo.setIsOwner(isOwner);              		//是否业主
		    relaInfo.setPersonId(roomPath);            	    //门禁卡持有者
		    relaInfo.setUnlockType(unLockType);             //门卡类型 
		    relaInfo.setUnlockKey(cardBean.getCardno()); 	//门卡序列号
		    calendar.setTime(new Date());
		    relaInfo.setUpdateTime(calendar);       		//更新时间
		    relaInfo.setUpdateUserid(1);            		//更新者[暂时不存储]
		    String endDate = cardBean.getEnddate(); 
		    if(endDate!= null && !"".equals(endDate)){
				Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(endDate);
			    calendar.setTime(date);
			    relaInfo.setExpiredTime(calendar);      	//门卡有效期
		    }else{
				Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse("9999-12-30");
			    calendar.setTime(date);
			    relaInfo.setExpiredTime(calendar);      	//门卡有效期
		    }
		    relaInfo.setOwnerIndex(ownerIndex);             //业主ID[暂时不存储]
			UnlockPersonRelaInfoAdd devAdd = new UnlockPersonRelaInfoAdd();
			devAdd.setUnlockPersonRelaInfo(relaInfo);
			UnlockPersonRelaInfoAddResponse res = stock.unlockPersonRelaInfoAdd(devAdd);
			if(res.getRetval()==0){
				rtnUnlockId = res.getUnlockId()+"";
			}else{
				LOG.error("这是查询的错误详细信息:"+res.getErrorDesc()+"--cardno:"+cardBean.getCardno());
			}
		}catch(ParseException e){
			e.printStackTrace();
			LOG.error("有效期解析错误"+"--cardno:"+cardBean.getCardno());
		}  catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--cardno:"+cardBean.getCardno());
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--cardno:"+cardBean.getCardno());
		} 
		return rtnUnlockId;
	}
	
	/**
	 * 修改人员卡信息
	 */
	public String updateUnlockPersonRelaInfo(ManCardBean cardBean,String roomPath){
		String rtnUnlockId = cardBean.getUnlockid();   //默认返回的卡ID
		try {
			ServiceStub stock=new ServiceStub();

			//创建 Calendar 对象
		    Calendar calendar = Calendar.getInstance();
		    // 初始化 Calendar 对象，但并不必要，除非需要重置时间
			UnlockPersonRelaInfo relaInfo = new UnlockPersonRelaInfo();
		    relaInfo.setUnlockId(Integer.parseInt(rtnUnlockId)); //webService返回的ID
		    relaInfo.setIsOwner(true);              //是否业主
		    relaInfo.setPersonId(roomPath);       //门禁卡持有者
		    relaInfo.setUnlockType(1);              //门卡类型 
		    relaInfo.setUnlockKey(cardBean.getCardno());      		 //门卡序列号
		    calendar.setTime(new Date());
		    relaInfo.setUpdateTime(calendar);       //更新时间
		    relaInfo.setUpdateUserid(1);            //更新者[暂时不存储]
		    String endDate = cardBean.getEnddate();
		    if(endDate!= null && !"".equals(endDate)){
				Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(endDate);
			    calendar.setTime(date);
			    relaInfo.setExpiredTime(calendar);      //门卡有效期
		    }else{
				Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse("9999-12-30");
			    calendar.setTime(date);
			    relaInfo.setExpiredTime(calendar);      //门卡有效期
		    }
		    relaInfo.setOwnerIndex(0);                  //业主ID[暂时不存储]
			
			UnlockPersonRelaInfoModify modify = new UnlockPersonRelaInfoModify();
			modify.setUnlockPersonRelaInfo(relaInfo);
			UnlockPersonRelaInfoModifyResponse res = stock.unlockPersonRelaInfoModify(modify);
			if(res.getRetval()==0){
			}else{
				LOG.error("这是查询的错误详细信息:"+res.getErrorDesc()+"--cardno:"+cardBean.getCardno());
			}
		}catch(ParseException e){
			e.printStackTrace();
			LOG.error("有效期解析错误"+"--cardno:"+cardBean.getCardno());
		} 
		catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--cardno:"+cardBean.getCardno());
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--cardno:"+cardBean.getCardno());
		} 
		return rtnUnlockId;
	}

	/**
	 * 根据 personId查询其所持有的门卡ID（非卡序列号）
	 * 返回  webservice 返回的门卡主记录ID
	 */
	public UnlockPersonRelaInfo[] getUnlockIdArrByPesonId(String roomPath){
		try {
			ServiceStub stock = new ServiceStub();
			GetUnlockPersonRelaInfoList getUnlockPersonRelaInfoList = new GetUnlockPersonRelaInfoList();
			getUnlockPersonRelaInfoList.setPersonId(roomPath);    //设置业主的房间ID[注意需要修改]关联dbo.ibmc_community
			getUnlockPersonRelaInfoList.setSessionId("");           //设置sessionID
			GetUnlockPersonRelaInfoListResponse res = stock.getUnlockPersonRelaInfoList(getUnlockPersonRelaInfoList);
			ArrayOfunlockPersonRelaInfo arrRelaInfo = res.getUnlockPersonRelaInfoArray();   //获取查询返回的结果
			UnlockPersonRelaInfo[] arr = arrRelaInfo.getUnlockPersonRelaInfo();          //获取查询返回的结果数组
			return arr;
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 新增、修改门卡
	 * [返回webservice插入的门卡表ID]
	 * roomIdPath ：房屋路径/房间路径
	 */
	public String saveUnlockPersonInfo(ManCardBean cardBean,String roomIdPath){
		//构建下发的ID
		String roomPath = IbmcConstant.formatDownCommId(roomIdPath);
		String oldUnlockId = cardBean.getUnlockid();
		String newUnlockId = "";
		if(oldUnlockId == null || "".equals(oldUnlockId)){
			//新增
			newUnlockId = this.addUnlockPersonRelaInfo(cardBean,roomPath);
		}else{
			//修改
			newUnlockId =this.updateUnlockPersonRelaInfo(cardBean,roomPath);
		}
		return newUnlockId;
	}
	
	
	
	/**
	 * 删除门卡操作
	 *  1、删除门卡对应的刷卡记录
	 *  2、删除门卡本身
	 */
	public String deleteUnlockPersonInfoByUnlockId(int unLockId){
		try {
			ServiceStub stock=new ServiceStub();
		    // 初始化 Calendar 对象，但并不必要，除非需要重置时间
			UnlockPersonRelaInfo relaInfo = new UnlockPersonRelaInfo();
		    relaInfo.setUnlockId(unLockId); //webService返回的ID
		    relaInfo.setUnlockType(1);
		    UnlockPersonRelaInfoRemove remove = new UnlockPersonRelaInfoRemove();
		    remove.setUnlockPersonRelaInfo(relaInfo);
			UnlockPersonRelaInfoRemoveResponse res = stock.unlockPersonRelaInfoRemove(remove);
			if(res.getRetval()==0){
				return "1";
			}else{
				LOG.error("这是查询的错误详细信息:"+res.getErrorDesc()+"--unLockId:"+unLockId);
			}
		}
		catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--unLockId:"+unLockId);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--unLockId:"+unLockId);
		} 
		return "-1";
	}
	
	public static void main(String[] args){
		WebUnlockPersonInfo in = new WebUnlockPersonInfo();
		//in.getUnlockPersonRelaInfoList("2.1");
		in.deleteUnlockPersonInfoByUnlockId(69);
	}
}
