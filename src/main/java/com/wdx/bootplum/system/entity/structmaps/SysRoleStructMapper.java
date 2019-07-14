package com.wdx.bootplum.system.entity.structmaps;

import com.wdx.bootplum.system.entity.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Auther: wudanhui
 * @Date: 2019-04-10 14:51
 * @Description: sysRole DTO DO 相互转换
 */
@Mapper
public interface SysRoleStructMapper {
    SysRoleStructMapper MAPPER = Mappers.getMapper(SysRoleStructMapper.class);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(source = "sysRoleDO.roleId", target = "roleId"),
            @Mapping(source = "sysRoleDO.roleName", target = "roleName"),
            @Mapping(source = "sysRoleDO.roleSign", target = "roleSign"),
            @Mapping(source = "sysRoleDO.remark", target = "remark"),
            @Mapping(source = "sysRoleDO.createBy", target = "createBy"),
            @Mapping(source = "sysRoleDO.createDate", target = "createDate"),
            @Mapping(source = "sysRoleDO.updateBy", target = "updateBy"),
            @Mapping(source = "sysRoleDO.updateDate", target = "updateDate"),
            @Mapping(source = "menuIds", target = "menuIds")
    })
    SysRoleAddDTO sysRoleDOToDTO(SysRoleDO sysRoleDO, List<String> menuIds);

    SysRoleDO sysRoleDTOToDO(SysRoleAddDTO sysRoleAddMenuIdDTO);


}
