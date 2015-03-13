package com.nomen.ntrain.ibmc.service.implement;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManDoorHouseDAO;
import com.nomen.ntrain.ibmc.dao.SysCommunityDAO;
import com.nomen.ntrain.ibmc.dao.SysCommunityTempDAO;
import com.nomen.ntrain.ibmc.excel.IbmcExcelOutForJxlImpl;
import com.nomen.ntrain.ibmc.service.ManHouseService;
import com.nomen.ntrain.util.PubFunc;

public class ManHouseServiceImpl extends BaseServiceImpl implements ManHouseService {
	
	private SysCommunityDAO sysCommunityDAO;
	private ManDoorHouseDAO manDoorHouseDAO;
	private SysCommunityTempDAO sysCommunityTempDAO;
	
	
	public void saveManHouseExpExcel(Map map, HttpServletResponse response) {
		List<SysCommunityBean> dataList = this.sysCommunityDAO.findHouseListByScope(map);
		IbmcExcelOutForJxlImpl ibmcExp = new IbmcExcelOutForJxlImpl();
		ibmcExp.expManHouse(dataList, response);
	}

	public String findHouseLinkDoorAndCardCount(String houseId) {
		return this.manDoorHouseDAO.findHouseLinkDoorAndCardCount(houseId);
	}

	public String saveManHouseImpExcel(Map map) throws Exception {
		//1、验证其有效性
		String parentid = (String) map.get("parentid"); //父亲parentid
		String optuserid = (String) map.get("optuserid"); //操作人
		String[] saveNameArr = (String[]) map.get("saveNameArr"); //保存文件名
		String fileFolder = (String) map.get("fileFolder"); //保存路径
		String realFilePath = fileFolder + "//" + saveNameArr[0];
		//清除临时数据
		Map iMap = new HashMap();
		iMap.put("parentid", parentid);
		iMap.put("optuserid", optuserid);
		this.sysCommunityTempDAO.deleteSysCommTempData(iMap);
		//1、导入Excel到临时表中
		this.parseManHouseExcel(realFilePath,optuserid,parentid);
		String rtn = this.validManHouseTempData(optuserid, parentid);
		return rtn;
	}
	
	//对导入的Excel进行解析
	private String parseManHouseExcel(String realFilePath,String optuserid,String firparentid){
		String impflag = "";
		String secparentid = "";    //房产id[房间parentid]
		String sequStr = "";     //获取序列号中的值
		String roomStr = "";     //获取房产/房间列中的值
		PubFunc func =  new PubFunc();
		File f = new File(realFilePath);
		try {
			jxl.Workbook rwb = Workbook.getWorkbook(f);
			Sheet st = rwb.getSheet("Sheet1");
	        if(st!=null){
	        	SysCommunityBean sysCommunityBean = new SysCommunityBean();
	        	sysCommunityBean.setOptuserid(optuserid);
				//getRows()获取Sheet表中所包含的总行数
				int count = st.getRows();
				int i = 2;  //从excel中的第三行开始读取数据
				while (i < count) {
					sequStr = st.getCell(0, i).getContents().trim();   //获取序列号中的值
					roomStr = st.getCell(5, i).getContents().trim();   //获取房产,房间中的值
					if(func.IsEmpty(sequStr) && func.IsEmpty(roomStr)){
						break;    //跳出循环
					}
					//循环Excel中的列
					for (int j = 0,colNum=st.getColumns(); j < colNum; j++) {       //getColumns()获取Sheet表中所包含的总列数
						
						String data  = st.getCell(j, i).getContents().trim();   //获取（第i行j列）中单元格中的数据
						String title  = st.getCell(j, 1).getContents().trim();  //获取（第1行j列）中单元格中的数据
						
						//房产类型编码
						if(title.equals("房产类型编码")){
							sysCommunityBean.setCommtype(data);   //类型（1 独立业主 2共有业主）
						}
						//房产业主
						if(title.equals("房产业主")){
							sysCommunityBean.setOwnername(data);  //房产业主姓名
						}
						//业主身份证号
						if(title.equals("业主身份证号")){
							sysCommunityBean.setOwneridcard(data);  //房产业主身份证号码
						}
						//联系电话
						if(title.equals("联系电话")){
							//房产业主联系电话
							sysCommunityBean.setTelephone(data);  //房产业主联系电话
						}
						//房产地址/房间名称
						if(title.equals("房产地址/房间名称")){
							sysCommunityBean.setCommname(data);  //房产地址/房间名称
						}
						//备注
						if(title.equals("备注")){
							sysCommunityBean.setRemark(data);  //备注
						}
					}
					String commtype = sysCommunityBean.getCommtype();
					//用于判断房产，房间parentid
					if(!func.IsEmpty(commtype)){
						sysCommunityBean.setParentid(firparentid);
						sysCommunityBean.setCommlev(IbmcConstant.COMM_LEV_HOUSE);    //房产层级
						secparentid = this.sysCommunityTempDAO.insertSysCommTempBean(sysCommunityBean);    //获取新增的房产id
					}else{
						sysCommunityBean.setParentid(secparentid);
						sysCommunityBean.setCommlev(IbmcConstant.COMM_LEV_ROOM);    //房间层级
						this.sysCommunityTempDAO.insertSysCommTempBean(sysCommunityBean);
					}
					i++;
				}
				rwb.close();
			    //删除源EXCEL表
			    func.delFile(realFilePath);	
	        }		
		} catch (Exception e) {
			impflag = "1";
		    //删除源EXCEL表
		    func.delFile(realFilePath);
			e.printStackTrace();
		}
		return impflag;
	}
	
	/**
	 * 验证房产信息导入数据的有效性 
	 * @param optuserid
	 * @param parentid
	 * @return {1：正确 2：错误}
	 */
	private String validManHouseTempData(String optuserid,String parentid){
		int errorCount = 0;
		try {
			//执行验证存储过程
			Map vMap = new HashMap();
			vMap.put("optuserid",optuserid);    //操作人员id
			vMap.put("parentid",parentid);      //社区id
			this.sysCommunityTempDAO.updateSysCommTempData(vMap);
			//查询是否存在错误的数据
			vMap.put("errorflag","1");      //errorflag错误标志
			errorCount = this.sysCommunityTempDAO.findSysCommTempErrorCount(vMap);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return (errorCount>0)?"2":"1" ;
	}
	
	/**
	 * 将导入的临时表数据正式保存到正式表中
	 * 
	 */
	public void saveManHouseByImpTempData(Map map) {
		this.sysCommunityTempDAO.saveSysCommTempDataIntoRegular(map);
	}
	
	public List<SysCommunityBean> findSysCommTempList(Map map){
		return this.sysCommunityTempDAO.findSysCommTempList(map);
	}
	
	public void saveManHouseTempExpExcel(Map map, HttpServletResponse response) {
		List<SysCommunityBean> dataList = this.sysCommunityTempDAO.findSysCommTempList(map);
		IbmcExcelOutForJxlImpl ibmcExp = new IbmcExcelOutForJxlImpl();
		ibmcExp.expManHouseTemp(dataList, response);
	}
	
	public List<SysCommunityBean> findSysCommTempHouseList(Map map) {
		return this.sysCommunityTempDAO.findSysCommTempHouseList(map);
	}
	
	//生成get,set方法
	public SysCommunityDAO getSysCommunityDAO() {
		return sysCommunityDAO;
	}

	public void setSysCommunityDAO(SysCommunityDAO sysCommunityDAO) {
		this.sysCommunityDAO = sysCommunityDAO;
	}

	public ManDoorHouseDAO getManDoorHouseDAO() {
		return manDoorHouseDAO;
	}

	public void setManDoorHouseDAO(ManDoorHouseDAO manDoorHouseDAO) {
		this.manDoorHouseDAO = manDoorHouseDAO;
	}

	public SysCommunityTempDAO getSysCommunityTempDAO() {
		return sysCommunityTempDAO;
	}

	public void setSysCommunityTempDAO(SysCommunityTempDAO sysCommunityTempDAO) {
		this.sysCommunityTempDAO = sysCommunityTempDAO;
	}
	
}
