package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.CustomerLossMapper;
import com.xxxx.crm.dao.CustomerMapper;
import com.xxxx.crm.dao.CustomerOrderMapper;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.CustomerLoss;
import com.xxxx.crm.vo.CustomerOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 neil
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    @Resource
    private CustomerLossMapper customerLossMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 分页条件查询
     *
     *
    neil
     * crm
     * @param customerLossQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery) {

        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(customerLossQuery.getPage(), customerLossQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerLoss> pageInfo = new PageInfo<>(customerLossMapper.selectByParams(customerLossQuery));

        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }
    /**
     * 更新客户的流失状态
     *  1. 查询待流失的客户数据
     *  2. 将流失客户数据批量添加到客户流失表中
     *  3. 批量更新客户的流失状态  0=正常客户  1=流失客户
     *
     *
     neil
     * crm
     * @param
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerState() {
        /* 1. 查询待流失的客户数据 */
        List<Customer> lossCustomerList = customerMapper.queryLossCustomers();

        /* 2. 将流失客户数据批量添加到客户流失表中 */
        // 判断流失客户数据是否存在
        if (lossCustomerList != null && lossCustomerList.size() > 0) {
            // 定义集合 用来接收流失客户的ID
            List<Integer> lossCustomerIds = new ArrayList<>();
            // 定义流失客户的列表
            List<CustomerLoss> customerLossList = new ArrayList<>();
            // 遍历查询到的流失客户的数据
            lossCustomerList.forEach(customer -> {
                // 定义流失客户对象
                CustomerLoss customerLoss = new CustomerLoss();
                // 创建时间  系统当前时间
                customerLoss.setCreateDate(new Date());
                // 客户经理
                customerLoss.setCusManager(customer.getCusManager());
                // 客户名称
                customerLoss.setCusName(customer.getName());
                // 客户编号
                customerLoss.setCusNo(customer.getKhno());
                // 是否有效  1=有效
                customerLoss.setIsValid(1);
                // 修改时间  系统当前时间
                customerLoss.setUpdateDate(new Date());
                // 客户流失状态   0=暂缓流失状态  1=确认流失状态
                customerLoss.setState(0);
                // 客户最后下单时间
                // 通过客户ID查询最后的订单记录（最后一条订单记录）
                CustomerOrder customerOrder = customerOrderMapper.queryLossCustomerOrderByCustomerId(customer.getId());
                // 判断客户订单是否存在，如果存在，则设置最后下单时间
                if (customerOrder != null) {
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                // 将流失客户对象设置到对应的集合中
                customerLossList.add(customerLoss);

                // 将流失客户的ID设置到对应的集合中
                lossCustomerIds.add(customer.getId());
            });
            // 批量添加流失客户记录
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLossList) != customerLossList.size(), "客户流失数据转移失败！");

            /* 3. 批量更新客户的流失状态 */
            AssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCustomerIds) != lossCustomerIds.size(), "客户流失数据转移失败！");
        }

    }


    /**
     * 更新流失客户的流失状态
     *  1. 参数校验
     *      判断id非空且对应的数据存在
     *      流失原因非空
     *  2. 设置参数的默认值
     *      设置流失状态  state=1  0=暂缓流失，1=确认流失
     *      流失原因
     *      客户流失时间  系统当前时间
     *      更新时间     系统当前时间
     *  3. 执行更新操作，判断受影响的行数
     *
     *
    neil
     * crm
     * @param id
     * @param lossReason
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerLossStateById(Integer id, String lossReason) {
        /* 1. 参数校验 */
        // 判断id非空
        AssertUtil.isTrue(null == id, "待确认流失的客户不存在！");
        // 通过id查询流失客户的记录
        CustomerLoss customerLoss = customerLossMapper.selectByPrimaryKey(id);
        // 判断流失客户记录是否存在
        AssertUtil.isTrue(null == customerLoss, "待确认流失的客户不存在！");
        // 流失原因非空
        AssertUtil.isTrue(StringUtils.isBlank(lossReason), "流失原因不能为空！");

        /* 2. 设置参数的默认值 */
        // 设置流失状态  state=1  0=暂缓流失，1=确认流失
        customerLoss.setState(1);
        // 设置流失原因
        customerLoss.setLossReason(lossReason);
        // 客户流失时间  系统当前时间
        customerLoss.setConfirmLossTime(new Date());
        // 更新时间     系统当前时间
        customerLoss.setUpdateDate(new Date());
        /* 3. 执行更新操作，判断受影响的行数 */
        AssertUtil.isTrue(customerLossMapper.updateByPrimaryKeySelective(customerLoss) < 1, "确认流失失败！");
    }
}
