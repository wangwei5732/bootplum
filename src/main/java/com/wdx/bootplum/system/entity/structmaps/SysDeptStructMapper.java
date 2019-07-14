package com.wdx.bootplum.system.entity.structmaps;

import com.wdx.bootplum.system.entity.SysDeptAddDTO;
import com.wdx.bootplum.system.entity.SysDeptDO;
import com.wdx.bootplum.system.entity.SysRoleAddDTO;
import com.wdx.bootplum.system.entity.SysRoleDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Auther: wudanhui
 * @Date: 2019-04-17 14:51
 * @Description: SysDept DTO DO 相互转换
 */
@Mapper
public interface SysDeptStructMapper {
    SysDeptStructMapper MAPPER = Mappers.getMapper(SysDeptStructMapper.class);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(source = "sysDeptDO.deptId", target = "deptId"),
            @Mapping(source = "sysDeptDO.parentId", target = "parentId"),
            @Mapping(source = "sysDeptDO.name", target = "name"),
            @Mapping(source = "sysDeptDO.orderNum", target = "orderNum"),
            @Mapping(source = "sysDeptDO.delFlag", target = "delFlag"),
            @Mapping(source = "parentDeptName", target = "parentDeptName"),
    })
    SysDeptAddDTO sysDeptDOToDTO(SysDeptDO sysDeptDO, String parentDeptName);



}
