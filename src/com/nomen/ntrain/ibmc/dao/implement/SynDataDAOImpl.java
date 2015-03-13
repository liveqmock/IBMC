package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SynDataBean;
import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.dao.SynDataDAO;
import com.nomen.ntrain.util.NsoftBaseDao;
@SuppressWarnings("all")
public class SynDataDAOImpl extends NsoftBaseDao implements SynDataDAO {

	public SynDataBean findSynDataBeanById(String id){
		return (SynDataBean)this.getSqlMapClientTemplate().queryForObject("SynData.findSynDataBeanById", id);
	}

	public int findNeedSynDataCount(String commId){
		return Integer.parseInt((String)this.getSqlMapClientTemplate().queryForObject("SynData.findNeedSynDataCount",commId));
	}

	public List<SynDataBean> findNeedSynDataList(String commId){
		return (List<SynDataBean>)this.getSqlMapClientTemplate().queryForList("SynData.findNeedSynDataList",commId);
	}

	public List<SynDataBean> findErrSynDataList(){
		return (List<SynDataBean>)this.getSqlMapClientTemplate().queryForList("SynData.findErrSynDataList");
	}

	public void updateSynDataBean(SynDataBean synDataBean){
		this.getSqlMapClientTemplate().update("SynData.updateSynDataBean", synDataBean);
	}

	public void deleteSynData(Map map){
		this.getSqlMapClientTemplate().delete("SynData.deleteSynData", map);
	}

	public String insertSynDataBean(SynDataBean synDataBean) {
		return (String)this.getSqlMapClientTemplate().insert("SynData.insertSynDataBean", synDataBean);
	}

	public String findSysCommPathById(String commId) {
		return (String)this.getSqlMapClientTemplate().queryForObject("SynData.findSysCommPathById", commId);
	}

	public String findSysCommTaskSignById(String commId) {
		return (String)this.getSqlMapClientTemplate().queryForObject("SynData.findSysCommTaskSignById", commId);
	}

	public void updateSysCommTaskSignById(String taskSign, String commId) {
		SysCommunityBean commBean = new SysCommunityBean();
		commBean.setTasksign(taskSign);
		commBean.setId(commId);
		this.getSqlMapClientTemplate().update("SynData.updateSysCommTaskSignById", commBean);
	}
	
}
