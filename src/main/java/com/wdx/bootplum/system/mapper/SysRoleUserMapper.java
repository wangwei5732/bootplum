package com.wdx.bootplum.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdx.bootplum.system.entity.SysUserRoleDO;

/**
 * 角色跟用户对应关系
 * @author wudanhui
 * @date 2019.4.8
 */

public interface SysRoleUserMapper extends BaseMapper<SysUserRoleDO> {

    int removeByRoleId(String roleId);//根据角色id删除对应用户

    SysUserRoleDO getRole(String userId);

}
