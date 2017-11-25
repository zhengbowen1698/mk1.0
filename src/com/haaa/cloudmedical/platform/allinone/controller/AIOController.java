package com.haaa.cloudmedical.platform.allinone.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.platform.allinone.service.AIOService;

/**
 * 
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/aio")
public class AIOController {

	@Resource
	private AIOService service;

	private Logger logger = Logger.getLogger(AIOController.class);

	/**
	 * 
	 * @Title: login 
	 * @Description: 9900身份认证，返回json数据
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/login.action" }, method = { RequestMethod.POST })
	public Map<String, Object> login(HttpServletRequest request) {
		System.out.println("------9900一体机登陆");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			String requestXml = request.getParameter("requestXml");
			responseMap = service.login(requestXml);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			responseMap.put("ResultCode", "9");
			responseMap.put("ResultMsg", "");
		}

		return responseMap;
	}

	
	/**
	 * 
	 * @Title: saveData 
	 * @Description: 收取9900数据并返回json数据
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/saveData.action" }, method = { RequestMethod.POST })
	public Map<String, Object> saveData(HttpServletRequest request) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			responseMap = service.saveData(request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			responseMap.put("ResultCode", "9");
			responseMap.put("ResultMsg", "");
		}
		return responseMap;
	}

	/**
	 * 
	 * @Title: saveCardioData 
	 * @Description: 保存心电数据
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/saveCardioData.action" }, method = { RequestMethod.POST })
	public Map<String, Object> saveCardioData(HttpServletRequest request) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			
			responseMap = service.saveCardioData(request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseMap.put("TimeStamp", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			responseMap.put("ResultCode", "9");
			responseMap.put("ResultMsg", "");
		}
		return responseMap;
	}
	
	
	/**
	 * 
	 * @Title: getTimeList 
	 * @Description: 后台一体机查询一体机列表（暂时无用）
	 * @param user_id
	 * @param downTime
	 * @param upTime
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/getListByTime.action" })
	public InfoJson getTimeList(String user_id, String downTime, String upTime) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getTimeList(user_id, downTime, upTime);
			infoJson.setStatus(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: getDetail 
	 * @Description: 后台显示一体机查询数据（暂时无用）
	 * @param order_id
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/getDetail.action" }, method = { RequestMethod.GET })
	public InfoJson getDetail( String order_id) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getDetail(order_id);
			infoJson.setStatus(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: getDetailByTime 
	 * @Description: 后台显示一体机查询数据
	 * @param order_id
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/getDetailByTime.action" }, method = { RequestMethod.GET })
	public InfoJson getDetailByTime(String user_id, String downTime, String upTime) {
		InfoJson infoJson = new InfoJson();
		try {
			infoJson = service.getDetailByTime(user_id, downTime, upTime);
			infoJson.setStatus(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return infoJson;
	}
	
}
