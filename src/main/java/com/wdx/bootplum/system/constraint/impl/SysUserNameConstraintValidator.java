package com.wdx.bootplum.system.constraint.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdx.bootplum.system.constraint.SysUserNameConstraint;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SysUserNameConstraintValidator implements ConstraintValidator<SysUserNameConstraint, Object> {

    @Autowired
    private ISysUserService userService;

    @Override
    public void initialize(SysUserNameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SysUserDO user = userService.getOne(new QueryWrapper<SysUserDO>().eq("username", o));
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }
}
