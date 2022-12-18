package cc.uncarbon.module.oss.aspect;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.context.UserContextHolder;
import cc.uncarbon.module.oss.constant.OssConstant;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.enums.SysLogStatusEnum;
import cc.uncarbon.module.sys.facade.SysLogFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertSysLogDTO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.StrPool;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
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
 * copied from cc.uncarbon.module.sys.aspect.SysLogAspect
 *
 * @author Uncarbon
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SysLogAspect {

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysLogFacade sysLogFacade;

    // Bean 属性复制配置项
    private static final CopyOptions copyOptions4MaskingArgs = new CopyOptions();


    static {
        copyOptions4MaskingArgs.setIgnoreNullValue(false);
        copyOptions4MaskingArgs.setIgnoreProperties(OssConstant.SENSITIVE_FIELDS);
    }

    @Pointcut("@annotation(cc.uncarbon.module.sys.annotation.SysLog)")
    public void sysLogPointcut() {
        // AOP Pointcut
    }

    @Around("sysLogPointcut()")
    public Object sysLogAround(ProceedingJoinPoint point) throws Throwable {
        /*
        执行切面，并记录成功or失败、异常
         */
        Object executeResult = null;
        boolean executeSuccessFlag = false;
        Exception executeFailedException = null;
        try {
            executeResult = point.proceed();
            executeSuccessFlag = true;
        } catch (Exception e) {
            // ignored
            executeFailedException = e;
        }

        /*
        记录对应系统日志
         */
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        SysLog sysLogAnnotation = methodSignature.getMethod().getAnnotation(SysLog.class);

        if (sysLogAnnotation.sync()) {
            this.sysLogSaving(point, methodSignature, sysLogAnnotation, executeSuccessFlag);
        } else{
            this.sysLogSavingAsync(point, methodSignature, sysLogAnnotation, executeSuccessFlag);
        }

        /*
        如果执行过程中存在异常则抛出
         */
        if (executeFailedException != null) {
            throw executeFailedException;
        }

        return executeResult;
    }

    /**
     * 保存系统日志
     */
    private void sysLogSaving(ProceedingJoinPoint point,
                              MethodSignature methodSignature,
                              SysLog sysLogAnnotation,
                              boolean executeSuccessFlag
    ) {
        AdminInsertSysLogDTO dto = new AdminInsertSysLogDTO()
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
        dto.setParams(Arrays.stream(point.getArgs()).map(
                each -> {
                    // 先去除敏感字段后再入库
                    afterMasked.clear();
                    BeanUtil.copyProperties(each, afterMasked, copyOptions4MaskingArgs);
                    return JSONUtil.toJsonStr(afterMasked);
                }
        ).collect(Collectors.joining(StrPool.COMMA)));

        /*
        记录IP地址
        注意：RPC 不能像单体一样拿到 request 对象
         */
        dto.setIp(UserContextHolder.getClientIP());

        // 记录状态
        dto.setStatus(executeSuccessFlag ? SysLogStatusEnum.SUCCESS : SysLogStatusEnum.FAILED);

        // 省略SysLogAspectExtension扩展

        // 保存系统日志
        sysLogFacade.adminInsert(dto);
    }

    /**
     * 异步保存系统日志
     */
    @Async(value = "taskExecutor")
    public void sysLogSavingAsync(ProceedingJoinPoint point,
                                  MethodSignature methodSignature,
                                  SysLog sysLogAnnotation,
                                  boolean executeSuccessFlag
    ) {
        this.sysLogSaving(point, methodSignature, sysLogAnnotation, executeSuccessFlag);
    }

}
