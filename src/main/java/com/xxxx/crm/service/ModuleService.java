package com.xxxx.crm.service;

import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.model.TreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModuleService {
    @Autowired
    ModuleMapper moduleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 查询所有的资源列表
     * <p>
     * <p>
     * neil
     * crm
     *
     * @param
     * @return java.util.List<com.xxxx.crm.model.TreeModel>
     */
    public List<TreeModel> queryAllModules(Integer roleId) {
        // 查询所有的资源列表
        List<TreeModel> treeDtos = moduleMapper.queryAllModules();
        // 根据角色id 查询角色拥有的菜单id  List<Integer>
        List<Integer> roleHasMids = permissionMapper.queryRoleHasModuleIdsByRoleId(roleId);
        if (null != roleHasMids && roleHasMids.size() > 0) {
            treeDtos.forEach(treeDto -> {
                if (roleHasMids.contains(treeDto.getId())) {
                    //  说明当前角色 分配了该菜单
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtos;
    }
}