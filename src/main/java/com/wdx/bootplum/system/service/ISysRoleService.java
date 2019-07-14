package com.wdx.bootplum.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wdx.bootplum.system.entity.SysRoleAddDTO;
import com.wdx.bootplum.system.entity.SysRoleDO;

import java.util.List;


/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-08
 */
public interface ISysRoleService extends IService<SysRoleDO> {

    int add(SysRoleAddDTO newSysRoleMenuDTO);//新增角色

    int remove(String id);//删除角色

    void batchmove(List<String> ids); //批量删除

    SysRoleDO findOne(String id);//根据id单一查询

    int update(SysRoleAddDTO newSysRoleMenuDTO);//修改

}
