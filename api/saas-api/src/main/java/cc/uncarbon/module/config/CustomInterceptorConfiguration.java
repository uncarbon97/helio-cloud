package cc.uncarbon.module.config;

import cc.uncarbon.module.interceptor.AdminSaTokenParseInterceptor;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 将自定义拦截器加入到拦截器队列中
 *
 * @author Uncarbon
 */
@Configuration
public class CustomInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        1. 请求头解析, 设定用户上下文
         */
        registry
                .addInterceptor(new AdminSaTokenParseInterceptor())
                .addPathPatterns("/**");

        /*
        演示 - 可选拦截器 - 从请求头解析租户信息
        其他家后台管理登录时，账号上面可以手动填入租户ID，如"000000"
        这个拦截器就是为了满足这种需要

        注：若使用该拦截器，cc.uncarbon.module.sys.service.SysUserService#adminLogin 处的“主动清空用户上下文”代码可能需要删除
         */
        /*registry
                .addInterceptor(new DefaultTenantParseInterceptor())
                .addPathPatterns("/**");*/

        /*
        2. 注解拦截器, 启用注解功能
         */
        registry
                .addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/**");

    }

}
