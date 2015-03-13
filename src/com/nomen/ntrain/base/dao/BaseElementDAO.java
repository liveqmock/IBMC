package com.nomen.ntrain.base.dao;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseElementBean;

/**
 * @description 系统管理_全局按钮表
    @author 郑学仕
    @date 2015-01-19
 */

public interface BaseElementDAO {
	/**
	 * 查询全局控件按钮列表
	 * @param 
	 * @return
	 */
	public List<BaseElementBean> findBaseElementList(Map map);
	
	/**
	 * 查询全局控件按钮列表Bean
	 * @param baseElementBean
	 * @return
	 */
	public BaseElementBean findBaseElementBean(String ele_id);
	
	/**
	 * 新增控件按钮
	 * @param baseElementBean
	 * @return
	 */
	public void insertBaseElement(BaseElementBean baseElementBean);
	
	/**
	 * 修改控件按钮
	 * @param baseElementBean
	 * @return
	 */
	public void updateBaseElement(BaseElementBean baseElementBean);
	
	/**
	 * 删除控件按钮
	 * @param id
	 * @return
	 */
	public void deleteBaseElement(String ele_id);

}
