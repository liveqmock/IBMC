package com.nomen.ntrain.util;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页 Bean
 * @author 许东雄
 */
public class Pages extends Component
{
    private String tagpage; 
    private String total;
    private String url;
    private String record;
    private String formname;
    
    public Pages(ValueStack arg0) {
        super(arg0);
    }
    
    @Override
    public boolean start(Writer writer) 
    {
    	boolean result = super.start(writer);
        try {
            StringBuilder str = new StringBuilder();
            boolean isValid = true;
            
            //从ValueStack中取出数值
            if (isValid) {
                if (total.startsWith("%{") && total.endsWith("}")) {
                    total = total.substring(2, total.length() -1);
                    total = (String)this.getStack().findValue(total);
                    isValid = total == null ? false : true;
                } else {
                    isValid = false;
                }
            }
            if (isValid) {
                if (tagpage.startsWith("%{") && tagpage.endsWith("}")) {
                	tagpage = tagpage.substring(2, tagpage.length() - 1);
                	tagpage = (String)this.getStack().findValue(tagpage);
                    isValid = tagpage == null ? false : true;
                } else {
                    isValid = false;
                }
            }
            if (isValid) {
                if (url.startsWith("%{") && url.endsWith("}")) {
                    url = url.substring(2, url.length() - 1);
                    url = (String)this.getStack().findValue(url);
                    isValid = url == null ? false : true;
                } else {
                    isValid = false;
                }
            }
            if (isValid) {
                if (record.startsWith("%{") && record.endsWith("}")) {
                	record = record.substring(2, record.length() - 1);
                	record = (String)this.getStack().findValue(record);
                    isValid = record == null ? false : true;
                } else {
                    isValid = false;
                }
            }
            if (isValid) {
                if (formname.startsWith("%{") && formname.endsWith("}")) {
                	formname = formname.substring(2, formname.length() - 1);
                	formname = (String)this.getStack().findValue(formname);
                    isValid = formname == null ? false : true;
                } else {
                    isValid = false;
                }
            }
            
            if (isValid) {
                int cpageInt = tagpage==null?1:this.Cint(tagpage);
                //设置下拉框选择事件
                String strOnchg="onchange='JavaScript:subFormPage(\""+url+"\",\""+formname+"\",\"1\",this.value);'";
                //总页数
                int allpage = (int)Math.ceil(Double.parseDouble(total)/Double.parseDouble(record));
                allpage=(allpage==0?1:allpage);
                String strSel="<select name='sel' "+strOnchg+">\n"
                			 +"<option "+(record.equals("15")?"selected":"")+" value='15'>15</option>\n"
                			 +"<option "+(record.equals("20")?"selected":"")+" value='20'>20</option>\n"
        			         +"<option "+(record.equals("30")?"selected":"")+" value='30'>30</option>\n"
        			         +"<option "+(record.equals("50")?"selected":"")+" value='50'>50</option>\n"
        			         +"</select>条/页，";
                str.append("<li>记录总数："+total+"&nbsp;条，");
                str.append(strSel);
                //当前页与总页数相等
                if (cpageInt >= allpage) {
                    //总页数只有一页
                    if (allpage == 1 && cpageInt==1) {
                        str.append("当前第&nbsp;1&nbsp;页</li><li class=\"pgempty\">首页</li><li class=\"pgempty\">上一页</li>"
                        		+"<li class=\"pgempty\">下一页</li><li class=\"pgempty\">末页</li>");
                    } else {
                        //到达最后一页
                    	str.append("当前第&nbsp;"+allpage+"&nbsp;页&nbsp;&nbsp;");
                    	str.append("<li class=\"pghover\" onclick='JavaScript:");
                    	str.append("subFormPage(\""+url+"\",\""+formname+"\",\"1\",\""+record+"\");");
                        str.append("'>首页</li><li class=\"pghover\" onclick='JavaScript:");
                        str.append("subFormPage(\""+url+"\",\""+formname+"\",\""
                        		+(cpageInt - 1)+"\",\""+record+"\");");
                        str.append("'>上页</li>");
                    	str.append("<li class=\"pgempty\">下一页</li><li class=\"pgempty\">末页</li>");
                    }  
                //当前页与总页数不相同
                } else {
                    if (cpageInt == 1) {
                        //第一页
                    	str.append("当前第&nbsp;1&nbsp;页</li><li class=\"pgempty\">首页</li><li class=\"pgempty\">上一页</li>");
                    	str.append("<li class=\"pghover\" onclick='JavaScript:");
                    	str.append("subFormPage(\""+url+"\",\""+formname+"\",\""
                        		+(cpageInt + 1)+"\",\""+record+"\");");
                        str.append("'>下页</li><li class=\"pghover\" onclick='JavaScript:");
                        str.append("subFormPage(\""+url+"\",\""+formname+"\",\""
                        		+allpage+"\",\""+record+"\");");
                        str.append("'>末页</li>");
                    } else {
                        //不是第一页
                        str.append("当前第&nbsp;"+tagpage+"&nbsp;页</li><li class=\"pghover\" onclick='JavaScript:");
                        str.append("subFormPage(\""+url+"\",\""+formname+"\",\"1\",\""+record+"\");");
                        str.append("'>首页</li><li class=\"pghover\" onclick='JavaScript:");
                        str.append("subFormPage(\""+url+"\",\""+formname+"\",\""
                        		+(cpageInt - 1)+"\",\""+record+"\");");
                        str.append("'>上页</li><li class=\"pghover\" onclick='JavaScript:");
                        str.append("subFormPage(\""+url+"\",\""+formname+"\",\""
                        		+(cpageInt + 1)+"\",\""+record+"\");");
                        str.append("'>下页</li><li class=\"pghover\" onclick='JavaScript:");
                        str.append("subFormPage(\""+url+"\",\""+formname+"\",\""
                        		+allpage+"\",\""+record+"\");");
                        str.append("'>末页</li>");
                    }
                }
            }
            writer.write(str.toString());
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int Cint(String s) {
		int i = 0;
		if (s != null && s.trim().length() != 0)
			i = Integer.parseInt(s);
		return i;
	}
    
    public String getTagpage() {
        return tagpage; 
    }
    public void setTagpage(String tagpage) {
        this.tagpage = tagpage;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
}
