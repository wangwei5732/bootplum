package com.wdx.bootplum.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdx.bootplum.system.entity.SysRoleMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-09
 */

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuDO> {

    int removeByRoleId(String roleId);//根据角色id进行删除

    int batchSave(List<SysRoleMenuDO> list);//全部批量保存

    List<String> selectAllMenuId(String id);//根据roleID 查询出所有的对应菜单id

    void updateAllRoleIDs(@Param("roleId") String roleId, @Param("rid") String rid);


}
