package com.haaa.cloudmedical.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.util.LogPrinter;

@Repository
public class FileFlushDao extends BaseTemplateDao{
	
	
	public void write(String sql,List<Object[]> batchArgs){
		this.jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	public String call(){
		LogPrinter.info("开始执行存储过程");
		String message="";
		Connection conn = null;
		try {
			conn = this.jdbcTemplate.getDataSource().getConnection();
			CallableStatement cs =conn.prepareCall("{call import_u(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.executeUpdate();
			message="总记录数："+cs.getInt(1)+";\n正确插入："+cs.getInt(2)+";\n异常数据量："+cs.getInt(3)+";\n未正常正确插入："+cs.getInt(4);
			LogPrinter.info(message);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		LogPrinter.info("结束执行存储过程");
		return message;
	}
	
	
	public String callAIO(){
		System.out.println("开始执行存储过程");
		String message="";
		Connection conn = null;
		try {
			conn = this.jdbcTemplate.getDataSource().getConnection();
			CallableStatement cs =conn.prepareCall("{call import_aio(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.executeUpdate();
			message="总记录数："+cs.getInt(1)+";总记录数："+cs.getInt(1)+";正确插入："+cs.getInt(2)+";异常数据量："+cs.getInt(3)+";未正常正确插入："+cs.getInt(4);
			LogPrinter.info(message);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		LogPrinter.info("结束执行存储过程");
		return message;
	}
	
}
