package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.CusDevPlan;

public interface CusDevPlanMapper extends BaseMapper<CusDevPlan,Integer> {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(CusDevPlan record);

    Integer insertSelective(CusDevPlan record);

    CusDevPlan selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(CusDevPlan record);

    Integer updateByPrimaryKey(CusDevPlan record);

}