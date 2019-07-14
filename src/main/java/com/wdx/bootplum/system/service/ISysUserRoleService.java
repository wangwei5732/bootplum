package com.wdx.bootplum.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserRoleDO;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author
 * @since 2019-01-09
 */
public interface ISysUserRoleService extends IService<SysUserRoleDO> {
    /**
     * 设置中间表userid后新增，boo为true则先删除在新增
     *
     * @param user
     * @param boo
     */
    void myInsertBatch(SysUserDO user, Boolean boo);
    /**
     *
     * @Description 获取角色信息
     * @Param [userId]
     * @return com.mgdaas.profarmer.entity.FmUserRole
     **/
    SysUserRoleDO getRole(String userId);
}

