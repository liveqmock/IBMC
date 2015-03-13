package com.nomen.ntrain.base.service.implement;
import com.nomen.ntrain.base.dao.BaseDAO;
import com.nomen.ntrain.ibmc.bean.SynDataBean;
import com.nomen.ntrain.ibmc.dao.SynDataDAO;
import com.nomen.ntrain.util.PubFunc;

public abstract class BaseServiceImpl{  
	private BaseDAO baseDAO; 
	private SynDataDAO      synDataDAO;
	protected PubFunc func = new PubFunc();
 
	/**
	 * 新增数据到同步表中
	 * @param modeSign     模块标记 参考 IbmcConstant
	 * @param optId        操作记录ID
	 * @param optType      操作类型
	 * @param commPath     空/值  该记录所属的区域 （空表示需要 重新查询该路径，否则以传递的路径为准）
	 *                        1、若该记录属于 房卡：则记录房屋路径
	 *                        2、若该记录属于房产、房间、村、区、市等，则记录其自身路径
	 *                        3、若该记录属于房产同门口机关联，则记录房产路径
	 * @param recId        关联数据ID（Community表中的某条记录）：
	 *                        1、若该记录属于 房卡：则该值为房屋ID
	 *                        2、若该记录属于房产同门口机关联，则为房屋ID
	 *                   
	 */
	public String saveSynData(String modeSign,String optId,String optType,String commPath,String recId){
		if(func.IsEmpty(commPath)){
		    //需要重新查询该路径
			commPath = makeCommPath(recId);
		}
		
		//查询记录所属webservice路径
		String webServicePath = "";
		SynDataBean synDataBean = new SynDataBean();
		synDataBean.setModesign(modeSign);
		synDataBean.setOptid(optId);
		synDataBean.setOpttype(optType);
		synDataBean.setCommpath(commPath);
		synDataBean.setWebservicepath(webServicePath);
		return this.synDataDAO.insertSynDataBean(synDataBean);
	}
	
	private String makeCommPath(String commId){		
		return this.synDataDAO.findSysCommPathById(commId);
	}
	
	//set get 
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public SynDataDAO getSynDataDAO() {
		return synDataDAO;
	}

	public void setSynDataDAO(SynDataDAO synDataDAO) {
		this.synDataDAO = synDataDAO;
	}
	
}
