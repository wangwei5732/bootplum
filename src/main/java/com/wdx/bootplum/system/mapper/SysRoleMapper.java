package com.wdx.bootplum.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdx.bootplum.system.entity.SysRoleAddDTO;
import com.wdx.bootplum.system.entity.SysRoleDO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-08
 */

public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    int add(SysRoleAddDTO newRoleMenuDTO);//新增角色

    int remove(String roleId);//删除角色

    String selectByRoleName(@Param("rname") String rname);

    void updateRid(@Param("roleId") String roleId, @Param("rid") String rid);


}
