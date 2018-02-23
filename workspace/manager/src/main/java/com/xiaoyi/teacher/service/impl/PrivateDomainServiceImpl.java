package com.xiaoyi.teacher.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.common.utils.ConstantUtil;
import com.xiaoyi.manager.dao.ITeacherDao;
import com.xiaoyi.manager.domain.Teacher;
import com.xiaoyi.teacher.dao.IPrivateDomainDao;
import com.xiaoyi.teacher.service.IPrivateDomainService;

@Service("domainService")
public class PrivateDomainServiceImpl implements IPrivateDomainService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ITeacherDao teacherDao;
	
	@Resource
	private IPrivateDomainDao domainDao;
	
	@Override
	public Short getSignStatus(JSONObject params) throws Exception {
		// TODO Auto-generated method stub
		try {
			Teacher teacher = teacherDao.selectByPrimaryKey(params.getString("teacherId"));
			if(null!=teacher) {
				return teacher.getSigned();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return -1;
	}

	@Override
	public int setAgreement(JSONObject params)  throws Exception{
		try {
			Teacher teacher = new Teacher();
			teacher.setTeacherid(params.getString("teacherId"));
			teacher.setSigned((short)1);
			return teacherDao.updateByPrimaryKeySelective(teacher);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public JSONObject queryPrivateMsg(JSONObject params) throws Exception {
		JSONObject result = new JSONObject();
		try {
			try {
				result = domainDao.selectDomainByTeacherId(params);			
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
			if(null!=result){
				String schoolId = result.getString("schoolId");
				String gradeId = result.getString("gradeId");				
				
				try {
					List<Map<String, Object>> schoolGradeList = domainDao.selectSchoolDetailById(params);
					
					if(!CollectionUtils.isEmpty(schoolGradeList)){
						for(Map<String,Object> schoolGrade : schoolGradeList){
							result.put("schoolName", schoolGrade.get(schoolId));
							result.put("gradeName", "");
						}
					}
				} catch (Exception e) {
					logger.error("查询学校出错！");
				}
			}

			return result;
		} catch (Exception e) {
			logger.error("内部错误！");
		}
		return null;
	}



}