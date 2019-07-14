package com.wdx.bootplum.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.common.domain.Tree;
import com.wdx.bootplum.common.utils.BuildTree;
import com.wdx.bootplum.system.entity.SysDeptDO;
import com.wdx.bootplum.system.mapper.SysDeptMapper;
import com.wdx.bootplum.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-12
 */
@Service
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptDO> implements ISysDeptService {

    @Autowired(required = false)
    SysDeptMapper sysDeptMapper;

    /**
     * 查询所有部门
     * @param map
     * @return
     */
    @Override
    public List<SysDeptDO> list(Map<String, Object> map){
        return sysDeptMapper.list(map);
    }

    @Override
    public Tree<SysDeptDO> getTree() {
        List<Tree<SysDeptDO>> trees = new ArrayList<Tree<SysDeptDO>>();
        List<SysDeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
        for (SysDeptDO sysDept : sysDepts) {
            Tree<SysDeptDO> tree = new Tree<SysDeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为0
        Tree<SysDeptDO> t = BuildTree.build(trees);
        return t;
    }

    /**
     * 查询部门下是否有用户（删除时）
     * @param deptId
     * @return
     */

    @Override
    public int checkDeptHasUser(String deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = sysDeptMapper.getDeptUserNumber(deptId);
        return result;
    }

    /**
     * 检验当前id下是否有子部门
     * @param deptId
     * @return
     */
    @Override
    public int count(String deptId) {
        return sysDeptMapper.count(deptId);
    }

    /**
     * 修改
     * @param sysDept
     * @return
     */
    @Override
    public int update(SysDeptDO sysDept){
        return sysDeptMapper.update(sysDept);
    }

    @Override
    public boolean batchRmove(String ids) {
        return false;
    }

}
