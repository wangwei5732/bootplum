package com.wdx.bootplum.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdx.bootplum.common.domain.Tree;
import com.wdx.bootplum.system.entity.SysDeptDO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-12
 */
public interface ISysDeptService extends IService<SysDeptDO> {

    List<SysDeptDO> list(Map<String, Object> map);

    Tree<SysDeptDO> getTree();

    int checkDeptHasUser(String deptId);//检验是否关联用户了，关联则不允许删除

    int count(String deptId);//检验当前id是否包含子部门

    int update(SysDeptDO sysDept);//修改


    boolean batchRmove(String ids);
}
