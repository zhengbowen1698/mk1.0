package com.haaa.cloudmedical.common.entity;

public class Attachment implements java.io.Serializable {

	private static final long serialVersionUID = 998271974498813782L;
	private int pic_num;
	private String order_id;
	private String report_type;
	private String file_name;
	private String uploadUrl;
	private String uplodeDir;
	private String contentType;
	private String description;
	private long fileLength;

	public int getPic_num() {
		return pic_num;
	}

	public void setPic_num(int pic_num) {
		this.pic_num = pic_num;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getUplodeDir() {
		return uplodeDir;
	}

	public void setUplodeDir(String uplodeDir) {
		this.uplodeDir = uplodeDir;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	@Override
	public String toString() {
		return "Attachment [pic_num=" + pic_num + ", order_id=" + order_id + ", report_type=" + report_type
				+ ", file_name=" + file_name + ", uploadUrl=" + uploadUrl + ", uplodeDir=" + uplodeDir
				+ ", contentType=" + contentType + ", description=" + description + ", fileLength=" + fileLength + "]";
	}

}