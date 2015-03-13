package com.nomen.ntrain.ibmc.Test;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.nomen.ntrain.ibmc.webservice.ServiceStub;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfbuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildModify;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildModifyResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearch;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.SearchRangeType;

/**
 * 这张对应的是 dbo.ibmc_community;表
 * @author Administrator
 *
 */
public class WebCommunity {
	public static void main(String[] args) {
		WebCommunity comm = new WebCommunity();
		
		BuildInfo addBuildInfo = new BuildInfo();
		/**
		 * 新增市
		 * 
		 */
		addBuildInfo.setBuildId("5");   //设置市ID[注意需要全部配置好]
		addBuildInfo.setName("福州市");   //设置地址全称
		addBuildInfo.setAddress("福州");     //设置本节点地址
		addBuildInfo.setSegment("");   //设置网段
		addBuildInfo.setMask("");      //设置子网掩码
		addBuildInfo.setGateway("");      //设置网关
		//comm.buildAdd(addBuildInfo);
		
		/**
		 * 新增区
		 * 
		 */
		addBuildInfo.setBuildId("5.1");   //设置区ID[注意需要全部配置好]
		addBuildInfo.setName("晋安区");   //设置地址全称
		addBuildInfo.setAddress("晋安区");     //设置本节点地址
		addBuildInfo.setSegment("");   //设置网段
		addBuildInfo.setMask("");      //设置子网掩码
		addBuildInfo.setGateway("");      //设置网关
		//comm.buildAdd(addBuildInfo);
		
		/**
		 * 新增社区,村
		 * 
		 */
		addBuildInfo.setBuildId("5.1.1");   //设置社区ID,村ID[注意需要全部配置好]
		addBuildInfo.setName("莲花社区");   //设置地址全称
		addBuildInfo.setAddress("莲花社区");     //设置本节点地址
		addBuildInfo.setSegment("");   //设置网段
		addBuildInfo.setMask("");      //设置子网掩码
		addBuildInfo.setGateway("");      //设置网关
		//comm.buildAdd(addBuildInfo);
		
		/**
		 * 新增房产
		 * 
		 */
		addBuildInfo.setBuildId("5.1.1.1");   //设置社区ID,村ID[注意需要全部配置好]
		addBuildInfo.setName("xxx路xx楼11");   //设置地址全称
		addBuildInfo.setAddress("莲花社区xxx路xx楼11");     //设置本节点地址
		addBuildInfo.setSegment("192.168.1.1");   //设置网段
		addBuildInfo.setMask("255.255.255.0");      //设置子网掩码
		addBuildInfo.setGateway("192.168.1.1");      //设置网关
		//comm.buildAdd(addBuildInfo);
		
		/**
		 * 新增房间1
		 * 
		 */
		addBuildInfo.setBuildId("5.1.1.1.1");   //设置房间ID[注意需要全部配置好]
		addBuildInfo.setName("房间11");   //设置地址全称
		addBuildInfo.setAddress("xxx路xx楼11房间11");     //设置本节点地址
		addBuildInfo.setSegment("");   //设置网段
		addBuildInfo.setMask("");      //设置子网掩码
		addBuildInfo.setGateway("");      //设置网关
		//comm.buildAdd(addBuildInfo);
		
		/**
		 * 新增房间2
		 * 
		 */
		addBuildInfo.setBuildId("5.1.1.1.2");   //设置房间ID[注意需要全部配置好]
		addBuildInfo.setName("房间22");   //设置地址全称
		addBuildInfo.setAddress("xxx路xx楼11房间22");     //设置本节点地址
		addBuildInfo.setSegment("");   //设置网段
		addBuildInfo.setMask("");      //设置子网掩码
		addBuildInfo.setGateway("");      //设置网关
		//comm.buildAdd(addBuildInfo);
		
		/**
		 * 修改BuildInfo信息
		 * 修改中 BuildId,Name,Address必须有 以 buildId 为主键
		 * 
		 */
		BuildInfo updateBuildInfo = new BuildInfo();
		updateBuildInfo.setBuildId("5");   //设置小区ID
		updateBuildInfo.setName("01区.01栋.02单元-102房wwwww");   //设置地址全称
		updateBuildInfo.setAddress("101eeee");     //设置本节点地址
		updateBuildInfo.setSegment("192.168.1");   //设置网段
		updateBuildInfo.setMask("255.255.255.0");      //设置子网掩码
		updateBuildInfo.setGateway("192.168.1");      //设置网关
		//comm.buildUpdate(updateBuildInfo);
		
		//查询
		//comm.buildSearch();
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
	
	/**
	 * 修改小区节点
	 */
	public void buildUpdate(BuildInfo buildInfo){
		try {
			ServiceStub stock = new ServiceStub();
			BuildModify buildModify54 = new BuildModify();
			buildModify54.setBuildInfo(buildInfo);    //小区信息
			buildModify54.setSessionId("");     //设置会话ID
			BuildModifyResponse buildModifyRes = stock.buildModify(buildModify54);
			System.out.println("这是修改的返回值:"+buildModifyRes.getRetval());    //获取返回值
			System.out.println("这是修改的错误详细信息:"+buildModifyRes.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询小区节点
	 */
	public void buildSearch(){
		try {
			ServiceStub stock = new ServiceStub();
			BuildSearch buildSearch = new BuildSearch();
			BuildSearchCond buildSearchCond = new BuildSearchCond();  //查询的条件类型
			buildSearchCond.setBuildId("");   //小区节点ID
			int flagBuildId = 1;
			buildSearchCond.setFlagBuildId(flagBuildId);  //小区ID查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			SearchRangeType searchRangeType = new SearchRangeType(); //查询记录范围
			int start = 0;
			int end = 100;
			boolean onlyCount = false;
			searchRangeType.setStart(start);   //开锁记录索引
			searchRangeType.setEnd(end);     //结束记录索引
			searchRangeType.setOnlyCount(onlyCount);  //是否只显示条数
			buildSearchCond.setSearchRange(searchRangeType);
			buildSearch.setBuildSearchCond(buildSearchCond);
			BuildSearchResponse buildSearchRes = stock.buildSearch(buildSearch);
			ArrayOfbuildInfo arrayBuildInfo = buildSearchRes.getBuildInfoArray();
			BuildInfo[] buildInfo = arrayBuildInfo.getBuildInfo();
			for(BuildInfo r :buildInfo){
				System.out.println("获取小区ID："+r.getBuildId());  //获取小区ID
				System.out.println("获取地址全称："+r.getName());  //获取地址全称
				System.out.println("获取本节点地址："+r.getAddress());  //获取本节点地址
				System.out.println("获取网段："+r.getSegment());  //获取网段
				System.out.println("获取子网掩码："+r.getMask());  //获取子网掩码
				System.out.println("获取网关："+r.getGateway());  //获取网关
			}
			System.out.println("查询总的记录数:"+buildSearchRes.getCount());  //获取结果集合总数
			System.out.println("这是查询的错误详细信息:"+buildSearchRes.getErrorDesc());  //获取错误详细信息
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
