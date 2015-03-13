package com.nomen.ntrain.ibmc.dao;

import java.util.List;

import com.nomen.ntrain.ibmc.bean.SysCommunityBean;

public interface SynWebServiceDAO {
	/**
	 *  通过ID查询出本身和上级（市、区、村、房产、房间）列表[向上倒查]
	 * @param commId
	 * @return
	 */
	public List<SysCommunityBean> findSysCommParentListByCommId(String commId);
	
	/**
	 *  通过ID查询本身和下级（市、区、村、房产、房间）列表[向下倒查]
	 * @param commId
	 * @return
	 */
	public List<SysCommunityBean> findSysCommChildsListByCommId(String commId);
	
	/**
	 *  通过ID修改其下行状态
	 * @param downSign  
	 *        commId
	 * @return
	 */
	public void updateSysCommDownSign(SysCommunityBean bean);
}
