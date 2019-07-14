package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色与菜单对应关系
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_menu")
public class SysRoleMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
