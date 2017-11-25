package com.haaa.cloudmedical.wechat.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.common.util.MD5Util;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.wechat.dao.WxLoginDao;
import com.haaa.cloudmedical.wechat.service.IWxLoginService;

@Service
public class WxLoginServiceImpl implements IWxLoginService {

    @Autowired
    WxLoginDao dao;

    @Override
    public StdDTO doLogin(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        //
        StdDTO dto = new StdDTO();
        dto.setStatus(0);
        if(StringUtil.isNotBlank(phone)){
            dto.setMsg("用户名不能为空");
            return dto;
        }
        if(StringUtil.isNotBlank(phone)){
            dto.setMsg("用户名不能为空");
            return dto;
        }
        String sql = "select * from n_user where user_phone = ? and user_pwd = ?";
        List<Map<String, Object>> list = dao.select(sql, phone, MD5Util.encode(password));
        if(list != null && list.size()> 0){
            Map<String, Object> map = list.get(0);
            request.getSession().setAttribute("user", map);
            dto.setStatus(1);
        }else{
            dto.setMsg("用户名或密码不正确");
        }
        
        return dto;
    }

}
