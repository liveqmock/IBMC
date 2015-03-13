package com.nomen.ntrain.ibmc.webservice;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.ArrayOfbuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchCond;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.SearchRangeType;

public class wsTest {
	public static void XXX(){
		   try { 
			   ServiceStub stock=new ServiceStub(); 
			   com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearch search=new com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearch();
			   BuildSearchCond param = new BuildSearchCond();
			   param.setBuildId("1");
			   param.setFlagBuildId(1);
			   SearchRangeType searchRang = new SearchRangeType();
			   searchRang.setOnlyCount(false);
			   searchRang.setEnd(100);
			   searchRang.setStart(0);
			   param.setSearchRange(searchRang);
			   search.setBuildSearchCond(param);
			   com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchResponse reson = stock.buildSearch(search);
			   System.out.println(reson.getErrorDesc());
			   System.out.println(reson.getBuildInfoArray());
			   BuildInfo[] infoArr =  reson.getBuildInfoArray().getBuildInfo();
			   System.out.println(infoArr.length);
			   for(BuildInfo i : infoArr){
				   System.out.println(i.getAddress()+":"+i.getGateway()); 
			   }
	        } catch (Exception remoteException) { 
	            remoteException.printStackTrace(); 
	        } 
	}
	
	public static void main (String[] args){
		wsTest ws = new wsTest();
		ws.XXX();
	}

}


