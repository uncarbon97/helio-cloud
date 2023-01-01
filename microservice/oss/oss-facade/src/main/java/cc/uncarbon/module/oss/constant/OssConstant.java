package cc.uncarbon.module.oss.constant;

public interface OssConstant {

    /**
     * 存储平台-本地存储-前缀
     */
    String PLATFORM_PREFIX_LOCAL = "local";

    /**
     * 敏感字段，`SysLogAspect` 切面记录系统操作日志时，会先去除敏感字段后再入库
     */
    String[] SENSITIVE_FIELDS = {};

}
