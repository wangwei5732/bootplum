package com.wdx.bootplum.system.constraint;

import com.wdx.bootplum.system.constraint.impl.SysUserNameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验用户名是否已存在
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SysUserNameConstraintValidator.class)
public @interface SysUserNameConstraint {

    String message() default "用户名不能重复";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
