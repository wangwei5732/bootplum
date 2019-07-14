package com.wdx.bootplum.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2019-03-18
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    /**
     * <p>
     * 查询 : 查询用户列表，分页显示
     * </p>
     *
     * @param page 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param map  状态
     * @return
     */
    List<SysUserDTO> getUserList(Page page, @Param("params") Map<String, Object> map);

}
