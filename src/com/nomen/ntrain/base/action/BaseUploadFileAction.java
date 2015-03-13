package com.nomen.ntrain.base.action;
/***
 * @description   附件上传
 * @author lianjinliang
 * @date   2011-10-11
 */
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.util.PubFile;
import com.opensymphony.xwork2.ActionSupport;
public class BaseUploadFileAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private File     uploads;        //视图层控件名字要与之相同
	private String   uploadsFileName;
	protected String savePath; 	     //保存文件的目录路径(通过依赖注入)
	@SuppressWarnings("deprecation")
	public String uploadFile(){
		  HttpServletResponse res = ServletActionContext.getResponse();
		  res.setCharacterEncoding("utf-8");
		try{
			  String extName = "";//扩展名
			  String newFileName= "";//新文件名
			  String nowTime = new SimpleDateFormat("yyyymmddHHmmss").format(new Date());//当前时间精确到秒+5位随机数
			  String rodomStr = (int)(10000+Math.random()*99999)+"";
			  String temp_savePath = ServletActionContext.getRequest().getRealPath(this.savePath);
			  //获取扩展名
			  if(uploadsFileName.lastIndexOf(".")>=0){
			   extName = uploadsFileName.substring(uploadsFileName.lastIndexOf("."));
			  }
			  newFileName = nowTime+rodomStr+extName;
			  //判断该目录是否存在，若不存在，则创建
			  PubFile docFolder = new PubFile(temp_savePath);
			  if(!docFolder.isExists()) {
					  docFolder.createFilefolder();
			  } 
			  uploads.renameTo(new File(temp_savePath+"/"+newFileName));
			  res.getWriter().print(newFileName);
		}
		catch(IOException ex){
			  ex.printStackTrace();
			  return null;
		}
		catch(Exception ex){
			  ex.printStackTrace();
			  return null;
		}
		return null; //这里不需要页面转向，所以返回空就可以了
	}
	public String getSavePath() {
		return savePath;
	} 
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public File getUploads() {
		return uploads;
	}
	public void setUploads(File uploads) {
		this.uploads = uploads;
	}
	public String getUploadsFileName() {
		return uploadsFileName;
	}
	public void setUploadsFileName(String uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}
}
