package com.haaa.cloudmedical.platform.survey.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.platform.survey.service.MedicalSurveyService;

/**
 * 后台健康调查
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/HealthSurvey")
public class MedicalSurveyController {

	@Resource
	private MedicalSurveyService service;
	
	private Logger logger = Logger.getLogger(MedicalSurveyController.class);

	/**
	 * 
	 * @Title: getPersonalInfo 
	 * @Description: 查询用户信息
	 * @param user_id
	 * @return
	 * @throws
	 */
	@RequestMapping("/getPersonalInfo.action")
	public InfoJson getPersonalInfo(String user_id) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getPersonalInfo(user_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: saveHealthSurvey 
	 * @Description: 保存后台上传健康信息调查信息
	 * @param data
	 * @return
	 * @throws
	 */
	@RequestMapping("/saveHealthSurvey.action")
	public InfoJson saveHealthSurvey(@RequestParam Map<String, Object> data) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.saveHealthSurvey(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
	/**
	 * 
	 * @Title: getHealthSurveyByTime 
	 * @Description: 后台按照时间查询健康调查
	 * @param user_id
	 * @param downTime
	 * @param upTime
	 * @return
	 * @throws
	 */
	@RequestMapping("/getByTime.action")
	public InfoJson getHealthSurveyByTime(String user_id,String downTime,String upTime){
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getHealthSurveyHistory(user_id, downTime, upTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}

	
	/**
	 * 
	 * @Title: getTimeList 
	 * @Description: 根据时间查询列表 
	 * @param user_id
	 * @param downTime
	 * @param upTime
	 * @return
	 * @throws
	 */
	@RequestMapping("/getTimeList.action")
	public InfoJson getTimeList(String user_id,String downTime,String upTime){
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getTimeList(user_id, downTime, upTime);
		} catch (Exception e) {
			logger.error("", e);

		}
		return infoJson;
	}
	
	/**
	 * 
	 * @Title: getHealthSurveyById 
	 * @Description: 根据 健康调查id查询健康调查的具体数据
	 * @param order_id
	 * @return
	 * @throws
	 */
	@RequestMapping("/getHealthSurveyById.action")
	public InfoJson getHealthSurveyById(String order_id){
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getHealthSurveyById(order_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
}
