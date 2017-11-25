package com.haaa.cloudmedical.platform.analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.dao.CommonDao;
import com.haaa.cloudmedical.common.entity.Page;

@Service
public class AnalysisService {
	
	@Autowired
	private CommonDao dao;
	
	public Page getErrData(String startDate,String endDate,String data_type,int pageno){
		StringBuilder sql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		if(data_type.equals("1")){
			sql.append("select user_name,user_card,user_phone,doctor_card,doctor_phone,user_nation,user_address,"
					+ "user_marriage,user_work,user_medicare_card,user_medical_burden,user_blood,date_format(d_time,'%Y-%m-%d') d_time,"
					+ "result_value from log_user where 1=1");
			if (startDate != null && !startDate.equals("2")) {
				sql.append(" and date_format(d_time,'%Y-%m-%d') >= ? ");
				values.add(startDate);
			}
			if (endDate != null && !endDate.equals("")) {
				sql.append(" and date_format(d_time,'%Y-%m-%d') <= ? ");
				values.add(endDate);
			}
		}else if(data_type.equals("2")){
			sql.append("select hosp_name,user_name,user_card,user_phone,user_height,user_weight,BMI,HighPressure,"
					+ "LowPressure,PulseRate,BloodSugar,temperature,HeartRate,result,FVC,FEV1,PEF,FEF25,FEF75,FEF2575,"
					+ "Oxygen,URO,BLD,BIL,KET,GLU,PRO,PH,NIT,LEU,SG,VC,MAL,date_format(create_date,'%Y-%m-%d') create_date,"
					+ "sql_text from log_aio where 1=1");
			if (startDate != null && !startDate.equals("")) {
				sql.append(" and date_format(create_date,'%Y-%m-%d') >= ? ");
				values.add(startDate);
			}
			if (endDate != null && !endDate.equals("")) {
				sql.append(" and date_format(create_date,'%Y-%m-%d') <= ? ");
				values.add(endDate);
			}
		}
		
		Page page = dao.pageQuery(sql.toString(), values.toArray(), pageno,20);
		return page;
	}
}
