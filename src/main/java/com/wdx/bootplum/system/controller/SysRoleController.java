package com.wdx.bootplum.system.controller;


import com.wdx.bootplum.common.controller.BaseController;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.SysRoleAddDTO;
import com.wdx.bootplum.system.entity.SysRoleDO;
import com.wdx.bootplum.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-08
 */
@Api(tags = {"SysRoleController"}, description = "系统后台角色管理接口")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Autowired
    ISysRoleService iSysRoleService;

    /**
     *展示角色信息
     * @return
     */
    @RequiresPermissions("sys:role:role")
    @GetMapping()
    @ApiOperation(value="展示角色列表", notes="不需传递参数")
    @ApiImplicitParam()
    public AjaxObject list() {
        List<SysRoleDO> roles = iSysRoleService.list();
            return AjaxObject.customOk("查询成功",roles);
    }


    /**
     * 保存角色
     * @param newSysRoleMenuDTO
     * @return
     */
    @RequiresPermissions("sys:role:add")
    @PostMapping
    @ApiOperation(value="保存角色列表", notes="传递 SysRoleAddMenuIdDTO实体类")
    @ApiImplicitParam(name = "newSysRoleMenuDTO", value = "实体类", required = true, dataType = "SysRoleAddDTO")
   public AjaxObject save(@RequestBody  @Validated SysRoleAddDTO newSysRoleMenuDTO ) {

        if (iSysRoleService.add(newSysRoleMenuDTO)>0) {
            return AjaxObject.customOk("保存成功",newSysRoleMenuDTO);
        } else {
            return AjaxObject.customFail("保存失败",null);
        }
    }


    /**
     * 删除角色,单个删除
     * @param id
     * @return
     */

    @RequiresPermissions("sys:role:remove")
    @DeleteMapping("/id")
    @ApiOperation(value="删除角色", notes="传递id")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "String")
   public AjaxObject remove(@PathVariable String id) {
        int count = iSysRoleService.remove(id);
        if (count>0) {
            return AjaxObject.customOk("删除成功,id为：",id);
        } else {
            return AjaxObject.customFail("删除失败",null);
        }
    }



    /**
     * 删除角色,可批量
     *
     */

    @RequiresPermissions("sys:role:batchRemove")
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    @DeleteMapping(value = "/{ids}")
    @ApiOperation(value="批量删除角色", notes="传递ids")
    @ApiImplicitParam(name = "ids", value = "角色id集合", required = true, dataType = "List")
   public AjaxObject batchRemove(@PathVariable List<String> ids) {
        try {
            iSysRoleService.batchmove(ids);
            return AjaxObject.customOk("删除成功，id为：",ids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxObject.customFail("删除失败",null);
        }
    }

    /**
     * 根据id查找,编辑
     * @param id
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @GetMapping(value = "/{id}")
    @ApiOperation(value="查找角色根据id", notes="传递id")
    @ApiImplicitParam(name = "id", value = "查找角色id", required = true, dataType = "String")
    public AjaxObject getById(@PathVariable String id){
        //只是返回role信息，对应的菜单信息，可调menul里对应的根据rid查找显示菜单树
        SysRoleDO sysRoleDO = iSysRoleService.findOne(id);
        return AjaxObject.customOk("查询成功",sysRoleDO);
    }


    /**
     * 更新角色信息
     */
    @RequiresPermissions("sys:role:edit")
    @PutMapping
    @ApiOperation(value="更新角色", notes="SysRoleAddMenuIdDTO实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newSysRoleMenuDTO", value = "实体类", required = true, dataType = "SysRoleMenuIdDTO")
    })
    public AjaxObject update(@RequestBody SysRoleAddDTO newSysRoleMenuDTO){

        int result = iSysRoleService.update(newSysRoleMenuDTO);

        if(result>0){
            return  AjaxObject.customOk("修改成功",newSysRoleMenuDTO);
        }

        return AjaxObject.customFail("修改失败",null);

    }

}
