package com.wdx.bootplum.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdx.bootplum.system.entity.SysMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-19
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {

    List<SysMenuDO> list(Map<String, Object> map);

    List<String> listUserPerms(String id);

    int update(SysMenuDO sysMenuDO);


    int count(@Param("mId") String mId);

    List<SysMenuDO> getLeft(@Param("rid") String rid);
}
