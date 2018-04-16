package com.xiaoyi.manager.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hamcrest.core.IsInstanceOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyi.manager.dao.IPictureDao;
import com.xiaoyi.manager.dao.ISchoolDao;
import com.xiaoyi.manager.dao.ITeacherDao;
import com.xiaoyi.manager.dao.IUserDao;
import com.xiaoyi.manager.dao.order.ITeachingDao;
import com.xiaoyi.manager.dao.teaching.ITeachingResourceDao;
import com.xiaoyi.manager.domain.Picture;
import com.xiaoyi.manager.domain.School;
import com.xiaoyi.manager.domain.Teacher;
import com.xiaoyi.manager.domain.User;
import com.xiaoyi.manager.service.ITeachingResourceService;
import com.xiaoyi.teacher.dao.ILessonTradeSumDao;
import com.xiaoyi.teacher.domain.LessonTradeSum;

@Service("teachingResourceService")
public class TeachingResourceServiceImpl implements ITeachingResourceService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	ITeachingDao teachingDao;
	
	@Resource
	ITeacherDao teacherDao;
	@Resource
	ISchoolDao schoolDao;
	
	@Resource
	IPictureDao pictureDao;
	
	@Resource
	IUserDao userDao;
	
	@Resource
	ITeachingResourceDao teachingResourceDao;
	
	@Resource
	ILessonTradeSumDao lessonTradeSumDao;
	
	@Transactional
	@Override
	public int addTeachingTeacher(List<JSONObject> datas) {
		int retCode = 0;
		//校验导入数据
		try {
			//去重
			List<Teacher> hasRecordedTeachers;
			List<String> telNums=null;
			
			List<School> hasRecordedSchool;
			List<String> schoolNames = null;
			try {
				telNums = new ArrayList<String>();
				schoolNames = new ArrayList<String>();
				
				for(JSONObject teacher : datas) {
					telNums.add(teacher.getString("telNumber"));
					schoolNames.add(teacher.getString("schoolName"));
				}
				hasRecordedTeachers = teachingResourceDao.selectTeachingResourceList(telNums);	
				hasRecordedSchool = teachingResourceDao.selectSchoolByNames(schoolNames);
			
				for(Teacher teacher : hasRecordedTeachers) {
					telNums.remove(teacher.getTelnumber());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
			if(CollectionUtils.isEmpty(telNums)){
				return 0;
			}
			
			//学校Id-name map
			Map<String,String> schooIdNameMap = new HashMap<String,String>();
			for(School sch : hasRecordedSchool) {
				schooIdNameMap.put(sch.getSchoolname(), sch.getSchoolid());
			}
			List<School> newAddedSchool = new ArrayList<School>();
			
			//插入老师记录
			try {
				List<Teacher> newAddTeachers = new ArrayList<Teacher>();
				if(!CollectionUtils.isEmpty(datas)) {
					List<Picture> picList = new ArrayList<Picture>();
					for(JSONObject data : datas) {
						String telNum = data.getString("telNumber");
						if(CollectionUtils.isEmpty(telNums)
								|| !telNums.contains(telNum)) {
							continue;
						}
						
						Teacher teacher = new Teacher();
						teacher.setEducation(data.getByte("education"));
						teacher.setGradeid(data.getString("graduation"));
						teacher.setNotes(data.getString("notes"));
						teacher.setOpenId("");
						
						String picId = UUID.randomUUID().toString();
						teacher.setPicid(picId);
						teacher.setRegdate(new Date());
						
						String schoolId = null;
						if(schooIdNameMap.containsKey(data.getString("schoolName"))) {
							schoolId = schooIdNameMap.get(data.getString("schoolName"));
						}else {//新增学校
							schoolId = UUID.randomUUID().toString();
							School sch = new School();
							sch.setSchoolid(schoolId);
							sch.setSchoolname(data.getString("schoolName"));
							
							newAddedSchool.add(sch);
						}
						teacher.setSchoolid(schoolId);
						teacher.setSex(data.getBoolean("sex"));
						teacher.setSigned((short)0);
						teacher.setTeacherid(UUID.randomUUID().toString());
						teacher.setTeachername(data.getString("teacherName"));
						teacher.setTeachinglevel(data.getByte("teachingLevel"));
						teacher.setTelnumber(telNum);
						
						newAddTeachers.add(teacher);
					
						//老师头像地址
						Picture pic = new Picture();
						pic.setPicid(picId);
						pic.setPictype((byte)3);
						pic.setPicname(data.getString("teacherName")+telNum);
						pic.setPicurl(data.getString("imgUrl"));
						
						picList.add(pic);
					}
					
					//新学校入库
					try {
						if(!CollectionUtils.isEmpty(newAddedSchool)) {
							teachingResourceDao.insertSchoolList(newAddedSchool);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					
					//老师入库
					try {
						if(!CollectionUtils.isEmpty(newAddTeachers)) {
							teachingResourceDao.insertTeachingResourceList(newAddTeachers);						
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				
					//头像入库
					try {
						if(!CollectionUtils.isEmpty(picList)) {
							teachingResourceDao.insertTeacherImages(picList);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					
					//插入导入的老师用户信息
					List<User> userList = new ArrayList<User>();
					try {
						if(!CollectionUtils.isEmpty(newAddTeachers)) {
							for(Teacher teacher : newAddTeachers) {							
								User user = new User();
								
								user.setHeadimgurl(teacher.getPicid());
								user.setLoginstatus(false);
								user.setNickname(teacher.getTeachername());
								user.setOpenid("");
								user.setUsername(teacher.getTelnumber());
								user.setUserprivilege((byte)0);
								user.setUsertype((byte)3);
								
								user.setUserid(teacher.getTeacherid());
								user.setUseraccountid(teacher.getTeacherid());
								user.setPassword(teacher.getTelnumber().substring(teacher.getTelnumber().length()-6, teacher.getTelnumber().length()));
						
								userList.add(user);
							}
							if(!CollectionUtils.isEmpty(userList)) {
								retCode = teachingResourceDao.insertTUserList(userList);
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			} catch (Exception e) {
				throw e;
			}
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return retCode;
	}

	@Override
	public List<JSONObject> queryTeachingList(JSONObject params) {
		try {
			List<JSONObject> result = teachingResourceDao.selectTeachingsByParams(params);
			
			sortJsonList(result, "regDate", false);
			return result;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
	}

	@Override
	public List<JSONObject> queryTeacherList(JSONObject params) {
		try {
			List<JSONObject> result = teachingResourceDao.selectTeachersByParams(params);
			
			sortJsonList(result, "regDate", false);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int operateTeacherLessons(JSONObject params) {
		try {
			String teacherId = params.getString("teacherId");
			
			if(!StringUtils.isEmpty(teacherId)) {
				LessonTradeSum record = lessonTradeSumDao.selectByPrimaryKey(teacherId);
				if(null == record) {
					record = new LessonTradeSum();
				}
				
				//record.setTeacherid(teacherId);
				
				int operateNum = params.getIntValue("operateNum");
				int operateType = params.getIntValue("operateType");
				
				//课时操作
				if(0==operateType) { //冻结					
					/*if(operateNum>record.getWithdrawlessonnum()) {
						return 0;
					}*/
					
					record.setFrozenlessonnum(record.getFrozenlessonnum()+operateNum);
				}else {	//解冻
					if(operateNum>record.getFrozenlessonnum()) {
						return 0;
					}
					
					record.setFrozenlessonnum(record.getFrozenlessonnum()-operateNum);
				}
												
				return lessonTradeSumDao.updateByPrimaryKeySelective(record);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return 0;
	}

	@Override
	public int deleteTeachingTeacher(String teacherId) {
		try {
			return teacherDao.deleteByPrimaryKey(teacherId); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * json list sort
	 * @param datas
	 * @param key
	 * @param asc
	 * @return
	 */
	private List<JSONObject> sortJsonList(List<JSONObject> datas, final String key, final boolean asc){
		Collections.sort(datas, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				if(null!=o1 && null!=o2 
						&& o1.getString(key)!=null
						&& o2.getString(key)!=null){
					
					//return o1.getString("createTime")>o2.getString("createTime")?0:1;
					if(!asc){
						return o2.getString(key).compareTo(o1.getString(key));				
					}else{
						return o1.getString(key).compareTo(o2.getString(key));
					}
				}
				return 0;
			}
		});
		
		return datas;
	}

	@Transactional
	@Override
	public int updateTeachingTeacher(JSONObject params) {
		String teacherId = params.getString("teacherId");
		String updatedTelNum = params.getString("telNumber");
		String addedSchoolName = params.getString("schoolName");
		
		
		logger.info("teacherId:"+teacherId);
		logger.info("updatedTelNum:"+updatedTelNum);
		logger.info("addedSchoolName:"+addedSchoolName);
		if(StringUtils.isEmpty(teacherId) || StringUtils.isEmpty(updatedTelNum)){
			logger.info("参数错误！");
			return -1;
		}
		
		try {			
			List<School> hasRecordedSchool;
			List<String> schoolNames = null;
			try {
				schoolNames = new ArrayList<String>();							
				
				//判断是否号码已重复
				logger.info("根据号码查询老师列表【params】："+ updatedTelNum);	
				try {
					Teacher oldTeacher = teacherDao.selectByPrimaryKey(teacherId);
					Teacher curTeacher = teacherDao.selectByTelNum(updatedTelNum);//selectByPrimaryKey(teacherId);
					if(null!=curTeacher && oldTeacher!=null 
							&& !updatedTelNum.equals(oldTeacher.getTelnumber())){
						logger.info("更新老师信息失败【号码已存在】！");
						return -2;
					}												
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("查询老师失败！");
				}
				
				String schoolId = params.getString("schoolId");
				School school = null;
				if(StringUtils.isEmpty(schoolId)){
					logger.info("根据schoolId查询学校【params】："+schoolId);
					school = schoolDao.selectByPrimaryKey(schoolId);
				}
				//新增学校
				if(null==school){
					//新增学校
					if(!StringUtils.isEmpty(addedSchoolName)){
						schoolNames.add(addedSchoolName);
					}
					//根据学校名称查找学校
					hasRecordedSchool = teachingResourceDao.selectSchoolByNames(schoolNames);			
					if(null==hasRecordedSchool || hasRecordedSchool.size()==1){
						School addedSchool = new School();
						schoolId = UUID.randomUUID().toString();
						addedSchool.setSchoolid(schoolId);
						addedSchool.setSchoolname(addedSchoolName);
						try {	
							logger.info("新增学校入库【params】:"+addedSchool);
							schoolDao.insertSelective(addedSchool);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("插入学校失败！");
						}
					}
				}				 
					
				Teacher updatedTeacher = new Teacher();
				updatedTeacher.setTeacherid(teacherId);
				updatedTeacher.setTeachername(params.getString("teacherName"));
				if(null!=params.getByte("education")){
					updatedTeacher.setEducation(params.getByte("education"));
				}
				updatedTeacher.setSchoolid(schoolId);
				if(null!=params.getString("graduation")){
					updatedTeacher.setGradeid(params.getString("graduation"));							
				}
				updatedTeacher.setNotes(params.getString("notes"));				
				if(null!=params.get("sex")){
					if((params.get("sex") instanceof Boolean)){
						updatedTeacher.setSex(params.getBoolean("sex"));
					}else if ((params.get("sex") instanceof Integer)) {
						updatedTeacher.setSex(params.getInteger("sex")==0?true:false);
					}
				}
				if(null!=params.getByte("teachingLevel")){
					updatedTeacher.setTeachinglevel(params.getByteValue("teachingLevel"));
				}
				updatedTeacher.setTelnumber(updatedTelNum);
				//更新老师入库
				try {
					logger.info("更新老师信息【params】："+updatedTeacher.toString());
					teacherDao.updateByPrimaryKeySelective(updatedTeacher);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}	
				
				//更新登录信息
				try {
					User user = new User();
					user.setUserid(updatedTeacher.getTeacherid());
					user.setUseraccountid(updatedTeacher.getTeacherid());
					user.setUsername(updatedTelNum);
					if( updatedTelNum.length()>6){
						user.setPassword(updatedTelNum.substring(updatedTelNum.length()-6, updatedTelNum.length()));
					}
					
					return userDao.updateByPrimaryKeySelective(user);
				} catch (Exception e) {
					logger.info("更新老师对应的登录信息失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("内部错误！");
		}
		return -1;		
	}
}
