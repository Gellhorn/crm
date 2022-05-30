package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;
    @RequestMapping("index")
    public String index() {
        return "role/role";
    }
    /**
     * 分页条件查询角色列表
     *
     *
     neil
     * crm
     * @param roleQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(RoleQuery roleQuery) {
        return roleService.queryByParamsForTable(roleQuery);
    }    /**
     * 添加角色
     *
     *
     neil
     * crm
     * @param role
     * @return com.xxxx.crm.base.ResultInfo
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addRole(Role role) {
        roleService.addRole(role);
        return success("角色添加成功！");
    }
    @PostMapping("update")
    @ResponseBody
    public ResultInfo upodateRole(Role role) {
        roleService.updateRole(role);
        return success("角色添加成功！");
    }


    @RequestMapping("toAddOrUpdateRolePage")
    public String toAddOrUpdateRolePage(Integer roleId, HttpServletRequest request) {
        // 如果roleId不为空，则表示修改操作，通过角色ID查询角色记录，存到请求域中
        if (roleId != null) {
            // 通过角色ID查询角色记录
            Role role = roleService.selectByPrimaryKey(roleId);
            // 设置到请求域中
            request.setAttribute("role", role);
        }
        return "role/add_update";
    }
    /**
     * 删除角色
     *
     *
            neil
     * crm
     * @param roleId
     * @return com.xxxx.crm.base.ResultInfo
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId) {
        roleService.deleteRole(roleId);
        return success("角色删除成功！");
    }
    /**
     * 角色授权
     *
     *
     neil
     * crm
     * @param roleId
     * @param mIds
     * @return com.xxxx.crm.base.ResultInfo
     */
    @PostMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId, Integer[] mIds) {

      roleService.addGrant(roleId, mIds);

        return success("角色授权成功！");
    }

    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 查询所有的角色列表
     * <p>
     * <p>
     * neil
     * crm
     *
     * @param
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleService.queryAllRoles(userId);
    }



}