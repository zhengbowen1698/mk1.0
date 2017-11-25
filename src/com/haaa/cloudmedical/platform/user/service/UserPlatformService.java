package com.haaa.cloudmedical.platform.user.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.common.util.IdCard;
import com.haaa.cloudmedical.dao.UserPlatformDao;
import com.haaa.cloudmedical.entity.Department;
import com.haaa.cloudmedical.entity.Doctor;
import com.haaa.cloudmedical.entity.Manager;
import com.haaa.cloudmedical.entity.Patient;
import com.haaa.cloudmedical.entity.User;

@Service
@Log(name = "会员管理")
public class UserPlatformService {

	@Autowired
	private UserPlatformDao dao;

	private Logger logger = Logger.getLogger(UserPlatformService.class);

	/**
	 * 检验手机号码是否已注册
	 * 
	 * @param user_phone
	 * @param flag
	 * @return
	 */
	public boolean contain(String user_phone, String type) {
		String sql = "";
		boolean flag = false;
		if (type.equals("1")) {
			sql = " select * from n_manager where user_phone = ? ";
		} else {
			sql = " select * from n_user where user_phone = ? ";
		}
		List<Map<String, Object>> list = dao.select(sql, user_phone);
		if (list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 检验身份证是否已注册
	 * 
	 * @param user_phone
	 * @param flag
	 * @return
	 */
	public boolean containUserCard(String user_card, String type) {
		String sql = "";
		boolean flag = false;
		if (type.equals("1")) {
			sql = " select * from n_manager where user_card = ? ";
		} else if (type.equals("2")) {
			sql = " select * from n_user where user_card = ? ";
		}
		List<Map<String, Object>> list = dao.select(sql, user_card);
		if (list.size() > 0)
			flag = true;
		return flag;
	}

	// 登录
	public ResponseDTO login(String user_phone, String user_pwd, HttpServletRequest request) {
		ResponseDTO dto = new ResponseDTO();
		// 管理员放行
		String admin = BeanUtil.getProperty("dbconfig").getString("mkh.admin.username");
		String password = BeanUtil.getProperty("dbconfig").getString("mkh.admin.password");
		if (admin.equals(user_phone) && user_pwd.equals(password)) {
			Map<String, Object> adminmap = new HashMap<String, Object>();
			adminmap.put("admin", admin);
			adminmap.put("password", password);
			adminmap.put("user_name", "admin");
			adminmap.put("user_id", "-1");
			dto.setData(adminmap);
			dto.setFlag(true);
			// 用户方到session中
			request.getSession().setAttribute("user", adminmap);
			return dto;
		} else if (admin.equals(user_phone) && !user_pwd.equals(password)) {
			dto.setFlag(false);
			dto.setErrmsg("密码有误");
			return dto;
		}
		// 用户判断
		String sql = "select * from n_manager where user_phone = ?";
		List<Map<String, Object>> list = dao.select(sql, user_phone);
		if (list != null && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			String user_flag = map.get("user_flag").toString();
			if (Constant.USER_FLAG_STOP.equals(user_flag)) {
				dto.setFlag(false);
				dto.setErrmsg("用户已停用");
				return dto;
			}
		} else {
			dto.setFlag(false);
			dto.setErrmsg("用户不存在");
			return dto;
		}
		// 验证用户
		Map<String, Object> user = dao.selectByAccount(user_phone, user_pwd);
		if (user != null) {
			dto.setData(user);
			dto.setFlag(true);
			// 用户方到session中
			request.getSession().setAttribute("user", user);
		} else {
			logger.error("账号或密码错误");
			dto.setErrmsg("账号或密码错误");
		}
		return dto;
	}

	// 菜单列表
	public ResponseDTO getMenu(String user_phone) {
		ResponseDTO dto = new ResponseDTO();
		//
		List<Map<String, Object>> list = this.getAdminMenu(user_phone);
		if (list != null) {
			dto.setData(list);
			dto.setFlag(true);
			return dto;
		}
		// 用户
		List<Map<String, Object>> menu = dao.getMenu(user_phone);
		if (menu.size() > 0) {
			dto.setData(menu);
			dto.setFlag(true);
		} else {
			logger.error("该用户没有权限");
			dto.setErrmsg("该用户没有权限");
		}
		return dto;
	}

	// 管理员
	private List<Map<String, Object>> getAdminMenu(String user_phone) {
		List<Map<String, Object>> list = null;
		String admin = BeanUtil.getProperty("dbconfig").getString("mkh.admin.username");
		if (admin.equals(user_phone)) {
			list = new ArrayList<Map<String, Object>>();
			String sql = "select * from n_menu where order_id = 1";
			Map<String, Object> map = this.dao.select(sql).get(0);
			sql = "select * from n_menu where c_menu_father = 1000";
			List<Map<String, Object>> menulist = dao.select(sql);
			map.put("menu_list", menulist);
			list.add(map);
			// 添加运维权限
			list.add(Constant.operation);
		}
		return list;
	}

	// 查询
	public Map<String, Object> query(String user_id, String user_type) {
		Map<String, Object> map = null;
		StringBuilder sql = new StringBuilder();
		String url = BeanUtil.getProperty("dbconfig").getString("pictureuploadhttp");
		if (user_type.equals(Constant.USER_TYPE_OPERATER)) {
			sql.append("select a.user_id,a.user_name,a.user_phone,a.role_code,a.user_head_pic_upload_index,"
					+ "a.user_card_front_upload_index,a.user_card_back_upload_index from n_manager a where 1=1 ");
		} else if (user_type.equals(Constant.USER_TYPE_PATIENT)) {
			sql.append("select a.user_id,a.user_name,a.user_address,a.email,a.user_birthday,a.user_sex,a.user_card,"
					+ "a.passport,a.user_age,a.user_phs_phone,a.user_height,a.user_weight,a.user_phone,a.user_blood,"
					+ "a.user_medicare_card,a.user_marriage,a.user_work,a.user_medical_burden,a.user_head_pic_upload_index,"
					+ "a.user_card_front_upload_index,a.user_card_back_upload_index,a.wechat,a.qq,a.mainland,a.country,"
					+ "a.country_name,c.doctor_id,c.doctor_name,c.department_order_id,d.department_name,d.hosp_order_id,"
					+ "e.hosp_name,f.areacounty_id county_city_id,f.areacounty_name county_city_name,"
					+ "g.areacounty_id prefecture_city_id,g.areacounty_name prefecture_city_name,h.areacounty_id province_id,"
					+ "h.areacounty_name province_name from n_user a,d_patient b left outer join d_doctor c on b.doctor_id = c.doctor_id,"
					+ "k_department d,k_hosp e,k_areacounty_all f,k_areacounty_all g,k_areacounty_all h where a.user_id = b.patient_id "
					+ "and c.department_order_id = d.order_id and d.hosp_order_id = e.order_id and e.area_id = f.areacounty_id "
					+ "and f.parent_id = g.areacounty_id and g.parent_id = h.areacounty_id");
		} else if (user_type.equals(Constant.USER_TYPE_DOCTOR)) {
			sql.append("select a.user_id,a.user_name,findname(a.user_sex) user_sex,a.user_card,a.user_age,a.passport,a.mainland,a.user_birthday,"
					+ "a.user_phone,b.doctor_title,b.doctor_introduce,c.hosp_order_id,d.hosp_name,b.department_order_id,a.country,a.country_name,"
					+ "c.department_name,a.user_head_pic_upload_index,a.user_card_front_upload_index,"
					+ "a.user_card_back_upload_index from n_user a,d_doctor b left join k_department c "
					+ "on b.department_order_id = c.order_id left join k_hosp d on c.hosp_order_id = d.order_id where a.user_id = b.doctor_id ");
		} else {

		}
		if (sql.length() > 0) {
			sql.append(" and a.user_id = ? ");
			map = dao.query(sql.toString(), user_id);
			Object mainland = map.get("mainland");
			if(mainland != null){
				if(mainland.toString().equals("1")){
					Object user_card = map.get("user_card");
					if (user_card != null) {
						IdCard card = IdCard.of(user_card.toString());
						if (card.flag) {
							map.put("user_age", card.getAge() + "");
							map.put("user_birthday", card.getBirthday());
							map.put("user_sex", card.getSex() == 1 ? "200001" : "200002");
						}
					}
				}
				if(mainland.toString().equals("0")){
					Object user_birthday = map.get("user_birthday");
					if (user_birthday != null) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						long age =  LocalDate.parse(user_birthday.toString(),formatter).until(LocalDate.now(), ChronoUnit.YEARS);
						map.put("user_age", age+"");
					}
				}
				
			}
			
			if (map.get("user_head_pic_upload_index") != null) {
				map.put("user_head_pic_upload_index", url + map.get("user_head_pic_upload_index"));
			}
			if (map.get("user_card_front_upload_index") != null) {
				map.put("user_card_front_upload_index", url + map.get("user_card_front_upload_index"));
			}
			if (map.get("user_card_back_upload_index") != null) {
				map.put("user_card_back_upload_index", url + map.get("user_card_back_upload_index"));
			}
		}
		return map;
	}

	// 修改密码
	@Log(name = "修改密码")
	@Transactional
	public boolean updatePassword(HttpServletRequest request, String user_phone, String user_pwd) {
		String sql = "update n_manager set user_pwd = ? where user_id = ? ";
		dao.execute(sql, user_pwd, user_phone);
		request.getSession().removeAttribute("user");
		return true;
	}

	// 编辑用户信息
	@Log(name = "编辑管理员信息")
	@Transactional
	public boolean update(HttpServletRequest request, Manager manager) {
		boolean flag = false;
		int rows = dao.update(manager, "n_manager");
		if (rows == 1) {
			if (manager.getUser_pwd() != null && manager.getUser_pwd().length() > 0) {
				request.getSession().removeAttribute("user");
			}
			flag = true;
		}
		return flag;
	}

	@Log(name = "编辑会员信息")
	@Transactional
	public Map<String, Object> update(User user, Patient patient) {
		Map<String, Object> response = new HashMap<String, Object>();
		dao.update(user, "n_user");
		patient.setPatient_id(user.getUser_id());
		patient.setPatient_name(user.getUser_name());
		dao.update(patient, "d_patient");
		response.put("flag", true);
		return response;
	}

	@Log(name = "编辑医生信息")
	public Map<String, Object> update(User user, Doctor doctor) {
		Map<String, Object> response = new HashMap<String, Object>();
		dao.update(user, "n_user");
		doctor.setDoctor_id(user.getUser_id());
		doctor.setDoctor_name(user.getUser_name());
		dao.update(user, "n_user");
		response.put("flag", true);
		return response;
	}

	// 数据初始化加载
	public Page query(String c_menu_code, int pageno) {
		String sql = null;
		if (c_menu_code.equals(Constant.MENU_CODE_ACCOUNT)) {
			sql = "select a.user_name, a.role_code, date_format(a.update_date,'%Y-%m-%d %H:%i') update_date, date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date from n_user a  ";
		} else if (c_menu_code.equals(Constant.MENU_CODE_ROLE)) {
			sql = "select a.role_name,date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date from n_role a ";
		} else if (c_menu_code.equals(Constant.MENU_CODE_MEMBER)) {
			sql = "select a.user_name,a.user_card,a.user_phone,a.user_is_vip,a.user_sex,c.doctor_name,a.user_age,a.user_marriage,date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date from n_user a,d_patient b,d_doctor c where a.user_id = b.patient_id and b.doctor_id = c.doctor_id ";
		} else if (c_menu_code.equals(Constant.MENU_CODE_DOCTOR)) {
			sql = "select a.user_name,a.user_id,a.user_card,a.user_phone,a.user_age,a.user_sex,b.doctor_title,d.hosp_name,c.department_name,date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date from n_user a ,d_doctor b,k_department c,k_hosp d where a.user_id = b.doctor_id and b.department_order_id=c.order_id and c.hosp_order_id=d.order_id";
		}
		Page page = dao.pageQuery(sql, pageno);
		return page;
	}

	// 用户管理
	public Page query(Manager manager, int pageno) {
		StringBuilder sql = new StringBuilder(
				"select a.user_id,a.user_name,a.user_phone,b.role_name,a.user_flag,findname(a.user_flag) user_flag_c,"
						+ "date_format(a.update_date,'%Y-%m-%d %H:%i') update_date, date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date "
						+ "from n_manager a left join n_role b on a.role_code = b.order_id where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		// 姓名
		if (manager.getUser_name() != null && !"".equals(manager.getUser_name())) {
			values.add("%" + manager.getUser_name() + "%");
			sql.append(" and a.user_name like ? ");
		}

		// 手机号码
		if (manager.getUser_phone() != null && !"".equals(manager.getUser_phone())) {
			values.add("%" + manager.getUser_phone() + "%");
			sql.append(" and a.user_phone like ? ");
		}

		// 角色
		if (manager.getRole_code() != null && !"".equals(manager.getRole_code())) {
			values.add(manager.getRole_code());
			sql.append(" and a.role_code = ? ");
		}

		// 状态
		if (manager.getUser_flag() != null && !"".equals(manager.getUser_flag())) {
			values.add(manager.getUser_flag());
			sql.append(" and a.user_flag = ? ");
		}

		sql.append(" order by a.create_date desc");

		Page page = dao.pageQuery(sql.toString(), values.toArray(), pageno);
		return page;
	}

	// 会员管理
	public Page query(User user, Doctor doctor, String startDate, String endDate, int pageno) {
		StringBuilder sql = new StringBuilder(
				"select a.user_id,a.user_name,a.user_card,a.user_phone,if(user_is_vip = 101,'vip会员','普通会员') user_is_vip,e.hosp_name,"
						+ "d.department_name,c.doctor_name,findname(a.user_sex) user_sex,a.user_age,findname(a.user_marriage) user_marriage,a.wechat,"
						+ "a.qq,date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date,a.passport,a.country,a.country_name,a.mainland from n_user a,"
						+ "d_patient b left outer join d_doctor c on b.doctor_id = c.doctor_id "
						+ "left outer join k_department d on c.department_order_id = d.order_id "
						+ "left outer join k_hosp e on d.hosp_order_id = e.order_id where a.user_id = b.patient_id ");
		List<Object> values = new ArrayList<Object>();
		// 姓名
		if (user.getUser_name() != null && !"".equals(user.getUser_name())) {
			values.add("%" + user.getUser_name() + "%");
			sql.append(" and a.user_name like ? ");
		}

		// 身份证
		if (user.getUser_card() != null && !"".equals(user.getUser_card())) {
			values.add("%" + user.getUser_card() + "%");
			sql.append(" and a.user_card like ? ");
		}

		// 护照
		if (user.getPassport() != null && !"".equals(user.getPassport())) {
			values.add("%" + user.getPassport() + "%");
			sql.append(" and a.passport like ? ");
		}

		// 手机号
		if (user.getUser_phone() != null && !"".equals(user.getUser_phone())) {
			values.add("%" + user.getUser_phone() + "%");
			sql.append(" and a.user_phone like ? ");
		}

		// 会员等级
		if (user.getUser_is_vip() != null && !"".equals(user.getUser_is_vip())) {
			values.add(user.getUser_is_vip());
			sql.append(" and a.user_is_vip= ? ");
		}

		// 性别
		if (user.getUser_sex() != null && !"".equals(user.getUser_sex())) {
			values.add(user.getUser_sex());
			sql.append(" and a.user_sex = ? ");
		}

		// 家庭医生
		if (doctor.getDoctor_name() != null && !"".equals(doctor.getDoctor_name())) {
			values.add("%" + doctor.getDoctor_name() + "%");
			sql.append(" and c.doctor_name like ? ");
		}

		// 创建日期
		if (startDate != null && startDate.length() > 0) {
			values.add(startDate);
			sql.append(" and date_format(a.create_date,'%Y-%m-%d') >= ? ");
		}
		if (endDate != null && endDate.length() > 0) {
			values.add(endDate);
			sql.append(" and date_format(a.create_date,'%Y-%m-%d') <= ? ");
		}
		sql.append(" and a.user_type= ? ").append(" order by a.create_date desc");
		values.add(Constant.USER_TYPE_PATIENT);
		Page page = dao.pageQuery(sql.toString(), values.toArray(), pageno);
		return page;
	}

	// 医生管理
	public Page query(User user, Doctor doctor, Department department, String startDate, String endDate, int pageno) {
		StringBuilder sql = new StringBuilder("select a.user_id,a.user_name,a.user_card,a.user_phone,a.user_age,"
				+ "findname (a.user_sex) user_sex,findname (b.doctor_title) doctor_title,d.hosp_name,c.department_name,"
				+ "date_format(a.create_date,'%Y-%m-%d %H:%i') ceate_date from n_user a,d_doctor b,k_department c,k_hosp d "
				+ "where a.user_id = b.doctor_id and b.department_order_id = c.order_id and c.hosp_order_id = d.order_id");
		List<Object> values = new ArrayList<Object>();
		// 姓名
		if (user.getUser_name() != null && !"".equals(user.getUser_name())) {
			values.add("%" + user.getUser_name() + "%");
			sql.append(" and a.user_name like ? ");
		}
		// 身份证
		if (user.getUser_card() != null && !"".equals(user.getUser_card())) {
			values.add("%" + user.getUser_card() + "%");
			sql.append(" and a.user_card like ? ");
		}
		// 手机号
		if (user.getUser_phone() != null && !"".equals(user.getUser_phone())) {
			values.add("%" + user.getUser_phone() + "%");
			sql.append(" and a.user_phone like ? ");
		}
		// 职称
		if (doctor.getDoctor_title() != null && !"".equals(doctor.getDoctor_title())) {
			values.add(doctor.getDoctor_title());
			sql.append(" and b.doctor_title = ? ");
		}
		// 医院
		if (department.getHosp_order_id() != null && !"".equals(department.getHosp_order_id())) {
			values.add(department.getHosp_order_id());
			sql.append(" and c.hosp_order_id= ? ");
		}
		// 科室
		if (doctor.getDepartment_order_id() != null && !"".equals(doctor.getDepartment_order_id())) {
			values.add(doctor.getDepartment_order_id());
			sql.append(" and b.department_order_id= ? ");
		}
		// 状态
		if (user.getUser_flag() != null && !"".equals(user.getUser_flag())) {
			values.add(user.getUser_flag());
			sql.append(" and a.user_flag= ? ");
		}
		// 创建日期
		if (startDate != null && startDate.length() > 0) {
			values.add(startDate);
			sql.append(" and date_format(a.create_date,'%Y-%m-%d') >= ? ");
		}
		if (endDate != null && endDate.length() > 0) {
			values.add(endDate);
			sql.append(" and date_format(a.create_date,'%Y-%m-%d') <= ? ");
		}
		sql.append(" and a.user_type = ? ").append(" order by a.create_date desc");
		values.add(Constant.USER_TYPE_DOCTOR);
		Page page = dao.pageQuery(sql.toString(), values.toArray(), pageno);
		return page;
	}

	@Log(name = "新增管理员")
	@Transactional
	public ResponseDTO addUser(Manager manager) {
		ResponseDTO dto = new ResponseDTO();
		manager.setUser_flag(Constant.USER_FLAG_START);
		long user_id = dao.insert(manager, "n_manager");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		dto.setFlag(true);
		dto.setData(map);

		return dto;
	}

	@Log(name = "新增会员")
	@Transactional
	public ResponseDTO addUser(User user, Patient patient) {
		ResponseDTO dto = new ResponseDTO();
		long user_id = dao.insert(user, "n_user");
		patient.setPatient_id(String.valueOf(user_id));
		dao.insert(patient, "d_patient");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		dto.setData(map);
		dto.setFlag(true);
		return dto;

	}

	@Log(name = "新增医生")
	@Transactional
	public ResponseDTO addUser(User user, Doctor doctor) {
		ResponseDTO dto = new ResponseDTO();
		long user_id = dao.insert(user, "n_user");
		doctor.setDoctor_id(String.valueOf(user_id));
		dao.insert(doctor, "d_doctor");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		dto.setFlag(true);
		dto.setData(map);
		return dto;
	}

	// 修改用户状态
	@Log(name = "启用/停用用户")
	@Transactional
	public boolean modify(String user_id, String user_flag) {
		String sql = "update n_manager set user_flag = ? where user_id = ?";
		dao.execute(sql, user_flag, user_id);
		return true;
	}

	public Page getPatientList(String doctor_id, String user_name, String user_card, String user_phone, int pageno,
			int pagesize) {
		Page page = dao.getPatientList(doctor_id, user_name, user_card, user_phone, pageno, pagesize);
		return page;
	}

	public Page getMessageList(String patient_id, String measurement_type, String measurement_result, String start,
			String end, int pageno, int pagesize) {
		Page page = dao.getMessageList(patient_id, measurement_type, measurement_result, start, end, pageno, pagesize);
		return page;

	}
}
