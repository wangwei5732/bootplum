package com.wdx.bootplum.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * token（sessionId）
     */
    private String token;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门Id
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态 0:禁用，1:正常
     */
    private Integer status;

    /**
     * 创建用户id
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * 性别
     */
    private Long sex;

    /**
     * 出身日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birth;

    /**
     * 现居住地
     */
    private String liveAddress;

    /**
     * 省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 所在地区
     */
    private String district;

    /**
     * 用户权限
     **/
    private Set<String> perms;
}
