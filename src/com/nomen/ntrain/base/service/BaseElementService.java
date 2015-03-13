package com.nomen.ntrain.base.service;

import java.util.List;
import java.util.Map;

import com.nomen.ntrain.base.bean.BaseElementBean;

/**
 * @description 系统管理_全局按钮表
    @author 郑学仕
    @date 2015-01-19
 */
public interface BaseElementService {
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
	 * 保存（新增/修改操作）
	 * @param baseElementBean
	 * @return
	 */
	public void saveBaseElement(BaseElementBean baseElementBean);
	
	
	/**
	 * 删除控件按钮
	 * @param ele_id
	 * @return
	 */
	public void deleteBaseElement(String ele_id);

}
