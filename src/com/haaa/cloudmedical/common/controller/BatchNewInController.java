package com.haaa.cloudmedical.common.controller;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Constant;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.service.FileUploadService;
import com.haaa.cloudmedical.common.util.LogPrinter;
import com.oreilly.servlet.MultipartRequest;

@Controller
@RequestMapping("/batch")
public class BatchNewInController {

	@Autowired
	private FileUploadService service;

	@RequestMapping("/read.action")
	@ResponseBody
	public Object readExcel(HttpServletRequest request) {
		String data_type = request.getHeader("data_type");
		ResponseDTO dto  = new ResponseDTO(); 
		String sheet_name = null;
		String[] params = null;
		int readRows = Constant.READ_EXCEL_ROWS;
		if (data_type.equals("1")) {
			params = Constant.IMPORT_MEMBER_PARAMS;
			sheet_name = Constant.IMPORT_MEMBER_SHEETNAME;
		} else if (data_type.equals("2")) {
			params = Constant.IMPORT_CHECK_PARAMS;
			sheet_name = Constant.IMPORT_CHECK_SHEETNAME;
		} else if (data_type.equals("3")) {
			params = Constant.IMPORT_DOCTOR_PARAMS;
			sheet_name = Constant.IMPORT_DOCTOR_SHEETNAME;
		} else if (data_type.equals("4")) {
			params = Constant.IMPORT_OPERATER_PARAMS;
			sheet_name = Constant.IMPORT_OPERATER_SHEETNAME;
		}
		HttpSession session = request.getSession();
		String saveDirectory = session.getServletContext().getRealPath(File.separator)+File.separator+"upload"+File.separator+"excel";
		File savePath = new File(saveDirectory);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, saveDirectory, 80*1024*1024, "UTF-8");
			Enumeration<?> elements =multi.getFileNames();
			while(elements.hasMoreElements()){
				String name = (String) elements.nextElement();
				File file = multi.getFile(name);
				if(file!=null){
					LogPrinter.info("开始读取文件");
					dto=service.read(file, sheet_name, readRows, params,data_type);
					file.delete();
					LogPrinter.info("结束读取文件");
				}
			}
		} catch (Exception e) {
			dto.setErrmsg("上传失败!");
			e.printStackTrace();
		}
		return dto;
	}
}
