package com.wdx.bootplum.system.entity.structmaps;

import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: wangwei
 * @Date: 2019-04-10 14:51
 * @Description: sysUser DTO DO 相互转换
 */
@Mapper
public interface SysUserStructMapper {
    SysUserStructMapper MAPPER = Mappers.getMapper(SysUserStructMapper.class);

    @InheritInverseConfiguration
    SysUserDTO sysUserToDTO(SysUserDO sysUserDO);

    SysUserDO sysUserFromDTO(SysUserDTO sysUserDTO);
}
