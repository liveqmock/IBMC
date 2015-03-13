package com.nomen.ntrain.ibmc.service.implement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManCardRecordBean;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.dao.ManCardRecordDAO;
import com.nomen.ntrain.ibmc.service.ManCardRecordService;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockInfo;
import com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockRecordInfo;

public class ManCardRecordServiceImpl extends BaseServiceImpl implements
		ManCardRecordService{
	
	private ManCardRecordDAO manCardRecordDAO;
	public ManCardRecordBean findManCardRecordBeanById(String id) {
		return this.manCardRecordDAO.findManCardRecordBeanById(id);
	}

	public List<ManCardRecordBean> findManCardRecordList(Map map, int tagpage,int record) {
		return this.manCardRecordDAO.findManCardRecordList(map,tagpage,record);
	}

	public void saveSynUnlockData(UnlockRecordInfo[] arr) {
		ManCardRecordBean bean = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(UnlockRecordInfo inf : arr){
			bean = new ManCardRecordBean();
			bean.setSynid(inf.getUnlockId()+"");
			bean.setCardno(inf.getPwdOrSerial());
			bean.setCardid(inf.getIcId()+"");
			bean.setEquipid(inf.getEquId());
			//bean.setRoomid(inf.getRoomId());
			Calendar cd = inf.getUnlockTime();
			bean.setTouchdate(df.format(cd.getTime()));
			bean.setTouchimg(inf.getImagePath());
			this.manCardRecordDAO.saveSynUnlockData(bean);
		}
	}

	public void deleteManCardRecordById(String id) {
		this.manCardRecordDAO.deleteManCardRecordById(id);
	}

	public void deleteManCardRecordByCommId(String commPath) {
		this.manCardRecordDAO.deleteManCardRecordByCommId(commPath);
	}

	public ManPeoBean findManPeoBeanByCardId(String cardid) {
		return this.manCardRecordDAO.findManPeoBeanByCardId(cardid);
	}

	public int findManCardRecordMaxSynId(Map map) {
		return this.manCardRecordDAO.findManCardRecordMaxSynId(map);
	}
	
	//以下为set get方法
	public ManCardRecordDAO getManCardRecordDAO() {
		return manCardRecordDAO;
	}

	public void setManCardRecordDAO(ManCardRecordDAO manCardRecordDAO) {
		this.manCardRecordDAO = manCardRecordDAO;
	}
	
}
