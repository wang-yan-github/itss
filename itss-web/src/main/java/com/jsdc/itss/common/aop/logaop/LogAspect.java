package com.jsdc.itss.common.aop.logaop;

import com.jsdc.itss.model.SysLog;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.service.SysLogService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName LogAspect
 * @Description TODO
 * @Author xujian
 * @Date 2022/1/4 11:48
 * @Version 1.0
 */

@Aspect
@Component
public class LogAspect {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLogService logService;

    @Pointcut(value = "@annotation(com.jsdc.itss.common.aop.logaop.LogInfo)")
    public void logPointcut(){

    }

    @AfterReturning(value = "logPointcut()",returning = "resultInfo")
    public void saveLoginfo(JoinPoint joinPoint, ResultInfo resultInfo) throws Exception {
        if(resultInfo.getCode() == 0 && null != resultInfo.getLogVo()){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            LogInfo annotation = signature.getMethod().getAnnotation(LogInfo.class);
            SysLog sysLog = new SysLog();

            BeanUtils.copyProperties(resultInfo.getLogVo(), sysLog);
            sysLog.setModule(annotation.value().getValue());
            if(null == resultInfo.getLogVo().getUserId()){
                SysUser sysUser = sysUserService.getUser();
                sysLog.setUser_id(sysUser.getId());
                sysLog.setUser_name(sysUser.getName());
            }else{
                SysUser sysUser = sysUserService.selectById(resultInfo.getLogVo().getUserId());
                sysLog.setUser_id(sysUser.getId());
                sysLog.setUser_name(sysUser.getName());
            }
            logService.insert(sysLog);
        }

    }
}
