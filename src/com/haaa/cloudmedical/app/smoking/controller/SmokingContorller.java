package com.haaa.cloudmedical.app.smoking.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.app.smoking.service.SmokingService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.entity.SmokingBaseInfo;
import com.haaa.cloudmedical.entity.SmokingPlan;
import com.haaa.cloudmedical.entity.UpdatePlan;



@Controller
@RequestMapping("/smoking")
public class SmokingContorller {

	private Logger logger = Logger.getLogger(SmokingContorller.class);
	@Autowired
	private CommonUserService common;
	@Autowired
	private SmokingService service;
	//创建戒烟计划
	@RequestMapping("/plan.action")
	@ResponseBody
	public Long createSmokingPlan(SmokingPlan smoking) throws SQLException, Exception{
		smoking.setUser_id(common.getPatientId(-1L));
		return service.createSmokingPlan(smoking);
	}
	//查询用户的性别和年龄
	@RequestMapping("/finduser.action")
	@ResponseBody
	public Map<String,Object> findUserAgeSex(String user_id) throws SQLException, Exception{
		user_id=common.getPatientId(user_id);
		Map<String,Object> map=service.findUserAgeSex(Long.parseLong(user_id) );

			 return map;

	}
	//查询用户所有的戒烟计划
	@RequestMapping("/selectAll.action")
	@ResponseBody	
	public  InfoJson  insertUserSmokingBaseInfo(String user_id) throws SQLException, Exception{
		user_id=common.getPatientId(user_id);
		InfoJson infoJson = new InfoJson();
		List<Map<String,Object>> list=service.selectAllPlan(Long.parseLong(user_id)) ;
		try{
		if (list!=null) {
			infoJson.setData(list);
			infoJson.setStatus(1);
		     }
		}
	 catch (Exception e) {
		logger.error("", e);
	}
		return infoJson;
	
}
	//保存烟史
	@RequestMapping("/baseInfo.action")
	@ResponseBody
	public Long saveUserBaseInfo(SmokingBaseInfo smoking) throws SQLException, Exception{
		smoking.setUser_id(common.getPatientId(-1L));
		return service.saveUserBaseInfo(smoking);
	}
	//查询烟史
	@RequestMapping("/findBaseInfo.action")
	@ResponseBody
	public Map<String,Object> findUserBaseInfo(String user_id) throws SQLException, Exception{
		user_id=common.getPatientId(user_id);
		return service.findUserBaseInfo(Long.parseLong(user_id));
	}
	//签到
	@RequestMapping("/signate.action")
	@ResponseBody
	public Integer signate(String user_id) throws SQLException, Exception{
		user_id=common.getPatientId(user_id);
		return service.signate(Long.parseLong(user_id));
	}	
	//查询是否已签到
	@RequestMapping("/isSignate.action")
	@ResponseBody
	public  int isSignate(Long user_id) {
		user_id=common.getPatientId(user_id);
		return service.isSignate(user_id);
		
	}
	//修改计划
	@RequestMapping("/updatePlan.action")
	@ResponseBody
	public Integer updatePlan(UpdatePlan plan) throws SQLException, Exception{
		return service.updatePlan(plan);
	}
	//取得当前进行中计划
	@RequestMapping("/getQuitSmokingPlan.action")
	@ResponseBody
	public Map<String,Object> getQuitSmokingPlan(String user_id) throws SQLException, Exception{
		user_id=common.getPatientId(user_id);
		return service.getQuitSmokingPlan(Long.parseLong(user_id));
	}


}
