package com.haaa.cloudmedical.platform.equipment.service;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.BeanPropertyUtil;
import com.haaa.cloudmedical.dao.equipment.BloodPressureDao;
import com.haaa.cloudmedical.dao.equipment.BloodSugarDao;
import com.haaa.cloudmedical.dao.ChronicPlanDao;
import com.haaa.cloudmedical.dao.equipment.BloodOxygenDao;
import com.haaa.cloudmedical.dao.equipment.EarThermometerDao;
import com.haaa.cloudmedical.dao.equipment.ElectrocardiographDao;
import com.haaa.cloudmedical.dao.equipment.LungCapacityDao;
import com.haaa.cloudmedical.dao.equipment.UrineTestDao;
import com.haaa.cloudmedical.dao.equipment.EquipmentDao;
import com.haaa.cloudmedical.entity.BloodOxygen;
import com.haaa.cloudmedical.entity.BloodPressure;
import com.haaa.cloudmedical.entity.BloodSugar;
import com.haaa.cloudmedical.entity.EarThermometer;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.EquipmentUse;
import com.haaa.cloudmedical.entity.LungCapacity;
import com.haaa.cloudmedical.entity.UrineTest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log(name = "后台7件套")
public class EquipmentService {
	@Resource
	private EquipmentDao equipmentDao;

	@Resource
	private BloodPressureDao bloodPressureDao;

	@Resource
	private BloodSugarDao bloodSugarDao;

	@Resource
	private EarThermometerDao earThermometerDao;

	@Resource
	private ElectrocardiographDao electrocardiographDao;

	@Resource
	private LungCapacityDao lungCapacityDao;

	@Resource
	private BloodOxygenDao bloodoxygenDao;

	@Resource
	private UrineTestDao urineTestDao;
	//慢性病管理计划的dao
	@Autowired
	private ChronicPlanDao chronicPlanDao;
	/**
	 * 
	 * @Title: addAll @Description: 后台添加7件套数据 @param equipmentUse @param
	 *         bloodOxygen @param bloodPressure @param bloodSugar @param
	 *         earThermometer @param electrocardiograph @param
	 *         lungCapacity @param urineTest @return @throws Exception @throws
	 */
	@SuppressWarnings("serial")
	@Transactional
	@Log(name = "7件套数据添加")
	public InfoJson addAll(EquipmentUse equipmentUse, BloodOxygen bloodOxygen, BloodPressure bloodPressure,
			BloodSugar bloodSugar, EarThermometer earThermometer, Electrocardiograph electrocardiograph,
			LungCapacity lungCapacity, UrineTest urineTest,String create_by) throws Exception {
		InfoJson infoJson = new InfoJson();
		// 用來表示是否有一套完整的7件套数据从后台页面传输
		int flag = 0;
		StringBuffer successBuffer = new StringBuffer();
		StringBuffer failBuffer = new StringBuffer();
		
		// 判断血压必须保存的几项参数有没有传输
		if (bloodPressure.getHighPressure() != null && !bloodPressure.getHighPressure().equals("")
				&& bloodPressure.getLowPressure() != null && !bloodPressure.getLowPressure().equals("")
				&& bloodPressure.getPulseRate() != null && !bloodPressure.getPulseRate().equals("")) {
			save(equipmentUse, Constant.BLOOD_PRESSURE_ID, "blood_pressure", bloodPressure);
			flag = 1;
			successBuffer.append("血压数据/n");
			//当血压数据有值的时候,增加管理计划的iterm表记录
			insetBloodPressureIterm(equipmentUse.getUser_id(),equipmentUse.getCreate_date(),create_by);
		} else {
			failBuffer.append("血压数据/n");
		}
		// 判断血糖必须保存的几项参数有没有传输
		if (bloodSugar.getBloodSugar() != null && !bloodSugar.getBloodSugar().equals("")
				&& bloodSugar.getMeasurement_period() != null && !bloodSugar.getMeasurement_period().equals("")) {
			save(equipmentUse, Constant.BLOOD_SUGAR_ID, "blood_sugar", bloodSugar);
			flag = 1;
			successBuffer.append("血糖数据/n");
			//管理向content里面插入数据计划
			insetBloodSugarIterm(equipmentUse.getUser_id(),equipmentUse.getCreate_date(),bloodSugar.getMeasurement_period(),create_by);
		} else {
			failBuffer.append("血糖数据/n");
		}
		// 判断耳温必须保存的几项参数有没有传输
		if (earThermometer.getTemperature() != null && !earThermometer.getTemperature().equals("")) {
			save(equipmentUse, Constant.EAR_THERMOMETER_ID, "ear_thermometer", earThermometer);
			flag = 1;
			successBuffer.append("耳温数据/n");
		} else {
			failBuffer.append("耳温数据/n");

		}
		// 判断心电必须保存的几项参数有没有传输
		if (electrocardiograph.getHeartRate() != null && !electrocardiograph.getHeartRate().equals("")
				&& electrocardiograph.getResult() != null && !electrocardiograph.getResult().equals("")) {
			save(equipmentUse, Constant.ELECTROCARDIOGRAPH_ID, "electrocardiograph", electrocardiograph);
			flag = 1;
			successBuffer.append("心电数据/n");
		} else {
			failBuffer.append("心电数据/n");
		}
		// 判断肺活量必须保存的几项参数有没有传输
		if (lungCapacity.getFev1() != null && !lungCapacity.getFev1().equals("") && lungCapacity.getFev1_rate() != null
				&& !lungCapacity.getFev1_rate().equals("") && lungCapacity.getFvc() != null
				&& !lungCapacity.getFvc().equals("") && lungCapacity.getFvc_rate() != null
				&& !lungCapacity.getFvc_rate().equals("") && lungCapacity.getPef() != null
				&& !lungCapacity.getPef().equals("")) {
			save(equipmentUse, Constant.LUNG_CAPACITY_ID, "lung_capacity", lungCapacity);
			flag = 1;
			successBuffer.append("肺活量数据/n");
		} else {
			failBuffer.append("肺活量数据/n");
		}
		// 判断血氧必须保存的几项参数有没有传输
		if (bloodOxygen.getOxygen() != null && !bloodOxygen.getOxygen().equals("") && bloodOxygen.getPulseRate() != null
				&& !bloodOxygen.getPulseRate().equals("")) {
			save(equipmentUse, Constant.BLOOD_OXYGEN_ID, "pulse_bloodoxygen", bloodOxygen);
			flag = 1;
			successBuffer.append("血氧数据/n");
		} else {
			failBuffer.append("血氧数据/n");

		}
		// 判断尿检必须保存的几项参数有没有传输
		if (urineTest.getBil() != null && !urineTest.getBil().equals("") && urineTest.getBld() != null
				&& !urineTest.getBil().equals("") && urineTest.getGlu() != null && !urineTest.getGlu().equals("")
				&& urineTest.getKet() != null && !urineTest.getKet().equals("") && urineTest.getLeu() != null
				&& !urineTest.getLeu().equals("") && urineTest.getNit() != null && !urineTest.getNit().equals("")
				&& urineTest.getPh() != null && !urineTest.getPh().equals("") && urineTest.getSg() != null
				&& !urineTest.getSg().equals("") && urineTest.getUro() != null && !urineTest.getUro().equals("")
				&& urineTest.getVc() != null && !urineTest.getVc().equals("")) {
			save(equipmentUse, Constant.URINE_TEST_ID, "urine_test", urineTest);
			flag = 1;
			successBuffer.append("尿检数据/n");
		} else {
			failBuffer.append("尿检数据/n");
		}
		if (flag == 1) {
			infoJson.setStatus(1);
			infoJson.setData(new HashMap<String, Object>() {
				{
					put("success", successBuffer.toString());
					put("fail", failBuffer.toString());
				}
			});

		} else {
			infoJson.setInfo("请填写至少一件套的完整信息！！！");
		}

		return infoJson;
	}
	
	/**
	 * @description 用于添加血糖记录的时候在管理计划表里面插入一条记录,并且调用存储过程更新report表
	 * @param patient_id
	 * @param create_date
	 * @param period
	 * @param create_by
	 */
	private void insetBloodSugarIterm(String patient_id, String create_date,String period, String create_by) {
		//获取用户的管理计划
		List<Map<String, Object>> plan = bloodSugarDao.getPlan(patient_id,period);
		if (plan!=null) {
			for (Map<String, Object> map : plan) {
				map.put("create_date", create_date);
				map.put("create_by", create_by);
				long item_order_id = bloodSugarDao.insert(map, "p_plan_item");
				//调用存储过程更新report表
				chronicPlanDao.callStoreUpd(String.valueOf(item_order_id));
			}
		}
	}
	/**
	 * @description 添加血压记录时候操作管理计划表向content里面插入数据计划 并且更新report表
	 * @param patient_id
	 * @param create_date
	 * @param create_by
	 */
	private void insetBloodPressureIterm(String patient_id,String create_date,String create_by) {
		//获取用户的管理计划
		List<Map<String, Object>> plan = bloodPressureDao.getPlan(patient_id);
		if (plan!=null) {
			for (Map<String, Object> map : plan) {
				map.put("create_date", create_date);
				map.put("create_by", create_by);
				long item_order_id = bloodPressureDao.insert(map, "p_plan_item");
				//调用存储过程更新report表
				chronicPlanDao.callStoreUpd(String.valueOf(item_order_id));
			}
		}		
	}

	@Log(name = "7件套具体数据添加")
	private void save(EquipmentUse equipmentUse, String equip_id, String equip_name, Object object) {
		equipmentUse.setEquipment_property_order_id(equip_id);
		equipmentUse.setEquipment_name(equip_name);
		equipmentUse.setCheck_data_source(Constant.MANUALUPLOAD); // 后台添加数据为手动上传
		Map<String, Object> equipMap = new HashMap<String, Object>();
		BeanPropertyUtil.toMapFromObject(equipMap, object);
		equipmentUse.setCreate_date((String) equipMap.get("create_date"));
		Number use_id = equipmentDao.insertAndGetKey(equipmentUse, "c_equipment_use");
		equipMap.put("equipment_use_order_id", use_id);
		equipmentDao.insert(equipMap, "c_" + equip_name);
	}

	/**
	 * 
	 * @Title: getBloodPressureChart @Description: 血压曲线图查询 @param user_id @param
	 *         downTime @param upTime @return @throws
	 */
	public InfoJson getBloodPressureChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 标题
		dataMap.put("title", "血压");
		// Y轴单位
		dataMap.put("unit", "mmHg");
		// 设置血压曲线图上限
		dataMap.put("max", 250);
		// 设置血压曲线图下限
		dataMap.put("min", 0);
		Map<String, Object> highPressureMap = new HashMap<String, Object>();
		Map<String, Object> lowPressureMap = new HashMap<String, Object>();
		List<List<Object>> highPressureDataList = new ArrayList<List<Object>>();
		List<List<Object>> lowPressureDataList = new ArrayList<List<Object>>();
		highPressureMap.put("name", "高压");
		highPressureMap.put("data", highPressureDataList);
		lowPressureMap.put("name", "低压");
		lowPressureMap.put("data", lowPressureDataList);
		List<Map<String, Object>> list = bloodPressureDao.getDataByTime(Long.valueOf(user_id), datemin, datemax);
		for (Map<String, Object> map : list) {
			/*
			 * List<Object> hList = new ArrayList<Object>(); List<Object> lList
			 * = new ArrayList<Object>(); hList.add(map.get("create_date"));
			 * hList.add(map.get("HighPressure"));
			 * lList.add(map.get("create_date"));
			 * lList.add(map.get("LowPressure"));
			 */
			highPressureDataList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("HighPressure"));
				}
			});
			lowPressureDataList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("LowPressure"));
				}
			});
		}
		List<Object> dataList = new ArrayList<Object>();
		dataList.add(highPressureMap);
		dataList.add(lowPressureMap);
		dataMap.put("list", dataList);
		infoJson.setData(dataMap);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getBloodSugarChart @Description: 后台血糖曲线图 ,前端echart @param
	 *         user_id @param period @param datemin @param
	 *         datemax @return @throws
	 */
	public InfoJson getBloodSugarChart(String user_id, int period, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 标题
		dataMap.put("title", "血糖");
		// Y坐标单位
		dataMap.put("unit", "mmol/L");
		dataMap.put("max", 0);
		dataMap.put("min", 0);
		Map<String, Object> bloodSugarMap = new HashMap<String, Object>();
		List<Object> bloodSugarList = new ArrayList<Object>();
		if (period == Constant.EMPTY_STOMACH) {
			bloodSugarMap.put("name", "空腹");
		} else if (period == Constant.AFTER_BREAKFAST || period == Constant.AFTER_DINNER
				|| period == Constant.AFTER_LUNCH) {
			bloodSugarMap.put("name", "餐后");
		} else {
			bloodSugarMap.put("name", "餐前");
		}
		bloodSugarMap.put("data", bloodSugarList);
		List<Map<String, Object>> list = bloodSugarDao.getDataByTime(Long.valueOf(user_id), period, datemin, datemax);
		for (Map<String, Object> map : list) {
			/*
			 * List<Object> sList = new ArrayList<Object>();
			 * sList.add(map.get("create_date"));
			 * sList.add(map.get("BloodSugar"));
			 */
			// 根据最大值设置血糖曲线图上限
			if (Math.abs(Float.parseFloat(String.valueOf(map.get("BloodSugar"))) + 2) > Float
					.parseFloat(dataMap.get("max") + "")) {
				dataMap.put("max", Math.abs(Float.parseFloat(String.valueOf(map.get("BloodSugar"))) + 2));
			}
			bloodSugarList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("BloodSugar"));
				}
			});
		}
		List<Object> dataList = new ArrayList<Object>();
		dataList.add(bloodSugarMap);
		dataMap.put("list", dataList);
		infoJson.setData(dataMap);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getBloodOxygenChart @Description: 后台血氧曲线图 @param user_id @param
	 *         datemin @param datemax @return @throws
	 */
	public InfoJson getBloodOxygenChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", "血氧饱和度");
		dataMap.put("unit", "百分比/(%)");
		// 设置血氧曲线图上限
		dataMap.put("max", 100);
		// 设置血氧曲线图下限
		dataMap.put("min", 0);
		Map<String, Object> bloodOxygenMap = new HashMap<String, Object>();
		List<Object> bloodOxygenList = new ArrayList<Object>();
		bloodOxygenMap.put("name", "血氧");
		bloodOxygenMap.put("data", bloodOxygenList);
		List<Map<String, Object>> list = bloodoxygenDao.getDataByTime(Long.valueOf(user_id), datemin, datemax);
		for (Map<String, Object> map : list) {
/*			List<Object> sList = new ArrayList<Object>();
			sList.add(map.get("create_date"));
			sList.add(map.get("Oxygen"));*/
			bloodOxygenList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("Oxygen"));
				}
			});
		}
		List<Object> dataList = new ArrayList<Object>();
		dataList.add(bloodOxygenMap);
		dataMap.put("list", dataList);
		infoJson.setData(dataMap);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getEarThermometerChart @Description: 后台耳温曲线图 @param
	 *         user_id @param datemin @param datemax @return @throws
	 */
	public InfoJson getEarThermometerChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", "耳温");
		dataMap.put("unit", "°C");
		dataMap.put("max", 50);
		dataMap.put("min", 0);
		Map<String, Object> earThermometerMap = new HashMap<String, Object>();
		List<Object> earThermometerList = new ArrayList<Object>();
		earThermometerMap.put("name", "耳温");
		earThermometerMap.put("data", earThermometerList);
		List<Map<String, Object>> list = earThermometerDao.getDataByTime(Long.valueOf(user_id), datemin, datemax);
		for (Map<String, Object> map : list) {
			/*
			 * List<Object> sList = new ArrayList<Object>();
			 * sList.add(map.get("create_date"));
			 * sList.add(map.get("temperature"));
			 */
			earThermometerList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("temperature"));
				}
			});
		}
		List<Object> dataList = new ArrayList<Object>();
		dataList.add(earThermometerMap);
		dataMap.put("list", dataList);
		infoJson.setData(dataMap);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getElectrocardiographList @Description: 后台查询心电图时间列表 @param
	 *         user_id @param datemin @param datemax @return @throws
	 */
	public InfoJson getElectrocardiographList(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		infoJson.setData(electrocardiographDao.getListByTime(Long.valueOf(user_id), datemin, datemax));
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getElectrocardiographChart @Description: 后台心电图曲线 @param
	 *         order_id @return @throws
	 */
	public InfoJson getElectrocardiographChart(String order_id) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> map = electrocardiographDao.getEcgByUseId(Long.valueOf(order_id));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 标题
		dataMap.put("title", map.get("result") + "  |  心率：" + map.get("HeartRate") + "次/分钟");
		dataMap.put("unit", "");
		dataMap.put("max", 0);
		dataMap.put("min", 0);
		Map<String, Object> ecgMap = new HashMap<String, Object>();
		List<Object> ecgdata = new ArrayList<Object>();
		ecgMap.put("data", ecgdata);
		if (map.get("ecg1") != null) {
			// 将7件套心电表内的心电数据拼接起来再进行split成字符串数组
			String ecg = map.get("ecg1") + "," + map.get("ecg2") + "," + map.get("ecg3") + "," + map.get("ecg4") + ","
					+ map.get("ecg5");
			String ecgList[] = ecg.split(",");
			for (int i = 0; i < ecgList.length; i++) {
/*				List<Object> sList = new ArrayList<Object>();
				sList.add(i);
				sList.add(ecgList[i]);*/
				final int j = i;
				if (Math.abs(Integer.parseInt(ecgList[i])) + 200 > Integer.parseInt(dataMap.get("max") + "")) {
					dataMap.put("max", Math.abs(Integer.parseInt(ecgList[i])) + 200);
					dataMap.put("min", -Math.abs(Integer.parseInt(ecgList[i])) - 200);
				}
				ecgdata.add(new ArrayList<Object>() {
					{
						add(j);
						add(ecgList[j]);
					}
				});
			}
		}
		List<Object> dataList = new ArrayList<Object>();
		dataList.add(ecgMap);
		dataMap.put("list", dataList);
		infoJson.setData(dataMap);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getLungCapacityChart @Description: 后台肺活量曲线 @param user_id @param
	 *         datemin @param datemax @return @throws
	 */
	public InfoJson getLungCapacityChart(String user_id, String datemin, String datemax) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", "肺活量");
		dataMap.put("unit", "L");
		dataMap.put("max", 10);
		dataMap.put("min", 0);
		Map<String, Object> fvcMap = new HashMap<String, Object>();
		Map<String, Object> fev1Map = new HashMap<String, Object>();
		Map<String, Object> pefMap = new HashMap<String, Object>();
		List<Object> fvcDataList = new ArrayList<Object>();
		List<Object> fev1DataList = new ArrayList<Object>();
		List<Object> pefDataList = new ArrayList<Object>();
		fvcMap.put("name", "fvc");
		fvcMap.put("data", fvcDataList);
		fev1Map.put("name", "fev1");
		fev1Map.put("data", fev1DataList);
		pefMap.put("name", "pef");
		pefMap.put("data", pefDataList);
		List<Map<String, Object>> list = lungCapacityDao.getDataByTime(Long.valueOf(user_id), datemin, datemax);
		for (Map<String, Object> map : list) {
/*			List<Object> fvcList = new ArrayList<Object>();
			List<Object> fev1List = new ArrayList<Object>();
			List<Object> pefList = new ArrayList<Object>();
			fvcList.add(map.get("create_date"));
			fvcList.add(map.get("fvc"));
			fev1List.add(map.get("create_date"));
			fev1List.add(map.get("fev1"));
			pefList.add(map.get("create_date"));
			pefList.add(map.get("pef"));*/
			fvcDataList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("fvc"));
				}
			});
			fev1DataList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("fev1"));
				}
			});
			pefDataList.add(new ArrayList<Object>() {
				{
					add(map.get("create_date"));
					add(map.get("pef"));
				}
			});
		}
		List<Object> dataList = new ArrayList<Object>();
		dataList.add(fvcMap);
		dataList.add(fev1Map);
		dataList.add(pefMap);
		dataMap.put("list", dataList);
		infoJson.setData(dataMap);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: getBloodOxygenPage @Description: 后台血氧按时分页 @param user_id @param
	 *         datemin @param datemax @param pageno @param
	 *         pagesize @return @throws
	 */
	public InfoJson getBloodOxygenPage(String user_id, String datemin, String datemax, int pageno, int pagesize) {
		return InfoJson
				.setSucc(bloodoxygenDao.pageQueryByTime(Long.valueOf(user_id), datemin, datemax, pageno, pagesize));
	}

	/**
	 * 
	 * @Title: getBloodPressurePage @Description: 后台血压按时分呀页 @param
	 *         user_id @param datemin @param datemax @param pageno @param
	 *         pagesize @return @throws
	 */
	public InfoJson getBloodPressurePage(String user_id, String datemin, String datemax, int pageno, int pagesize) {
		return InfoJson
				.setSucc(bloodPressureDao.pageQueryByTime(Long.valueOf(user_id), datemin, datemax, pageno, pagesize));
	}

	/**
	 * 
	 * @Title: getBloodSugarPage @Description: 后台血糖按时分页 @param user_id @param
	 *         period @param datemin @param datemax @param pageno @param
	 *         pagesize @return @throws
	 */
	public InfoJson getBloodSugarPage(String user_id, int period, String datemin, String datemax, int pageno,
			int pagesize) {
		return InfoJson.setSucc(
				bloodSugarDao.pageQueryByTime(Long.valueOf(user_id), period, datemin, datemax, pageno, pagesize));
	}

	/**
	 * 
	 * @Title: getEarThermometerPage @Description: 后台耳温按时分页 @param
	 *         user_id @param datemin @param datemax @param pageno @param
	 *         pagesize @return @throws
	 */
	public InfoJson getEarThermometerPage(String user_id, String datemin, String datemax, int pageno, int pagesize) {
		return InfoJson
				.setSucc(earThermometerDao.pageQueryByTime(Long.valueOf(user_id), datemin, datemax, pageno, pagesize));
	}

	/**
	 * 
	 * @Title: getLungCapacityPage @Description: 后台肺活量按时分页 @param user_id @param
	 *         datemin @param datemax @param pageno @param
	 *         pagesize @return @throws
	 */
	public InfoJson getLungCapacityPage(String user_id, String datemin, String datemax, int pageno, int pagesize) {
		return InfoJson
				.setSucc(lungCapacityDao.pageQueryByTime(Long.valueOf(user_id), datemin, datemax, pageno, pagesize));
	}

	/**
	 * 
	 * @Title: getUrineTestPage @Description: 后台尿检按时分页 @param user_id @param
	 *         datemin @param datemax @param pageno @param
	 *         pagesize @return @throws
	 */
	public InfoJson getUrineTestPage(String user_id, String datemin, String datemax, int pageno, int pagesize) {
		return InfoJson
				.setSucc(urineTestDao.queryPageByTime(Long.valueOf(user_id), datemin, datemax, pageno, pagesize));
	}
}
