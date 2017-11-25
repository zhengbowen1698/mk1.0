package com.haaa.cloudmedical.platform.smoking.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.entity.SmokingPlatformView;
import com.haaa.cloudmedical.platform.smoking.service.SmokingPlatformService;

@Controller
@RequestMapping("/smoking")
public class SmokingPlatformController {
	@Autowired
	private SmokingPlatformService service;	

	//后台页面戒烟用户信息查询接口where条件
	@RequestMapping("/findSmokingUser.action")
	@ResponseBody
	public Page findSmokingUser(SmokingPlatformView view,Integer pageno) throws SQLException, Exception{
		
		
		
		return service.findSmokingUser(view,pageno);
	}
	//后台页面戒烟用户信息查询接口
	@RequestMapping("/selectUserInfo.action")
	@ResponseBody
	public Map<String,Object> selectUserInfo(String user_id) throws SQLException, Exception{
	    
		
		
		return service.selectUserInfo(Long.parseLong(user_id));
	}
	
	   
//分页查询戒烟计划
	@RequestMapping("/selectAllPlanP.action")
	@ResponseBody
	public Page selectAllPlanP(Long user_id,Integer pageno ){
		return service.selectAllPlanP(user_id, pageno);
	}
}
