package cc.uncarbon.module.aspect;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.context.UserContextHolder;
import cc.uncarbon.framework.web.util.IPUtil;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.constant.SysConstant;
import cc.uncarbon.module.sys.enums.SysLogStatusEnum;
import cc.uncarbon.module.sys.facade.SysLogFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertSysLogDTO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * SysLog 切面实现类
 *
 * @author Uncarbon
 * @author ruoyi
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SysLogAspect {

    /**
     * 用于数据脱敏的Bean复制选项
     */
    private static final CopyOptions MASKING_COPY_OPTIONS = new CopyOptions()
            .setIgnoreNullValue(false)
            .setIgnoreProperties(SysConstant.SENSITIVE_FIELDS);

    /**
     * 最大数据文本保存长度
     */
    private static final int MAX_STRING_SAVE_LENGTH = 2000;

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysLogFacade sysLogFacade;


    /**
     * 顺利运行
     */
    @AfterReturning(pointcut = "@annotation(annotation)", returning = "ret")
    public void doAfterReturning(JoinPoint joinPoint, SysLog annotation, Object ret) {
        if (annotation.syncSaving()) {
            sysLogSavingAsync(joinPoint, annotation, null, ret);
            return;
        }
        sysLogSaving(joinPoint, annotation, null, ret);
    }

    /**
     * 出现异常
     */
    @AfterThrowing(value = "@annotation(annotation)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, SysLog annotation, Exception e) {
        if (annotation.syncSaving()) {
            sysLogSavingAsync(joinPoint, annotation, e, null);
            return;
        }
        sysLogSaving(joinPoint, annotation, e, null);
    }

    /**
     * 同步保存系统日志
     */
    private void sysLogSaving(final JoinPoint joinPoint, SysLog annotation, final Exception e, Object ret) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        AdminInsertSysLogDTO dto = new AdminInsertSysLogDTO()
                // 记录操作人
                .setUserId(UserContextHolder.getUserId())
                .setUsername(UserContextHolder.getUserName())
                // 记录请求方法
                .setMethod(methodSignature.toString())
                // 记录操作内容
                .setOperation(annotation.value())
                // 默认置为成功
                .setStatus(SysLogStatusEnum.SUCCESS)
                ;

        /*
        记录请求参数
         */
        HashMap<Object, Object> afterMasked = new HashMap<>(16, 1);
        String params = Arrays.stream(joinPoint.getArgs()).map(
                each -> {
                    // 先去除敏感字段后再入库
                    afterMasked.clear();
                    BeanUtil.copyProperties(each, afterMasked, MASKING_COPY_OPTIONS);
                    return JSONUtil.toJsonStr(afterMasked);
                }
        ).collect(Collectors.joining(StrPool.COMMA));
        if (StrUtil.length(params) > MAX_STRING_SAVE_LENGTH) {
            params = StrUtil.sub(params, 0, MAX_STRING_SAVE_LENGTH);
        }
        dto.setParams(params);

        /*
        记录IP地址
        https://gitee.com/uncarbon97/helio-boot/issues/I5KN1X
         */
        String ip = UserContextHolder.getClientIP();
        if (StrUtil.isEmpty(ip)) {
            // 兜底处理，直接从当前线程的请求头中拿
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                ip = IPUtil.getClientIPAddress(requestAttributes.getRequest());
            }
        }
        dto.setIp(ip);

        if (e != null) {
            // 异常不为空，置状态为失败
            dto.setStatus(SysLogStatusEnum.FAILED);

            // 错误消息
            String errorMsg = e.getMessage();
            if (StrUtil.length(errorMsg) > MAX_STRING_SAVE_LENGTH) {
                errorMsg = StrUtil.sub(errorMsg, 0, MAX_STRING_SAVE_LENGTH);
            }
            dto.setErrorMsg(errorMsg);
        }

        // 保存系统日志
        sysLogFacade.adminInsert(dto);
    }

    /**
     * 异步保存系统日志
     */
    @Async(value = "taskExecutor")
    public void sysLogSavingAsync(final JoinPoint joinPoint, SysLog annotation, final Exception e, Object ret) {
        this.sysLogSaving(joinPoint, annotation, e, ret);
    }
}
