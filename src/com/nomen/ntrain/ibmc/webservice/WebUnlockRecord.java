package com.nomen.ntrain.ibmc.webservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfunlockInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfunlockRecordInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetImage;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetImageResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockRecordCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockRecordRequest;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockRecordResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.SearchRangeType;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockRecordInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockSearchCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockSearchRequest;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockSearchResponse;


public class WebUnlockRecord {
	private static final Log LOG = LogFactory.getLog(WebUnlockRecord.class);
	
	/**
	 * 刷卡记录列表
	 * @param sDate  
	 * @param eDate  
	 * @return
	 */
	public UnlockInfo[] unlockSearch(String sDateStr,String eDateStr){
		try {
			System.out.println(sDateStr);
			System.out.println(eDateStr);
			ServiceStub stock = new ServiceStub();
			UnlockSearchCond unlockSearchCond = new UnlockSearchCond();
			unlockSearchCond.setRelationPersonOrRoom(2);  // 1关联业主  对应ownindex 2 关联房间 对应roomid_personid
			unlockSearchCond.setPersonType(1);  //开锁类型1-IC 2-密码 3-中心 4-网络 5-公有密码 7-指纹
			unlockSearchCond.setUnlockType(1);  //开锁人员所属类型（1-业主、2-物业）
			
			String temp = "yyyy-MM-dd HH:mm:ss";
			//创建 Calendar 对象
		    Calendar calendar = Calendar.getInstance();
		    //初始化 Calendar 对象，但并不必要，除非需要重置时间
			Date sDate = (new SimpleDateFormat(temp)).parse(sDateStr);
		    //calendar.setTime(new Date());
			calendar.setTime(sDate);
			unlockSearchCond.setStartDt(calendar);     //起始时间

		    Calendar calendar2 = Calendar.getInstance();
			Date eDate = (new SimpleDateFormat(temp)).parse(eDateStr);
		    calendar2.setTime(eDate);
			unlockSearchCond.setEndDt(calendar2);     //结束时间

			UnlockSearchRequest req = new UnlockSearchRequest();
			req.setUnlockSearchCond(unlockSearchCond);
			UnlockSearchResponse res = stock.unlockSearch(req);

			System.out.println(res.getRetval());  //获取卡操作的设备信息
			System.out.println(res.getErrorDesc());  //获取卡操作的设备信息
			ArrayOfunlockInfo arrayOfunlockInfo = res.getUnlockInfoArray();
			UnlockInfo[] unlockInfoArr = arrayOfunlockInfo.getUnlockInfo();  //获取查询之后的卡信息
			return unlockInfoArr;
		}catch(ParseException e){
			e.printStackTrace();
			LOG.error("有效期解析错误");
		}  catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 刷卡记录列表2
	 * @param sDate  
	 * @param eDate  
	 * @return
	 */
	public UnlockRecordInfo[] unlockSearch2(String sDateStr,String eDateStr,int unLockId){
		try {
			ServiceStub stock = new ServiceStub();
			GetUnlockRecordCond unlockSearchCond = new GetUnlockRecordCond();
			/**
			//创建 Calendar 对象
		    Calendar calendar = Calendar.getInstance();
		    //初始化 Calendar 对象，但并不必要，除非需要重置时间
			Date sDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(sDateStr);
		    //calendar.setTime(new Date());
			calendar.setTime(sDate);
			unlockSearchCond.setStartTime(calendar);     //起始时间

		    Calendar calendar2 = Calendar.getInstance();
			Date eDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(eDateStr);
		    calendar2.setTime(eDate);
			unlockSearchCond.setEndTime(calendar2);     //结束时间
			**/
			unlockSearchCond.setOrderType(0);   //排序类型 0-asc 1-desc
			
			SearchRangeType searchRangeField = new SearchRangeType();
			searchRangeField.setStart(0);
			searchRangeField.setEnd(20);
			searchRangeField.setOnlyCount(false);
			
			unlockSearchCond.setSearchRange(searchRangeField);
			
			unlockSearchCond.setUnLockId(unLockId);
			unlockSearchCond.setUnLockType(1);
			/****
			        private DateTime startTime; //开始时间
			        private DateTime endTime;//结束时间
			        private int orderType; //排序类型 0-asc 1-desc
			        private SearchRangeType searchRangeField;
			        private int flagStartTime = 1;
			        private int flagEndTime = 1;
			        private int flagOrderType = 1;   
			 */
			unlockSearchCond.setFlagStartTime(1);
			unlockSearchCond.setFlagEndTime(1);
			unlockSearchCond.setFlagOrderType(2);
			unlockSearchCond.setFlagUnlockType(2);
			unlockSearchCond.setFlagUnlockId(2);
			
			GetUnlockRecordRequest req = new GetUnlockRecordRequest();
			req.setGetUnlockSearchCond(unlockSearchCond);
			GetUnlockRecordResponse res = stock.getUnlockRecord(req);
			System.out.println(res.getRetval());  //获取卡操作的设备信息
			System.out.println(res.getErrorDesc());  //获取卡操作的设备信息
			ArrayOfunlockRecordInfo arr = res.getUnlockRecordInfoArray();
			UnlockRecordInfo[] unlockInfoArr = arr.getUnlockRecordInfo();  //获取查询之后的卡信息
			return unlockInfoArr;
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] getImage(String path){
		try {
			ServiceStub stock = new ServiceStub();
			GetImage gI = new GetImage();
			gI.setFilePath(path);
			GetImageResponse  res = stock.getImage(gI);
			if(res.getRetval() == 0){
				DataHandler dHandler =  res.getImageData();
				byte[] b=new byte[dHandler.getInputStream().available()];
				dHandler.getInputStream().read(b);  
				System.out.println(byte2hex(b));
				return b;
			}
		}catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void main(String[] args){
		WebUnlockRecord inx = new WebUnlockRecord();
		// UnlockInfo[] xx = in.unlockSearch("2015-02-03 20:27:19","2015-02-03 20:50:19");
		// for(UnlockInfo x :xx){
		//	 System.out.println(x.getIcId()+"::"+x.getPwdOrSerial()+x.getId());
		// }
		try{
			byte[] b = (inx.getImage("D:/images/ibmcImage/ibmc_unlock/192.168.1.192_1423147231_1.jpg"));
			System.out.println(byte2hex(b));
			 InputStream in =new ByteArrayInputStream(b);
			 File file=new File("D:","a.jpg");//可以是任何图片格式.jpg,.png等
			 FileOutputStream fos=new FileOutputStream(file);
			 byte[] bc =new byte[1024];
			 int nRead =0;
			 while((nRead = in.read(bc)) != -1) {
				 fos.write(bc,0, nRead);
			  }
			 
		   fos.flush();
		          fos.close();
		               in.close(); }catch(Exception e) {
		             e.printStackTrace();
		             }finally{
		             }
		            }
	
	 public static String byte2hex(byte[] b)// 二进制转字符串
	 {
	  StringBuffer sb =new StringBuffer();
	 String stmp ="";
	  for(int n =0; n < b.length; n++) {
	     stmp = Integer.toHexString(b[n] &0XFF);
	         if(stmp.length() ==1) {
	          sb.append("0"+ stmp);
	      }else{
	          sb.append(stmp);
	 
	      }
	         }
	        return sb.toString();
	 }
}
