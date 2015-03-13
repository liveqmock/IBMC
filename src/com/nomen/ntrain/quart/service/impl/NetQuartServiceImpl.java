package com.nomen.ntrain.quart.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.nomen.ntrain.quart.bean.NetQuartBean;
import com.nomen.ntrain.quart.constant.Constant;
import com.nomen.ntrain.quart.dao.NetQuartDAO;
import com.nomen.ntrain.quart.service.NetQuartService;
import com.nomen.ntrain.quart.task.QuartzJobBean;

@SuppressWarnings("unchecked")
public class NetQuartServiceImpl extends QuartzJobBean implements NetQuartService {
	private static final Log LOG = LogFactory.getLog(NetQuartServiceImpl.class);

    private NetQuartDAO netQuartDAO;
    private Scheduler scheduler;
	
    /**
     * 启动定时任务
     * 
     * @param netQuartBean
     */
    protected void enabled(NetQuartBean netQuartBean) {
        try {
            CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
            		netQuartBean.getTriggerName(), netQuartBean.getJobgroup());
            if (null == trigger) {
                // Trigger不存在，那么创建一个
                JobDetail jobDetail = new JobDetail(netQuartBean.getId(),
                		netQuartBean.getJobgroup(), QuartzJobBean.class);
                jobDetail.getJobDataMap().put("targetObjectId", netQuartBean.getJobid());
                jobDetail.getJobDataMap().put("targetObjectMethod", netQuartBean.getMethname());
                jobDetail.getJobDataMap().put("targetObjectMethodParam", netQuartBean.getParam());

                trigger = new CronTrigger(netQuartBean.getTriggerName(),
                		netQuartBean.getJobgroup(), netQuartBean.getCronexpr());
                this.scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                trigger.setCronExpression(netQuartBean.getCronexpr());
                this.scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    /**
     * 禁用指定的定时任务
     * 
     * @param netQuartBean
     */
    protected void disabled(NetQuartBean netQuartBean) {
    	try {
            Trigger trigger = this.scheduler.getTrigger(netQuartBean.getTriggerName(),
            		netQuartBean.getJobgroup());
            if (null != trigger) {
                this.scheduler.deleteJob(netQuartBean.getId(), netQuartBean.getJobgroup());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void IniLoadClass() {
    	LOG.info("IniLoadClass.....");
    	Map map = new HashMap();
    	map.put("jobstatus", Constant.JS_DISABLED);
    	List<NetQuartBean> list = netQuartDAO.findNetQuartList(map);
    	for (NetQuartBean nq : list) {
    		disabled(nq);
    	}
    	map.put("jobstatus", Constant.JS_ENABLED);
    	list = netQuartDAO.findNetQuartList(map);
    	for (NetQuartBean nq : list) {
    		enabled(nq);
    	}
    }
    
    public NetQuartBean findNetQuartBean(String id) {
		return netQuartDAO.findNetQuartBean(id);
	}
    
    public List findNetQuartList(Map map, int page, int record) {
		return netQuartDAO.findNetQuartList(map,page,record);
	}
    
    public List findNetQuartList(Map map) {
		return netQuartDAO.findNetQuartList(map);
	}
		
    public String saveNetQuart(NetQuartBean netQuartBean) {
		String id = netQuartBean.getId();
		if(null == id || id.length() == 0){
			id = this.netQuartDAO.insertNetQuart(netQuartBean);
			// 根据ID获取netQuartBean
			netQuartBean = netQuartDAO.findNetQuartBean(netQuartBean.getId());
			// 获取jobstatus
			String jobstatus = netQuartBean.getJobstatus();
			// 启用
			if(Constant.JS_ENABLED.equals(jobstatus)){
				enabled(netQuartBean);
			}
			// 禁用
			if(Constant.JS_DISABLED.equals(jobstatus)){
				disabled(netQuartBean);	
			}
		}
		else{
			//this.netQuartDAO.updateNetQuart(netQuartBean);
			this.updateNetQuartJobstatus(netQuartBean);
		}
		return id;
	}
	
    public void deleteNetQuart(String id) {
    	NetQuartBean q = this.findNetQuartBean(id);
    	q.setJobstatus(Constant.JS_DISABLED);
    	disabled(q);
		this.netQuartDAO.deleteNetQuart(id);
	}
    
    public void deleteNetQuartByDate(String date) {
    	Map param = new HashMap();
    	param.put("jobgroup", 0);
    	param.put("maindate", date);
    	List<NetQuartBean> dList = this.findNetQuartList(param);
    	for (NetQuartBean nq : dList) {
			this.disabled(nq);
		}
		this.netQuartDAO.deleteNetQuartByDate(date);
	}
    
	public void updateNetQuartJobstatus(NetQuartBean netQuartBean) {
		// 更新
		netQuartDAO.updateNetQuart(netQuartBean);
		// 根据ID获取netQuartBean
		netQuartBean = netQuartDAO.findNetQuartBean(netQuartBean.getId());
		// 获取jobstatus
		String jobstatus = netQuartBean.getJobstatus();
		// 启用
		if(Constant.JS_ENABLED.equals(jobstatus)){
			enabled(netQuartBean);
		}
		// 禁用
		if(Constant.JS_DISABLED.equals(jobstatus)){
			disabled(netQuartBean);	
		}
	}
    
    // Get和Set方法
	public NetQuartDAO getNetQuartDAO() {
		return netQuartDAO;
	}

	public void setNetQuartDAO(NetQuartDAO netQuartDAO) {
		this.netQuartDAO = netQuartDAO;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
}
