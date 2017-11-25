package com.haaa.cloudmedical.common.dao;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.haaa.cloudmedical.common.entity.Page;


public class BaseTemplateDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	/**
	 * @description 将
	 * @param map
	 * @return
	 */
	protected String[] paramArray(Map<String, Object> map) {
		int i = 0;
		int size = map.size();
		StringBuilder paramName = new StringBuilder();
		StringBuilder paramValue = new StringBuilder();
		StringBuilder updateStatement = new StringBuilder();
		Set<String> set = map.keySet();
		for (String key : set) {
			Object object = map.get(key);
			if (object != null) {
				String value = "";
				if (object.getClass() == Date.class) {
					value = DateFormat.getDateTimeInstance().format(object);
				} else {
					value = object.toString();
				}
				if (i < size - 1) {
					paramName.append(key + ",");
					paramValue.append("?,");
					updateStatement.append(key + "='" + value + "',");
				} else {
					paramName.append(key);
					paramValue.append("?");
					updateStatement.append(key + "='" + value + "'");
				}
			}
			i++;
		}
		return new String[] { paramName.toString(), paramValue.toString(), updateStatement.toString() };
	}
	/**
	 * @description 获得参数和值
	 * @param map
	 * @return
	 */
	protected Map<String, Object[]> getParamsAndValues(Map<String,Object> map) {
		Map<String, Object[]> property = null;
		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		for (Map.Entry<String,Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if(value!=null&&value.toString().trim().length()>0&&!value.toString().equals("null")){
				params.add(entry.getKey());
				values.add(value);
			}
		}
		if (params.size() > 0 && values.size() > 0) {
			property = new HashMap<String, Object[]>();
			property.put("params", params.toArray());
			property.put("values", values.toArray());
		}
		return property;
	}

	protected Map<String, Object[]> getParamsAndValues(Object object) {
		Map<String, Object[]> map = null;
		List<String> params = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		Field[] fields = object.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field field : fields) {
			try {
				Object value = field.get(object);
				if (value!=null&&value.toString().trim().length()>0&&!value.toString().equals("null")) {
					params.add(field.getName());
					values.add(value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} 
		}
		if (params.size() > 0 && values.size() > 0) {
			map = new HashMap<String, Object[]>();
			map.put("params", params.toArray());
			map.put("values", values.toArray());
		}
		return map;
	}

	protected Object getId(Object object, String primaryKey) {
		Object value = -1;
		try {
			Field field = object.getClass().getDeclaredField(primaryKey);
			field.setAccessible(true);
			try {
				value = field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return value;
	}

	// 增
	/**
	 * 
	 * @param map
	 * @param table
	 * @return 插入后的主键值
	 */
	public long insert(Map<String, Object> map, String table) {
		long key = 0;
		if (map.size() > 0) {
			Map<String,Object[]> property = this.getParamsAndValues(map);
			Object[] params = property.get("params");
			Object[] values  =property.get("values");
			String sql ="insert into "+table+"("+StringUtils.join(params, ",")+") values("+StringUtils.repeat("?", ",", params.length)+")";
			System.out.println(sql);
			jdbcTemplate.update(sql, values);
			sql = "select LAST_INSERT_ID()";
			key = jdbcTemplate.queryForObject(sql, Long.class);
		}
		return key;
	}
	public long insert(Object object, String table) {
		long key = 0;
		Map<String,Object[]> property = this.getParamsAndValues(object);
		Object[] params = property.get("params");
		Object[] values  =property.get("values");
		String sql ="insert into "+table+"("+StringUtils.join(params, ",")+") values("+StringUtils.repeat("?", ",", params.length)+")";
		System.out.println(sql);
		jdbcTemplate.update(sql, values);
		sql = "select LAST_INSERT_ID()";
		key = jdbcTemplate.queryForObject(sql, Long.class);
		return key;
	}

	// 删
	/**
	 * 
	 * @param table
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int delete(String table, String id) {
		String sql = "select column_name from information_schema.`key_column_usage` where table_name='" + table
				+ "' and constraint_name='primary' limit 1";
		String primaryKey = jdbcTemplate.queryForObject(sql, String.class);
		sql = "delete from " + table + " where " + primaryKey + "=?";
		System.out.println(sql);
		int rows = jdbcTemplate.update(sql, id);
		return rows;
	}
	
	public int delete(Object object,String table){
		String sql = "select column_name from information_schema.`key_column_usage` where table_name='" + table
				+ "' and constraint_name='primary' limit 1";
		String primaryKey = jdbcTemplate.queryForObject(sql, String.class);
		Object id = this.getId(object, primaryKey);
		sql = "delete from " + table + " where " + primaryKey + "=?";
		System.out.println(sql);
		int rows = jdbcTemplate.update(sql, id);
		return rows;
		
	}

	// 改
	/**
	 * 
	 * @param map
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public int update(Map<String, Object> map, String table) {
		int rows = 0;
		if (map.size() > 0) {
			String sql = "select column_name from information_schema.`key_column_usage` where table_name='" + table
					+ "' and constraint_name='primary' limit 1";
			String primaryKey = jdbcTemplate.queryForObject(sql, String.class);
			Object id = map.get(primaryKey);
			Map<String,Object[]> property = this.getParamsAndValues(map);
			Object[] params = property.get("params");
			Object[] values  =property.get("values");
			sql = "update "+table+" set "+StringUtils.join(params, "=?,")+"=? where "+primaryKey+" = '"+id+"'";
			System.out.println(sql);
			rows = jdbcTemplate.update(sql,values );
		}
		return rows;
	}
	
	public int update(Object object,String table){
		int rows = 0;
		String sql = "select column_name from information_schema.`key_column_usage` where table_name='" + table
				+ "' and constraint_name='primary' limit 1";
		String primaryKey = jdbcTemplate.queryForObject(sql, String.class);
		Object id = this.getId(object, primaryKey);
		Map<String,Object[]> property = this.getParamsAndValues(object);
		Object[] params = property.get("params");
		Object[] values  =property.get("values");
		sql = "update "+table+" set "+StringUtils.join(params, "=?,")+"=? where "+primaryKey+" = '"+id+"'";
		rows = jdbcTemplate.update(sql, values);
		return rows;
	}
	public <T> T getValue(String sql, Object[] args, Class<T> elementType) {
		int size = this.jdbcTemplate.queryForObject("select count(*) from ( "+sql+" ) t", args, Integer.class);
		if(size==1){
			return this.jdbcTemplate.queryForObject(sql, args, elementType);
		}
		return null;
	}
	
	public Map<String, Object> get(String sql, Object[] args) {
		Map<String, Object> result = this.jdbcTemplate.queryForMap(sql, args);
		return result;
	}
	
	public <T> T get(String sql, Object[] args, Class<T> beanClass) {
		T result = this.jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(beanClass));
		return result;
	}
	
	public <T> List<T> select(String sql, Object[] args, Class<T> beanClass) {
		List<T> result = this.jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T>(beanClass));
		return result;
	}
	
	public List<Map<String,Object>> select(String sql){
		 List<Map<String,Object>>  list =jdbcTemplate.queryForList(sql);
		return list;
	}
	public List<Map<String,Object>> select(String sql,Object... args){
		 List<Map<String,Object>>  list =jdbcTemplate.queryForList(sql, args);
		return list;
	}

	public int execute(String sql) {
		int rows = jdbcTemplate.update(sql);
		return rows;
	}

	public int execute(String sql, Object... args) {
		int rows = jdbcTemplate.update(sql, args);
		return rows;
	}

	// 将一个对象存入缓存
	public void redisPut(String key, Object value) {
		BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps(key);
		ops.set(value);
	}

	// 将一个对象存入缓存,有效期seconds分钟
	public void redisPut(String key, Object value, long seconds) {
		BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps(key);
		ops.set(value);
		ops.expire(seconds, TimeUnit.SECONDS);
	}

	// 从缓存中取得对象
	public Object redisGet(String key) {
		BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps(key);
		Object value = ops.get();
		return value;
	}

	// 从缓存中删除一个对象
	public void redisDelete(String key) {
		redisTemplate.delete(key);
	}

	// 分页查询
	/**
	 * 
	 * @param sql
	 * @param pageno
	 * @return
	 */
	public Page pageQuery(String sql, int pageno) {
		Page page = this.pageQuery(sql, pageno, 0);
		return page;
	}
	
	public Page pageQuery(String sql, Object[] args,int pageno) {
		Page page = this.pageQuery(sql, args,pageno, 0);
		return page;
	}
	

	/**
	 * 
	 * @param sql
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public Page pageQuery(String sql, int pageno, int pagesize) {
		Page page = new Page();
		if (pagesize > 0)
			page.setPageSize(pagesize);
		int start = (pageno - 1) * page.getPageSize();
		int size = jdbcTemplate.queryForObject("select count(*) from(" + sql + ") t", Integer.class);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql + " limit " + start + "," + page.getPageSize());
		list.forEach(m->m.forEach((k,v)->m.putIfAbsent(k, "")));
		page.setData(list);
		page.setPageCount(
				size % page.getPageSize() == 0 ? (size / page.getPageSize()) : (size / page.getPageSize() + 1));
		page.setPageNo(pageno);
		page.setRecordCount(size);
		return page;
	}
	
	public Page pageQuery(String sql, Object[] args,int pageno, int pagesize) {
		Page page = new Page();
		if (pagesize > 0)
			page.setPageSize(pagesize);
		int start = (pageno - 1) * page.getPageSize();
		int size = jdbcTemplate.queryForObject("select count(*) from(" + sql + ") t", args,Integer.class);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql + " limit " + start + "," + page.getPageSize(),args);
		list.forEach(m->m.forEach((k,v)->m.putIfAbsent(k, "")));
		page.setData(list);
		page.setPageCount(
				size % page.getPageSize() == 0 ? (size / page.getPageSize()) : (size / page.getPageSize() + 1));
		page.setPageNo(pageno);
		page.setRecordCount(size);
		return page;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = this.jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("connection 获取失败");
		}
		return conn;
	}
	
	public Map<String, String> callProcedure(String procedure, Object[] inParams, String[] outParams) {
		int start = 0;
		Connection conn = this.getConnection();
		Map<String, String> map = null;
		try {
			CallableStatement cs = conn.prepareCall("{ call " + procedure + "}");
			if (inParams != null && inParams.length > 0) {
				start = inParams.length;
				for (int i = 0; i < inParams.length; i++) {
					cs.setObject(i + 1, inParams[i]);
				}
			}
			if (outParams != null && inParams.length > 0) {
				for (int i = 0; i < outParams.length; i++) {
					cs.registerOutParameter(i + start, Types.JAVA_OBJECT);
				}
			}
			cs.execute();
			if (outParams != null && outParams.length > 0) {
				map = new HashMap<>(outParams.length);
				for (int i = 0; i < outParams.length; i++) {
					map.put(outParams[i], cs.getString(i + 1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;

	}
}