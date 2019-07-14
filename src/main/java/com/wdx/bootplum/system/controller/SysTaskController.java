package com.wdx.bootplum.system.controller;


import com.wdx.bootplum.common.enums.AjaxCodeEnum;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.SysTaskDO;
import com.wdx.bootplum.system.service.ISysTaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.wdx.bootplum.common.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 定时任务表 前端控制器
 * </p>
 *
 * @author yangyibin
 * @since 2019-04-10
 */
@RestController
@RequestMapping("/SysTask/sysTaskDO")
public class SysTaskController extends BaseController {

    @Autowired
    ISysTaskService iSysTaskService;

    @ApiOperation(value="展示定时器列表", notes="不需传递参数")
    @ApiImplicitParam()
    @GetMapping
    public AjaxObject list() {
        List<SysTaskDO> list = iSysTaskService.list();
        return AjaxObject.customOk("", list);
    }

}
