package com.haaa.cloudmedical.entity;

public class HospitalReport {
	private String order_id;
	private String report_no;
	private String hospital_report_time;
	private String hospital_report_days;
	private String hosp_order_id;
	private String hosp_name;
	private String department_order_id;
	private String department_name;
	private String patient_id;
	private String patient_name;
	private String report_doctor;
	private String hospital_report_disease;
	private String hospital_report_result;
	private String create_date;
	private String update_date;
	private String report_upload_index;
	private String doctor_id;
	private String doctor_name;
	private String report_doctor_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getReport_no() {
		return report_no;
	}

	public void setReport_no(String report_no) {
		this.report_no = report_no;
	}

	public String getHospital_report_time() {
		return hospital_report_time;
	}

	public void setHospital_report_time(String hospital_report_time) {
		this.hospital_report_time = hospital_report_time;
	}

	public String getHospital_report_days() {
		return hospital_report_days;
	}

	public void setHospital_report_days(String hospital_report_days) {
		this.hospital_report_days = hospital_report_days;
	}

	public String getHosp_order_id() {
		return hosp_order_id;
	}

	public void setHosp_order_id(String hosp_order_id) {
		this.hosp_order_id = hosp_order_id;
	}

	public String getHosp_name() {
		return hosp_name;
	}

	public void setHosp_name(String hosp_name) {
		this.hosp_name = hosp_name;
	}

	public String getDepartment_order_id() {
		return department_order_id;
	}

	public void setDepartment_order_id(String department_order_id) {
		this.department_order_id = department_order_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getReport_doctor() {
		return report_doctor;
	}

	public void setReport_doctor(String report_doctor) {
		this.report_doctor = report_doctor;
	}

	public String getHospital_report_disease() {
		return hospital_report_disease;
	}

	public void setHospital_report_disease(String hospital_report_disease) {
		this.hospital_report_disease = hospital_report_disease;
	}

	public String getHospital_report_result() {
		return hospital_report_result;
	}

	public void setHospital_report_result(String hospital_report_result) {
		this.hospital_report_result = hospital_report_result;
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

	public String getReport_upload_index() {
		return report_upload_index;
	}

	public void setReport_upload_index(String report_upload_index) {
		this.report_upload_index = report_upload_index;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getReport_doctor_id() {
		return report_doctor_id;
	}

	public void setReport_doctor_id(String report_doctor_id) {
		this.report_doctor_id = report_doctor_id;
	}

	@Override
	public String toString() {
		return "HospitalReport [order_id=" + order_id + ", report_no=" + report_no + ", hospital_report_time="
				+ hospital_report_time + ", hospital_report_days=" + hospital_report_days + ", hosp_order_id="
				+ hosp_order_id + ", hosp_name=" + hosp_name + ", department_order_id=" + department_order_id
				+ ", department_name=" + department_name + ", patient_id=" + patient_id + ", patient_name="
				+ patient_name + ", report_doctor=" + report_doctor + ", hospital_report_disease="
				+ hospital_report_disease + ", hospital_report_result=" + hospital_report_result + ", create_date="
				+ create_date + ", update_date=" + update_date + ", report_upload_index=" + report_upload_index
				+ ", doctor_id=" + doctor_id + ", doctor_name=" + doctor_name + ", report_doctor_id=" + report_doctor_id
				+ "]";
	}

}
