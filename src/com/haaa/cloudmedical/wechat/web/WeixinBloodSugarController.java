/**
 * 
 */
package com.haaa.cloudmedical.wechat.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.wechat.service.IWeixinBloodSugarService;

/**
 * @author Bowen Fan
 *
 */
@RestController
@RequestMapping("/blood_sugar-wx")
public class WeixinBloodSugarController {

	@Resource
	private IWeixinBloodSugarService service;

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/graph.action")
	public StdDTO getGraph(HttpServletRequest request, String open_id, Integer period, Integer days) {
		StdDTO stdDTO = new StdDTO();
		try {
			open_id = "zhangming";
			if (days == null) {
				days = 30;
			}
			if (period == null) {
				period = Constant.EMPTY_STOMACH;
			}
			stdDTO = service.getGraph(open_id, period, days);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};

	@RequestMapping("/page.action")
	public StdDTO getPage(HttpServletRequest request, String open_id, String yearmonth, Integer pageno,
			Integer pagesize) {
		StdDTO stdDTO = new StdDTO();
		try {
			open_id = "zhangming";
			if (pagesize == null) {
				pagesize = Constant.DEFAULT_PAGESIZE;
			}
			if (pageno == null) {
				pageno = 1;
			}
			stdDTO = service.getPage(open_id, yearmonth, pageno, pagesize);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};

	@RequestMapping("/months.action")
	public StdDTO getMonths(HttpServletRequest request, String open_id) {
		StdDTO stdDTO = new StdDTO();
		try {
			open_id = "zhangming";
			stdDTO = service.getMonths(open_id);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};

	@RequestMapping("/classify.action")
	public StdDTO classify(HttpServletRequest request, Float blood_sugar, int period) {
		StdDTO stdDTO = new StdDTO();
		try {
			stdDTO = service.classify(blood_sugar, period);
		} catch (Exception e) {
			stdDTO.setStatus(1);
			logger.error(e.getMessage(), e);
		}
		return stdDTO;
	};
}