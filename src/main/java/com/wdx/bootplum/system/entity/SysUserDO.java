package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wdx.bootplum.common.utils.Regex;
import com.wdx.bootplum.system.constraint.SysUserNameConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wangwei
 * @since 2019-03-18
 */
@Data
@TableName("sys_user")
@ApiModel(value = "用户model")
public class SysUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(groups = {SysUserDO.update.class}, message = "id不能为空")
    @TableId
    private String userId;

    /**
     * 用户名
     */
    @NotBlank(groups = {SysUserDO.add.class}, message = "用户名不能为空")
    @SysUserNameConstraint(groups = {SysUserDO.add.class}, message = "用户名已存在")
    @ApiModelProperty(notes = "用户名")
    private String username;

    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(notes = "密码")
    private String password;

    @ApiModelProperty(notes = "部门id")
    private String deptId;

    /**
     * 邮箱
     */
    @NotBlank(groups = {SysUserDO.add.class, update.class}, message = "邮箱不能为空")
    @Pattern(regexp = Regex.EMAIL, groups = {SysUserDO.add.class, update.class}, message = "邮箱格式不正确")
    @ApiModelProperty(notes = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @NotBlank(groups = {SysUserDO.add.class, update.class}, message = "手机号不能为空")
    @Pattern(groups = {SysUserDO.add.class, update.class}, regexp = Regex.PHONE, message = "手机号不正确")
    @ApiModelProperty(notes = "手机号")
    private String mobile;

    /**
     * 状态 0:禁用，1:正常
     */
    @ApiModelProperty(notes = "状态1：启用0.禁用")
    private Integer status;

    /**
     * 创建用户id
     */
    @ApiModelProperty(notes = "创建用户id")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 修改人id
     */
    @ApiModelProperty(notes = "修改人id")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(notes = "修改时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * 性别
     */
    @Range(groups = {SysUserDO.add.class, update.class}, min = 0, max = 1, message = "性别只能输入只能输入0或1")
    @ApiModelProperty(notes = "性别 0：男、1：女")
    private Long sex;

    /**
     * 出生日期
     */
    @Past(groups = {SysUserDO.add.class, update.class}, message = "出生日期不能大于当前日期")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "出生日期")
    private LocalDateTime birth;

    /**
     * 现居住地x
     */
    @NotBlank(groups = {SysUserDO.add.class, update.class}, message = "现居住地不能为空")
    @ApiModelProperty(notes = "现居住地")
    private String liveAddress;


    /**
     * 省份
     */
    @NotBlank(groups = {SysUserDO.add.class, update.class}, message = "省不能为空")
    @ApiModelProperty(notes = "省")
    private String province;

    /**
     * 所在城市
     */
    @NotBlank(groups = {SysUserDO.add.class, update.class}, message = "市不能为空")
    @ApiModelProperty(notes = "市")
    private String city;

    /**
     * 所在地区
     */
    @NotBlank(groups = {SysUserDO.add.class, update.class}, message = "区县不能为空")
    @ApiModelProperty(notes = "区县")
    private String district;

    /**
     * 删除状态：0未删除，1已删除
     */
    @ApiModelProperty(notes = "0未删除,1已删除")
    private String deleteStatus;

    /**
     * 用户权限;非数据库字段
     **/
    @TableField(exist = false)
    private Set<String> perms;

    /**
     * 用户角色
     */
    @TableField(exist = false)
    @ApiModelProperty(notes = "用户角色")
    private List<SysUserRoleDO> userRoleList;

    /**
     * @return java.lang.String
     * @Author wangwei
     * @Description //TODO 这个get是为了给shiro-redis使用的，如果不加会报错，具体错误可以屏蔽以后看下，mark下，以后在去查看问题
     * @Date 16:07 2019-03-28
     * @Param []
     **/
    public String getId() {
        return userId;
    }


    public interface add {
    }

    public interface update {
    }
}
