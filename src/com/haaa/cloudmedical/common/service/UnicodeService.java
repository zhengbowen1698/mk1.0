package com.haaa.cloudmedical.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.dao.UnicodeDao;
import com.haaa.cloudmedical.common.entity.InfoJson;
import com.haaa.cloudmedical.common.util.redis.RedisCacheUtil;

@Service
public class UnicodeService {

	@Resource
	private UnicodeDao unicodeDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private RedisCacheUtil redisCacheUtil;

	private Logger logger = Logger.getLogger(UnicodeService.class);

	@SuppressWarnings("unchecked")
	public void init() {
		// unicode_init unicode初始化标志位，如果为true,表示unicode已初始化

		String sql = "select order_id,unicode_name,unicode_type from k_unicode";
		List<Map<String, Object>> resultlList = unicodeDao.queryAll(sql);

		String order_id = null;
		String order_id1 = null;
		String order_id2 = null;
		String unicode_type = null;
		List<Map<String, Object>> subList = null;
		Map<String, List<Map<String, Object>>> type_map = new HashMap<String, List<Map<String, Object>>>();
		System.out.println(resultlList);
		/**
		 * 将order_id取出，作为键值对的键
		 */
		for (Map<String, Object> map : resultlList) {
			order_id = String.valueOf(map.get("order_id"));
			unicode_type = (String) map.get("unicode_type");
			if (!type_map.containsKey(unicode_type)) {
				subList = new ArrayList<Map<String, Object>>();
				subList.add(map);
				type_map.put(unicode_type, subList);
			} else {
				subList = type_map.get(unicode_type);
				subList.add(map);
			}
			/*order_id1 = "unicode_id_" + order_id;*/
			redisCacheUtil.setCacheObject("unicode_id_" + order_id, map);
/*			order_id2 = "uni_id_" + order_id;
			redisCacheUtil.setCacheObject(order_id2, map.get("unicode_name"));
			redisCacheUtil.setCacheObject(map.get("unicode_name") + "", map.get("order_id"));*/
			
			redisCacheUtil.setCacheObject("unicode_name_"+map.get("unicode_name"), map.get("order_id"));


		}
		Set<String> type_set = type_map.keySet();

		for (Object key : type_set) {
			System.out.println("type___" + key);
			redisCacheUtil.setCacheList("unicode_type_" + key, type_map.get(key));
		}
		System.out.println("unicode初始化结束");

	}

	public InfoJson getById(String id) {
		InfoJson infoJson = new InfoJson();
		infoJson.setData(redisCacheUtil.getCacheObject("unicode_id_" + id));
		infoJson.setStatus(1);
		return infoJson;
	}

	public InfoJson getByType(String type) {
		InfoJson infoJson = new InfoJson();
		infoJson.setData(redisCacheUtil.getCacheList("unicode_type_" + type));
		infoJson.setStatus(1);
		return infoJson;
	}

	public String getNameById(String id) {
		String result = null;
		result = String.valueOf(((Map)redisCacheUtil.getCacheObject("unicode_id_" + id)).get("order_id"));
		return result;
	}

	public String getIdByName(String name) {
		String result = null;
		result = String.valueOf(((Map)redisCacheUtil.getCacheObject("unicode_name_"+name)).get("unicode_name"));
		return result;
	}

	/**
	 * 
	 * @Title: getBloodSugarPeriod @Description: app需求返回码表 @return @throws
	 */
	@SuppressWarnings("unchecked")
	public InfoJson getBloodSugarPeriod() {
		InfoJson infoJson = new InfoJson();
		List<Object> list = redisCacheUtil.getCacheList("unicode_type_" + "blood_sugar");
		while (list.size() > 3) {
			list.remove(list.size() - 1);
		}

		for (int i = 0; i < 3; i++) {
			String name = ((Map<String, String>) list.get(i)).get("unicode_name");
			if (name.contains("前")) {
				((Map<String, String>) list.get(i)).put("unicode_name", "餐前");
			} else if (name.contains("后")) {
				((Map<String, String>) list.get(i)).put("unicode_name", "餐后");
			} else {
				((Map<String, String>) list.get(i)).put("unicode_name", "空腹");
			}
		}
		infoJson.setData(list);
		infoJson.setStatus(1);
		return infoJson;
	}

}
