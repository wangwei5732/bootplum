package com.wdx.bootplum.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录表单信息
 *
 * @author wangwei
 * @since 2019-09-18 15:21
 */
@Data
public class LoginFormDO {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名为空")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码为空")
    private String password;
}
