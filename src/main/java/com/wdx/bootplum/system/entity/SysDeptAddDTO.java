package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门管理DTO，增加了上级部门名称字段
 * @author wudanhui
 * @date 2019.4.17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDeptAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(notes = "创建部门id")
    @NotBlank( message = "id不能为空")
    private String deptId;

    /**
     * 上级部门ID，一级部门为0
     */
    @ApiModelProperty(notes = "创建父部门id")
    @NotBlank(message = "id不能为空")
    private String parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(notes = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(notes = "排序号")
    @NotNull(message = "排序号不能为空")
    private Integer orderNum;

    /**
     * 标识  1：正常
     */
    @ApiModelProperty(notes = "标识")
    @NotNull(message = "标识不能为空")
    private Integer delFlag;

    /**
     * 部门名称
     */
    @ApiModelProperty(notes = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String parentDeptName;

}
