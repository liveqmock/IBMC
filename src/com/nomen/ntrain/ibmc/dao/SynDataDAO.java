package com.nomen.ntrain.ibmc.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.ibmc.bean.SynDataBean;

/**
 * @description 记录操作数据_用于同webservice同步数据库操作接口
 * @author ljl
 * @date 2015-1-29
 */
public interface SynDataDAO {

	/**
	 * 通过ID查询
	 * @param map
	 * @return
	 */
	public SynDataBean findSynDataBeanById(String id);

	/**
	 * 查询需要同步的数据的数量
	 * @param   commId  区域Id
	 * @return
	 */
	public int findNeedSynDataCount(String commId);

	/**
	 * 查询需要同步的数据（待同步、以及同步失败的数据）
	 * @param   commId  区域Id
	 * @return
	 */
	public List<SynDataBean> findNeedSynDataList(String commId);

	/**
	 * 查询同步失败的数据
	 *    需要按ID同步
	 * @return
	 */
	public List<SynDataBean> findErrSynDataList();

	/**
	 * 添加记录操作数据_用于同webservice同步信息
	 * @param modeSign    模块标记 参考 IbmcConstant
	 * @param optId       操作记录ID
	 * @param optType     操作类型
	 * @param commPath    该记录所属的区域 （-1表示需要 重新查询该路径，否则以传递的路径为准）
	 *                        1、若该记录属于 房卡：则记录房间路径
	 *                        2、若该记录属于房产、房间、村、区、市等，则记录其自身路径
	 *                        3、若该记录属于房产同门口机关联，则记录房产路径
	 * @param webServicePath
	 * @return
	 */
	public String insertSynDataBean(SynDataBean synDataBean);

	/**
	 * 更新记录操作数据_用于同webservice同步信息
	 * @param synDataBean
	 * @return
	 */
	public void updateSynDataBean(SynDataBean synDataBean);

	/**
	 * 删除记录操作数据_用于同webservice同步信息
	 * @param map
	 * @return
	 */
	public void deleteSynData(Map map);
	

	/**
	 * 通过ID获取记录在Community表中的 commpath 
	 * @param commId
	 * @return
	 */
	public String findSysCommPathById(String commId);
	

	/**
	 * 通过ID获取记录在Community表中的任务调度状态
	 * @param commId
	 * @return
	 */
	public String findSysCommTaskSignById(String commId);
	
	/**
	 * 更新任务状态
	 * @param commId
	 */
	public void   updateSysCommTaskSignById(String taskSign,String commId);
}
