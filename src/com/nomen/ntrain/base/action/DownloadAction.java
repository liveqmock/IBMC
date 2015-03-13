package com.nomen.ntrain.base.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.common.CommonAction;

/**
 * @description 下载公用Action  
 * @author 钱新红
 * @date 2009-11-26
 */
public class DownloadAction extends CommonAction 
{
	private static final long serialVersionUID = 266213025626670982L;
	private String inputPath;         	//下载路径
	private String fileName;			//下载文件名
	private InputStream inputStream;    //下载输入流

	public InputStream setInputStream() {
		try{
			return ServletActionContext.getServletContext().getResourceAsStream(inputPath + fileName);
		}catch(Exception ex){
			return null;
		}
	}
	
	public String execute() {
		try{
		    this.inputStream = this.setInputStream();
			if(this.inputStream == null) {
				this.setActMessage("struts.messages.error.download");
				return INPUT;
			}
		} catch(Exception ex) {}
		return SUCCESS;
	}

	//以下为get和set方法
	public String getFileName() throws UnsupportedEncodingException {
//		String filename=new String(this.fileName.getBytes("UTF-8"), "ISO8859-1");
		String filename=java.net.URLEncoder.encode(this.fileName,"utf-8");
		return filename;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getInputPath() {
		return inputPath;
	}
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
