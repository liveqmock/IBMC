package com.nomen.ntrain.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.struts2.ServletActionContext;

/**
 * 实现文件夹和文件的生成，读写，名称修改，删除，以及编码转换的类  
 * @author 连金亮
 * @date 2011-05-20
 */

public class PubFile
{
	private String fname;		//文件或文件夹名称
    private String path;		//绝对路径
    private String pathfile;	//完整的文件或文件夹路径
    private PubFunc func= new PubFunc();
    private final String[] indot={"doc","xml","txt","properties"};
    private String sReturn="";  //返回的信息
    private static final int BUFFER_SIZE = 512 * 1024;
    
    public PubFile(){
    	
    }
    public PubFile(String sPath,String sName) {
    	this.path = sPath;
    	this.fname = sName;
    	this.pathfile = sPath + "\\" + sName;
    }
    
    public PubFile(String pathfile) {
    	this.pathfile = pathfile;
    }
    
    /*
     * 判断文件或文件夹是否存在
     */
    public boolean isExists() throws Exception
    {
    	File file=new File(this.pathfile);
    	return file.exists();
    }
    
    /*
     * 判断文件或文件夹名称的有效性
     * @return String
     */
    public String checkFileName() throws Exception
    {
    	String str="";
    	List list=Arrays.asList(indot);
    	if(fname.indexOf(".")<0) {
    		fname=func.Trim(fname)+".txt";
    	} else if(PubFunc.getCharInStr(fname, "\\.")>1) {
    		str="文件名有误，不允许带有多个\".\"符号";
    	} else if(!list.contains(fname.split("\\.")[1])) {
    		str="文件名后缀有误，只允许生成txt、doc、xml、properties文件";
    	}
    	return str;
    }

    /*
     * 创建文件
     * @return 1：成功;0：失败
     */
    public int createFile() throws Exception
    {
    	int count=0;
		String str=func.Trim(checkFileName());
		File f=new File(this.pathfile);
		if(str!="") {
			sReturn=str;
			return count;
		}
		
	    if(!f.exists()) {
	    	f.createNewFile();
		    count=1;
		    sReturn="文件"+fname+"创建成功！";
		}
	    else {
	    	sReturn="文件"+fname+"已经存在，请重新指定文件名称！";
	    }
        return count;
    }

    /*
     * 创建文件夹
     * @return 1：成功;0：失败
     */
    public int createFilefolder() throws Exception
    {
    	int count=0; 
        File f=new File(this.pathfile);
        if(!f.exists()) {
        	f.mkdir();
        	count=1;
        	sReturn="文件夹"+fname+"创建成功！";
        }
        else
        	sReturn="文件夹"+fname+"已经存在，请重新指定文件夹名称！";
       
        return count;
    }
 
    /*
     * 文件重命名
     * @return 1：成功;0：失败
     */
    public int renameFile(String newname) throws Exception
    {
    	int count=0;
        String dot="",strRealPath="";

    	File f1=new File(this.pathfile);
        File f2;
        dot=fname.substring(fname.lastIndexOf("."));
        if(newname.indexOf(".")>=0) strRealPath=path+"\\"+newname;
        else strRealPath=path+"\\"+newname+dot;
        f2=new File(strRealPath);
        if(!f1.exists()) {
        	sReturn="要更名的文件不存在，请重新指定一个文件！";
        }
        else if(f2.exists()) {
        	sReturn="指定文件的目录下已存在同名的文件，请重新指定一个文件名称！";
        }
        else if(f1.renameTo(f2)) {
        	count=1;
        	sReturn="文件名修改成功！";
        }
        else {
        	sReturn="文件名修改失败，请检查一下文件的格式！";
        }   
 
        return count;
    }
    
    /*
     * 文件夹重命名
     * @return 成功：1;失败：0
     */
    public int renameFilefolder(String newname) throws Exception
    {
    	int count=0;
    
		File fileRenameF=new File(this.pathfile);       //更名前的文件夹
    	File fileRenameToF=new File(path+"\\"+newname);   //更名后的文件夹
		if(fileRenameF.exists()) {
			//判断新的文件夹名称是否与现存的文件夹重名
			if(!fileRenameToF.exists()) {
				fileRenameF.renameTo(fileRenameToF);
				count=1;
				sReturn="文件夹名称修改成功！";
	        } else {
				sReturn="指定的文件夹名称和现有的文件夹名称重复，请重新指定一个文件夹名称！";
	        }
		}
	    else {
		   sReturn="要更名的文件夹不存在，请重新指定一个文件夹";
	    }
	
        return count;
    }
    
    /*
     * 文件删除
     * @return 成功：1;失败：0
     */
    public int deleteFile() throws Exception
    {
    	int count=0;
 
		File f=new File(this.pathfile);
    	f.delete();
    	count=1;
    	sReturn="文件删除成功！";
    	return count;
    }
    
    /*
     * 文件夹删除
     * @return 成功：1;失败：0
     */
    public void deleteFilefolder(File f) throws Exception
    {
    	if(f.exists()&& f.isDirectory())
        {
    		File files[]=f.listFiles();   
            for(int i=0;i<files.length;i++){
            	deleteFilefolder(files[i]);   
            }
        }
    	if(!f.delete()&&f.exists()) {
    		deleteFilefolder(f);
    	} 
    }
    
    /*
     * 读取文件中一行的数据，并返回这个数据
     * @return String
     */
    public String ReadFileOneLine() throws Exception 
    {
    	String currentRecord = null;// 保存文本的变量
		BufferedReader file = new BufferedReader(new FileReader(this.pathfile));// 创建新的BufferedReader对象
		String returnStr = null;
		 
		currentRecord = file.readLine(); //读取一行数据并保存到currentRecord变量中

        if (currentRecord == null) { 
        	returnStr = "null";
        } else {// 文件不为空
        	returnStr = currentRecord;
        }
        // 返回读取文件的数据
        return returnStr;
    }
    
    /*
     * 读取文件中的所有内容
     * @return String
     */
    public String ReadFileAll() throws Exception
    {
    	File file = new File(this.pathfile);
    	BufferedReader reader = null;
    	StringBuffer strReturn = new StringBuffer();
    	
    	reader = new BufferedReader(new FileReader(file));
    	String strTemp = null;
    	int line = 1;//一次读入一行，直到读入null为文件结束
    	
    	while ((strTemp = reader.readLine()) != null) {
    		strReturn.append(strTemp);
    		line++;
    	}
    	reader.close();
    	return strReturn.toString();
    }
    
    /*
     * 写文件操作,写入一行
     * @param strWrite 写入的内容
     */
    public void WriteFileOneLine(String strWrite) throws Exception
    {
    	// 创建PrintWriter对象，用于写入数据到文件中
    	PrintWriter pw = new PrintWriter(new FileOutputStream(this.pathfile));
    	// 用文本格式打印整数Writestr
    	pw.println(strWrite);
    	// 清除PrintWriter对象
    	pw.close();
    }
    
    /*
     * 以数组的形式按行写入文件
     * @param args 写入的内容
     */
    public void WriteFileOneLine(String[] args) throws Exception 
    { 
    	FileWriter fw = new FileWriter(this.pathfile); 
    	PrintWriter out = new PrintWriter(fw); 
    	for (int i = 0; i < args.length; i++) { 
    		out.write(args[i]); 
    		out.println(); 
    	} 
    	fw.close(); 
    	out.close();
    }
    
    /*
     * 把所有内容写入文件
     * @param strWrite 写入的内容
     */
    public void WriteFile(String strWrite) throws Exception 
    {
    	File file = new File(this.pathfile);
    	DataOutputStream outs = new DataOutputStream(new FileOutputStream(file));
    	outs.write(strWrite.getBytes());
    	outs.close();
    }
    
    /*
     * 把编码转换为中文
     * @param s 预转换的字符串
     * @param type 转换方式
     * @return String
     */
    public String UnicodeToChinese(String s,String type)
    {
    	if(func.Trim(type)=="") type="gbk";     //type为编程方式
    	try
        {
    		if(s==null||s.equals("")) return ""; 
            String newstring=null; 
            newstring=new String(s.getBytes("ISO8859_1"),type); 
            return newstring;
        } 
        catch(UnsupportedEncodingException e) 
        {
        	return s; 
        } 
    } 

    /*
     * 把中文转换为编码
     * @param s 预转换的字符串
     * @param type 转换方式
     * @return String
     */
    public String ChineseToUnicode(String s,String type)
    {
    	if(func.Trim(type)=="") type="gbk";     //type为编程方式
    	try
        {
    		if(s==null||s.equals("")) return ""; 
            String newstring=null; 
            newstring=new String(s.getBytes(type),"ISO8859_1"); 
            return newstring; 
        } 
        catch(UnsupportedEncodingException e) 
        {
        	return s; 
        } 
    }
    
    /*
	 * 随机生成文件名称
     * @param filename 原文件名
	 * @return
	 */
    public String generateFileName(String filename){
    	SimpleDateFormat  sdf=new SimpleDateFormat("yyMMddHHmmss");
    	String sDate=sdf.format(new Date());
    	Random rd=new Random();
    	String oneRandom =sDate+ String.valueOf(rd.nextInt(9999));
    	int intflag=filename.lastIndexOf(".");
    	String filetype=filename.substring(intflag, filename.length());
    	String savename=oneRandom+filetype;
    	return savename;
    }

    /*
     * 判断所要保存的文件夹是否存在(不存在创建文件夹)
     * @param savepath
     */
    public void CreateFolder(String savepath){
    	String dstPathOne =ServletActionContext.getServletContext().getRealPath(savepath);
		File f = new File(dstPathOne);
		if (!f.exists()) {
			f.mkdir();
		}
    }
    
    /*
	 * 复制文件
     * @param src 原文件对象
     * @param dst 目标文件对象
	 */
	public  void  copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0){
					out.write(buffer);
				}
			} finally {
				if (null!=in){
					in.close();
				}
				if (null!=out){
					out.close();
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
    
    //get和set方法
	public String getSReturn() {
		return sReturn;
	}
	public void setSReturn(String return1) {
		sReturn = return1;
	}
	public String getPathfile() {
		return pathfile;
	}
	public void setPathfile(String pathfile) {
		this.pathfile = pathfile;
	}
}