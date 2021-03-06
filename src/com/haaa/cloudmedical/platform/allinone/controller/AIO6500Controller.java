package com.haaa.cloudmedical.platform.allinone.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haaa.cloudmedical.entity.AIO6500;
import com.haaa.cloudmedical.platform.allinone.service.AIO6500Service;

@Controller
@RequestMapping("/aio6500")
public class AIO6500Controller {

	@Resource
	private AIO6500Service service;

	private Logger logger = Logger.getLogger(AIO6500Controller.class);

	/**
	 * 
	 * @Title: login 
	 * @Description: 6500一体机登录
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping("/login.action")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		System.out.println(request.getSession().getId());
		PrintWriter out = response.getWriter();
		String paitInfo = service.login(request);
		out.print(paitInfo);
	}

	/**
	 * 
	 * @Title: saveData 
	 * @Description: 6500保存趋势数据 
	 * @param aio
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping("/saveData.action")
	public void saveData(AIO6500 aio, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		PrintWriter out = response.getWriter();
		try {
			out.println(service.saveData(aio));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			out.println("ERR_DB_UPLOAD_TREND:");
		}
	}

	/**
	 * 
	 * @Title: personalInfo 
	 * @Description: 6500数据存储时处理个人用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping("/personalInfo.action")
	public void personalInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		System.out.println(request.getSession().getId());
		PrintWriter out = response.getWriter();
		try {
			out.println(service.personalInfo(request));
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			out.println("ERR_SESSION_INVALID:");
		}
	}

	/**
	 * 
	 * @Title: saveReport 
	 * @Description: 保存6500体检报告文件
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping("/report.action")
	public void saveReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		System.out.println(request.getSession().getId());
		PrintWriter out = response.getWriter();
		try {
			out.println(service.saveReport(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			out.println("ERR_SESSION_INVALID:");			
		}		
	}
	
	/**
	 * 
	 * @Title: saveOriginalData 
	 * @Description: 保存6500原始数据文件
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping("/originalInfo.action")
	public void saveOriginalData(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		System.out.println(request.getSession().getId());
		PrintWriter out = response.getWriter();
		try {
			out.println(service.saveOriginalData(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			out.println("ERR_SESSION_INVALID:");			
		}	
	}
	
	/**
	 * 
	 * @Title: controlInfo 
	 * @Description: 处理6500文件传输控制信息
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping("/controlInfo.action")
	public void controlInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		System.out.println(request.getSession().getId());
		PrintWriter out = response.getWriter();
		try {
			out.println(service.controlInfo(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			out.println("ERR_SESSION_INVALID:");			
		}	
	}
	
	

}
