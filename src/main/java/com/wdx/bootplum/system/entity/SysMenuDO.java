package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author wudanhui
 * @since 2019-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId()
    @ApiModelProperty(notes = "创建菜单id")
    @NotBlank( message = "id不能为空")
    private String menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    @ApiModelProperty(notes = "创建父菜单id")
    @NotBlank(message = "id不能为空")
    private String parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(notes = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单URL
     */
    @ApiModelProperty(notes = "菜单路径")
    //@NotBlank(message = "菜单路径不能为空")
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @ApiModelProperty(notes = "授权信息名称")
   // @NotBlank(message = "授权信息不能为空")
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @ApiModelProperty(notes = "类型")
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 菜单图标
     */
    @ApiModelProperty(notes = "菜单图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(notes = "排序")
    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @ApiModelProperty(notes = "创建人")
    @NotBlank(message = "创建人不能为空")
    private String createBy;

    /**
     * 修改人
     */
    @ApiModelProperty(notes = "修改人")
    @NotBlank(message = "修改人不能为空")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(notes = "修改时间")
    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updateDate;





}
