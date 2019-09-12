package com.wdx.bootplum.common.aspect;

import com.wdx.bootplum.common.controller.BaseController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author wangwei
 * @Description //TODO com.wdx.bootplum.common.aspect.Log 注解的切面
 * @Date 11:49 2019-04-03
 * @Param
 * @return
 **/
@Aspect
@Component
public class LogAspect extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

//    @Autowired
//    private FmSysLogService sysLogService;


    @Pointcut("@annotation(com.wdx.bootplum.common.aspect.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        FmSysLog sysLog = new FmSysLog();
//        Log log = method.getAnnotation(Log.class);
//        if (log != null) {
//            // 注解上的描述
//            sysLog.setOperation(log.value());
//        }
//        // 请求的方法名
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = signature.getName();
//        sysLog.setMethod(className + "." + methodName + "()");
//        // 请求的参数
//        Object[] args = joinPoint.getArgs();
//        try {
//            String params = JsonUtil.beanToString(args[0]).substring(0, 4999);
//            sysLog.setParams(params);
//        } catch (Exception e) {
//
//        }
//        // 获取request
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        // 设置IP地址
//        sysLog.setIp(IPUtils.getIpAddr(request));
//        // 用户名
//        FmBackUser currUser = getLoginInfo();
//        if (null == currUser) {
//            if (null != sysLog.getParams()) {
//                sysLog.setUserId("-1L");
//                sysLog.setUsername(sysLog.getParams());
//            } else {
//                sysLog.setUserId("-1L");
//                sysLog.setUsername("获取用户信息为空");
//            }
//        } else {
//            sysLog.setUserId(currUser.getUserId());
//            sysLog.setUsername(currUser.getUsername());
//        }
//        sysLog.setTime((int) time);
//        // 系统当前时间
//        Date date = new Date();
//        sysLog.setGmtCreate(date);
//        // 保存系统日志
//        sysLogService.insert(sysLog);
    }
}
