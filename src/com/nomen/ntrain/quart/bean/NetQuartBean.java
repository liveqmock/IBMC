package com.nomen.ntrain.quart.bean;

/**
 * 调度器表
 * @author 梁桂钊
 * @date   2013-09-16
 */
public class NetQuartBean{
    private String jobid;		//jobid[com.nomen.ntrain.quart.task包下的类名]
    private String jobgroup;	//分组（0临时性任务，1永久性任务）
    private String jobstatus;	//状态（0禁用 1启用）
    private String cronexpr;	//运行表达式
    private String remark;		//备注
    private String methname	;	//运行方法名称
    private String param;       //运行方法参数值如（12!23）表示执行某个方法，该方法有两个参数
    private String maindate;    //维护时间（新增、修改都记录）
    private String id;			//id主键
    
    public String getMaindate() {
		return maindate;
	}

	public void setMaindate(String maindate) {
		this.maindate = maindate;
	}

	public String getTriggerName() {
    	//System.err.println(this.getJobid() + "Trigger-------------");
        //return this.getJobid() + "Trigger";
		return this.getId() + "Trigger";
    }
    
    // Get和Set方法
	public String getCronexpr() {
		return cronexpr;
	}
	public void setCronexpr(String cronexpr) {
		this.cronexpr = cronexpr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobgroup() {
		return jobgroup;
	}
	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String getJobstatus() {
		return jobstatus;
	}
	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}
	public String getMethname() {
		return methname;
	}
	public void setMethname(String methname) {
		this.methname = methname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
