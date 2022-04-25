package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;
    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }


    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主页面
     * @return
     * 将user 对象设置到请求阈中
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
      Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
      if(userId == 0) {
          try {
              throw new Exception("未登录");
          } catch (Exception e) {
              e.printStackTrace();
          }

      }

      request.setAttribute("user",userService.selectByPrimaryKey(userId));
      return "main";
    }
}