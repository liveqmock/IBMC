package com.nomen.ntrain.quart.task;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @description 调度器任务
 * @author 梁桂钊
 * @date 2013-09-16
 */
public class QuartzJob{
	
    public void hello() {
        System.out.println("Quartz-hello的任务调度！！！"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public void world(String username,String age) {
        System.out.println(username+"::"+age+":Quartz-world的任务调度！！！"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
