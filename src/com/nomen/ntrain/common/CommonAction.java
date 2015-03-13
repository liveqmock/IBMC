package com.nomen.ntrain.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.nomen.ntrain.base.bean.LoginBean;
import com.nomen.ntrain.util.Constant;
import com.nomen.ntrain.util.PubFile;
import com.nomen.ntrain.util.PubFunc;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @description Action基础类  
 * @author 连金亮
 * @date 2011-05-20
 */

public class CommonAction extends ActionSupport 
{
	private static final long serialVersionUID=200905253423L;
	private static final Log LOG = LogFactory.getLog(CommonAction.class);
	protected static PubFunc func=null;					//公用类实例
	@SuppressWarnings("unused")
	private static int flag=CommonAction.getInstance();
	protected File[] upfile;								//上传的文件数组
	protected String[] filename;                        //上传的文件名数组
	private String tokenid;								//提交异常判断字段
	private String tagpage;                          	//当前页数
	private String record;               				//每页显示记录数
	private String total;                              	//总记录数
	protected CommonAction(){
	}
	
	public static synchronized int getInstance(){
		if(func==null) {
			func=new PubFunc();
		}
		return 0;
	}

	/**
	 * 获取当前登录的用户信息对象
	 * @return
	 */
	public static LoginBean getLoginSessionBean(){
		LoginBean loginBean=(LoginBean) ActionContext.getContext().getSession().get(Constant.LOGIN_PARAM);
		//loginBean = new LoginBean();
		//loginBean.setId("1");
		//loginBean.setLevsign("1");
		return loginBean;
	}
	
	/**
	 * 执行上传操作（不限制大小）
	 * @param path 目录相对路径
	 * @param buffersize 缓存大小
	 * @return
	 */
	protected String[] execUpload(String path,int buffersize) {
		try {
			//判断是否存在上传文件
			if(this.upfile == null) {
				return null;
			}
			//未设置缓存大小时，使用默认的缓存
			if(buffersize == 0) {
				buffersize = 16*1024;
			}
			//格式化相对路径
			if(path.endsWith("/")) {
				path = path.substring(0,path.length()-1);
			}
			//定义保存的文件名数组
			String []savename = new String[upfile.length];
			//获取绝对物理路径
			path = ServletActionContext.getServletContext().getRealPath(path);
			//保存路径不存在时，创建路径
			PubFile file = new PubFile(path);
			if(!file.isExists()) {
				file.createFilefolder();
			}
			//保存文件
			for(int i=0;i<this.upfile.length;i++) {
				InputStream in = null;
				OutputStream out = null;
				try{
					if(this.upfile[i] != null) {
						savename[i] = this.getRandomFileName(filename[i]);
						in = new BufferedInputStream(new FileInputStream(this.upfile[i]), buffersize);
						out = new BufferedOutputStream(new FileOutputStream(new File(path+"/"+savename[i])), buffersize);
						byte []buffer = new byte[buffersize];
						//读写内容
						while(in.read(buffer)>0) {
							out.write(buffer);
						}
					}
				} catch (IOException ioex) {
					ioex.printStackTrace();
				} finally {
					if(in != null) {
						in.close();
					}
					if(out != null) {
						out.close();
					}
				}
			}
			return savename;
		} catch(Exception ex) {
			return null;
		}
	}

	/**
	 * 给文件进行随机命名
	 * @param filename 源文件
	 * @return
	 */
	private String getRandomFileName(String filename) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(PubFunc.dateToString(new java.util.Date(), "yyyyMMddHHmmss"));
		sb.append((int)(10000+Math.random()*99999));
		int pos = filename.lastIndexOf(".");
		sb.append(filename.substring(pos));
		return sb.toString();
	}
	
	/**
	 * 添加页面显示信息
	 * @param sParam 提示信息变量 
	 */
	protected void setActMessage(String sParam) {
		String msg="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		msg+="<script language=\"JavaScript\"> parent.$.jBox.tip(\""+this.getText(sParam)+"\", 'warning');</script>";
		this.addActionMessage(msg);
	}
	
	/**
	 * 添加页面显示信息
	 * @param sParam 提示信息变量
	 * @param script 除了提示信息，额外的脚本
	 */
	protected void setActMessage(String sParam,String script) {
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<script language=\"JavaScript\">\n");
		if(!func.IsEmpty(sParam)) {
			sb.append(" parent.$.jBox.tip(\""+this.getText(sParam)+"\", 'warning');");
		}
		sb.append(" "+script+"\n");
		sb.append("</script>");
		this.addActionMessage(sb.toString());
	}

	/**
	 * 添加页面显示信息
	 * @param sParam 提示信息变量
	 * @param script 除了提示信息，额外的脚本
	 */
	protected void reloadParentPage() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"JavaScript\">\n");
		sb.append("parent.$.jBox.tip(\"操作成功！\", 'warning');  parent.window.reloadForSubPage();parent.$.jBox.close();");
		sb.append("</script>");
		this.addActionMessage(sb.toString());
	}
	
	/**
	 * 添加页面显示信息
	 * @param sParam 提示信息变量
	 * @param script 除了提示信息，额外的脚本
	 */
	protected void reloadParentPage2() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"JavaScript\">\n");
		sb.append("parent.$.jBox.tip(\"操作成功！\", 'warning');   parent.window.reloadForSubPage();");
		sb.append("</script>");
		this.addActionMessage(sb.toString());
	}
	
	/**
	 * 添加页面操作失败提示
	 * @param sParam 提示信息变量
	 * @param script 除了提示信息，额外的脚本
	 */
	protected void operateError() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"JavaScript\">\n");
		sb.append(" parent.$.jBox.tip(\"操作失败！\", 'warning');  parent.$.jBox.close();");
		sb.append("</script>");
		this.addActionMessage(sb.toString());
	}
	
	/**
	 * 添加页面操作成功提示
	 * @param sParam 提示信息变量
	 * @param script 除了提示信息，额外的脚本
	 */
	protected void operateSuccess() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"JavaScript\">\n");
		sb.append(" parent.$.jBox.tip(\"操作成功！\", 'warning');  parent.$.jBox.close();");
		sb.append("</script>");
		this.addActionMessage(sb.toString());
	}
	
	/**
	 * 判断是否合法的提交操作
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isValidToken() {
		ActionContext ctx=ActionContext.getContext();
		Map session= ctx.getSession();
		String sessToken=(String)session.get("tokenid");
		//当页面的tokenid与session中的tokenid一致的话，表示正在执行非法提交操作
		if(this.tokenid == null||func.Trim(this.tokenid).equals(sessToken)) {
			return false;
		} else {
			session.put("tokenid", func.Trim(this.tokenid));
			return true;
		}
	}
	
	
	/**
	 * 输出到客户端
	 * @param response
	 * @param info
	 * @throws IOException
	 */
	protected void print( String info){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");		
			response.getWriter().print(info);
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}

	/**
	 * 输出到客户端
	 * @param response
	 * @param info
	 * @throws IOException
	 */
	protected void printList(List list){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");	
			JSONArray json = JSONArray.fromObject(list);
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}

	/**
	 * 输出到客户端
	 * @param response
	 * @param info
	 * @throws IOException
	 */
	protected void printBean(Object obj){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");	
			JSONArray json = JSONArray.fromObject(obj);
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * 维护人、所属部门、维护时间
	 */
	protected void getOpraterInfoIntoRequest(){
		LoginBean login=(LoginBean)ActionContext.getContext().getSession().get(Constant.LOGIN_PARAM);
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("opraName", login.getUsername());
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		request.setAttribute("opraTime", format.format(new Date()));
		request.setAttribute("opraId",login.getId());
	}
	
	/**
	 * 获取正确的uri
	 * @return
	 */
	public String getRightUri(){
		String uri = ServletActionContext.getRequest().getRequestURI();  
		uri = func.Trim(uri); 
		if(uri.startsWith("/")){
			uri = uri.substring(1);
		}
		String[] param = uri.split("/");
		uri = param[0]; 
		return uri;
	}
	/** 
	 * .properties文件
     * 根据Key 读取Value
     * @param key 
     * @param path 路径
     * @return 
     */  
    public static String proVal(String key,String path) {  
        Properties props = new Properties();  
        try {  
            InputStream in = new BufferedInputStream(new FileInputStream(path));  
            props.load(in);  
            in.close();  
            String value = props.getProperty(key);  
            return value;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
    public static void proDate(String key, String value,String path){
    	 Properties prop = new Properties();  
         try {  
             File file = new File(path);  
             if (!file.exists())  
                 file.createNewFile();  
             InputStream fis = new FileInputStream(file);  
             prop.load(fis);  
             fis.close();//一定要在修改值之前关闭fis  
             OutputStream fos = new FileOutputStream(path);  
             prop.setProperty(key, value);  
             prop.store(fos, "Update '" + key + "' value");  
             fos.close();  
         } catch (IOException e) {  
             System.err.println("Visit "+ path +" for updating "+ value +" value error");  
             e.printStackTrace();
         }  
    }
	/**
	 * 构建分页数据列表
	 * list 数据列表；totalNum 数据总记录数
	 */
	public String creItemListPage(List list,String totalNum){
		String jsonList=JSONArray.fromObject(list).toString();
		String itemList="{\"totalCount\":" +totalNum+", \"items\":"+jsonList+"}";
		return itemList;
	}
	
	//以下为get和set方法
	public static PubFunc getFunc() {
		return func;
	}
	public String getTokenid() {
		return Constant.getTokenId();
	}
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}
	public String getRecord() {
		if(record == null) {
			return this.getText("page.record");
		} else {
			return record;
		}
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTagpage() {
		if(tagpage == null || "0".equals(tagpage)) {
			return "1";
		} else {
			return tagpage;
		}
	}
	public void setTagpage(String tagpage) {
		this.tagpage = tagpage;
	}
	
	public void setUpfileFileName(String[] filename) {
		this.filename = filename;
	}

	public File[] getUpfile() {
		return upfile;
	}

	public void setUpfile(File[] upfile) {
		this.upfile = upfile;
	}

		
}