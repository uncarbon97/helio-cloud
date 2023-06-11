package cc.uncarbon.module.sys.annotation;

import cc.uncarbon.module.sys.extension.SysLogAspectExtension;

import java.lang.annotation.*;


/**
 * 放在方法上，可将操作记录至系统日志中
 *
 * @author Uncarbon
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SysLog {

    /**
     * 操作内容(如:新增部门)
     */
    String value();

    /**
     * 是否同步保存至系统日志数据表中
     * true = 同步保存：如果开启了事务/事务注解，若系统日志保存失败，则会抛出异常触发回滚，使得本次操作也失败
     * false = 异步保存：若系统日志保存失败，不影响本次操作
     */
    boolean syncSaving() default false;

    /**
     * 保存系统日志至数据表的时机
     * 默认为仅“成功时”
     * 多个输入值间默认为“或”关系
     */
    When[] when() default When.SUCCESS;
    enum When {
        /**
         * 成功时
         */
        SUCCESS,

        /**
         * 失败时
         */
        FAILED,
    }

    /**
     * 扩展点，可以增强系统日志，在保存时的动作
     * 示例见：SysLogAspectExtensionForSysUserLogin
     */
    Class<? extends SysLogAspectExtension> extension() default SysLogAspectExtension.class;

}
