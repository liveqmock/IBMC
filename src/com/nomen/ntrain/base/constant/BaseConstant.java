package com.nomen.ntrain.base.constant;

import java.util.ArrayList;
import java.util.List;

import com.nomen.ntrain.base.util.ButtonClassBean;

public final class BaseConstant {
	/**
	 * 构建按钮样式列表
	 */
	public static List<ButtonClassBean> btnClassList(){
		List<ButtonClassBean> classList = new ArrayList();
		ButtonClassBean cB = new ButtonClassBean();
		cB.setBtnClass("btn-disable");
		cB.setBtnValue("白色");
		classList.add(cB);
		cB = new ButtonClassBean();
		cB.setBtnClass("btn-info");
		cB.setBtnValue("蓝色");
		classList.add(cB);
		cB = new ButtonClassBean();
		cB.setBtnClass("btn-danger");
		cB.setBtnValue("红色");
		classList.add(cB);
		return classList;
	}
}
