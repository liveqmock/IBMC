package com.nomen.ntrain.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;


/**
 * 分页标签类
 * @author 许东雄
 */
public class PageTag extends ComponentTagSupport
{
	private static final long serialVersionUID=200905261332L;
	private String tagpage;    //当前页
    private String total;	   //总记录数
    private String record;     //每页显示的记录数
    private String url;        //请求地址
    private String formname;   //提交的表单名称
  
    @Override
    public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) 
    {
    	return new Pages(arg0); //返回Pages Component，分页的逻辑处理都在这个Component中
    }
    
    //获得参数
    protected void populateParams() 
    {
        super.populateParams();
        
        Pages pages = (Pages)component;
        pages.setTagpage(tagpage);
        pages.setTotal(total);
        pages.setRecord(record);
        pages.setUrl(url);
        pages.setFormname(formname);
    }

	public void setFormname(String formname) {
		this.formname = formname;
	}
	public void setTagpage(String tagpage) {
        this.tagpage = tagpage;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public void setUrl(String url) {
        this.url = url;
    }
	public void setRecord(String record) {
		this.record = record;
	}
}
