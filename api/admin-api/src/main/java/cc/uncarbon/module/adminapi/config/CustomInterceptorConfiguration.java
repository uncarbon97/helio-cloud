package cc.uncarbon.module.adminapi.config;

import cc.uncarbon.module.adminapi.interceptor.AdminSaTokenParseInterceptor;
import cn.dev33.satoken.interceptor.SaInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 将自定义拦截器加入到拦截器队列中
 *
 * @author Uncarbon
 */
@Configuration
@RequiredArgsConstructor
public class CustomInterceptorConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        1. 通用请求头解析，设定用户、租户上下文
         */
        registry
                .addInterceptor(new AdminSaTokenParseInterceptor())
                .addPathPatterns("/**");

        /*
        2. 注解拦截器, 启用注解功能
         */
        registry
                .addInterceptor(new SaInterceptor())
                .addPathPatterns("/**");
    }
}
