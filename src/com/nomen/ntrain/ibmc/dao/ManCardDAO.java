package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardBean;

/**
 * @description 门卡管理_门卡库表数据库操作接口
 * @author ljl
 * @date 2015-1-23
 */
public interface ManCardDAO {

	/**
	 * 查找门卡管理_门卡库表信息
	 * @param cardId
	 * @return
	 */
	public ManCardBean findManCardBeanById(String cardId);

	/**
	 * 查找门卡管理_门卡库表列表信息
	 * @param map {
	 *                houseid   房产（ID）
	 *                cardtype  门卡类型（1业主卡 2临时卡 3正式卡）
	 *                [cardmac] 门卡内置ID
	 *            }
	 * @return
	 */
	public List<ManCardBean> findManCardListByPage(Map map,int tagpage,int record);

	/**
	 * 添加门卡管理_门卡库表信息
	 * @param cardCardBean
	 * @return
	 */
	public String insertManCardBean(ManCardBean bean);

	/**
	 * 更新门卡管理_门卡库表信息
	 * @param cardCardBean
	 * @return
	 */
	public void updateManCardBean(ManCardBean bean);

	/**
	 * 删除门卡管理_门卡库表信息[虚拟]
	 * @param cardId
	 * @return
	 */
	public void updateManCardDelSignById(String cardId);

	/**
	 * 删除村/房屋/房间对应的门卡[虚拟]
	 * @param cardId
	 * @return
	 */
	public void updateManCardDelSignByCommId(String commId);
	
	/**
	 * 通过门卡(序列号)查询门卡类型(业主卡/正式卡/临时卡)
	 * @param cardNo
	 * @return
	 */
	public ManCardBean findManCardByCardNo(String cardNo);
	
	/**
	 * 重置门卡
	 * @param cardId
	 */
	public void updateManCardValidDate(ManCardBean bean);
	
	/**
	 * 通过主记录ID查询 其webservice对应的门卡ID
	 * @param cardId
	 * @return
	 */
	public String findManCardUnLockId(String cardId);
	
	/**
	 * 更新webservice返回的ID至 unlockid
	 * @param bean
	 * @return
	 */
	public void updateManCardUnLockId(ManCardBean bean);

	/**
	 * 查找房产下所有的门卡列表(包括状态为已删除的记录)
	 * @param houseId
	 * @return
	 */
	public List<ManCardBean> findManCardListByHouseId(String houseId);

	/**
	 * 删除门卡数据[真实]
	 * @param cardId
	 * @return
	 */
	public void deleteManCardByCardId(String cardId);


	/**
	 * 查找[省/市/区/村/房产/房间]下的门卡
	 * @param  commId 省/市/区/村/房产/房间ID
	 * @return
	 */
	public List<ManCardBean> findManCardListByCommId(String commId);

	/**
	 * 删除[省/市/区/村/房产/房间ID]门卡数据[真实]
	 * @param commId  省/市/区/村/房产/房间ID
	 * @return
	 */
	public void deleteManCardByCommId(String commId);
	
	/**
	 * 查询门卡刷卡发送短信记录
	 * @param map { startdate   开始时间
	 *                enddate  结束时间
	 *            }
	 * @return
	 */
	public List<ManCardBean> findManCardLinkNoticeMessList(Map map,int tagpage,int record);

	/**
	 * 短信通知管理中引入门卡列表
	 * @param map {
	 * 			commpath 区域代码
	 * 			userid 用户id
	 * 			commlev 层级
	 * 			fields  关键字段
	 * 			keyword 关键字
	 *          }
	 * @return
	 */
	public List<ManCardBean> findManCardListInMessImpByCommId(Map map,int tagpage,int record);

	/**
	 * 更新正式卡的使用期限
	 * @param cardCardBean
	 * @return
	 */
	public void updateManCardBeanByCardidStr(ManCardBean manCardBean);
}
