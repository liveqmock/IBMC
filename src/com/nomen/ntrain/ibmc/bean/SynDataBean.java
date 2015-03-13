package com.nomen.ntrain.ibmc.bean;

import java.io.Serializable;

/**
 * @description 记录操作数据_用于同webservice同步POJO 
 * @author ljl
 * @date 2015-1-29
 */
public class SynDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String modesign;//操作模块标记（COMMUNITY、CARD、DOOR）
    private String optid;   //操作记录ID
    private String optdate; //操作记录时间
    private String downsign;//下行状态（0未下行 1成功 -1失败）
    private String downdate;//下行时间
    private String opttype; //操作类型（1新增、2修改、3删除）
    private String id;      //主键（ID）
    private String commpath;//节点路径
    private String webservicepath;//该数据需要同步到的webservice的路径

	public String getWebservicepath() {
		return webservicepath;
	}
	public void setWebservicepath(String webservicepath) {
		this.webservicepath = webservicepath;
	}
	public String getCommpath() {
		return commpath;
	}
	public void setCommpath(String commpath) {
		this.commpath = commpath;
	}
	//Get和Set方法
    public String getModesign() {
        return modesign;
    }
    public void setModesign(String modesign) {
        this.modesign = modesign;
    }
    public String getOptid() {
        return optid;
    }
    public void setOptid(String optid) {
        this.optid = optid;
    }
    public String getOptdate() {
        return optdate;
    }
    public void setOptdate(String optdate) {
        this.optdate = optdate;
    }
    public String getDownsign() {
        return downsign;
    }
    public void setDownsign(String downsign) {
        this.downsign = downsign;
    }
    public String getDowndate() {
        return downdate;
    }
    public void setDowndate(String downdate) {
        this.downdate = downdate;
    }
    public String getOpttype() {
        return opttype;
    }
    public void setOpttype(String opttype) {
        this.opttype = opttype;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
