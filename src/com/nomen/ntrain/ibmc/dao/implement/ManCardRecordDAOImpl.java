package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardRecordBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.dao.ManCardRecordDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class ManCardRecordDAOImpl extends NsoftBaseDao implements ManCardRecordDAO{

	public ManCardRecordBean findManCardRecordBeanById(String id) {
		return (ManCardRecordBean)this.getSqlMapClientTemplate().queryForObject("ManCardRecord.findManCardRecordBeanById", id);
	}

	public List<ManCardRecordBean> findManCardRecordList(Map map, int tagpage,
			int record) {
		map.put("total", this.getObjectTotal("ManCardRecord.findManCardRecordList", map));
		return (List<ManCardRecordBean>)this.getSqlMapClientTemplate().queryForList("ManCardRecord.findManCardRecordList", map,tagpage,record);
	}

	public void saveSynUnlockData(ManCardRecordBean recordBean) {
		this.getSqlMapClientTemplate().update("ManCardRecord.saveSynUnlockData", recordBean);
	}

	public void deleteManCardRecordById(String id) {
		this.getSqlMapClientTemplate().delete("ManCardRecord.deleteManCardRecordById", id);
	}

	public void deleteManCardRecordByCommId(String commPath) {
		this.getSqlMapClientTemplate().delete("ManCardRecord.deleteManCardRecordByCommId", commPath);
	}

	public ManPeoBean findManPeoBeanByCardId(String cardid) {
		return (ManPeoBean)this.getSqlMapClientTemplate().queryForObject("ManCardRecord.findManPeoBeanByCardId", cardid);
	}

	public int findManCardRecordMaxSynId(Map map) {
		return Integer.parseInt((String)this.getSqlMapClientTemplate().queryForObject("ManCardRecord.findManCardRecordMaxSynId", map));
	}

}
