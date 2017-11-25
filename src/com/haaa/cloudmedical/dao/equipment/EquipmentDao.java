package com.haaa.cloudmedical.dao.equipment;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;
import com.haaa.cloudmedical.common.util.BeanPropertyUtil;
import com.haaa.cloudmedical.entity.EquipmentUse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class EquipmentDao extends BaseTemplateDao {
	
	public Number insertAndGetKey(final Object object ,String table) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final Map<String, Object> data = new HashMap<String, Object>();
		//通过自定义工具类将需要持久化的对象转换成map(通过反射方式拿到对象里面的字段)
		BeanPropertyUtil.toMapFromObject(data, object);
		//获得map里面的参数和值
		Map<String,Object[]> property = this.getParamsAndValues(data);
		Object[] params = property.get("params");
		Object[] values  =property.get("values");
		String sql ="insert into "+table+"("+StringUtils.join(params, ",")+") values("+StringUtils.repeat("?", ",", params.length)+")";
		System.out.println(sql);
		//获取插入行的主键
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, 1);
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
				return ps;
			}
		}, keyHolder);

		Number generatedId = keyHolder.getKey();
		return generatedId;
	}
	
	
	public long add(EquipmentUse equipmentUse){
		Map<String, Object> map = new HashMap<String, Object>();
		BeanPropertyUtil.toMapFromObject(map, equipmentUse);
		return insert(map, "c_equipment_use");
	}
	
	
	
	
	public Map<String, Object>queryDetailById(long id ,String table){
	    String sql = "select * from "+table+" where order_id=?";
	    return jdbcTemplate.queryForMap(sql, id);	
	}
	

	public String queryNameById(Object id) {
		String name = "";
		String sql = "select equipment_name from c_equipment_property where order_id =" + id;
		name = (String) this.jdbcTemplate.queryForObject(sql, String.class);
		return name;
	}

/*	public List<Map<String, Object>> queryByTime(String table, Object user_id, Object upTime, Object downTime) {
		String sql = "select DATE_FORMAT(A.create_date,'%Y-%m-%d %H:%i:%s'),B.* from c_equipment_use A," + table
				+ " B where A.order_id=B.equipment_use_order_id and A.user_id = ? and A.create_date between ? and ? order by A.create_date asc";
		return this.jdbcTemplate.queryForList(sql, new Object[] { user_id, upTime, downTime });
	}*/
	
	
	public int updateCommon(Map<String, Object> data){
		return super.update(data, "c_common_check");
	}
	

	
}
