package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRoleDO implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色标识
     */
    private String roleSign;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建用户id
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
