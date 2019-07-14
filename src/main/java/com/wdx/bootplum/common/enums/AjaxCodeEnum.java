package com.wdx.bootplum.common.enums;

/**
 * @Auther: wangwei
 * @Date: 2019-03-13 14:38
 * @Description:AjaxObject数据返回的code公共枚举类
 */
public enum AjaxCodeEnum {
    /**
     * 失败
     **/
    FAIL("FAIL","失败"),
    /**
     * 成功
     **/
    OK("OK","成功"),
    /**
     * 参数异常
     **/
    INVALID_PARAMETERS("INVALID_PARAMETERS","参数异常"),
    /**
     * 用户无权限
     **/
    USER__UNAUTHORIZED_ERROR("UNAUTHORIZED","用户无权限"),
    /**
     * 未认证
     **/
    USER__UNAUTHENTICATED_ERROR("UNAUTHENTICATED","未认证")
    ,
    /**
     * 用户名密码错误
     **/
    USER_PASSWORD_ERROR("USER_PASSWORD_ERROR","用户名密码错误"),
    /**
     * 参数异常
     **/
    USER_LOCKED_ERROR("USER_LOCKED_ERROR","用户锁定");

    private String code;
    private String name;

    AjaxCodeEnum(final String code, final String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
