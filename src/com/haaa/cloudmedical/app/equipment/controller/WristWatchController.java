/**
 * 
 */
package com.haaa.cloudmedical.app.equipment.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.app.equipment.service.WristWatchService;
import com.haaa.cloudmedical.app.util.CommonUserService;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.entity.BloodOxygen;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.EquipmentUse;

/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/wristwatch")
public class WristWatchController {

	@Resource
	private WristWatchService service;

	@Resource
	private CommonUserService commonUserService;
	
	private Logger logger = Logger.getLogger(WristWatchController.class);

	@RequestMapping("/addAll.action")
	public InfoJson addAll(EquipmentUse equipmentUse, Electrocardiograph electrocardiograph, BloodOxygen bloodOxygen) {
		InfoJson infoJson = new InfoJson();
		try {
			//获取用户以及医生的id(从缓存中获取)
			equipmentUse.setUser_id(commonUserService.getPatientId(equipmentUse.getUser_id()));
			equipmentUse.setDoctor_id(commonUserService.getDoctorId());
			if ((bloodOxygen.getOxygen() != null && !bloodOxygen.getOxygen().equals("")
					&& bloodOxygen.getPulseRate() != null && !bloodOxygen.getPulseRate().equals(""))
					|| (electrocardiograph.getEcg1() != null && !electrocardiograph.getEcg1().equals("")
							&& electrocardiograph.getHeartRate() != null
							&& !electrocardiograph.getHeartRate().equals("") && electrocardiograph.getResult() != null
							&& !electrocardiograph.getResult().equals(""))) {
				infoJson = service.addAll(equipmentUse, electrocardiograph, bloodOxygen);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return infoJson;
	}

}
