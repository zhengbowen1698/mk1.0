package com.haaa.cloudmedical.entity;

import java.util.Date;


public class Electrocardiograph {
	private String order_id;

	private String equipment_use_order_id;

	private String HeartRate;

	private String result;

	private String ecg1;
	
	private String ecg2;
	
	private String ecg3;
	
	private String ecg4;
	
	private String ecg5;

	private String create_date;

	private String update_date;
	
	
	

	/**
	 * 
	 */
	public Electrocardiograph() {
		super();
	}

	/**
	 * @param order_id
	 * @param equipment_use_order_id
	 * @param heartRate
	 * @param result
	 * @param ecg1
	 * @param ecg2
	 * @param ecg3
	 * @param ecg4
	 * @param ecg5
	 * @param create_date
	 * @param update_dates
	 */
	public Electrocardiograph(String order_id, String equipment_use_order_id, String heartRate, String result,
			String ecg1, String ecg2, String ecg3, String ecg4, String ecg5, String create_date, String update_date) {
		super();
		this.order_id = order_id;
		this.equipment_use_order_id = equipment_use_order_id;
		HeartRate = heartRate;
		this.result = result;
		this.ecg1 = ecg1;
		this.ecg2 = ecg2;
		this.ecg3 = ecg3;
		this.ecg4 = ecg4;
		this.ecg5 = ecg5;
		this.create_date = create_date;
		this.update_date = update_date;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getEquipment_use_order_id() {
		return equipment_use_order_id;
	}

	public void setEquipment_use_order_id(String equipment_use_order_id) {
		this.equipment_use_order_id = equipment_use_order_id;
	}

	public String getHeartRate() {
		return HeartRate;
	}

	public void setHeartRate(String heartRate) {
		HeartRate = heartRate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getEcg1() {
		return ecg1;
	}

	public void setEcg1(String ecg1) {
		this.ecg1 = ecg1;
	}

	public String getEcg2() {
		return ecg2;
	}

	public void setEcg2(String ecg2) {
		this.ecg2 = ecg2;
	}

	public String getEcg3() {
		return ecg3;
	}

	public void setEcg3(String ecg3) {
		this.ecg3 = ecg3;
	}

	public String getEcg4() {
		return ecg4;
	}

	public void setEcg4(String ecg4) {
		this.ecg4 = ecg4;
	}

	public String getEcg5() {
		return ecg5;
	}

	public void setEcg5(String ecg5) {
		this.ecg5 = ecg5;
	}

	

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	@Override
	public String toString() {
		return "Electrocardiograph [order_id=" + order_id + ", equipment_use_order_id=" + equipment_use_order_id
				+ ", HeartRate=" + HeartRate + ", result=" + result + ", ecg1=" + ecg1 + ", ecg2=" + ecg2 + ", ecg3="
				+ ecg3 + ", ecg4=" + ecg4 + ", ecg5=" + ecg5 + ", create_date=" + create_date + ", update_date="
				+ update_date + "]";
	}


	
}
