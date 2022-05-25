package com.xxxx.crm.service;

import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.model.TreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModuleService {
    @Autowired
    ModuleMapper moduleMapper;
    /**
 * 查询所有的资源列表
 *
 *
        neil
 * crm
 * @param
 * @return java.util.List<com.xxxx.crm.model.TreeModel>
 */
public List<TreeModel> queryAllModules(Integer roleId) {
    // 查询所有的资源列表
    List<TreeModel> treeModelList = moduleMapper.queryAllModules();
    return treeModelList;
    }
}
