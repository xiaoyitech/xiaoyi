package com.xiaoyi.manager.domain;

import java.util.Date;

public class SendTmpMsgFailed {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column send_tmp_msg_failed.lessonTradeId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    private String lessontradeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column send_tmp_msg_failed.sendTime
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    private Date sendtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column send_tmp_msg_failed.openId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    private String openid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column send_tmp_msg_failed.msgId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    private String msgid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column send_tmp_msg_failed.lessonTradeId
     *
     * @return the value of send_tmp_msg_failed.lessonTradeId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public String getLessontradeid() {
        return lessontradeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column send_tmp_msg_failed.lessonTradeId
     *
     * @param lessontradeid the value for send_tmp_msg_failed.lessonTradeId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public void setLessontradeid(String lessontradeid) {
        this.lessontradeid = lessontradeid == null ? null : lessontradeid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column send_tmp_msg_failed.sendTime
     *
     * @return the value of send_tmp_msg_failed.sendTime
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column send_tmp_msg_failed.sendTime
     *
     * @param sendtime the value for send_tmp_msg_failed.sendTime
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column send_tmp_msg_failed.openId
     *
     * @return the value of send_tmp_msg_failed.openId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column send_tmp_msg_failed.openId
     *
     * @param openid the value for send_tmp_msg_failed.openId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column send_tmp_msg_failed.msgId
     *
     * @return the value of send_tmp_msg_failed.msgId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public String getMsgid() {
        return msgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column send_tmp_msg_failed.msgId
     *
     * @param msgid the value for send_tmp_msg_failed.msgId
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    public void setMsgid(String msgid) {
        this.msgid = msgid == null ? null : msgid.trim();
    }
}