package com.haaa.cloudmedical.app.equipment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haaa.cloudmedical.common.annotation.Log;
import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.DataTransfromUtil;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.dao.equipment.ElectrocardiographDao;
import com.haaa.cloudmedical.entity.Electrocardiograph;
import com.haaa.cloudmedical.entity.EquipmentUse;

/**
 * 
 * @author Bowen Fan
 *
 */
@Service
@Log(name = "app心电")
public class ElectrocardiographService {

	@Resource
	private ElectrocardiographDao electrocardiographDao;

	/**
	 * 
	 * @throws Exception
	 * @Title: add @Description: 心电数据添加 @param equipmentUse @param
	 *         electrocardiograph @return @throws
	 */
	@Transactional
	@Log(name = "心电记录添加")
	public InfoJson add(EquipmentUse equipmentUse, Electrocardiograph electrocardiograph) throws Exception {
		InfoJson infoJson = new InfoJson();
		DataTransfromUtil.electroTrans(electrocardiograph);
		if (equipmentUse.getCreate_date() == null) {
			Date date = new Date();
			equipmentUse.setCreate_date(DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss"));
		}
		equipmentUse.setEquipment_name("electrocardiogaph");
		equipmentUse.setEquipment_property_order_id(Constant.ELECTROCARDIOGRAPH_ID);
		//持久化到数据库并获取主键
		Long id = (Long) electrocardiographDao.insertAndGetKey(equipmentUse, "c_equipment_use");
		electrocardiograph.setCreate_date(equipmentUse.getCreate_date());
		electrocardiograph.setEquipment_use_order_id(String.valueOf(id));
		//持久化到数据库并获取主键
		long result = (Long) electrocardiographDao.insertAndGetKey(electrocardiograph, "c_electrocardiograph");
		//通过主键查询出添加的内容用于封装返回对象
		Map<String, Object> map = electrocardiographDao.queryDetailById(result, "c_electrocardiograph");
		//格式化日期 yyyy-MM-dd HH:mm:ss转成yyyy-MM-dd 
		map.put("datetime", DateUtil.DateFormat(map.remove("create_date"), "yyyy-MM-dd"));
		//查询结果移除心电数据
		map.remove("ecg1");
		map.remove("ecg2");
		map.remove("ecg3");
		map.remove("ecg4");
		map.remove("ecg5");
		infoJson.setData(map);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: queryRecent @Description: 曲线图近期数据查询 @param
	 *         user_id @return @throws
	 */
	public InfoJson queryRecent(long user_id, int days) {
		InfoJson infoJson = new InfoJson();
		if (electrocardiographDao.queryRecent(user_id, days).size() != 0) {
			infoJson.setCount(infoJson.getCount() + 1);
		}
		List<Map<String, Object>> list = electrocardiographDao.queryRecent(user_id, days);
		infoJson.setData(list);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: pageQuery 
	 * @Description: 分页查询，按月分页查询 
	 * @param user_id 
	 * @param year_month 
	 * @param pageno 
	 * @param pagesize 
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unused")
	public InfoJson pageQuery(long user_id, String year_month, int pageno, int pagesize) {
		InfoJson infoJson = new InfoJson();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<Object> monthList = new ArrayList<Object>();
		// 如果year_month为空，进行普通分页查询
		if (!StringUtil.isEmpty(year_month)) {
			dataList = electrocardiographDao.queryByMonth(user_id, year_month, pageno, pagesize);
			infoJson.setCount(electrocardiographDao.pageNumberByMonth(user_id , year_month, pagesize));
		} else {
			dataList = electrocardiographDao.pageQuery(user_id, pageno, pagesize);
			infoJson.setCount(electrocardiographDao.pageNumber(user_id , pagesize));
		}
		if (dataList.size() > 0) {
			Iterator<Map<String, Object>> iterator = dataList.iterator();
			Map<String, Object> map = iterator.next();
			Date date = (Date) map.get("datetime");
			String yearMonth = DateUtil.dateFormat(date, "yyyy-MM");
			String monthDay = DateUtil.dateFormat(date, "MM-dd");
			String tempYearMonth = null;
			// 分类可以用groupingBy代替  通过遍历循环,按照年月进行分组
			outer: while (true) {
				if (map == null) {
					break;
				}
				Map<String, Object> monthMap = new HashMap<String, Object>();
				tempYearMonth = yearMonth;
				monthList.add(monthMap);
				monthMap.put("yearmonth", yearMonth);
				List<Object> timeList = new ArrayList<Object>();
				while (tempYearMonth.equals(yearMonth)) {
					monthMap.put("timelist", timeList);
					Map<String, Object> timeMap = new HashMap<String, Object>();
					map.put("datetime", DateUtil.dateFormat(date, "MM-dd HH:mm"));
					map.put("month", yearMonth);
					timeMap.put("singledata", map);
					int hour = DateUtil.getHour(date);
					int period = periodDefine(hour);
					timeMap.put("period", period);
					timeMap.put("month", yearMonth);
					timeList.add(timeMap);
					if (iterator.hasNext()) {
						map = iterator.next();
						date = (Date) map.get("datetime");
						yearMonth = DateUtil.dateFormat(date, "yyyy-MM");
						monthDay = DateUtil.dateFormat(date, "MM-dd");
					} else {
						break outer;
					}
				}
			}
		}
		infoJson.setData(monthList);
		infoJson.setStatus(1);
		return infoJson;
	}

	/**
	 * 
	 * @Title: periodDefine 
	 * @Description: 根据查询的时间 判断早中晚时间段，-1上午，0下午，1晚上 
	 * @param hour 
	 * @return 
	 * @throws
	 */
	private int periodDefine(int hour) {
		int period = 0;
		if (hour < Constant.MORNING_END && hour >= 0) {
			period = -1;
		} else if (hour < Constant.NIGHT_BEGIN) {
			period = 0;
		} else {
			period = 1;
		}
		return period;
	}

	/**
	 * 返回具有信息的年月
	 * 
	 * @param user_id
	 * @return
	 */
	public InfoJson queryMonth(Long user_id) {
		InfoJson infoJson = new InfoJson();
		infoJson.setData(electrocardiographDao.queryMonth(user_id));
		infoJson.setStatus(1);
		return infoJson;
	}

	public InfoJson getEcgById(String user_id, String use_id) {
		InfoJson infoJson = new InfoJson();
		Map<String, Object> ecgMap = null;
		if (!StringUtil.isEmpty(use_id)) {
			ecgMap = electrocardiographDao.getEcgByUseId(Long.valueOf(use_id));
		} else if (!StringUtil.isEmpty(user_id)) {
			ecgMap = electrocardiographDao.getEcgByUserId(Long.valueOf(user_id));
		}
		if (ecgMap != null) {
			infoJson.setCount(1);
		}
		infoJson.setData(ecgMap);
		infoJson.setStatus(1);
		return infoJson;
	}

}
