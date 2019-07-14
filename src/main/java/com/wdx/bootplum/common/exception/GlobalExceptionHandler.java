package com.wdx.bootplum.common.exception;

import com.wdx.bootplum.common.enums.AjaxCodeEnum;
import com.wdx.bootplum.common.vo.AjaxObject;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * @Author wangwei
 * @Description //TODO 统一异常处理
 * @Date 19:44 2019-03-13
 * @Param 
 * @return 
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
//    @Autowired
//    LogService logService;
    /**
     * @Author wangwei
     * @Description //TODO 统一处理全局异常
     * @Date 17:17 2019-03-13
     * @Param [e]
     * @return com.wdx.bootplum.common.vo.AjaxObject
     **/
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public AjaxObject defaultErrorHandler(Exception e){
        logger.error(e.getMessage(),e);
        return AjaxObject.customFail("服务器异常",null);
    }

    /**
     * @Author wangwei
     * @Description //TODO 统一处理API异常
     * @Date 19:32 2019-03-13
     * @Param [e]
     * @return com.wdx.bootplum.common.vo.AjaxObject
     **/
    @ExceptionHandler(value = BusinessApiException.class)
    @ResponseBody
    public AjaxObject jsonErrorHandler( BusinessApiException e){
        logger.error(e.getMsg(),e);
        return AjaxObject.customAjax(e.getCode(),e.getMsg(),null);
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.wdx.sringboot.learn.util.AjaxObject>
     * @Author wangwei
     * @Description //TODO 统一处理无权限异常
     * @Date 17:20 2018-12-20
     * @Param [req, e]
     **/
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public AjaxObject jsonErrorHandler(UnauthorizedException e){
        logger.error(e.getMessage(),e);
        return AjaxObject.basicAjax(AjaxCodeEnum.USER__UNAUTHORIZED_ERROR,null);
    }
    /**
     * @return org.springframework.http.ResponseEntity<com.wdx.sringboot.learn.util.AjaxObject>
     * @Author wangwei
     * @Description //TODO 统一处理未认证异常
     * @Date 17:20 2018-12-20
     * @Param [req, e]
     **/
    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public AjaxObject jsonErrorHandler(UnauthenticatedException e){
        logger.error(e.getMessage(),e);
        return AjaxObject.basicAjax(AjaxCodeEnum.USER__UNAUTHENTICATED_ERROR,null);
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.wdx.sringboot.learn.util.AjaxObject>
     * @Author wangwei
     * @Description //TODO 统一处理参数验证异常
     * @Date 17:20 2018-12-20
     * @Param [req, e]
     **/
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxObject jsonErrorHandler(MethodArgumentNotValidException e){
        logger.error(e.getMessage(),e);
        List<ObjectError> fieldErrorList = e.getBindingResult().getAllErrors();
        String errorList = "";
        for (int i = 0; i < fieldErrorList.size(); i++) {
            if (errorList != "") {
                errorList += ",";
            }
            errorList += fieldErrorList.get(i).getDefaultMessage();
        }
        return AjaxObject.basicAjax(AjaxCodeEnum.INVALID_PARAMETERS,errorList);
    }
}

