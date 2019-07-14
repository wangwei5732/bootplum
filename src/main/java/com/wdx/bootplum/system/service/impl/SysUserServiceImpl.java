package com.wdx.bootplum.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;
import com.wdx.bootplum.system.mapper.SysUserMapper;
import com.wdx.bootplum.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-03-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;


    /**
     * 查询 : 查询用户列表，分页显示
     *
     * @param page 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param map
     * @return
     */
    @Override
    public Page<SysUserDTO> getUserList(Page<SysUserDTO> page, Map<String, Object> map) {
        return page.setRecords(sysUserMapper.getUserList(page, map));
    }

    /**
     * 修改用户账号状态
     *
     * @param userId
     * @return
     */
    @Override
    public int updateStatus(String userId) {
        SysUserDO sysUserDO = sysUserMapper.selectById(userId);
        if (sysUserDO.getStatus() == 0) {
            sysUserDO.setStatus(1);
        } else {
            sysUserDO.setStatus(0);
        }
        return sysUserMapper.updateById(sysUserDO);
    }
}
