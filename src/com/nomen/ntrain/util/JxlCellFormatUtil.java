package com.nomen.ntrain.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.WritableFont.FontName;
import jxl.write.biff.RowsExceededException;

public class JxlCellFormatUtil {
	private WritableFont writeableFont;
	WritableCellFormat cellFormat;
	public JxlCellFormatUtil(){
		
	}
	/**
	 * 
	 * @param font_Style        WritableFont的字体样式
	 * @param font_Size         WritableFont的字体大小
	 * @param font_IsBold    WritableFont的字体（加粗）
	 * @param font_Italic       WritableFont是否斜体
	 * @param cell_Bground      单元格的背景色
	 * @param cell_Border       单元格的边框
	 * @param cell_BLineStyle   单元格的边框线样式
	 * @param cell_AlignMent    单元格数据水平对其方式
	 * @param cell_VerticalAlignment  单元格数据垂直对其方式
	 * @return
	 * @throws Exception
	 */
	private WritableCellFormat _reloadCellFormatForJxl(FontName font_Style,int font_Size,boolean font_IsBold,boolean font_Italic,
			Colour cell_Bground,Border cell_Border,BorderLineStyle cell_BLineStyle,Alignment cell_AlignMent,VerticalAlignment cell_VerticalAlignment) throws Exception{
		/*
         * 设置标题行格式
         * 第一个:字体,WritableFont.createFont("宋体")
         * 第二个:大小,12
         * 第三个:是否为粗体,WritableFont.BOLD 或WritableFont.NO_BOLD
         * 第四个:判断是否为斜体,选择true时为斜体
         * 第五个:样式,UnderlineStyle.NO_UNDERLINE下划线
         * 第六个:颜色,jxl.format.Colour.RED字体颜色是红色的
         */
		//设置标题行单元格格式[居中]
		writeableFont= new WritableFont(font_Style, font_Size, font_IsBold?WritableFont.BOLD:WritableFont.NO_BOLD , font_Italic);
		WritableCellFormat cellFormat = new WritableCellFormat(writeableFont);
		cellFormat.setBackground(cell_Bground);
		cellFormat.setBorder(cell_Border,cell_BLineStyle);
		cellFormat.setAlignment(cell_AlignMent);
		cellFormat.setVerticalAlignment(cell_VerticalAlignment);
		return cellFormat;
	}
	
	/**
	 * 构造字体为 
	 * 			 加粗/不加粗
                 无边框
	 * @return
	 * @throws Exception
	 */
	public WritableCellFormat _formatCellStyle(int fontSize,boolean font_IsBold,Border cell_Border,BorderLineStyle cell_BLineStyle,Alignment cell_AlignMent,VerticalAlignment cell_VerticalAlignment) throws Exception{
		WritableCellFormat cellFormat = this._reloadCellFormatForJxl(WritableFont.createFont("宋体"), fontSize, font_IsBold, false, Colour.WHITE, cell_Border, cell_BLineStyle, cell_AlignMent, cell_VerticalAlignment);
		return cellFormat;
	}
	
	/**
	 * 
	 * @param ws
	 * @param colIndex   列
	 * @param colWidth   列宽[-1时表示不设置列宽]
	 * @param cellTitle  单元格标题
	 * @param wcf
	 */
	public void _setCellStyle(WritableSheet ws,int colIndex,int colWidth,int cellColIndex,int cellRowIndex,String cellTitle,WritableCellFormat wcf){
		try {
			if(colWidth != -1){
				ws.setColumnView(colIndex, colWidth);
			}
			ws.addCell(new Label(cellColIndex,cellRowIndex,cellTitle,wcf));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void _makeTitleByArr(WritableSheet ws,String[][] titleArr,int rowIndex,int colIndex,WritableCellFormat wcf) throws RowsExceededException, WriteException{
		for(String[] title : titleArr){
			ws.setColumnView(colIndex, Integer.valueOf(title[0]));
			ws.addCell(new Label(colIndex++,rowIndex,title[1],wcf));
		}
	}
	
	public void _makeTitle(WritableSheet ws,int colWidth,String title,int colIndex,int rowIndex,WritableCellFormat wcf) throws RowsExceededException, WriteException{
		if(colWidth!=-1){
			ws.setColumnView(colIndex, colWidth);
		}
		ws.addCell(new Label(colIndex,rowIndex,title,wcf));
	}
	/**
	 * 合并单元格
	 * @param ws
	 * @param colWidth     列宽
	 * @param title        标题
	 * @param sColIndex    开始列
	 * @param sRowIndex    开始行
	 * @param eColIndex    结束列
	 * @param eRowIndex    结束行
	 * @param wcf        
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void _makeTitleByMerge(WritableSheet ws,int colWidth,String title,int sColIndex,int sRowIndex,int eColIndex,int eRowIndex,WritableCellFormat wcf) throws RowsExceededException, WriteException{
		if(colWidth!=-1){
			ws.setColumnView(sColIndex, colWidth);
		}
		ws.addCell(new Label(sColIndex,sRowIndex,title,wcf));
		ws.mergeCells(sColIndex, sRowIndex, eColIndex, eRowIndex);
	}
	/**
	 * 添加水印文字
	 * @param image    图片数据
	 * @param text     添加文字
	 * @param color    颜色
	 * @param font     字体
	 * @param x        X坐标
	 * @param y        Y坐标
	 */
	public byte[] _makeTextWatermark(byte[] image,String text,Color color,Font font,int x,int y){
		try {
			Image  img = ImageIO.read(new ByteArrayInputStream(image));
			BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			Font dfont = font==null?new Font("宋体",Font.BOLD,20):font;
			g.drawImage(img, 0, 0, null);   
	        g.setColor(color);   
	        g.setFont(dfont);
	        FontMetrics fm = g.getFontMetrics(dfont);	        
	        java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);
	        int width     = getWidth(bi);
	        int height    = getHeight(bi);
	        int textWidth =(int) (rect.getWidth());
	        int textHeight = (int) (rect.getHeight());	
	        int temp_x=0,temp_y=0;
	        temp_x= x;
	        temp_y= y;
        	if(x>=width-textWidth-5){
        		temp_x = width-textWidth-5;
        	}
        	if(y>=height-textHeight-5){
        		temp_y=height-5;
        	}
        	if(temp_x<=0){
        		temp_x=0;
        	}
        	if(temp_y<=0){
        		temp_y=textHeight-5;
        	}
	        g.drawString(text,temp_x,temp_y);
	        g.dispose();
	        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
	        ImageIO.write(bi, "jpg", byteArrayOut);
	        return byteArrayOut.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取图片宽度
	 * @param bufferedimage
	 * @return
	 */
	public  int getWidth(BufferedImage bufferedimage) {
		return bufferedimage.getWidth();
	}
	/**
	 * 获取图片的高度
	 * @param bufferedimage
	 * @return
	 */
	public  int getHeight(BufferedImage bufferedimage) {
		return bufferedimage.getHeight();
	}
}
