package com.nomen.ntrain.ibmc.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.ManCardBean;

/**
 * @description 门卡管理_门卡库表业务逻辑层
 * @author ljl
 * @date 2015-1-23
 */
public interface ManCardService {

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
	 *                [fields]   
	 *                [keyword] 
	 *            }
	 * @return
	 */
	public List<ManCardBean> findManCardListByPage(Map map,int tagpage,int record);

	/**
	 * 添加门卡管理_门卡库表信息
	 * @param cardCardBean
	 * @return
	 */
	public String saveManCardBean(ManCardBean cardCardBean);

	/**
	 * 删除门卡管理_门卡库表信息
	 * @param cardId
	 * @return
	 */
	public void deleteManCardById(String cardId);
	
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
	public void updateManCardValidDate(String cardId,String operUserId);
	
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
	 * @param cardidstr  正式卡主键idstr
	 *        houseidstr 房产houseidstr
	 */
	public void updateManCardBeanByCardidStr(String cardidstr,String houseidstr);
}
