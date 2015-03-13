package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardRecordBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockRecordInfo;

/**
 * @description 刷卡历史
 * @author ljl
 * @date 2015-2-02
 */
public interface ManCardRecordService {

	/**
	 * 刷卡详细
	 * @param id
	 * @return
	 */
	public ManCardRecordBean findManCardRecordBeanById(String id);

	/**
	 * 刷卡列表
	 * @param map {
	 *                commpath  市/区/村/房产
	 *                cardtype  门卡类型（1业主卡 2临时卡 3正式卡）
	 *                [fields]  
	 *                [keyword] 
	 *            }
	 * @return
	 */
	public List<ManCardRecordBean> findManCardRecordList(Map map,int tagpage,int record);

	/**
	 * 保存刷卡记录
	 * @param bean {cardno,equipid,unlockdate,imagepath}
	 * @return
	 */
	public void saveSynUnlockData(UnlockRecordInfo[] arr);

	/**
	 * 删除刷卡记录
	 * @param cardCardBean
	 * @return
	 */
	public void deleteManCardRecordById(String id);

	/**
	 * 删除[省/市/区/村/房产/房间ID]刷卡数据
	 * @param commPath  市/区/村/房产/房间ID
	 * @return
	 */
	public void deleteManCardRecordByCommId(String commPath);
	
	/**
	 * 查询刷卡租户详细信息
	 * @param cardid 门卡id
	 * @return
	 */
	public ManPeoBean findManPeoBeanByCardId(String cardid);
	
	/**
	 * 返回最大的synId
	 * @param map
	 * @return
	 */
	public int findManCardRecordMaxSynId(Map map);

}
