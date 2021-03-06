package com.xiaoyi.manager.dao;

import java.util.List;

import com.xiaoyi.manager.domain.SendTmpMsgFailed;

public interface ISendTmpMsgFailedDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table send_tmp_msg_failed
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    int deleteByPrimaryKey(String lessontradeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table send_tmp_msg_failed
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    int insert(SendTmpMsgFailed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table send_tmp_msg_failed
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    int insertSelective(SendTmpMsgFailed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table send_tmp_msg_failed
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    SendTmpMsgFailed selectByPrimaryKey(String lessontradeid);

    /**
     * 查找提现超过5天的记录
     * @return
     */
    List<SendTmpMsgFailed> selectByRepeatTimes();
    
    /**
     * 最近5天的提现记录repeatedTime+1
     * @return
     */
    int updateRepeatTimes();
    
    /**
     * 更新重传成功的消息状态
     */
    int updateRepeatTimesByIds(List<String> lessonTradeIds);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table send_tmp_msg_failed
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    int updateByPrimaryKeySelective(SendTmpMsgFailed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table send_tmp_msg_failed
     *
     * @mbggenerated Tue Apr 10 09:47:44 CST 2018
     */
    int updateByPrimaryKey(SendTmpMsgFailed record);
}