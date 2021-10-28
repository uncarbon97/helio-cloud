package cc.uncarbon.module.config;

import cc.uncarbon.module.interceptor.AdminSaTokenParseInterceptor;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 将自定义拦截器加入到拦截器队列中
 * @author Uncarbon
 */
@Configuration
public class SaTokenInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        1. 请求头解析, 设定用户上下文
         */
        registry
                .addInterceptor(new AdminSaTokenParseInterceptor())
                .addPathPatterns("/**");

        /*
        2. 注解拦截器, 启用注解功能
         */
        registry
                .addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/**");

    }

}
