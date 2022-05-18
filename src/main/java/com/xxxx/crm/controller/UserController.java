package com.xxxx.crm.controller;


import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user/")
public class UserController extends BaseController {
    @Autowired
    UserService userService;


    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        ResultInfo resultInfo = new ResultInfo();

        UserModel userModel =  userService.userLogin(userName,userPwd);
        resultInfo.setResult(userModel);




        return resultInfo;
    }
    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword, String newPassword, String repeatPassword) {
        ResultInfo resultInfo = new ResultInfo();




            // 获取cookie中的userId
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            // 调用Service层修改密码方法
            userService.updatePassWord(userId, oldPassword, newPassword, repeatPassword);



        return resultInfo;
    }
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }
    /**
     * 分页多条件查询用户列表
     *
     *
     neil
     * crm
     * @param userQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }
    @RequestMapping("index")
    public String index(){
        return "user/user";
    }
}
