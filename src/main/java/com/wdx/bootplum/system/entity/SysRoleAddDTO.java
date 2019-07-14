package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色添加菜单ID字段
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRoleAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "创建角色id")
    @NotBlank( message = "id不能为空")
    private String roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(notes = "创建角色名称")
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    /**
     * 角色标识
     */
    @ApiModelProperty(notes = "角色标识")
    @NotBlank(message = "角色标识不能为空")
    private String roleSign;

    /**
     * 备注
     */
    @ApiModelProperty(notes = "备注")
    @NotBlank(message = "备注不能为空")
    private String remark;

    /**
     * 创建用户id
     */
    @ApiModelProperty(notes = "创建用户id")
    @NotBlank(message = "不能为空")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createDate;

    /**
     * 更新人
     */
    @ApiModelProperty(notes = "更新人")
    @NotBlank(message = "更新人不能为空")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(notes = "修改时间")
    @NotNull(message = "更新时间不能为空")
    private LocalDateTime updateDate;

    /**
     *对应菜单集合
     */
    @ApiModelProperty(notes = "对应菜单集合")
    private List<String> menuIds;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
