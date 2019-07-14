package com.wdx.bootplum.system.controller;


import com.wdx.bootplum.common.controller.BaseController;
import com.wdx.bootplum.common.domain.Tree;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.SysDeptAddDTO;
import com.wdx.bootplum.system.entity.SysDeptDO;
import com.wdx.bootplum.system.entity.structmaps.SysDeptStructMapper;
import com.wdx.bootplum.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-12
 */
@Api(tags = {"SysDeptController"}, description = "系统后台部门管理接口")
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {

    @Autowired
    ISysDeptService iSysDeptService;


    /**
     * 部门树,展示时用到
     *
     * @return
     */

    @GetMapping("/")
    public Tree<SysDeptDO> tree() {
        Tree<SysDeptDO> tree = new Tree<SysDeptDO>();
        tree = iSysDeptService.getTree();
        return tree;
    }


    /**
     * 展示全部部门，通过tree方法显示出树状效果
     *
     * @return
     */
    @RequiresPermissions("sys:dept:dept")
    @GetMapping
    @ApiOperation(value = "展示部门列表", notes = "")
    @ApiImplicitParam()
    public AjaxObject list() {
        Map<String, Object> query = new HashMap<>(16);
        List<SysDeptDO> sysDeptList = iSysDeptService.list(query);
        return AjaxObject.customOk("查询成功", sysDeptList);
    }


    /**
     * 获取上级
     */
    @GetMapping("/add/{pId}")
    //@RequiresPermissions("system:sysDept:add")
    public AjaxObject getPname(@PathVariable("pId") String pId, Model model) {
        model.addAttribute("pId", pId);
        if (pId.equals("0")) {
            model.addAttribute("pName", "总部门");
            return AjaxObject.customOk("返回成功",model);
        } else {
            model.addAttribute("pName", iSysDeptService.getById(pId).getName());
            return AjaxObject.customOk("返回成功",model);
        }

    }
    /**
     * 添加部门
     */
    @RequiresPermissions("sys:dept:add")
    @PostMapping
    @ApiOperation(value = "添加部门", notes = "传递部门的实体类")
    @ApiImplicitParam(name = "sysDeptDO", value = "实体类", required = true, dataType = "SysDeptDO")
    public AjaxObject add(@RequestBody @Validated SysDeptDO sysDeptDO) {
        boolean result = iSysDeptService.save(sysDeptDO);

        if (result) {
            return AjaxObject.customOk("保存成功", sysDeptDO);
        }
        return AjaxObject.customFail("保存失败", null);
    }

    /**
     * 删除部门
     *
     * @param ids
     * @return
     */

    @RequiresPermissions("sys:dept:remove")
    @DeleteMapping
    @ApiOperation(value = "删除部门", notes = "传递id")
    @ApiImplicitParam(name = "deptId", value = "部门id", required = true, dataType = "String")
    public AjaxObject remove(@RequestParam String[] ids) {

        for (int i = 0; i < ids.length; i++) {
            if (iSysDeptService.count(ids[i]) > 0) {
                return AjaxObject.customFail("包含子部门不允许删除", null);
            }
            if (iSysDeptService.checkDeptHasUser(ids[i]) == 0) {
               iSysDeptService.removeById(ids[i]);

            } else {
                return AjaxObject.customFail("部门包含用户不允许删除", null);
            }

        }
        return AjaxObject.customOk("删除成功",ids);
    }


    /**
     * 根据id显示内容（编辑）
     *
     * @param id
     * @return
     */
    @RequiresPermissions("sys:dept:edit")
    @GetMapping("/{id}")
    @ApiOperation(value = "查找角色根据id", notes = "传递id")
    @ApiImplicitParam(name = "id", value = "查找角色id", required = true, dataType = "String")
    public AjaxObject getById(@PathVariable String id) {
        SysDeptDO sysDeptDO = iSysDeptService.getById(id);
        //如果父id为0，直接显示总部门
        if (sysDeptDO != null) {
            if (sysDeptDO.getParentId().equals("0")) {
                SysDeptAddDTO sysDeptAddDTO = SysDeptStructMapper.MAPPER.sysDeptDOToDTO(sysDeptDO,"总部门");
                return AjaxObject.customOk("查询成功", sysDeptAddDTO);
            }
            //如果不等于0，则根据父id查询上级部门名称
            else {
                String pName = iSysDeptService.getById(sysDeptDO.getParentId()).getName();
                SysDeptAddDTO sysDeptAddDTO = SysDeptStructMapper.MAPPER.sysDeptDOToDTO(sysDeptDO,pName);
                return AjaxObject.customOk("查询成功", sysDeptAddDTO);
            }
        }
        return AjaxObject.customOk("查询成功",null);
    }

    /**
     * 修改部门信息
     *
     * @param sysDept
     * @return
     */

    @RequiresPermissions("sys:dept:edit")
    @PutMapping
    @ApiOperation(value = "修改部门信息", notes = "sysDeptDO实体类")
    @ApiImplicitParam(name = "sysDept", value = "实体类", required = true, dataType = "sysDeptDO")
    public AjaxObject update(@RequestBody SysDeptDO sysDept) {

        if (iSysDeptService.update(sysDept) > 0) {
            return AjaxObject.customOk("修改成功，修改后数据为：", sysDept);
        }
        return AjaxObject.customFail("修改失败", null);
    }


}
