package cc.uncarbon.module.oss.aspect;

import cc.uncarbon.framework.core.context.UserContextHolder;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.enums.SysLogStatusEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * SysLog 切面实现类
 *
 * @author Uncarbon
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SysLogRpcAspect {

    @Pointcut("@annotation(cc.uncarbon.module.sys.annotation.SysLog)")
    public void sysLogPointcut() {
        // AOP Pointcut
    }

    @Around("sysLogPointcut()")
    public Object sysLogAround(ProceedingJoinPoint point) throws Throwable {
        // --------------------Begin @SysLog--------------------

        Object executeResult;
        executeResult = point.proceed();

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        SysLog sysLogAnnotation = methodSignature.getMethod().getAnnotation(SysLog.class);

        SysLogEntity sysLogEntity = new SysLogEntity()
                // 记录操作人
                .setUserId(UserContextHolder.getUserId())
                .setUsername(UserContextHolder.getUserName())
                // 记录请求方法
                .setMethod(methodSignature.toString())
                // 记录操作内容
                .setOperation(sysLogAnnotation.value());

        /*
        记录请求参数
         */
        HashMap<Object, Object> afterMasked = new HashMap<>();
        sysLogEntity.setParams(Arrays.stream(point.getArgs()).map(
                each -> {
                    // 先去除敏感字段后再入库
                    afterMasked.clear();
                    BeanUtil.copyProperties(each, afterMasked, copyOptions4MaskingArgs);
                    return JSONUtil.toJsonStr(afterMasked);
                }
        ).collect(Collectors.joining(",")));

        /*
        记录IP地址
        注意：RPC 不能像单体一样拿到 request 对象
         */
        sysLogEntity.setIp(UserContextHolder.getClientIP());

        // 记录状态
        sysLogEntity.setStatus(SysLogStatusEnum.SUCCESS);

        // 执行扩展 - 保存到 DB 前
        for (SysLogAspectExtension extension : extensions) {
            extension.beforeSaving(sysLogAnnotation, point, sysLogEntity);
        }

        this.callSysLogServiceSave(sysLogEntity);
        // --------------------End @SysLog--------------------

        return executeResult;
    }

    @Async(value = "taskExecutor")
    void callSysLogServiceSave(SysLogEntity sysLogEntity) {
        sysLogService.save(sysLogEntity);
    }

}