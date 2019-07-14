package com.wdx.bootplum;

import com.wdx.bootplum.system.entity.*;
import com.wdx.bootplum.system.entity.structmaps.SysDeptStructMapper;
import com.wdx.bootplum.system.entity.structmaps.SysMenuStructMapper;
import com.wdx.bootplum.system.entity.structmaps.SysRoleStructMapper;
import com.wdx.bootplum.system.entity.structmaps.SysUserStructMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangwei
 * @Date: 2019-04-10 15:05
 * @Description:
 */
public class StructMapperTest {
    /**
     * @Author wangwei
     * @Description //TODO 测试DTO向DO转换
     * @Date 15:09 2019-04-10
     * @Param []
     * @return void
     **/
    @Test
    public void testMapDtoToDo() {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUsername("wangwei");
        sysUserDTO.setCity("济南");
        SysUserDO sysUserDO = SysUserStructMapper.MAPPER.sysUserFromDTO(sysUserDTO);
        System.out.println("sysUserDO:"+sysUserDO);
    }
    /**
     * @Author wangwei
     * @Description //TODO 测试DO向DTO转换
     * @Date 15:09 2019-04-10
     * @Param []
     * @return void
     **/
    @Test
    public void testMapDoToDto() {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setUsername("wangwei");
        sysUserDO.setCity("济南");
        SysUserDTO sysUserDTO = SysUserStructMapper.MAPPER.sysUserToDTO(sysUserDO);
        System.out.println("sysUserDTO:"+sysUserDTO);
    }

    @Test
    public void test1(){
        SysRoleDO sysRoleDO = new SysRoleDO();
        List<String> list = new ArrayList<>();
        list.add("1231");
        sysRoleDO.setRoleName("zhangsan");
        sysRoleDO.setRoleId("111");
        SysRoleAddDTO sysRoleAddMenuIdDTO = SysRoleStructMapper.MAPPER.sysRoleDOToDTO(sysRoleDO,list);
        System.out.println("结果为:"+sysRoleAddMenuIdDTO);

    }
    @Test
    public void test2(){
        SysRoleAddDTO sysRoleAddMenuIdDTO = new SysRoleAddDTO();
        sysRoleAddMenuIdDTO.setRoleId("1231");
        sysRoleAddMenuIdDTO.setRoleName("wangwu");
        SysRoleDO sysRoleDO = SysRoleStructMapper.MAPPER.sysRoleDTOToDO(sysRoleAddMenuIdDTO);
        System.out.println("结果为:"+sysRoleDO);

    }

    @Test
    public void test3(){
        SysDeptDO deptDO = new SysDeptDO();

        String parentDeptName ="总部门";

        deptDO.setName("管理员");

        SysDeptAddDTO sysDeptAddDTO = SysDeptStructMapper.MAPPER.sysDeptDOToDTO(deptDO, parentDeptName);

        System.out.println("转换后的结果为："+sysDeptAddDTO);

    }

    @Test
    public void test4(){
        SysMenuDO sysMenuDO = new SysMenuDO();

        String parentMenuName ="总目录";

        sysMenuDO.setName("姓名");

        SysMenuAddDTO sysMenuAddDTO = SysMenuStructMapper.MAPPER.sysMenuDOToDTO(sysMenuDO,parentMenuName);

        System.out.println("转换后的结果为："+sysMenuAddDTO);

    }

}
