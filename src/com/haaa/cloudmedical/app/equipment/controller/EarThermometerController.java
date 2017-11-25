package com.haaa.cloudmedical.app.equipment.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.equipment.service.EarThermometerService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.entity.EarThermometer;
import com.haaa.cloudmedical.entity.EquipmentUse;

@RestController
@RequestMapping({ "/ear_thermometer" })
public class EarThermometerController {
	@Resource
	private EarThermometerService service;

	@Resource
	private CommonUserService commonUserService;
	
	private Logger logger = Logger.getLogger(EarThermometerController.class);

	
	@RequestMapping(value = { "/add.action" }, method = { RequestMethod.POST })
	public InfoJson add(EquipmentUse equipmentUse,EarThermometer earThermometer, HttpServletRequest request) {
		InfoJson infoJson = new InfoJson();
		try {
			//获取用户以及医生的id(从缓存中获取)
			equipmentUse.setUser_id(commonUserService.getPatientId(equipmentUse.getUser_id()));
			equipmentUse.setDoctor_id(commonUserService.getDoctorId());
			if (earThermometer.getTemperature()!=null) {
				infoJson = service.add(equipmentUse, earThermometer);
			}else{
				infoJson.setInfo("没有数据传入！！！");
			}			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}        
		return infoJson;
	}
	
	
	/**
	 * 
	 * @Title: query 
	 * @Description: 数据查询
	 * @param user_id
	 * @param recent  不为null时表示，曲线图查询
	 * @param year_month
	 * @param pageno
	 * @param pagesize
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/query.action" }, method = { RequestMethod.GET })
	public InfoJson query(Long user_id, String recent, String year_month, Integer pageno, Integer pagesize,Integer days,
			HttpServletRequest request) {
		InfoJson infoJson = new InfoJson();
		try {
			//根据是否传递user_id判断从患者端还是医生端传递
			user_id = commonUserService.getPatientId(user_id);
			//默认数据DEFAULT_PAGESIZE = 15
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			//默认查询最近 RECENT = 30
			if (recent != null) {
				if (days==null) {
					days = Constant.RECENT;
				}
				infoJson = service.queryRecent(user_id,days);
			}
			else if (pageno != null) {
				infoJson = service.pageQuery(user_id, year_month, pageno, pagesize);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: dataStat 
	 * @Description: app饼状图数据统计 
	 * @param user_id
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/dataStat.action" }, method = { RequestMethod.GET })
	public InfoJson dataStat(Long user_id,Integer days) {
		InfoJson infoJson = new InfoJson();
		try {
			//根据是否传递user_id判断从患者端还是医生端传递
			user_id = commonUserService.getPatientId(user_id);
			if (days==null) {
				days = Constant.RECENT;
			}
			infoJson = service.dataStat(user_id,days);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}

	/**
	 * 
	 * @Title: queryMonth 
	 * @Description: 查询一年内具有数据的月份
	 * @param user_id
	 * @return
	 * @throws
	 */
	@RequestMapping(value = { "/month.action" }, method = { RequestMethod.GET })
	public InfoJson queryMonth(Long user_id) {
		InfoJson infoJson = new InfoJson();
		try {
			//根据是否传递user_id判断从患者端还是医生端传递
			user_id = commonUserService.getPatientId(user_id);
			infoJson = service.queryMonth(user_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}

}