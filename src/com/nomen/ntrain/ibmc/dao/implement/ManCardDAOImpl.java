package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardBean;
import com.nomen.ntrain.ibmc.dao.ManCardDAO;
import com.nomen.ntrain.util.NsoftBaseDao;
@SuppressWarnings("all")
public class ManCardDAOImpl extends NsoftBaseDao implements ManCardDAO {

	public ManCardBean findManCardBeanById(String cardId){
		return (ManCardBean)this.getSqlMapClientTemplate().queryForObject("ManCard.findManCardBeanById", cardId);
	}

	public List<ManCardBean> findManCardListByPage(Map map,int tagpage,int record){
		map.put("total", this.getObjectTotal("ManCard.findManCardList", map));
		return (List<ManCardBean>)this.getSqlMapClientTemplate().queryForList("ManCard.findManCardList", map,tagpage,record);
	}

	public String insertManCardBean(ManCardBean bean){
		return (String)this.getSqlMapClientTemplate().insert("ManCard.insertManCardBean", bean);
	}

	public void updateManCardBean(ManCardBean bean){
		this.getSqlMapClientTemplate().update("ManCard.updateManCardBean", bean);
	}

	public void updateManCardDelSignById(String cardId){
		this.getSqlMapClientTemplate().update("ManCard.updateManCardDelSignById", cardId);
	}

	public void updateManCardDelSignByCommId(String commId){
		this.getSqlMapClientTemplate().update("ManCard.updateManCardDelSignByCommId", commId);
	}
	
	public ManCardBean findManCardByCardNo(String cardNo) {
		List list = this.getSqlMapClientTemplate().queryForList("ManCard.findManCardByCardNo", cardNo);
		if(null != list && list.size()>0){
			return (ManCardBean)list.get(0);
		}
		return null;
	}


	public void updateManCardValidDate(ManCardBean bean){
		this.getSqlMapClientTemplate().update("ManCard.updateManCardValidDate", bean);
	}

	public String findManCardUnLockId(String cardId) {
		return (String)this.getSqlMapClientTemplate().queryForObject("ManCard.findManCardUnLockId", cardId);
	}

	public void updateManCardUnLockId(ManCardBean bean) {
		this.getSqlMapClientTemplate().update("ManCard.updateManCardUnLockId", bean);
	}

	public List<ManCardBean> findManCardListByHouseId(String houseId) {
		return (List<ManCardBean>)this.getSqlMapClientTemplate().queryForList("ManCard.findManCardListByHouseId", houseId);
	}

	public void deleteManCardByCardId(String cardId) {
		this.getSqlMapClientTemplate().delete("ManCard.deleteManCardByCardId", cardId);
	}

	public List<ManCardBean> findManCardListByCommId(String commId) {
		return (List<ManCardBean>)this.getSqlMapClientTemplate().queryForList("ManCard.findManCardListByCommId", commId);
	}

	public void deleteManCardByCommId(String commId) {
		this.getSqlMapClientTemplate().delete("ManCard.deleteManCardByCommId", commId);
	}
	
	public List<ManCardBean> findManCardLinkNoticeMessList(Map map,int tagpage, int record) {
		map.put("total", this.getObjectTotal("ManCard.findManCardLinkNoticeMessList", map));
		return (List<ManCardBean>)this.getSqlMapClientTemplate().queryForList("ManCard.findManCardLinkNoticeMessList", map,tagpage,record);
	}

	public List<ManCardBean> findManCardListInMessImpByCommId(Map map,int tagpage, int record) {
		map.put("total", this.getObjectTotal("ManCard.findManCardListInMessImpByCommId", map));
		return (List<ManCardBean>)this.getSqlMapClientTemplate().queryForList("ManCard.findManCardListInMessImpByCommId", map,tagpage,record);
	}

	public void updateManCardBeanByCardidStr(ManCardBean manCardBean) {
		this.getSqlMapClientTemplate().update("ManCard.updateManCardBeanByCardidStr", manCardBean);
	}
}
