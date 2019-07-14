package com.wdx.bootplum.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserRoleDO;
import com.wdx.bootplum.system.mapper.SysRoleUserMapper;
import com.wdx.bootplum.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author Linjs
 * @since 2019-01-09
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysRoleUserMapper, SysUserRoleDO> implements ISysUserRoleService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public void myInsertBatch(SysUserDO user, Boolean boo) {
        if (boo) {
            this.remove(new QueryWrapper<SysUserRoleDO>().in("user_id", user.getUserId()));//先清空原有角色在新增
        }
        if (user.getUserRoleList() != null && user.getUserRoleList().size() != 0) {//用户角色
            List<SysUserRoleDO> userRoleList = new ArrayList<SysUserRoleDO>();
            for (SysUserRoleDO userRole : user.getUserRoleList()) {
                userRole.setUserId(user.getUserId());
                userRoleList.add(userRole);
            }
            this.saveBatch(userRoleList);
        }
    }

    /**
     * @return com.mgdaas.profarmer.entity.FmUserRole
     * @Description 获取角色信息
     * @Param [userId]
     **/
    @Override
    public SysUserRoleDO getRole(String userId) {
        SysUserRoleDO userRole = sysRoleUserMapper.getRole(userId);
        return userRole;
    }
}
