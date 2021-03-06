package com.xiaoyi.custom.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface ICustomService {
	
	List<JSONObject> queryTransactionCourses(String openId);
	
	/**
	 * ii.	新增预约
	 * {
	 * 		orderType(on/of),
	 * 		grade(),
	 * 		subject(),
	 * 		studentName,
	 * 		telphone,
	 * 		weixin
	 * }
	 * @return 
	 */
	int commitSchedule(JSONObject params);
	
	/**
	 * 
	 * @param openId
	 * @return
	 */
	List<JSONObject> getMySchedules(String openId);
	
	int confirmTRecords(JSONObject params) throws RuntimeException;
	
	/**
	 * 获取学情分析报告
	 */
	List<JSONObject> queryStuTeachingReport(JSONObject params);
	
	/**
	 * 家长投诉
	 */
	int insertComplains(JSONObject params);
	
	/**
	 * 获取补缴金额
	 */
	JSONObject getDebt(JSONObject params);
	//获取家长对应的学生列表
	public List<JSONObject> getPSRList(String openid);
	//获取某月学生上课记录详情
	public List<JSONObject> getStuTeachingDetailByMonth(JSONObject reqData);
}
