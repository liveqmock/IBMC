package com.nomen.ntrain.ibmc.service.implement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

import com.nomen.ntrain.base.service.implement.BaseServiceImpl;
import com.nomen.ntrain.ibmc.bean.ManPeoBean;
import com.nomen.ntrain.ibmc.constant.IbmcConstant;
import com.nomen.ntrain.ibmc.dao.ManPeoDAO;
import com.nomen.ntrain.ibmc.excel.IbmcExcelOutForJxlImpl;
import com.nomen.ntrain.ibmc.service.ManPeoService;

public class ManPeoServiceImpl extends BaseServiceImpl implements ManPeoService {

	private ManPeoDAO manPeoDAO;
	
	public List<ManPeoBean> findManPeoList(Map map, int page, int record) {
		return this.manPeoDAO.findManPeoList(map, page, record);
	}
	
	public List<ManPeoBean> findManPeoList(Map map) {
		return this.manPeoDAO.findManPeoList(map);
	}
	
	public ManPeoBean findManPeoBeanById(String id) {
		Map map = new HashMap();
		map.put("id", id);
		return this.manPeoDAO.findManPeoBean(map);
	}
	
	public ManPeoBean findManPeoBean(Map map) {
		return this.manPeoDAO.findManPeoBean(map);
	}

	public boolean findManPeoIsExist(String idcard) {
		return this.manPeoDAO.findManPeoIsExist(idcard);
	}

	public String insertManPeoBean(ManPeoBean manPeoBean) {
		return this.manPeoDAO.insertManPeoBean(manPeoBean);
	}

	public void updateManPeoBean(ManPeoBean manPeoBean) {
		this.manPeoDAO.updateManPeoBean(manPeoBean);
	}
	
	public String saveManPeo(Map map) {
		ManPeoBean manPeoBean = (ManPeoBean) map.get("manPeoBean");
		String fileFolder = (String) map.get("fileFolder");
		String idcard = manPeoBean.getIdcard();
		fileFolder = fileFolder+idcard.substring(0, 6);  //构建上传图片的文件夹路径[取身份证号码前6位数字]
		String id = manPeoBean.getId();
		try {
			String photoBase64 = manPeoBean.getPhotobase64();
			BASE64Decoder decoder = new BASE64Decoder();
			File file=new File(fileFolder);
			if(!file.exists()) {
				file.mkdir();
			}
			String pathfile = fileFolder+"\\"+idcard+".jpg";  //构建上传图片文件的路径
			FileOutputStream write = new FileOutputStream(pathfile);
			byte[] decoderBytes = decoder.decodeBuffer(photoBase64);
			write.write(decoderBytes);
		    write.close();
		    
		    manPeoBean.setPhotopath(IbmcConstant.MAN_PER_PHOTO_PATH+idcard.substring(0, 6)+"/"+idcard+".jpg");
			if(func.IsEmpty(id)){
				id = this.manPeoDAO.insertManPeoBean(manPeoBean);
			}else{
				this.manPeoDAO.updateManPeoBean(manPeoBean);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void deleteManPeo(Map map) {
		this.manPeoDAO.deleteManPeo(map);
	}

	public void deleteManPeoByIdCard(String idcard) {
		this.manPeoDAO.deleteManPeoByIdCard(idcard);
	}
	
	public void saveManPeoExpExcel(Map map, HttpServletResponse response) {
		List<ManPeoBean> dataList = this.manPeoDAO.findManPeoList(map);
		IbmcExcelOutForJxlImpl ibmcExp = new IbmcExcelOutForJxlImpl();
		ibmcExp.expManPeo(dataList, response);
	}
	
	public List<ManPeoBean> findManPeoListByCardno(Map map) {
		return this.manPeoDAO.findManPeoListByCardno(map);
	}
	
	//生成get,set方法
	public ManPeoDAO getManPeoDAO() {
		return manPeoDAO;
	}

	public void setManPeoDAO(ManPeoDAO manPeoDAO) {
		this.manPeoDAO = manPeoDAO;
	}
	
}
