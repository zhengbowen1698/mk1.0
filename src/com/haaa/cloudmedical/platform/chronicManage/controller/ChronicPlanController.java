package com.haaa.cloudmedical.platform.chronicManage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.entity.ChronicManage;
import com.haaa.cloudmedical.entity.ChronicTrack;
import com.haaa.cloudmedical.entity.User;
import com.haaa.cloudmedical.platform.chronicManage.service.ChronicPlanService;

@Controller
@RequestMapping("/chronicManage")
public class ChronicPlanController {

    @Autowired
    private ChronicPlanService service;

    /**
     * 查看慢病人员详情
     * @param user
     * @param chronicManage
     * @param dto
     * @param pageno
     * @return
     */
    @RequestMapping("/gridQuery.action")
    @ResponseBody
    public Object gridQuery(User user, ChronicManage model, String doctor_name, Integer pageNo, String datemin, String datemax) {
        if (pageNo == null) {
            pageNo = 1;
        }
        Page page = service.queryPage(user, doctor_name, model, pageNo, datemin, datemax);
        return page;
    }

    /**
     * 查看所有计划
     * @param user_id
     * @return
     */
    @RequestMapping("/userPlan.action")
    @ResponseBody
    public Object userPlans(String user_id) {
        Map<String, Object> user_chronic_plan = service.getUserPlanItem(user_id);
        return user_chronic_plan;
    }

    /**
     * 查看计划详情
     * @param order_id
     * @return
     */
    @RequestMapping("/userPlanDetail.action")
    @ResponseBody
    public Object userPlanDetail(String order_id) {
        List<Map<String, Object>> user_plan_info = service.userPlanDetail(order_id);
        return user_plan_info;
    }

    /**
     * 修改计划
     * @param order_id
     * @return
     */
    @RequestMapping("modifyUserPlan.action")
    @ResponseBody
    public Object modifyUserPlan(ChronicManage model) {
        boolean flag = service.updatePlan(model);
        return flag;
    }

    /**
     * 追踪前查询
     * @param order_id
     * @return
     */
    @RequestMapping("query.action")
    @ResponseBody
    public Object query(String order_id) {
        Map<String, Object> map = service.query(order_id);
        return map;

    }

    /**
     * 追踪该计划
     * @param order_id
     * @return
     */
    @RequestMapping("trackUserPlan.action")
    @ResponseBody
    public Object trackUserPlan(ChronicTrack model, String check_target) {
        ResponseDTO dto = new ResponseDTO();
        Long order_id = service.saveTrackPlan(model, check_target);
        if (order_id != null) {
            dto.setFlag(true);
            dto.setData(order_id);
        } else {
            dto.setFlag(false);
            dto.setData(null);
        }
        return dto;
    }

    /**
     * 新增计划
     * @param order_id
     * @return
     */
    @RequestMapping("addUserPlan.action")
    @ResponseBody
    public Object addUserPlan(ChronicManage chronicManage, String remarks) {
        Long order_id = service.addUserPlan(chronicManage);
        return order_id;
    }

    /**
     * 追踪列表
     * @param order_id
     * @return
     */
    @RequestMapping("queryTrackPlan.action")
    @ResponseBody
    public Object queryTrackPlan(String order_id) {
        List<Map<String, Object>> list = service.queryPlanList(order_id);
        return list;
    }

    /**
     * 查询模板列表
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年6月20日 上午9:39:36
     */
    @RequestMapping("queryTempPage.action")
    @ResponseBody
    public Object queryPlanTemplatePage(@RequestParam Map<String, Object> model) {
        return service.queryPlanTemplatePage(model);
    }

    /**
     * 添加模板
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年6月20日 上午10:44:55
     */
    @RequestMapping("addPlanTemplate.action")
    @ResponseBody
    public Object addPlanTemplate(@RequestParam Map<String, Object> model) {
        return service.addPlanTemplate(model);
    }

    /**
     * 更新模板
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年6月20日 上午10:55:05
     */
    @RequestMapping("updatePlanTemplate.action")
    @ResponseBody
    public Object updatePlanTemplate(@RequestParam Map<String, Object> model) {
        return service.updatePlanTemplate(model);
    }

    /**
     * 查询模板
     * @Description: 
     * @author 吴琪
     * @param order_id
     * @return 
     * @date: 2017年6月20日 上午10:55:26
     */
    @RequestMapping("getPlanTemplate.action")
    @ResponseBody
    public Object getPlanTemplate(String order_id) {
        return service.getPlanTemplate(order_id);
    }

    /**
     * 删除模板
     * @Description: 
     * @author 吴琪
     * @param order_id
     * @return 
     * @date: 2017年6月21日 下午1:11:44
     */
    @RequestMapping("deletePlanTemplate.action")
    @ResponseBody
    public Object deletePlanTemplate(String order_id) {
        return service.deletePlanTemplate(order_id);
    }

    /**
     * 查询模板
     * @Description: 
     * @author 吴琪
     * @param plan_type
     * @return 
     * @date: 2017年6月21日 下午1:11:52
     */
    @RequestMapping("getTemplateList.action")
    @ResponseBody
    public Object getTemplateList(String plan_type) {
        return service.getTemplateList(plan_type);
    }
}
