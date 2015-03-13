package com.nomen.ntrain.ibmc.webservice;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.util.SynMessage;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAdd;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildModify;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildModifyResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildRemove;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildRemoveResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearch;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchResponse;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.SearchRangeType;
/**
 * 市、区、村、房屋、房间管理
 * @author lenovo
 *
 */
public class WebCommunity {
	private static final Log LOG = LogFactory.getLog(WebCommunity.class);
	
	/**
	 * 修改小区节点
	 */
	public SynMessage updateBuild(String downId,SysCommunityBean commBean){
		SynMessage msg = new SynMessage();
		try {
			BuildInfo updateBuildInfo = new BuildInfo();
			updateBuildInfo.setBuildId(downId);   //设置小区ID
			updateBuildInfo.setName(commBean.getCommname());   //设置地址全称
			updateBuildInfo.setAddress("");     //设置本节点地址
			updateBuildInfo.setSegment("");   //设置网段
			updateBuildInfo.setMask("");      //设置子网掩码
			updateBuildInfo.setGateway("");   //设置网关

			
			ServiceStub stock = new ServiceStub();
			BuildModify buildModify54 = new BuildModify();
			buildModify54.setBuildInfo(updateBuildInfo); //小区信息
			buildModify54.setSessionId("");        //设置会话ID
			BuildModifyResponse buildModifyRes = stock.buildModify(buildModify54);
			if(buildModifyRes.getRetval()==0){
				msg.setSuccess(true);
			}else{
				msg.setSuccess(false);
				LOG.error(buildModifyRes.getErrorDesc()+"--downId:"+downId);
				msg.setMessage("这是查询的错误详细信息:"+buildModifyRes.getErrorDesc());
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--downId:"+downId);
			msg.setMessage("AxisFault - e"+"--downId:"+downId);
		} catch (RemoteException e) {
			e.printStackTrace();
			msg.setSuccess(false);
			LOG.error("RemoteException - e"+"--downId:"+downId);
			msg.setMessage("RemoteException - e"+"--downId:"+downId);
		} 
		return msg;
	}
	
	/**
	 * 添加小区节点
	 */
	public SynMessage addBuild(String downId,SysCommunityBean commBean){
		SynMessage msg = new SynMessage();
		try {
			BuildInfo buildInfo = new BuildInfo();
			buildInfo.setBuildId(downId);   //设置小区ID
			buildInfo.setName(commBean.getCommname());//设置地址全称
			buildInfo.setAddress(commBean.getCommname());      //设置本节点地址
			buildInfo.setSegment("");      //设置网段
			buildInfo.setMask("");         //设置子网掩码
			buildInfo.setGateway("");      //设置网关
			
			ServiceStub stock=new ServiceStub();
			BuildAdd buildAdd = new BuildAdd();
			buildAdd.setBuildInfo(buildInfo);   //小区信息
			buildAdd.setSessionId("");      //设置会话ID
			BuildAddResponse buildRes = stock.buildAdd(buildAdd);   //获取返回结果集
			if(buildRes.getRetval()==0){
				msg.setSuccess(true);
			}else{
				msg.setSuccess(false);
				LOG.error(buildRes.getErrorDesc()+"--downId:"+downId);
				msg.setMessage("这是查询的错误详细信息:"+buildRes.getErrorDesc());
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--downId:"+downId);
			msg.setMessage("AxisFault - e"+"--downId:"+downId);
		} catch (RemoteException e) {
			e.printStackTrace();
			msg.setSuccess(false);
			LOG.error("RemoteException - e"+"--downId:"+downId);
			msg.setMessage("RemoteException - e"+"--downId:"+downId);
		} 
		return msg;
	}
	
	/**
	 * 查询小区节点是否存在
	 */
	public SynMessage isExistsBulid(String downId){
		SynMessage msg = new SynMessage();
		try {
			ServiceStub stock = new ServiceStub();
			BuildSearch buildSearch = new BuildSearch();
			BuildSearchCond buildSearchCond = new BuildSearchCond();  //查询的条件类型
			buildSearchCond.setBuildId(downId);   //小区节点ID
			buildSearchCond.setFlagBuildId(2);  //小区ID查询标识 1-不加入条件查询 2-等于条件 3-模糊条件
			SearchRangeType searchRangeType = new SearchRangeType(); //查询记录范围
			searchRangeType.setStart(0);   //开锁记录索引
			searchRangeType.setEnd(1);     //结束记录索引
			searchRangeType.setOnlyCount(true);  //是否只显示条数
			buildSearchCond.setSearchRange(searchRangeType);
			buildSearch.setBuildSearchCond(buildSearchCond);
			BuildSearchResponse buildSearchRes = stock.buildSearch(buildSearch);
			
			if(buildSearchRes.getCount()>0){
				msg.setSuccess(true);
				msg.setCount(buildSearchRes.getCount());
			}else{
				msg.setSuccess(true);
				msg.setCount(0);
				LOG.error(buildSearchRes.getErrorDesc()+"--downId:"+downId);
				msg.setMessage("这是查询的错误详细信息:"+buildSearchRes.getErrorDesc());
			}
		} catch (AxisFault e) {
			e.printStackTrace();
			msg.setSuccess(false);
			LOG.error("AxisFault - e"+"--downId:"+downId);
			msg.setMessage("AxisFault - e"+"--downId:"+downId);
		} catch (RemoteException e) {
			e.printStackTrace();
			msg.setSuccess(false);
			LOG.error("RemoteException - e"+"--downId:"+downId);
			msg.setMessage("RemoteException - e"+"--downId:"+downId);
		}
		return msg;
	}
	
	

	/**
	 * 新增/修改房屋/社区/区市/等
	 * 若返回-1 则表示操作失败,可能原因是webservice不通等
	 */
	public String saveBulid(SysCommunityBean commBean){
		//构建下发的ID
		String downId = IbmcConstant.formatDownCommId(commBean.getCommpath());
		//判断该ID是否存在
		SynMessage eM = this.isExistsBulid(downId);
		if(eM.isSuccess()){
			if(eM.getCount()>0){
					//存在房产数据,需要判断房产的业主信息是否存在
					SynMessage uM = this.updateBuild(downId,commBean);
					if(!uM.isSuccess()){
						downId = "-1";
					}
					//人员
			}else{
				//新增数据
				SynMessage sM = this.addBuild(downId,commBean);
				if(IbmcConstant.COMM_LEV_HOUSE.equals(commBean.getCommlev())){
					//存在房产数据,需要判断房产的业主信息是否存在
					//判断人员表中是否存在该人员
				}
				if(!sM.isSuccess()){
					downId = "-1";
				}
			}
		}else{
			downId = "-1";
		}
		return downId;
	}
	
	/**
	 * 删除小区节点
	 */
	public String deleteBuild(String commPath){
		//构建下发的ID
		String downId = IbmcConstant.formatDownCommId(commPath);
		try {
			BuildInfo buildInfo = new BuildInfo();
			buildInfo.setBuildId(downId);   		//设置小区ID
			ServiceStub stock=new ServiceStub();
			BuildRemove buildRemove = new BuildRemove();
			buildRemove.setBuildInfo(buildInfo);    //小区信息
			buildRemove.setSessionId("");      		//设置会话ID
			BuildRemoveResponse buildRes = stock.buildRemove(buildRemove);   //获取返回结果集
			if(buildRes.getRetval()==0){
				return "1";
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			LOG.error("AxisFault - e"+"--downId:"+downId);
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.error("RemoteException - e"+"--downId:"+downId);
		} 
		return "-1";
	}

	public static void main(String[] args){
		WebCommunity in = new WebCommunity();
		SysCommunityBean commBean = new SysCommunityBean();
		//in.reMoveBuild(commBean);
	}
}
