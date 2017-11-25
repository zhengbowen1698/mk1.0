package com.haaa.cloudmedical.entity;

import java.util.Date;

import com.haaa.cloudmedical.common.entity.Constant;

public class UrineTest {

	private String order_id;

	private String equipment_use_order_id;

	private String uro;

	private String bld;

	private String bil;

	private String ket;

	private String glu;

	private String pro;

	private String ph;

	private String nit;

	private String leu;

	private String sg;

	private String vc;

	private String create_date;

	private String update_date;
	
	

	/**
	 * 
	 */
	public UrineTest() {
		super();
	}

	/**
	 * @param order_id
	 * @param equipment_use_order_id
	 * @param uro
	 * @param bld
	 * @param bil
	 * @param ket
	 * @param glu
	 * @param pro
	 * @param ph
	 * @param nit
	 * @param leu
	 * @param sg
	 * @param vc
	 * @param create_date
	 * @param update_date
	 */
	public UrineTest(String order_id, String equipment_use_order_id, String uro, String bld, String bil, String ket,
			String glu, String pro, String ph, String nit, String leu, String sg, String vc, String create_date,
			String update_date) {
		super();
		this.order_id = order_id;
		this.equipment_use_order_id = equipment_use_order_id;
		this.uro = uro;
		this.bld = bld;
		this.bil = bil;
		this.ket = ket;
		this.glu = glu;
		this.pro = pro;
		this.ph = ph;
		this.nit = nit;
		this.leu = leu;
		this.sg = sg;
		this.vc = vc;
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

	public String getUro() {
		return uro;
	}

	public void setUro(String uro) {
		this.uro = uro;
	}

	public String getBld() {
		return bld;
	}

	public void setBld(String bld) {
		this.bld = bld;
	}

	public String getBil() {
		return bil;
	}

	public void setBil(String bil) {
		this.bil = bil;
	}

	public String getKet() {
		return ket;
	}

	public void setKet(String ket) {
		this.ket = ket;
	}

	public String getGlu() {
		return glu;
	}

	public void setGlu(String glu) {
		this.glu = glu;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getLeu() {
		return leu;
	}

	public void setLeu(String leu) {
		this.leu = leu;
	}

	public String getSg() {
		return sg;
	}

	public void setSg(String sg) {
		this.sg = sg;
	}

	public String getVc() {
		return vc;
	}

	public void setVc(String vc) {
		this.vc = vc;
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
		return "UrineTest [order_id=" + order_id + ", equipment_use_order_id=" + equipment_use_order_id + ", uro=" + uro
				+ ", bld=" + bld + ", bil=" + bil + ", ket=" + ket + ", glu=" + glu + ", pro=" + pro + ", ph=" + ph
				+ ", nit=" + nit + ", leu=" + leu + ", sg=" + sg + ", vc=" + vc + ", create_date=" + create_date
				+ ", update_date=" + update_date + "]";
	}

	
}
