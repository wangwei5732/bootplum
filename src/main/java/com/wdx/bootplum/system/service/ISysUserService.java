package com.wdx.bootplum.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-03-18
 */
public interface ISysUserService extends IService<SysUserDO> {

    /**
     * 查询 : 查询用户列表，分页显示
     *
     * @param page 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param map
     * @return
     */
    Page<SysUserDTO> getUserList(Page<SysUserDTO> page, Map<String, Object> map);

    /**
     * 修改用户账号状态
     *
     * @param userId
     * @return
     */
    int updateStatus(String userId);
}
