package com.wdx.bootplum.system.entity.structmaps;


import com.wdx.bootplum.system.entity.SysMenuAddDTO;
import com.wdx.bootplum.system.entity.SysMenuDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: wudanhui
 * @Date: 2019-04-19 09:35
 * @Description: SysMenu DTO DO 相互转换
 */
@Mapper
public interface SysMenuStructMapper {
    SysMenuStructMapper MAPPER = Mappers.getMapper(SysMenuStructMapper.class);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(source = "sysMenuDO.menuId", target = "menuId"),
            @Mapping(source = "sysMenuDO.parentId", target = "parentId"),
            @Mapping(source = "sysMenuDO.name", target = "name"),
            @Mapping(source = "sysMenuDO.url", target = "url"),
            @Mapping(source = "sysMenuDO.perms", target = "perms"),
            @Mapping(source = "sysMenuDO.type", target = "type"),
            @Mapping(source = "sysMenuDO.icon", target = "icon"),
            @Mapping(source = "sysMenuDO.orderNum", target = "orderNum"),
            @Mapping(source = "sysMenuDO.createDate", target = "createDate"),
            @Mapping(source = "sysMenuDO.createBy", target = "createBy"),
            @Mapping(source = "sysMenuDO.updateBy", target = "updateBy"),
            @Mapping(source = "sysMenuDO.updateDate", target = "updateDate"),
            @Mapping(source = "parentMenuName", target = "parentMenuName")
    })
    SysMenuAddDTO sysMenuDOToDTO(SysMenuDO sysMenuDO, String parentMenuName);



}
