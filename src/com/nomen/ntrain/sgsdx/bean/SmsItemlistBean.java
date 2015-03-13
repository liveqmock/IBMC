package com.nomen.ntrain.sgsdx.bean;

/**
 * @description 短信接口_短信内容POJO 
 * @author 林木山
 * @date 2012-7-30
 */
public class SmsItemlistBean {
    private String id;       //ID（主键）
    private String username; //接收人姓名
    private String phone;    //接收人手机号
    private String sms;      //信息内容
    private String sendsign; //信息发送结果（1成功，0失败）
    private String sendtime; //最后一次发送时间
    private String sendcount;//已发送次数

	//Get和Set方法
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSms() {
        return sms;
    }
    public void setSms(String sms) {
        this.sms = sms;
    }
    public String getSendsign() {
        return sendsign;
    }
    public void setSendsign(String sendsign) {
        this.sendsign = sendsign;
    }
    public String getSendtime() {
        return sendtime;
    }
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
    public String getSendcount() {
        return sendcount;
    }
    public void setSendcount(String sendcount) {
        this.sendcount = sendcount;
    }
}
