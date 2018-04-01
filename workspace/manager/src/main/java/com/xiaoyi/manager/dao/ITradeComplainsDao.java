package com.xiaoyi.manager.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.manager.domain.TradeComplains;

public interface ITradeComplainsDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tradecomplains
     *
     * @mbggenerated Sat Mar 31 10:40:32 CST 2018
     */
    int deleteByPrimaryKey(String lessontradeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tradecomplains
     *
     * @mbggenerated Sat Mar 31 10:40:32 CST 2018
     */
    int insert(TradeComplains record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tradecomplains
     *
     * @mbggenerated Sat Mar 31 10:40:32 CST 2018
     */
    int insertSelective(TradeComplains record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tradecomplains
     *
     * @mbggenerated Sat Mar 31 10:40:32 CST 2018
     */
    TradeComplains selectByPrimaryKey(String lessontradeid);

    List<JSONObject> selectAllByPrimaryKeys(List<String> lessontradeIds);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tradecomplains
     *
     * @mbggenerated Sat Mar 31 10:40:32 CST 2018
     */
    int updateByPrimaryKeySelective(TradeComplains record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tradecomplains
     *
     * @mbggenerated Sat Mar 31 10:40:32 CST 2018
     */
    int updateByPrimaryKey(TradeComplains record);
}