package com.nomen.ntrain.base.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.nomen.ntrain.base.bean.BaseMenuBean;
import com.nomen.ntrain.base.service.BaseMenuService;
import com.nomen.ntrain.util.Constant;
import com.nomen.ntrain.util.Dom4jHelper;

/**
 * 角色/菜单维护应用
 * @date   2013-11-13
 */
public class BaseMenuAction extends BaseAction {
	private static final long 	serialVersionUID = 1L;
	private static final Log 	LOG = LogFactory.getLog(BaseMenuAction.class);
	private BaseMenuService 	baseMenuService;
	private Map<String,String>  querymap;           //参数集合
	private BaseMenuBean	 	baseMenuBean;     	//代码信息表
    
    //菜单链接跳转
    public String toForwardMenu(){
    	if(this.querymap==null){
			this.querymap = new HashMap<String,String>();
		}
    	//实现对查询条件的构造
		this.querymap.put("fatherid", Constant.MENU_FATHERID_LEV1);
    	return SUCCESS;
    }
	
	/**
     * 角色/菜单维护 列表
     * @return
     */
	public void  listBaseMenuByJq(){
		
		HttpServletRequest req = ServletActionContext.getRequest();
		if(this.querymap==null){
			this.querymap = new HashMap<String,String>();
		}
		//查询
		Map<String,String> param = new HashMap<String,String>();
		param.put("fatherid",  Constant.MENU_FATHERID_LEV1);
		List dataList = this.baseMenuService.findBaseMenuTreeList(param);
		this.printList(dataList);
	}
	
	/**
     * 菜单维护[树状]
     * @return
     */
	public void  listBaseMenuTreeByJq(){
		HttpServletRequest req = ServletActionContext.getRequest();
		if(this.querymap==null){
			this.querymap = new HashMap<String,String>();
		}
		//查询
		Map<String,String> param = new HashMap<String,String>();
		param.put("fatherid",  Constant.MENU_FATHERID_LEV1);
		List dataList = this.baseMenuService.findBaseMenuTreeList(param);
		this.printList(dataList);
	}
	
	/**
	 * 角色/菜单维护 一级跳转
	 * @return
	 */
	public String setBaseMenu1(){
		try {
			if(func.Trim(this.fun).equals("1")){
				this.baseMenuBean = new BaseMenuBean();
				this.baseMenuBean.setPur("0");
				this.baseMenuBean.setSpecsign("0");
				//查询下一个排序号
				this.baseMenuBean.setSortnum(
						this.baseMenuService.findBaseMenuNextSort(func.Trim(this.querymap.get("fatherid"))));
				LOG.info("菜单列表一级跳转新增成功！");
			}else{
				//修改，根据id查询该记录
				this.baseMenuBean = this.baseMenuService.findBaseMenuBeanById(baseMenuBean.getId());
				LOG.info("菜单列表一级跳转修改成功！");
			}
		} catch (Exception e) {
			LOG.error("菜单列表一级跳转失败！");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 角色/菜单维护 二级跳转
	 * @return
	 */
	public String setBaseMenu2(){
		try {
			if(func.Trim(this.fun).equals("1")){
				this.baseMenuBean.setPur("0");
				this.baseMenuBean.setSpecsign("0");
				//查询上级节点的lev
				BaseMenuBean menuBean = this.baseMenuService.findBaseMenuBeanById(func.Trim(this.baseMenuBean.getFatherid()));
				String lev = menuBean.getLev();
				lev = (func.Cint(lev)+1)+"";
				this.baseMenuBean.setLev(lev);
				//查询下一个排序号				
				this.baseMenuBean.setSortnum(
					this.baseMenuService.findBaseMenuNextSort(func.Trim(this.baseMenuBean.getFatherid())));
				LOG.info("菜单列表二级跳转新增成功！");
			}else{
				//修改，根据id查询该记录
				this.baseMenuBean = this.baseMenuService.findBaseMenuBeanById(baseMenuBean.getId());
				LOG.info("菜单列表二级跳转修改成功！");
			}
		} catch (Exception e) {
			LOG.error("菜单列表二级跳转失败！");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 角色/菜单维护 保存
	 * @return
	 */
	public String saveBaseMenu(){
		try{
			if(this.isValidToken()){
				this.baseMenuService.saveBaseMenu(baseMenuBean);
				LOG.info("菜单列表保存成功！");
				this.reloadParentPage();
			}
		}catch(Exception e){
			LOG.error("菜单列表保存失败！");
			e.printStackTrace();
		}
		return Constant.NO_DATA;
	}
	
	/***************************************************/
	/******************* JQUERY  ***********************/
	/***************************************************/
	
	/**
	 * 菜单列表 删除
	 */
	public void deleteBaseMenuByJq(){
		try {
			String id = this.baseMenuBean.getId();
			if(null != id || id.length() != 0){
				this.baseMenuService.deleteBaseMenu(id);
				this.print("1");
				LOG.info("菜单列表删除成功！");
			}
		} catch(Exception ex) {
			this.print("-1");
			LOG.error("菜单列表删除失败！");
			ex.printStackTrace();
		}
	}
	
	/**
	 *  菜单列表 启用/禁用
	 */
	public void updateBaseMenuUsesignByJq(){
		
		try {
			this.baseMenuService.updateBaseMenuUsesign(baseMenuBean);
			this.print("1");
			LOG.info("菜单列表启用/禁用成功！");
		} catch (Exception e) {
			this.print("-1");
			LOG.error("菜单列表启用/禁用失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 菜单列表 核查菜单链接是否存在
	 */
	public void checkmenuUrlByJq(){
		String filename = getClass().getResource("/com/nomen/ntrain/ptrain/config/action/").getFile();
		try {
			// 获取所有文件名
			File file = new File(filename);
			File[] files = file.listFiles();
			List<String> fileList = new ArrayList<String>();
			for (File f : files) {
				fileList.add(f.getName());
			}
			
			// 获取所有菜单链接
			String xmlpath = "";
			List<String> namespaceList = null;
			List<String> actionnameList = null;
			List<String> dataList = new ArrayList<String>();	
			for (String fn : fileList) {
				xmlpath = filename+fn;
				namespaceList = Dom4jHelper.selectStrutsNodesByAttribute(xmlpath, "//struts/package", "namespace");
				actionnameList = Dom4jHelper.selectStrutsNodesByAttribute(xmlpath, "//struts/package/action", "name");
				for (String actionname : actionnameList) {
					dataList.add(namespaceList.get(0)+"/"+actionname+".shtml");
				}
			}
			namespaceList = null;
			actionnameList = null;
			if(dataList.contains(this.baseMenuBean.getUrl())){
				this.print("true");
			}
			else{
				this.print("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.print("err");
		}
	}
	
	//Get和Set方法
	public BaseMenuService getBaseMenuService() {
		return baseMenuService;
	}

	public void setBaseMenuService(BaseMenuService baseMenuService) {
		this.baseMenuService = baseMenuService;
	}

	public BaseMenuBean getBaseMenuBean() {
		return baseMenuBean;
	}

	public void setBaseMenuBean(BaseMenuBean baseMenuBean) {
		this.baseMenuBean = baseMenuBean;
	}

	public Map<String, String> getQuerymap() {
		return querymap;
	}

	public void setQuerymap(Map<String, String> querymap) {
		this.querymap = querymap;
	}
}
