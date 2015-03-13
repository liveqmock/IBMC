package com.nomen.ntrain.ibmc.excel;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.nomen.ntrain.ibmc.bean.ManDoorBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.util.JxlCellFormatUtil;
import com.nomen.ntrain.util.PubFunc;

public class IbmcExcelOutForJxlImpl extends JxlCellFormatUtil {
	private PubFunc func = null;
	
	public IbmcExcelOutForJxlImpl(){
		if(null==func){
			this.func = new PubFunc();
		}
	}
	
	/**	 
	 * 人员管理_导出Excel
	 */
	public void expManPeo(List<ManPeoBean> dataList,HttpServletResponse response){
		try {
			ServletOutputStream sos = response.getOutputStream();
			WritableWorkbook wwb = Workbook.createWorkbook(sos);
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			//标题
			WritableCellFormat wcf0 = this._formatCellStyle(18, true, jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, 
					jxl.format.Alignment.CENTRE, jxl.format.VerticalAlignment.CENTRE);
			//设置标题行单元格格式[居中加粗]
			WritableCellFormat wcf1 = this._formatCellStyle(10, true,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			wcf1.setWrap(true);
			//设置内容格式[居左]
			WritableCellFormat wcf2 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.LEFT,
					jxl.format.VerticalAlignment.CENTRE);
			wcf2.setWrap(true);// 自动换行
			//设置内容格式[居中]
			WritableCellFormat wcf3 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			
			//标题栏
			//设置初始行数|列数
			int rowIndex = 0, colIndex = 0;
			//添加标题内容
			ws.setRowView(rowIndex, 40*18);	//行高
			this._makeTitleByMerge(ws, -1, "人员查询导出Excel", 0, rowIndex, 8, rowIndex, wcf0);
			rowIndex = 1;
			ws.setRowView(rowIndex, 12*21);	//行高
			ws.setColumnView(colIndex, 7);	//列宽
			//添加标题内容
			ws.addCell(new Label(colIndex++, rowIndex,"序号",wcf1));
			ws.setColumnView(colIndex, 18);
			ws.addCell(new Label(colIndex++, rowIndex,"姓名",wcf1));
			ws.setColumnView(colIndex, 22);
			ws.addCell(new Label(colIndex++, rowIndex,"证件号码",wcf1));
			ws.setColumnView(colIndex, 10);
			ws.addCell(new Label(colIndex++, rowIndex,"性别",wcf1));
			ws.setColumnView(colIndex, 14);
			ws.addCell(new Label(colIndex++, rowIndex,"出生年月",wcf1));
			ws.setColumnView(colIndex, 10);
			ws.addCell(new Label(colIndex++, rowIndex,"民族",wcf1));
			ws.setColumnView(colIndex, 32);
			ws.addCell(new Label(colIndex++, rowIndex,"联系电话",wcf1));
			ws.setColumnView(colIndex, 45);
			ws.addCell(new Label(colIndex++, rowIndex,"住址",wcf1));
			ws.setColumnView(colIndex, 45);
			ws.addCell(new Label(colIndex++, rowIndex,"工作单位",wcf1));
			//内容数据
			for(ManPeoBean data:dataList){
				rowIndex++;colIndex=0;
				ws.setRowView(rowIndex, 252);
				ws.addCell(new Label(colIndex++, rowIndex, (rowIndex-1)+"", wcf3));//序号
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getName()), wcf3));//姓名
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getIdcard()), wcf2));//证件号码
				String sexname = (data.getSex().equals(IbmcConstant.SEX_BOY))?"男":"女";
				ws.addCell(new Label(colIndex++, rowIndex, sexname, wcf3));//性别
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getBirthday()), wcf2));//出生年月
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getNation()), wcf3));//民族
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getTelephone()), wcf2));//联系电话
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getAddress()), wcf2));//住址
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getUnitname()), wcf2));//工作单位
			}
			wwb.write();
			// 设置输出类型
			response.setContentType("application/download;charset=UTF-8");
			//response.setContentType("application/msexcel;charset=UTF-8");
			// 输出文件名
			String filename="人员查询导出Excel";
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			// 设置输出文件头
			response.setHeader("Content-disposition", "attachment; filename="+ filename+".xls");
			wwb.close();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**	 
	 * 设备管理_门口机 门口机登记导出Excel
	 */
	public void expManDoor(List<ManDoorBean> dataList,HttpServletResponse response){
		try {
			ServletOutputStream sos = response.getOutputStream();
			WritableWorkbook wwb = Workbook.createWorkbook(sos);
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			
			//设置标题行单元格格式[居中加粗]
			WritableCellFormat wcf1 = this._formatCellStyle(10, true,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			//设置内容格式[居左]
			WritableCellFormat wcf2 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.LEFT,
					jxl.format.VerticalAlignment.CENTRE);
			wcf2.setWrap(true);// 自动换行
			//设置内容格式[居中]
			WritableCellFormat wcf3 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			
			//设置初始行数|列数
			int rowIndex = 0, colIndex = 0;
			//添加标题内容
			ws.setRowView(rowIndex, 12*21);	//行高
			ws.setColumnView(colIndex, 7);	//列宽
			ws.addCell(new Label(colIndex++, rowIndex,"序号",wcf1));
			ws.setColumnView(colIndex, 27);
			//ws.addCell(new Label(colIndex++, rowIndex,"社区地址",wcf1));
			//ws.setColumnView(colIndex, 22);
			//ws.addCell(new Label(colIndex++, rowIndex,"房屋地址",wcf1));
			//ws.setColumnView(colIndex, 14);
			ws.addCell(new Label(colIndex++, rowIndex,"门口机名称",wcf1));
			ws.setColumnView(colIndex, 21);
			ws.addCell(new Label(colIndex++, rowIndex,"门口机IP",wcf1));
			ws.setColumnView(colIndex, 32);
			ws.addCell(new Label(colIndex++, rowIndex,"门口机MAC",wcf1));
			ws.setColumnView(colIndex, 45);
			ws.addCell(new Label(colIndex++, rowIndex,"门口机厂商名称",wcf1));
			//内容数据
			for(ManDoorBean data:dataList){
				rowIndex++;colIndex=0;
				ws.setRowView(rowIndex, 252);
				ws.addCell(new Label(colIndex++, rowIndex, rowIndex+"", wcf3));//序号
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getName()), wcf2));//门口机名称
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getDoorip()), wcf2));//门口机IP
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getDoormac()), wcf2));//门口机MAC
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getFacname()), wcf2));//门口机厂商名称
			}
			wwb.write();
			// 设置输出类型
			response.setContentType("application/download;charset=UTF-8");
			//response.setContentType("application/msexcel;charset=UTF-8");
			// 输出文件名
			String filename="门口机导出Excel";
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			// 设置输出文件头
			response.setHeader("Content-disposition", "attachment; filename="+ filename+".xls");
			wwb.close();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**	 
	 * 房产管理_房产信息导出Excel
	 */
	public void expManHouse(List<SysCommunityBean> dataList,HttpServletResponse response){
		try {
			ServletOutputStream sos = response.getOutputStream();
			WritableWorkbook wwb = Workbook.createWorkbook(sos);
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			//标题
			WritableCellFormat wcf0 = this._formatCellStyle(18, true, jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, 
					jxl.format.Alignment.CENTRE, jxl.format.VerticalAlignment.CENTRE);
			//设置标题行单元格格式[居中加粗]
			WritableCellFormat wcf1 = this._formatCellStyle(10, true,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			//设置内容格式[居左]
			WritableCellFormat wcf2 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.LEFT,
					jxl.format.VerticalAlignment.CENTRE);
			wcf2.setWrap(true);// 自动换行
			//设置内容格式[居中]
			WritableCellFormat wcf3 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			
			
			//标题栏
			//设置初始行数|列数
			int rowIndex = 0, colIndex = 0;
			//添加标题内容
			ws.setRowView(rowIndex, 40*15);	//行高
			this._makeTitleByMerge(ws, -1, "房产导出Excel", 0, rowIndex, 6, rowIndex, wcf0);
			rowIndex = 1;
			//添加标题内容
			ws.setRowView(rowIndex, 12*21);	//行高
			ws.setColumnView(colIndex, 7);	//列宽
			ws.addCell(new Label(colIndex++, rowIndex,"序号",wcf1));
			ws.setColumnView(colIndex, 35);
			ws.addCell(new Label(colIndex++, rowIndex,"区域/社区详细",wcf1));
			ws.setColumnView(colIndex, 42);
			ws.addCell(new Label(colIndex++, rowIndex,"房产地址",wcf1));
			ws.setColumnView(colIndex, 12);
			ws.addCell(new Label(colIndex++, rowIndex,"房产类型",wcf1));
			ws.setColumnView(colIndex, 15);
			ws.addCell(new Label(colIndex++, rowIndex,"业主名称",wcf1));
			ws.setColumnView(colIndex, 12);
			ws.addCell(new Label(colIndex++, rowIndex,"房间数",wcf1));
			ws.setColumnView(colIndex, 25);
			ws.addCell(new Label(colIndex++, rowIndex,"备注",wcf1));
			//内容数据
			for(SysCommunityBean data:dataList){
				rowIndex++;colIndex=0;
				ws.setRowView(rowIndex, 252);
				ws.addCell(new Label(colIndex++, rowIndex, (rowIndex-1)+"", wcf3));//序号
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getParentname()), wcf2));//区域/社区详细
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getCommname()), wcf2));//房产地址
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(("1").equals(data.getCommtype())?"独立业主":"共有业主"), wcf3));//房产类型（1 独立业主 2共有业主）
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getOwnername()), wcf3));//业主名称
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getChildnum()+"间"), wcf3));//房间总数
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getRemark()), wcf3));//备注
			}
			wwb.write();
			// 设置输出类型
			response.setContentType("application/download;charset=UTF-8");
			//response.setContentType("application/msexcel;charset=UTF-8");
			// 输出文件名
			String filename="房产信息导出Excel";
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			// 设置输出文件头
			response.setHeader("Content-disposition", "attachment; filename="+ filename+".xls");
			wwb.close();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**	 
	 * 导出导入房产中验证不通过的数据Excel
	 */
	public void expManHouseTemp(List<SysCommunityBean> dataList,HttpServletResponse response){
		try {
			ServletOutputStream sos = response.getOutputStream();
			WritableWorkbook wwb = Workbook.createWorkbook(sos);
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			//标题
			WritableCellFormat wcf0 = this._formatCellStyle(18, true, jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, 
					jxl.format.Alignment.CENTRE, jxl.format.VerticalAlignment.CENTRE);
			//设置标题行单元格格式[居中加粗]
			WritableCellFormat wcf1 = this._formatCellStyle(10, true,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			//设置内容格式[居左]
			WritableCellFormat wcf2 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.LEFT,
					jxl.format.VerticalAlignment.CENTRE);
			wcf2.setWrap(true);// 自动换行
			//设置内容格式[居中]
			WritableCellFormat wcf3 = this._formatCellStyle(10, false,
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
					jxl.format.Alignment.CENTRE,
					jxl.format.VerticalAlignment.CENTRE);
			//设置内容格式[含有背景颜色]
			WritableCellFormat wcf4 = this._formatCellStyle(9, false, 
					jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, 
					jxl.format.Alignment.CENTRE, jxl.format.VerticalAlignment.CENTRE);
			//设置背景颜色;  
			wcf4.setBackground(Colour.RED);
			
			//标题栏
			//设置初始行数|列数
			int rowIndex = 0, colIndex = 0;
			//添加标题内容
			ws.setRowView(rowIndex, 40*18);	//行高
			this._makeTitleByMerge(ws, -1, "房产信息导入", 0, rowIndex, 7, rowIndex, wcf0);
			rowIndex = 1;
			//添加标题内容
			ws.setRowView(rowIndex, 12*21);	//行高
			ws.setColumnView(colIndex, 12);	//列宽
			ws.addCell(new Label(colIndex++, rowIndex,"序号",wcf1));
			ws.setColumnView(colIndex, 12);
			ws.addCell(new Label(colIndex++, rowIndex,"房产类型编码",wcf1));
			ws.setColumnView(colIndex, 15);
			ws.addCell(new Label(colIndex++, rowIndex,"房产业主",wcf1));
			ws.setColumnView(colIndex, 25);
			ws.addCell(new Label(colIndex++, rowIndex,"业主身份证号",wcf1));
			ws.setColumnView(colIndex, 18);
			ws.addCell(new Label(colIndex++, rowIndex,"联系电话",wcf1));
			ws.setColumnView(colIndex, 32);
			ws.addCell(new Label(colIndex++, rowIndex,"房产地址/房间名称",wcf1));
			ws.setColumnView(colIndex, 35);
			ws.addCell(new Label(colIndex++, rowIndex,"备注",wcf1));
			ws.setColumnView(colIndex, 35);
			ws.addCell(new Label(colIndex++, rowIndex,"验证结果",wcf1));
			int houseIndex = 0;
			int roomIndex = 0;
			String index = "";
			//内容数据
			for(SysCommunityBean data:dataList){
				rowIndex++;colIndex=0;
				ws.setRowView(rowIndex, 252);
				if(IbmcConstant.COMM_LEV_HOUSE.equals(data.getCommlev())){    //房产
					houseIndex = houseIndex+1;
					roomIndex = 0;
					index = houseIndex+"";
				}else if(IbmcConstant.COMM_LEV_ROOM.equals(data.getCommlev())){  //房间
					roomIndex = roomIndex+1;
					index = houseIndex+"."+roomIndex;
				}
				ws.addCell(new Label(colIndex++, rowIndex, index, wcf3));//序号
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getCommtype()), wcf3));//房产类型编码
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getOwnername()), wcf3));//房产业主
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getOwneridcard()), wcf3));//业主身份证号
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getTelephone()), wcf3));//联系电话
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getCommname()), wcf2));//房产地址/房间名称
				ws.addCell(new Label(colIndex++, rowIndex, func.Trim(data.getRemark()), wcf2));//备注
				//错误标识（0：成功，1：错误）
				ws.addCell(new Label(colIndex++, rowIndex, ("0").equals(data.getErrorflag())?"通过":data.getErrorstr(), ("0").equals(data.getErrorflag())?wcf2:wcf4));//验证结果
			}
			wwb.write();
			// 设置输出类型
			response.setContentType("application/download;charset=UTF-8");
			//response.setContentType("application/msexcel;charset=UTF-8");
			// 输出文件名
			String filename="房产信息导入导出Excel";
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			// 设置输出文件头
			response.setHeader("Content-disposition", "attachment; filename="+ filename+".xls");
			wwb.close();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//============================辅助方法=============================
	public static String convert(String str){
	     try{
	       //把ISO编译过的字符转译为UTF-8
	       return new String(str.getBytes("UTF-8"),"ISO-8859-1");
	     }catch(Exception e){return null;}
	}
}