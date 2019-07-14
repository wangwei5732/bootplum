package com.wdx.bootplum.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdx.bootplum.common.controller.BaseController;
import com.wdx.bootplum.common.utils.DateTimeUtils;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;
import com.wdx.bootplum.system.entity.SysUserRoleDO;
import com.wdx.bootplum.system.service.ISysUserRoleService;
import com.wdx.bootplum.system.service.ISysUserService;
import com.wdx.bootplum.system.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-03-18
 */
@Api(tags = {"SysUserController"}, description = "系统后台用户管理接口")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysUserRoleService sysUserRoleService;


    /**
     * 创建用户
     *
     * @param sysUserDO
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "根据SysUserDO对象创建用户")
    @ApiImplicitParam(name = "SysUserDO", value = "用户详细实体SysUserDO", required = true, dataType = "SysUserDO")
    @RequiresPermissions("sys:user:add")
    @PostMapping
    public AjaxObject add(@RequestBody @Validated(SysUserDO.add.class) SysUserDO sysUserDO) {
        sysUserDO.setCreateBy(getUserId());
        sysUserDO.setCreateDate(DateTimeUtils.getCurrentLocalDateTime());
        sysUserDO.setStatus(1);
        sysUserDO.setPassword(MD5Utils.encrypt(sysUserDO.getUsername(), sysUserDO.getPassword()));
        if (sysUserService.save(sysUserDO)) {
            //插入角色信息
            sysUserRoleService.myInsertBatch(sysUserDO, false);
            return AjaxObject.customOk("创建成功", null);
        }
        return AjaxObject.customFail("创建失败", null);
    }

    /**
     * 根据userId获取用户数据
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId获取用户数据")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String")
    @RequiresPermissions("sys:user:findById")
    @GetMapping(value = "/{userId}")
    public AjaxObject findById(@NotBlank(message = "userId不能为空") @PathVariable String userId) {
        SysUserDO sysUserDO = sysUserService.getById(userId);
        if (sysUserDO != null) {
            sysUserDO.setPassword(null);
            sysUserDO.setUserRoleList(sysUserRoleService.list(new QueryWrapper<SysUserRoleDO>().eq("user_id", userId)));
            return AjaxObject.customOk("获取成功", sysUserDO);
        }
        return AjaxObject.customFail("获取失败", null);
    }

    /**
     * 更新用户数据
     *
     * @param sysUserDO
     * @return
     */
    @ApiOperation(value = "更新用户数据")
    @ApiImplicitParam(name = "SysUserDO", value = "用户详细实体SysUserDO", required = true, dataType = "SysUserDO")
    @RequiresPermissions("sys:user:edit")
    @PutMapping
    public AjaxObject update(@RequestBody @Validated(SysUserDO.update.class) SysUserDO sysUserDO) {
        sysUserDO.setUpdateBy(getUserId());
        sysUserDO.setUpdateDate(DateTimeUtils.getCurrentLocalDateTime());
        sysUserDO.setUsername(null);
        sysUserDO.setPassword(null);
        if (sysUserService.updateById(sysUserDO)) {
            return AjaxObject.customOk("更新成功", null);
        }
        return AjaxObject.customFail("更新失败", null);
    }

    /**
     * 获取分页形式用户数据列表
     *
     * @param name
     * @param deptId
     * @return
     */
    @ApiOperation(value = "获取分页形式用户数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", paramType = "String"),
            @ApiImplicitParam(name = "deptId", value = "部门id", paramType = "String"),
            @ApiImplicitParam(name = "current", value = "第几页，默认为第一页", paramType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页显示几条，默认20条", paramType = "Integer")
    })
    @RequiresPermissions("sys:user:user")
    @GetMapping
    public AjaxObject<Page<SysUserDTO>> list(@RequestParam(required = false) String name, @RequestParam(required = false) String deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("deptId", deptId);
        Page<SysUserDTO> page = this.getPage();
        page = sysUserService.getUserList(page, map);
        return AjaxObject.customOk("获取成功", page);
    }

    /**
     * 根据ID删除用户,支持批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除用户,支持批量删除")
    @ApiImplicitParam(name = "ids", value = "用户id", dataType = "String", required = true)
    @RequiresPermissions("sys:user:remove")
    @DeleteMapping
    public AjaxObject delete(@RequestParam String[] ids) {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setDeleteStatus("1");
        if (sysUserService.update(sysUserDO,new UpdateWrapper<SysUserDO>().in("user_id",ids))) {
            return AjaxObject.customOk("删除成功", null);
        }
        return AjaxObject.customOk("删除失败", null);
    }

    /**
     * 调整用户状态
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "调整用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", required = true)
    })
    @RequiresPermissions("sys:user:updateStatus")
    @PutMapping(value = "/{userId}")
    public AjaxObject updateStatus(@NotBlank(message = "userId不能为空") @PathVariable String userId) {
        if (sysUserService.updateStatus(userId) == 1) {
            return AjaxObject.customOk("操作成功", null);
        }
        return AjaxObject.customFail("操作失败", null);
    }

    /**
     * 修改当前登录用户密码
     *
     * @param password
     * @return
     */
    @ApiOperation(value = "修改当前登录用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", required = true)
    })
    @RequiresPermissions("sys:user:resetPwd")
    @PutMapping(value = "/updatePassword")
    public AjaxObject updatePassword(@NotBlank(message = "密码不能为空") @RequestParam String password) {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setPassword(MD5Utils.encrypt(getUsername(), password));
        sysUserDO.setUserId(getUserId());
        if (sysUserService.updateById(sysUserDO)) {
            return AjaxObject.customOk("修改成功", null);
        }
        return AjaxObject.customOk("修改失败", null);
    }

    /**
     * 修改用户密码
     *
     * @param password
     * @return
     */
    @ApiOperation(value = "修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", required = true)
    })
    @RequiresPermissions("sys:user:resetPwd")
    @PutMapping(value = "/updateUserPassword")
    public AjaxObject updatePassword(@NotBlank(message = "密码不能为空") @RequestParam String userId,@RequestParam String password) {
        SysUserDO sysUserDO = sysUserService.getById(userId);
        if (sysUserDO == null) {
            return AjaxObject.customOk("无此用户", null);
        }
        sysUserDO.setPassword(MD5Utils.encrypt(sysUserDO.getUsername(), password));
        if (sysUserService.updateById(sysUserDO)) {
            return AjaxObject.customOk("修改成功", null);
        }
        return AjaxObject.customOk("修改失败", null);
    }



}
