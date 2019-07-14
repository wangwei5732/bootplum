package com.wdx.bootplum.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdx.bootplum.system.entity.SysDeptDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-12
 */
public interface SysDeptMapper extends BaseMapper<SysDeptDO> {

   List<SysDeptDO> list(Map<String, Object> map);

   int getDeptUserNumber(String deptId);

   int count(@Param("deptId") String deptId);

   int update(SysDeptDO dept);

}
