package com.wdx.bootplum.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.system.entity.SysTaskDO;
import com.wdx.bootplum.system.mapper.SysTaskMapper;
import com.wdx.bootplum.system.service.ISysTaskService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author yangyibin
 * @since 2019-04-10
 */
@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTaskDO> implements ISysTaskService {

}
