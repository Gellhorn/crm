package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;


    /***
     * 营销机会数据查询（分页多条件查询） 101001
     *  如果flag的值不为空，且值为1，则表示当前查询的是客户开发计划；否则查询营销机会数据
     *
     * 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     * @param saleChanceQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,
                                                       Integer flag, HttpServletRequest request) {


        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }
 }
