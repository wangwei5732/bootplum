package com.wdx.bootplum.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdx.bootplum.common.domain.Tree;
import com.wdx.bootplum.system.entity.SysMenuDO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-19
 */
public interface ISysMenuService extends IService<SysMenuDO> {

    Set<String> listPerms(String userId);

    List<SysMenuDO> list(Map<String, Object> params);

    Tree<SysMenuDO> getTree();//获取树

    Tree<SysMenuDO> getTree(String id);//根据角色id获取对应菜单树

    int update(SysMenuDO sysMenuDO);

    List<String> getTreeList(String roleId);

    int count(String mId);//检验当前id是否包含子部门

    List<SysMenuDO> getTreeLeft(String rid);
}
