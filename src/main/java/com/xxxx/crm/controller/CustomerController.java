package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.service.CustomerService;
import com.xxxx.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 neil
 */

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

//    @Resource
//    private CustomerOrderService orderService;


    @RequestMapping("index")
    public String index() {
        return "customer/customer";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomersByParams(CustomerQuery customerQuery) {
        return customerService.queryByParamsForTable(customerQuery);
    }
    @RequestMapping("toAddOrUpdateCustomerPage")
    public String addOrUpdateSaleChancePage(Integer id,Model model){
        // 如果id不为空，则查询客户记录
        if (null != id) {
            // 通过id查询客户记录
            Customer customer = customerService.selectByPrimaryKey(id);
            // 将客户记录存到作用域中
            model.addAttribute("customer",customer);
        }
        model.addAttribute("customer",customerService.selectByPrimaryKey(id)) ;
        return "customer/add_update";
    }




    @RequestMapping("add")
    @ResponseBody
    public ResultInfo saveCustomer(Customer customer){
        customerService.addCustomer(customer);
        return success("客户添加成功");
    }    /**
     * 修改客户信息
     *
     *
     neil
     * crm
     * @param customer
     * @return com.xxxx.crm.base.ResultInfo
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
        return success("修改客户信息成功！");
    } @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer id) {
        customerService.deleteCustomer(id);
        return success("修改客户信息成功！");
    }   /**
     * 打开客户的订单页面
     *
     *
     neil
     * crm
     * @param
     * @return java.lang.String
     */
    @RequestMapping("toCustomerOrderPage")
    public String toCustomerOrderPage(Integer customerId, Model model) {
        // 通过客户ID查询客户记录，设置到请求域中
        model.addAttribute("customer", customerService.selectByPrimaryKey(customerId));
        return "customer/customer_order";
    }
    /**
     * 客户贡献分析
     *
     *
     neil
     * crm
     * @param customerQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("queryCustomerContributionByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery) {
        return customerService.queryCustomerContributionByParams(customerQuery);
    }
    /**
     * 查询客户构成 （折线图）
     *
     *
     neil
     * crm
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("countCustomerMake")
    @ResponseBody
    public Map<String, Object> countCustomerMake() {
        return customerService.countCustomerMake();
    }
    /**
     * 查询客户构成 （饼状图）
     *
     *
     neil
     * crm
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("countCustomerMake02")
    @ResponseBody
    public Map<String, Object> countCustomerMake02() {
        return customerService.countCustomerMake02();
    }

}


