package com.haaa.cloudmedical.common.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haaa.cloudmedical.common.util.BeanUtil;

public class Constant {

	public static final String[] URINEPARAMS = { "URO", "BLD", "BIL", "KET", "GLU", "PRO", "PH", "NIT", "LEU", "SG",
			"VC" };

	public static final String[] LUNGCAPACITYPARAMS = { "fvc", "fev1", "pef" };

	public static final String MENU_CODE_ACCOUNT = String.valueOf(1010);
	public static final String MENU_CODE_ROLE = String.valueOf(1020);
	public static final String MENU_CODE_MEMBER = String.valueOf(2010);
	public static final String MENU_CODE_DOCTOR = String.valueOf(2020);
	public static final String MENU_CODE_DATA = String.valueOf(3010);

	public static final String USER_TYPE_PATIENT = String.valueOf(1500001); // 类型-患者
	public static final String USER_TYPE_DOCTOR = String.valueOf(1500002); // 类型-医生
	public static final String USER_TYPE_OPERATER = String.valueOf(1500003); // 类型-运营

	public static final String ROLE_CODE_PATIENT = "1500001"; // 角色-患者
	public static final String ROLE_CODE_DOCTOR = "1500002"; // 角色-医生
	public static final String ROLE_CODE_OPERATER = "1500003"; // 角色-运营
	public static final String ROLE_CODE_MANAGER = "1500004"; // 角色-管理

	public static final String MESSAGE_SIGN_NAME = "我的健康365";
	public static final String MESSAGE_URL = "https://eco.taobao.com/router/rest";
	public static final String MESSAGE_APPKEY = String.valueOf(23549835);
	public static final String MESSAGE_SECRET = "c1945cd6764a265a8df527e930f419cd";
	public static final String MESSAGE_TEMPLATE_CODE = "SMS_63145021";

	public static final String PIC_TYPE_CHECK = String.valueOf(500001); // 体检报告照片
	public static final String PIC_TYPE_CLINIC = String.valueOf(500002); // 门诊报告照片
	public static final String PIC_TYPE_HOSPITAL = String.valueOf(500003); // 住院报告照片
	public static final String PIC_TYPE_INSURANCE = String.valueOf(500004); // 医保报告照片
	public static final String PIC_TYPE_CARD = String.valueOf(500005); // 身份证照片
	public static final String PIC_TYPE_HEAD = String.valueOf(500006); // 头像照片
	public static final String PIC_TYPE_CHRONIC = String.valueOf(500008); // 慢病追踪照片
	public static final String PIC_TYPE_NEWS = String.valueOf(500009); // 新闻照片
	public static final String PIC_TYPE_MedicalRecord = String.valueOf(500007); // 病历照片
	public static final String PIC_TYPE_AIO6500REPORT = String.valueOf(500010); // 6500一体机体检报告

	public static final String PIC_TYPE_AIO9900REPORT = String.valueOf(500012); // 9900心电图片

	public static final String PIC_TYPE_HOSP_ICON = String.valueOf(500011); // 医院的标志图片

	public static final String CHECK_REPORT = String.valueOf(500001); // 体检报告
	public static final String CLINIC_REPORT = String.valueOf(500002); // 门诊报告
	public static final String HOSPITAL_REPORT = String.valueOf(500003); // 住院报告
	public static final String INSURANCE_REPORT = String.valueOf(500004); // 医保报告
	public static final String MECHINE_REPORT = String.valueOf(500005); // 一体机

	public static final String USER_SOURCE_REGIEST = String.valueOf(1900001); // 患者端App注册
	public static final String USER_SOURCE_BATCH = String.valueOf(1900002); // 后台批量导入
	public static final String USER_SOURCE_PLATFORM = String.valueOf(1900003); // 后台新增
	public static final String USER_SOURCE_MACHINE = String.valueOf(1900004); // 一体机注册
	public static final String USER_SOURCE_DOCTOR = String.valueOf(1900005); // 医生端App添加

	public static final String USER_FLAG_START = String.valueOf(2000001); // 用户状态-有效
	public static final String USER_FLAG_STOP = String.valueOf(2000002); // 用户状态-无效

	public static final String COMMON_FLAG_YES = String.valueOf(101);
	public static final String COMMON_FLAG_NO = String.valueOf(102);

	public static final int HIGH_PRESSURE_HIGH = 140;

	public static final int HIGH_PRESSURE_LOW = 90;

	public static final int LOW_PRESSURE_HIGH = 90;

	public static final int LOW_PRESSURE_LOW = 60;

	public static final float LOW_TEMPERATURE = 35.7F;

	public static final float HIGH_TEMPERATURE = 37.5F;

	public static final float LOW_FBG = 3.9F; // 最低空腹血糖

	public static final float LOW_PRE_BG = 3.9F; // 最低餐前血糖

	public static final float LOW_POST_BG = 3.9F; // 最低餐后血糖

	public static final float HIGH_FBG = 6.9F; // 最高空腹血糖

	public static final float HIGH_PRE_BG = 7.2F; // 最高餐前血糖

	public static final float HIGH_POST_BG = 7.8F; // 最高餐后血糖

	public static final int DEFAULT_PAGESIZE = 15;

	public static String[] IMPORT_MEMBER_PARAMS = { "user_name", "user_card", "user_phone", "doctor_phone",
			"doctor_card", "user_nation", "user_address", "user_marriage", "user_work", "user_medicare_card",
			"user_medical_burden", "user_blood" };
	public static String[] IMPORT_CHECK_PARAMS = { "user_name", "user_card", "user_phone", "user_height", "user_weight",
			"temperature", "Oxygen", "BloodSugar", "HighPressure", "LowPressure", "PulseRate", "HeartRate", "result",
			"PH", "SG", "BLD", "KET", "BIL", "GLU", "PRO", "URO", "LEU", "VC", "NIT", "fvc", "fev1", "pef", "fef25",
			"fef75", "fef2575", "create_date" };
	public static String[] IMPORT_DOCTOR_PARAMS = { "doctor_name", "user_sex", "doctor_card", "doctor_phone",
			"hospital", "department", "doctor_title", "doctor_introduce" };
	public static String[] IMPORT_OPERATER_PARAMS = { "user_name", "user_sex", "user_phone" };

	public static final String IMPORT_MEMBER_SHEETNAME = "基本信息";
	public static final String IMPORT_CHECK_SHEETNAME = "体检一体机数据";
	public static final String IMPORT_DOCTOR_SHEETNAME = "用户管理";
	public static final String IMPORT_OPERATER_SHEETNAME = "用户管理";

	public static final int READ_EXCEL_ROWS = 50;

	public static final int RECENT = 30; // 7件套曲线图查询默认查询天数

	public static final String URINEMINUS = "700001";

	public static final String URINEPLUSMINUS = "700002";

	public static final String URINEPLUS = "700003";

	public static final String URINETWOPLUS = "700004";

	// 个推
	public static final String AppId = BeanUtil.getProperty("dbconfig").getString("AppId");
	public static final String AppKey = BeanUtil.getProperty("dbconfig").getString("AppKey");
	public static final String AppSecret = BeanUtil.getProperty("dbconfig").getString("AppSecret");
	public static final String MasterSecret = BeanUtil.getProperty("dbconfig").getString("MasterSecret");
	public static final String URL = BeanUtil.getProperty("dbconfig").getString("URL");

	public static final String doctor_AppId = BeanUtil.getProperty("dbconfig").getString("doctor.AppId");
	public static final String doctor_AppKey = BeanUtil.getProperty("dbconfig").getString("doctor.AppKey");
	public static final String doctor_AppSecret = BeanUtil.getProperty("dbconfig").getString("doctor.AppSecret");
	public static final String doctor_MasterSecret = BeanUtil.getProperty("dbconfig").getString("doctor.MasterSecret");

	public static final String URINETHREEPLUS = "700005";

	public static final String URINEFOURPLUS = "700006";

	public static final String[] URINESG = { "1.005", "1.010", "1.015", "1.020", "1.025", "1.030" };

	public static final String[] HEARTRESULT = { "600000", "600001", "600002", "600003", "600004", "600005", "600006",
			"600007", "600008", "600009", "600010", "600011", "600012", "600013" };

	public static final int MORNING_END = 12;

	public static final int NIGHT_BEGIN = 18;

	public static final String BLUETOOTHUPLOAD = "1800001";

	public static final String MANUALUPLOAD = "1800002";

	public static final float BMI1 = 18.5f;

	public static final float BMI2 = 24f;

	public static final float BMI3 = 28f;

	public static final float BMI4 = 32f;

	public static final float URINEPH1 = 4.6f;

	public static final float URINEPH2 = 8.0f;

	public static final float URINESG1 = 1.015f;

	public static final float URINESG2 = 1.025f;

	public static final String remind_type_measurement = String.valueOf(1400001);
	public static final String remind_type_medication = String.valueOf(1400002);
	public static final String remind_type_doctor = String.valueOf(1400003);

	public static final String remind_measurement_bloodpressure = String.valueOf(1400004); // 血压
	public static final String remind_measurement_bloodsugar = String.valueOf(1400005); // 血糖
	public static final String remind_measurement_weight = String.valueOf(1400006); // 体重
	public static final String remind_measurement_oxygen = String.valueOf(1400007); // 血氧
	public static final String remind_measurement_heartrate = String.valueOf(1400008); // 心率
	public static final String remind_measurement_vitalcapacity = String.valueOf(1400009); // 肺活量
	public static final String remind_measurement_temperature = String.valueOf(1400010); // 体温
	public static final String remind_measurement_urine = String.valueOf(1400011); // 尿液

	public static final String measurement_message_bloodpressure = "测量前需要安静休息五分钟，测量前30分钟内禁止吸烟或饮咖啡，排空膀胱";
	public static final String measurement_message_bloodsugar = "空腹血糖测量前需要安静休息5分钟，采血前不用降糖药、不吃早餐、不喝水、不运动；饭后血糖测量前需要安静休息5分钟，饭后2小时才能抽血检查";
	public static final String measurement_message_weight = "测量前禁止剧烈运动，测量时双足平稳站在秤上，身体放松";
	public static final String measurement_message_oxygen = "测量前需要安静休息5分钟，保持手指干净和温暖";
	public static final String measurement_message_heartrate = "测量前需要安静休息五分钟，测量前30分钟内禁止剧烈运动";
	public static final String measurement_message_vitalcapacity = "测量前保持放松状态，进行一两次深呼吸动作";
	public static final String measurement_message_temperature = "测量时请正确使用耳温计，测量前30分钟内禁止剧烈运动";
	public static final String measurement_message_urine = "测量前多喝水，禁止服用药物，尿液标本保持新鲜、清洁";

	public static final String BLOOD_PRESSURE_ID = "1";
	public static final String BLOOD_SUGAR_ID = "5";
	public static final String EAR_THERMOMETER_ID = "9";
	public static final String ELECTROCARDIOGRAPH_ID = "13";
	public static final String LUNG_CAPACITY_ID = "17";
	public static final String BLOOD_OXYGEN_ID = "21";
	public static final String URINE_TEST_ID = "25";
	//9900一体机
	public static final String SELF_SERVICE = "29";
	public static final String WRIST_WATCH = "33";
	//6500一体机
	public static final String SELFSERVICE_6500 = "37";

	public static final int EMPTY_STOMACH = 800001;
	public static final int AFTER_BREAKFAST = 800002;
	public static final int BEFORE_LUNCH = 800003;
	public static final int AFTER_LUNCH = 800004;
	public static final int BEFORE_DINNER = 800005;
	public static final int AFTER_DINNER = 800006;
	public static final int BEFORE_SLEEP = 800007;
	
	public static final int EMPTY = 1;
	public static final int AFTER = 2;

	public static final String MALE = "200001";

	public static final String FEMALE = "200002";

	public static final Map<String, String> MEAS_TYPE = new HashMap<String, String>();

	static {
		MEAS_TYPE.put(remind_measurement_bloodpressure, measurement_message_bloodpressure);
		MEAS_TYPE.put(remind_measurement_bloodsugar, measurement_message_bloodsugar);
		MEAS_TYPE.put(remind_measurement_weight, measurement_message_weight);
		MEAS_TYPE.put(remind_measurement_oxygen, measurement_message_oxygen);
		MEAS_TYPE.put(remind_measurement_heartrate, measurement_message_heartrate);
		MEAS_TYPE.put(remind_measurement_vitalcapacity, measurement_message_vitalcapacity);
		MEAS_TYPE.put(remind_measurement_temperature, measurement_message_temperature);
		MEAS_TYPE.put(remind_measurement_urine, measurement_message_urine);
	}

	// 运维菜单
	public static final Map<String, Object> operation = new HashMap<String, Object>();

	static {
		operation.put("c_menu_name", "运维管理");
		operation.put("c_menu_code", "10000");
		List<Map<String, Object>> optlist = new ArrayList<Map<String, Object>>();
		Map<String, Object> menumap = new HashMap<String, Object>();
		menumap.put("c_menu_name", "运维详情");
		menumap.put("c_menu_code", "10010");
		menumap.put("c_menu_father", "10000");
		menumap.put("c_action_path", "view/operations/operations-list.html");
		optlist.add(menumap);
		operation.put("menu_list", optlist);
	}

	public static final Map<String, String> BLOOD_TYPE_UNICODE_MAP = new HashMap<String, String>();

	public static final Map<String, String> MARRIAGE_UNICODE_MAP = new HashMap<String, String>();

	static {
		BLOOD_TYPE_UNICODE_MAP.put("A", "100001");
		BLOOD_TYPE_UNICODE_MAP.put("B", "100002");
		BLOOD_TYPE_UNICODE_MAP.put("AB", "100003");
		BLOOD_TYPE_UNICODE_MAP.put("O", "100004");
		BLOOD_TYPE_UNICODE_MAP.put("A型", "100001");
		BLOOD_TYPE_UNICODE_MAP.put("B型", "100002");
		BLOOD_TYPE_UNICODE_MAP.put("AB型", "100003");
		BLOOD_TYPE_UNICODE_MAP.put("O型", "100004");
		BLOOD_TYPE_UNICODE_MAP.put("其它", "57");
		BLOOD_TYPE_UNICODE_MAP.put("其他", "57");

		MARRIAGE_UNICODE_MAP.put("已婚", "300001");
		MARRIAGE_UNICODE_MAP.put("未婚", "300002");
		MARRIAGE_UNICODE_MAP.put("离婚", "300003");
		MARRIAGE_UNICODE_MAP.put("丧偶", "300004");
		MARRIAGE_UNICODE_MAP.put("性伴侣", "300005");
	}

	public static final Map<String, Integer> BlOOD_PRESSURE_LEVEL = new HashMap<String, Integer>();

	static {
		BlOOD_PRESSURE_LEVEL.put("H1", 90);
		BlOOD_PRESSURE_LEVEL.put("H2", 120);
		BlOOD_PRESSURE_LEVEL.put("H3", 140);
		BlOOD_PRESSURE_LEVEL.put("H4", 160);
		BlOOD_PRESSURE_LEVEL.put("H5", 180);

		BlOOD_PRESSURE_LEVEL.put("L1", 60);
		BlOOD_PRESSURE_LEVEL.put("L2", 80);
		BlOOD_PRESSURE_LEVEL.put("L3", 90);
		BlOOD_PRESSURE_LEVEL.put("L4", 100);
		BlOOD_PRESSURE_LEVEL.put("L5", 110);
	}

	public static final Map<String, Float> BLOOD_SUGAR_LEVEL = new HashMap<String, Float>();

	static {
		BLOOD_SUGAR_LEVEL.put("fbg1", 4.4f);
		BLOOD_SUGAR_LEVEL.put("fbg2", 6.1f);
		BLOOD_SUGAR_LEVEL.put("fbg3", 7.2f);
		BLOOD_SUGAR_LEVEL.put("fbg4", 8.3f);
		BLOOD_SUGAR_LEVEL.put("fbg5", 10.0f);

		BLOOD_SUGAR_LEVEL.put("post1", 5.0f);
		BLOOD_SUGAR_LEVEL.put("post2", 7.2f);
		BLOOD_SUGAR_LEVEL.put("post3", 8.8f);
		BLOOD_SUGAR_LEVEL.put("post4", 11.1f);
		BLOOD_SUGAR_LEVEL.put("post5", 15.5f);

		BLOOD_SUGAR_LEVEL.put("pre1", 4.4f);
		BLOOD_SUGAR_LEVEL.put("pre2", 6.7f);
		BLOOD_SUGAR_LEVEL.put("pre3", 8.2f);
		BLOOD_SUGAR_LEVEL.put("pre4", 10.0f);
		BLOOD_SUGAR_LEVEL.put("pre5", 14.4f);
	}

	public static final Map<String, String> MEASUREMENT_PERIOD_MAP = new HashMap<String, String>();

	static {
		MEASUREMENT_PERIOD_MAP.put("800001", "空腹");
		MEASUREMENT_PERIOD_MAP.put("800002", "早餐后");
		MEASUREMENT_PERIOD_MAP.put("800003", "午餐前");
		MEASUREMENT_PERIOD_MAP.put("800004", "午餐后");
		MEASUREMENT_PERIOD_MAP.put("800005", "晚餐前");
		MEASUREMENT_PERIOD_MAP.put("800006", "晚餐后");
		MEASUREMENT_PERIOD_MAP.put("800007", "睡前");

		MEASUREMENT_PERIOD_MAP.put("空腹", "800001");
		MEASUREMENT_PERIOD_MAP.put("早餐后", "800002");
		MEASUREMENT_PERIOD_MAP.put("午餐前", "800003");
		MEASUREMENT_PERIOD_MAP.put("午餐后", "800004");
		MEASUREMENT_PERIOD_MAP.put("晚餐前", "800005");
		MEASUREMENT_PERIOD_MAP.put("晚餐后", "800006");
		MEASUREMENT_PERIOD_MAP.put("睡前", "800007");

	}

}