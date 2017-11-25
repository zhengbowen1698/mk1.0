package com.haaa.cloudmedical.platform.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.IdCard;
import com.haaa.cloudmedical.common.util.MD5Util;
import com.haaa.cloudmedical.entity.Department;
import com.haaa.cloudmedical.entity.Doctor;
import com.haaa.cloudmedical.entity.Hospital;
import com.haaa.cloudmedical.entity.Manager;
import com.haaa.cloudmedical.entity.Patient;
import com.haaa.cloudmedical.entity.User;
import com.haaa.cloudmedical.platform.user.service.UserPlatformService;

@Controller
@RequestMapping("/user-platform")
public class UserPlatFormController {

	@Autowired
	private UserPlatformService service;

	/**
	 * 检验手机号码是否已注册
	 * 
	 * @param user_phone
	 * @param flag
	 * @return
	 */
	@RequestMapping("/exists.action")
	@ResponseBody
	public boolean contain(String user_phone, String type) {
		boolean flag = false;
		flag = service.contain(user_phone, type);
		return flag;
	}

	/**
	 * 检验身份证是否已注册
	 * 
	 * @param user_phone
	 * @param flag
	 * @return
	 */
	@RequestMapping("/checkUserCard.action")
	@ResponseBody
	public boolean containUserCard(String user_card, String type) {
		boolean flag = false;
		user_card = user_card.trim().toUpperCase();
		flag = service.containUserCard(user_card, type);
		return flag;
	}

	/**
	 * 登录
	 * 
	 * @param user_phone
	 * @param user_pwd
	 * @return
	 */
	@RequestMapping("/login.action")
	@ResponseBody
	public Object login(String user_phone, String user_pwd, HttpServletRequest request) {
		ResponseDTO dto = service.login(user_phone, MD5Util.encode(user_pwd), request);
		return dto;
	}

	/**
	 * 菜单列表
	 * 
	 * @param user_phone
	 * @param user_pwd
	 * @return
	 */
	@RequestMapping("/getMenu.action")
	@ResponseBody
	public Object getMenu(String user_phone) {
		ResponseDTO dto = service.getMenu(user_phone);
		return dto;
	}

	/**
	 * 分页查询
	 * 
	 * @param user
	 * @param patient
	 * @param doctor
	 * @param department
	 * @param hospital
	 * @param startDate
	 * @param endDate
	 * @param c_menu_code
	 * @param pageno
	 * @return
	 */
	@RequestMapping("/managergridquery.action")
	@ResponseBody
	public Object query(Manager manager, Integer pageno) {
		Page page = null;
		if (pageno == null)
			pageno = 1;
		page = service.query(manager, pageno);

		return page;
	}

	@RequestMapping("/membergridquery.action")
	@ResponseBody
	public Object query(User user, Doctor doctor, String startDate, String endDate, Integer pageno) {
		Page page = null;
		if (pageno == null)
			pageno = 1;
		page = service.query(user, doctor, startDate, endDate, pageno);
		return page;
	}

	@RequestMapping("/doctorgridquery.action")
	@ResponseBody
	public Object query(User user, Doctor doctor, Department department, Hospital hospital, String startDate,
			String endDate, Integer pageno) {
		Page page = null;
		if (pageno == null)
			pageno = 1;
		page = service.query(user, doctor, department, startDate, endDate, pageno);
		return page;
	}

	/**
	 * 当前用户信息查询
	 * 
	 * @param user_phone
	 * @param c_menu_code
	 * @return
	 */
	@RequestMapping("/query.action")
	@ResponseBody
	public Object query(String user_id, String user_type) {
		Map<String, Object> map = service.query(user_id, user_type);
		map.forEach((k, v) -> {
			map.compute(k, (k1, v1) -> v == null || v.equals("null") ? "" : v);
		});
		return map;
	}

	/**
	 * 修改用户信息
	 * 
	 * @return
	 */
	@RequestMapping("/modifyManager.action")
	@ResponseBody
	public Object modify(HttpServletRequest request, Manager manager) {
		boolean flag = false;
		if (manager.getUser_pwd() != null && !"".equals(manager.getUser_pwd()))
			manager.setUser_pwd(MD5Util.encode(manager.getUser_pwd()));
		try {
			flag = service.update(request, manager);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@RequestMapping("/modifyMember.action")
	@ResponseBody
	public Object modify(User user, Patient patient) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = service.update(user, patient);
		} catch (Exception e) {
			map.put("flag", false);
			map.put("errmsg", "修改失败");
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/modifyDoctor.action")
	@ResponseBody
	public Object modify(User user, Doctor doctor) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response = service.update(user, doctor);
		} catch (Exception e) {
			response.put("flag", false);
			response.put("errmsg", "修改失败");
			e.printStackTrace();
		}
		return response.get("flag");
	}

	/**
	 * a 新增用户
	 * 
	 * @return
	 */
	@RequestMapping("/addManager.action")
	@ResponseBody
	public Object add(Manager manager) {
		ResponseDTO dto = new ResponseDTO();
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		manager.setUser_pwd(MD5Util.encode(manager.getUser_pwd()));
		manager.setCreate_date(date);
		try {
			dto = service.addUser(manager);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	@RequestMapping("/addMember.action")
	@ResponseBody
	public Object add(User user, Patient patient) {
		ResponseDTO dto = new ResponseDTO();
		String user_card = user.getUser_card();
		if (user_card != null && user_card.trim().length() > 0) {
			user_card = user_card.trim().toUpperCase();
			IdCard card = IdCard.of(user_card);
			if (card.flag) {
				user.setUser_age(card.getAge() + "");
				user.setUser_birthday(card.getBirthday());
				user.setUser_sex(card.getSex() == 1 ? "200001" : "200002");
			} else {
				dto.setErrmsg("非法身份证");
				return dto;
			}
		}
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		String user_pwd = BeanUtil.getProperty("dbconfig").getObject("initPassword").toString();
		user.setUser_type(Constant.USER_TYPE_PATIENT);
		user.setUser_pwd(MD5Util.encode(user_pwd));
		user.setUser_source(Constant.USER_SOURCE_PLATFORM);
		user.setCreate_date(date);
		patient.setPatient_name(user.getUser_name());
		patient.setCreate_date(date);
		try {
			dto = service.addUser(user, patient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@RequestMapping("/addDoctor.action")
	@ResponseBody
	public Object add(User user, Doctor doctor) {
		ResponseDTO dto = new ResponseDTO();
		String user_card = user.getUser_card();
		if (user_card != null && user_card.trim().length() > 0) {
			user_card = user_card.trim().toUpperCase();
			IdCard card = IdCard.of(user_card);
			if (card.flag) {
				user.setUser_age(card.getAge() + "");
				user.setUser_birthday(card.getBirthday());
				user.setUser_sex(card.getSex() == 1 ? "200001" : "200002");
			} else {
				dto.setErrmsg("非法身份证");
				return dto;
			}
		} else {
			user.setCountry("0");
			user.setCountry_name("");
		}
		String date = DateUtil.DateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		String user_pwd = BeanUtil.getProperty("dbconfig").getObject("initPassword").toString();
		user.setUser_type(String.valueOf(Constant.USER_TYPE_DOCTOR));
		user.setCreate_date(date);
		user.setUser_pwd(MD5Util.encode(user_pwd));
		doctor.setDoctor_name(user.getUser_name());
		doctor.setCreate_date(date);
		try {
			dto = service.addUser(user, doctor);
		} catch (Exception e) {

		}
		return dto;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping("/password.action")
	@ResponseBody
	@Transactional
	public Object save(HttpServletRequest request, String user_id, String user_pwd) {
		boolean flag = service.updatePassword(request, user_id, MD5Util.encode(user_pwd));
		return flag;
	}

	/**
	 * 修改用户状态
	 * 
	 * @param user_id
	 * @param flag
	 * @return
	 */
	@RequestMapping("/userflag.action")
	@ResponseBody
	public boolean modify(String user_id, String user_flag) {
		boolean flag = service.modify(user_id, user_flag);
		return flag;

	}

	/**
	 * 获取患者列表
	 * 
	 * @param doctor_id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getPatientList.action")
	@ResponseBody
	public Page getPatientList(String doctor_id, String user_name, String user_card, String user_phone, Integer pageNo,
			Integer pageSize) {
		if (pageSize == null) {
			pageSize = 10;
		}
		if (user_card != null) {
			user_card = user_card.trim().toUpperCase();
		}
		Page page = service.getPatientList(doctor_id, user_name, user_card, user_phone, pageNo, pageSize);
		return page;
	}

	/**
	 * 获取推送消息列表
	 * 
	 * @param patient_id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getMessageList.action")
	@ResponseBody
	public Page getMessageList(String patient_id, String measurement_type, String measurement_result, String start,
			String end, Integer pageNo, Integer pageSize) {
		if (pageSize == null) {
			pageSize = 20;
		}
		Page page = service.getMessageList(patient_id, measurement_type, measurement_result, start, end, pageNo,
				pageSize);
		return page;

	}

	@RequestMapping("/getAndriodUrl.action")
	@ResponseBody
	public Object getAndriodUrl() {
		String andriod_patient_url = BeanUtil.getProperty("dbconfig").getString("download_app_patient_url");
		String andriod_doctor_url = BeanUtil.getProperty("dbconfig").getString("download_app_doctor_url");
		Map<String, String> map = new HashMap<>();
		map.put("andriod_patient_url", andriod_patient_url);
		map.put("andriod_doctor_url", andriod_doctor_url);
		return map;
	}
}
