package com.nomen.ntrain.ibmc.dao.implement;

import java.util.List;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;
import com.nomen.ntrain.ibmc.dao.SynWebServiceDAO;
import com.nomen.ntrain.util.NsoftBaseDao;

public class SynWebServiceDAOImpl extends NsoftBaseDao implements SynWebServiceDAO {

	public List<SysCommunityBean> findSysCommParentListByCommId(String commId) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SynWebService.findSysCommParentListByCommId", commId);
	}

	public List<SysCommunityBean> findSysCommChildsListByCommId(String commId) {
		return (List<SysCommunityBean>)this.getSqlMapClientTemplate().queryForList("SynWebService.findSysCommChildsListByCommId", commId);
	}

	public void updateSysCommDownSign(SysCommunityBean bean) {
		this.getSqlMapClientTemplate().update("SynWebService.updateSysCommDownSign",bean);
	}

}
