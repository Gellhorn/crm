package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

//    @Resource
//    private RoleService roleService;

    @RequestMapping("index")
    public String index() {
        return "role/role";
    }

}