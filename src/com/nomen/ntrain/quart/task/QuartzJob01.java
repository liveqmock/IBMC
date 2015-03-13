package com.nomen.ntrain.quart.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 调度器任务
 * @author 梁桂钊
 * @date 2013-09-16
 */
public class QuartzJob01{

    public void work() {
        System.out.println("Quartz01的任务调度！！！"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
    
    public void liang(){
    	System.out.println("liang!"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
