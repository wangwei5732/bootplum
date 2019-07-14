package com.wdx.bootplum.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.common.domain.Tree;
import com.wdx.bootplum.common.utils.BuildTree;
import com.wdx.bootplum.system.entity.SysMenuDO;
import com.wdx.bootplum.system.mapper.SysMenuMapper;
import com.wdx.bootplum.system.mapper.SysRoleMenuMapper;
import com.wdx.bootplum.system.service.ISysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-19
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuDO> implements ISysMenuService {
    @Autowired(required = false)
    SysMenuMapper sysMenuMapper;
    @Autowired(required = false)
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Set<String> listPerms(String userId) {
        List<String> perms = sysMenuMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }


    /**
     * 树形式展示 菜单内容
     * @return
     */
    @Override
    public Tree<SysMenuDO> getTree() {
        List<Tree<SysMenuDO>> trees = new ArrayList<Tree<SysMenuDO>>();
        List<SysMenuDO> menuDOs = sysMenuMapper.list(new HashMap<>(16));
        for (SysMenuDO sysMenuDO : menuDOs) {
            Tree<SysMenuDO> tree = new Tree<SysMenuDO>();
            tree.setId(sysMenuDO.getMenuId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为0，根据数据库实际情况调整
        Tree<SysMenuDO> t = BuildTree.build(trees);
        return t;
    }

    /**
     * 根据roleid展示，角色编辑
     */
    @Override
    public Tree<SysMenuDO> getTree(String rid) {
        // 根据roleId查询权限菜单
        List<SysMenuDO> menus = sysMenuMapper.list(new HashMap<String, Object>(16));
        List<String> menuIds = sysRoleMenuMapper.selectAllMenuId(rid);
        List<String> temp = menuIds;
        for (SysMenuDO menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<SysMenuDO>> trees = new ArrayList<Tree<SysMenuDO>>();
        List<SysMenuDO> menuDOs = sysMenuMapper.list(new HashMap<String, Object>(16));
        for (SysMenuDO sysMenuDO : menuDOs) {
            Tree<SysMenuDO> tree = new Tree<SysMenuDO>();
            tree.setId(sysMenuDO.getMenuId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> state = new HashMap<>(16);
            String menuId = sysMenuDO.getMenuId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为0，根据数据库实际情况调整
        Tree<SysMenuDO> t = BuildTree.build(trees);
        return t;
    }
    /**
     * 展示菜单
     * @param params
     * @return
     */
    @Override
    public List<SysMenuDO> list(Map<String, Object> params) {
        List<SysMenuDO> menus = sysMenuMapper.list(params);
        return menus;
    }

    /**
     * 修改
     * @param sysMenuDO
     * @return
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public int update(SysMenuDO sysMenuDO) {
        int r = sysMenuMapper.update(sysMenuDO);
        return r;
    }

    @Override
    public List<String> getTreeList(String roleId) {
        // 根据roleId查询权限菜单
       // List<SysMenuDO> menus = sysMenuMapper.list(new HashMap<String, Object>(16));
        List<String> menuIds = sysRoleMenuMapper.selectAllMenuId(roleId);
        return menuIds;
    }

    @Override
    public int count(String mId) {
      return   sysMenuMapper.count(mId);
    }

    /**
     * 左侧菜单树
     * @param rid
     * @return
     */
    @Override
    public List<SysMenuDO> getTreeLeft(String rid) {
        return sysMenuMapper.getLeft(rid);
    }

}
