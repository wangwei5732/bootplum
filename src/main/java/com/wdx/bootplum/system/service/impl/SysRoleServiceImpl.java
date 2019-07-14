package com.wdx.bootplum.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.common.utils.DateTimeUtils;
import com.wdx.bootplum.system.entity.SysRoleAddDTO;
import com.wdx.bootplum.system.entity.SysRoleDO;
import com.wdx.bootplum.system.entity.SysRoleMenuDO;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.mapper.SysRoleMapper;
import com.wdx.bootplum.system.mapper.SysRoleMenuMapper;
import com.wdx.bootplum.system.mapper.SysRoleUserMapper;
import com.wdx.bootplum.system.service.ISysRoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-08
 */
@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements ISysRoleService {

    @Autowired(required = false)
    SysRoleMapper sysRoleMapper;

    @Autowired(required = false)
    SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired(required = false)
    SysRoleUserMapper roleUserMapper;

    /**
     * 保存角色信息
     * @param newRoleMenuDTO
     * @return
     */

    @Override
    public int add(SysRoleAddDTO newRoleMenuDTO) {
        //执行添加新角色操作
        String uuid = UUID.randomUUID().toString().replace("-", "");
        SysUserDO sysUserDO = (SysUserDO) SecurityUtils.getSubject().getPrincipal();
        newRoleMenuDTO.setCreateBy(sysUserDO.getUserId());
        newRoleMenuDTO.setCreateDate(DateTimeUtils.getCurrentLocalDateTime());
        newRoleMenuDTO.setRoleId(uuid);
        int count = sysRoleMapper.add(newRoleMenuDTO);
        //获取菜单集合以及对应的角色id
        List<String> menuIds = newRoleMenuDTO.getMenuIds();
        if(menuIds!=null && menuIds.size()>0) {
            String roleId = newRoleMenuDTO.getRoleId();
            List<SysRoleMenuDO> rms = new ArrayList<>();
            //将菜单id取出，连同角色id存入集合中
            for (String menuId : menuIds) {
                SysRoleMenuDO rmDo = new SysRoleMenuDO();
                rmDo.setRoleId(roleId);
                rmDo.setMenuId(menuId);
                rms.add(rmDo);
            }
            //执行添加前先进行删除，一个角色ID只能对应一套菜单(修改时)
            sysRoleMenuMapper.deleteById(roleId);
            if (rms.size() > 0) {
                int i = sysRoleMenuMapper.batchSave(rms);
                return i;
            }
        }
        return count;

    }

    /**
     * 删除角色信息，单个删除
     */

    @Override
    public int remove(String id) {
        int i = sysRoleMapper.deleteById(id);
        roleUserMapper.removeByRoleId(id);
        sysRoleMenuMapper.removeByRoleId(id);
        return i;
    }

    /**
     * 批量删除角色信息
     */

    @Override
    public void batchmove(List<String> ids) {
        for (int i = 0; i <ids.size() ; i++) {
            sysRoleMapper.remove(ids.get(i));
            roleUserMapper.removeByRoleId(ids.get(i));
            sysRoleMenuMapper.removeByRoleId(ids.get(i));
        }

    }

    /**
     * 根据id单一查找
     * @param id
     * @return
     */
    @Override
    public SysRoleDO findOne(String id) {
        //只是返回role信息，对应的菜单信息，可调menul里对应的根据rid查找显示菜单树
        SysRoleDO roleDO = sysRoleMapper.selectById(id);
        return roleDO;
    }

    /**
     *修改
     * @param newSysRoleMenuDTO
     * @return
     */
    @Override
    public int update(SysRoleAddDTO newSysRoleMenuDTO) {
        if (newSysRoleMenuDTO != null) {
            //根据id查询角色信息
            String roleId = newSysRoleMenuDTO.getRoleId();
            String roleName = newSysRoleMenuDTO.getRoleName();
            //根据id将已有角色信息修改
            sysRoleMapper.remove(roleId);
            sysRoleMenuMapper.removeByRoleId(roleId);
            SysUserDO sysUserDO = (SysUserDO) SecurityUtils.getSubject().getPrincipal();
            newSysRoleMenuDTO.setUpdateBy(sysUserDO.getUserId());
            newSysRoleMenuDTO.setUpdateDate(DateTimeUtils.getCurrentLocalDateTime());
            int result = this.add(newSysRoleMenuDTO);
            //根据角色名称查找出角色id，然后将修改后的菜单角色表中的角色id全部替换为之前的id
            String rid = sysRoleMapper.selectByRoleName(roleName);
            sysRoleMenuMapper.updateAllRoleIDs(roleId,rid);
            //将role表中被随机生成的id改为之前id
            sysRoleMapper.updateRid(roleId,rid);
            return result;
        }
        return -1;
    }
}
