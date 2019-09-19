package com.wdx.bootplum;

import com.wdx.bootplum.system.entity.*;
import com.wdx.bootplum.system.entity.structmaps.SysDeptStructMapper;
import com.wdx.bootplum.system.entity.structmaps.SysMenuStructMapper;
import com.wdx.bootplum.system.entity.structmaps.SysRoleStructMapper;
import com.wdx.bootplum.system.entity.structmaps.SysUserStructMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangwei
 * @Date: 2019-04-10 15:05
 * @Description:
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BootplumApplication.class)
public class StructMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(StructMapperTest.class);
    @Autowired
    RedisProperties redisProperties;

    /**
     * @return void
     * @Author wangwei
     * @Description //TODO 测试DTO向DO转换
     * @Date 15:09 2019-04-10
     * @Param []
     **/
    @Test
    public void testMapDtoToDo() {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUsername("wangwei");
        sysUserDTO.setCity("济南");
        SysUserDO sysUserDO = SysUserStructMapper.MAPPER.sysUserFromDTO(sysUserDTO);
        System.out.println("sysUserDO:" + sysUserDO);
    }

    /**
     * @return void
     * @Author wangwei
     * @Description //TODO 测试DO向DTO转换
     * @Date 15:09 2019-04-10
     * @Param []
     **/
    @Test
    public void testMapDoToDto() {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setUsername("wangwei");
        sysUserDO.setCity("济南");
        SysUserDTO sysUserDTO = SysUserStructMapper.MAPPER.sysUserToDTO(sysUserDO);
        System.out.println("sysUserDTO:" + sysUserDTO);
    }

    @Test
    public void test1() {
        SysRoleDO sysRoleDO = new SysRoleDO();
        List<String> list = new ArrayList<>();
        list.add("1231");
        sysRoleDO.setRoleName("zhangsan");
        sysRoleDO.setRoleId("111");
        SysRoleAddDTO sysRoleAddMenuIdDTO = SysRoleStructMapper.MAPPER.sysRoleDOToDTO(sysRoleDO, list);
        System.out.println("结果为:" + sysRoleAddMenuIdDTO);

    }

    @Test
    public void test2() {
        SysRoleAddDTO sysRoleAddMenuIdDTO = new SysRoleAddDTO();
        sysRoleAddMenuIdDTO.setRoleId("1231");
        sysRoleAddMenuIdDTO.setRoleName("wangwu");
        SysRoleDO sysRoleDO = SysRoleStructMapper.MAPPER.sysRoleDTOToDO(sysRoleAddMenuIdDTO);
        System.out.println("结果为:" + sysRoleDO);

    }

    @Test
    public void test3() {
        SysDeptDO deptDO = new SysDeptDO();

        String parentDeptName = "总部门";

        deptDO.setName("管理员");

        SysDeptAddDTO sysDeptAddDTO = SysDeptStructMapper.MAPPER.sysDeptDOToDTO(deptDO, parentDeptName);

        System.out.println("转换后的结果为：" + sysDeptAddDTO);

    }

    @Test
    public void test4() {
        SysMenuDO sysMenuDO = new SysMenuDO();

        String parentMenuName = "总目录";

        sysMenuDO.setName("姓名");

        SysMenuAddDTO sysMenuAddDTO = SysMenuStructMapper.MAPPER.sysMenuDOToDTO(sysMenuDO, parentMenuName);

        System.out.println("转换后的结果为：" + sysMenuAddDTO);

    }

    @Test
    public void test5() {
        System.out.println(redisProperties.getHost());
    }

    @Test
    public void testJJS() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        try {
            scriptEngine.eval("var imports = new JavaImporter(org.slf4j); " +
                    "print(imports);" +
                    "var logger = Java.type(\"org.slf4j.LoggerFactory\").getLogger(\"scriptType\");" +
                    "logger.info('hello hahah')");

        } catch (ScriptException e) {
            e.printStackTrace();
        }
        logger.info("helllo");
    }
}
