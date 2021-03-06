package com.wdx.bootplum.system.controller;


import com.wdx.bootplum.common.controller.BaseController;
import com.wdx.bootplum.common.enums.AjaxCodeEnum;
import com.wdx.bootplum.common.exception.BusinessApiException;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;
import com.wdx.bootplum.system.entity.structmaps.SysUserStructMapper;
import com.wdx.bootplum.system.service.ISysUserService;
import com.wdx.bootplum.system.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 登录相关控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-03-18
 */
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {
    @Autowired
    ISysUserService sysUserService;

    /**
     * @return com.wdx.bootplum.common.vo.AjaxObject
     * @Author wangwei
     * @Description //TODO 用户登录
     * @Date 10:35 2019-03-22
     * @Param [username, password]
     **/
    @PostMapping(value = "login")
    public AjaxObject login(@RequestBody SysUserDO sysUserDO) throws Exception {

        String password = MD5Utils.encrypt(sysUserDO.getUsername(), sysUserDO.getPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(sysUserDO.getUsername(), password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            sysUserDO = getUser();
            SysUserDTO sysUserDTO = SysUserStructMapper.MAPPER.sysUserToDTO(sysUserDO);
            sysUserDTO.setToken((String) subject.getSession().getId());
            return AjaxObject.customOk("登录成功", sysUserDTO);
        } catch (IncorrectCredentialsException e) {
            throw new BusinessApiException(AjaxCodeEnum.USER_PASSWORD_ERROR);
        } catch (LockedAccountException e) {
            throw new BusinessApiException(AjaxCodeEnum.USER_LOCKED_ERROR);
        } catch (AuthenticationException e) {
            throw new BusinessApiException(AjaxCodeEnum.USER_PASSWORD_ERROR);
        }
    }

    /**
     * @return com.wdx.bootplum.common.vo.AjaxObject
     * @Author wangwei
     * @Description //TODO shiro 用户未登录转发处理路径，在shiroConfig中配置
     * @Date 20:10 2019-03-28
     * @Param [msg]
     **/
    @RequestMapping(value = "unauth")
    public AjaxObject unauth(String msg) throws Exception {
        return AjaxObject.customFail("用户未登录", msg);
    }

}
