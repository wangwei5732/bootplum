package com.wdx.bootplum.system.controller;


import com.wdx.bootplum.common.controller.BaseController;
import com.wdx.bootplum.common.domain.Tree;
import com.wdx.bootplum.common.utils.DateTimeUtils;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.SysMenuAddDTO;
import com.wdx.bootplum.system.entity.SysMenuDO;
import com.wdx.bootplum.system.entity.structmaps.SysMenuStructMapper;
import com.wdx.bootplum.system.service.ISysMenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 * http://192.168.40.102/svn/bootplum/trunk/server/bootplum
 *
 * @author wudanhui
 * @since 2019-04-18
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    @Autowired
    ISysMenuService iSysMenuService;

    /**
     * 菜单树,展示时用到
     *
     * @return
     */

    @GetMapping("/")
    public Tree<SysMenuDO> tree() {
        Tree<SysMenuDO> tree = new Tree<SysMenuDO>();
        tree = iSysMenuService.getTree();
        return tree;
    }

    /**
     * 左侧菜单树
     */
    @ApiOperation(value = "左侧菜单树")
    @RequestMapping("/leftMenu")
    public AjaxObject treeLeft(String rid) {
        Tree<SysMenuDO> tree = new Tree<SysMenuDO>();

        List<SysMenuDO> list = iSysMenuService.getTreeLeft(rid);
        return AjaxObject.customOk("查询成功", list);
    }

    /**
     * 根据角色id获取菜单树（角色编辑）
     *
     * @param roleId
     * @return
     */

    @GetMapping("/tree/{roleId}")
    @ResponseBody
    public AjaxObject tree(@PathVariable("roleId") String roleId) {
        Tree<SysMenuDO> tree = iSysMenuService.getTree(roleId);
        if (tree != null) {
            return AjaxObject.customOk("请求成功", tree);
        }
        return AjaxObject.customFail("请求失败", null);
    }

    /**
     * 根据角色id获取菜单树(集合)（角色编辑）
     *
     * @param roleId
     * @return
     */

    @GetMapping("/treelist/{roleId}")
    @ResponseBody
    public AjaxObject treeList(@PathVariable("roleId") String roleId) {

        List<String> tree = iSysMenuService.getTreeList(roleId);
        if (tree != null) {
            return AjaxObject.customOk("请求成功", tree);
        }
        return AjaxObject.customFail("请求失败", null);
    }

    /**
     * 展示全部部门，通过tree方法显示出树状效果
     *
     * @return
     */
    @RequiresPermissions("sys:menu:menu")
    @GetMapping
    @ApiOperation(value = "展示部门列表", notes = "")
    @ApiImplicitParam()
    public AjaxObject list() {
        Map<String, Object> query = new HashMap<>(16);
        List<SysMenuDO> sysDeptList = iSysMenuService.list(query);
        return AjaxObject.customOk("查询成功", sysDeptList);
    }

    /**
     * 添加菜单
     *
     * @param sysMenuDO
     * @return
     */
    @RequiresPermissions("sys:menu:add")
    @PostMapping
    @ApiOperation(value = "添加菜单", notes = "传递菜单实体类")
    @ApiImplicitParam(name = "newSysRoleMenuDTO", value = "实体类", required = true, dataType = "SysRoleAddDTO")
    public AjaxObject save(@RequestBody @Validated SysMenuDO sysMenuDO) {
        sysMenuDO.setCreateBy(getUserId());
        sysMenuDO.setCreateDate(DateTimeUtils.getCurrentLocalDateTime());
        if (iSysMenuService.save(sysMenuDO)) {
            return AjaxObject.customOk("保存成功", sysMenuDO);
        }
        return AjaxObject.customFail("保存失败", null);

    }


    /**
     * 删除菜单
     *
     * @param mId
     * @return
     */
    @RequiresPermissions("sys:menu:remove")
    @DeleteMapping("/{mId}")
    @ApiOperation(value = "删除菜单", notes = "传递菜单id")
    @ApiImplicitParam(name = "mId", value = "菜单id", required = true, dataType = "String")
    public AjaxObject remove(@PathVariable String mId) {
        if (iSysMenuService.count(mId) > 0) {
            return AjaxObject.customFail("包含子菜单不允许删除", null);
        }
        if (iSysMenuService.removeById(mId)) {
            return AjaxObject.customOk("删除成功，id为:", mId);
        }
        return AjaxObject.customFail("删除失败", null);

    }


    /**
     * 根据id显示内容（编辑）
     *
     * @param id
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/{id}")
    @ApiOperation(value = "查找菜单根据id", notes = "传递id")
    @ApiImplicitParam(name = "id", value = "查找菜单id", required = true, dataType = "String")

    public AjaxObject getById(@PathVariable String id) {
        SysMenuDO sysMenuDO = iSysMenuService.getById(id);
        //如果父id为0，直接显示总部门
        if (sysMenuDO != null) {
            if (sysMenuDO.getParentId().equals("0")) {

                SysMenuAddDTO sysMenuAddDTO = SysMenuStructMapper.MAPPER.sysMenuDOToDTO(sysMenuDO, "根目录");
                return AjaxObject.customOk("查询成功", sysMenuAddDTO);

            }
            //如果不等于0，则根据父id查询上级部门名称
            else {
                String pName = iSysMenuService.getById(sysMenuDO.getParentId()).getName();
                SysMenuAddDTO sysMenuAddDTO = SysMenuStructMapper.MAPPER.sysMenuDOToDTO(sysMenuDO, pName);
                return AjaxObject.customOk("查询成功", sysMenuAddDTO);
            }
        }
        return AjaxObject.customOk("查询成功", null);
    }

    /**
     * 修改
     *
     * @param sysMenuDO
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @PutMapping
    @ApiOperation(value = "修改菜单信息", notes = "sysMenuDO实体类")
    @ApiImplicitParam(name = "sysMenuDO", value = "实体类", required = true, dataType = "sysMenuDO")
    public AjaxObject update(@RequestBody @Validated SysMenuDO sysMenuDO) {
        sysMenuDO.setUpdateBy(getUserId());
        sysMenuDO.setUpdateDate(DateTimeUtils.getCurrentLocalDateTime());
        if (iSysMenuService.update(sysMenuDO) > 0) {
            return AjaxObject.customOk("修改成功，修改后数据为：", sysMenuDO);
        }
        return AjaxObject.customFail("修改失败", null);
    }


}
