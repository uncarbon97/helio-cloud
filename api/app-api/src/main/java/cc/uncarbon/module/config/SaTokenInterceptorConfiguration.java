package cc.uncarbon.module.config;

import cc.uncarbon.framework.core.props.HelioProperties;
import cc.uncarbon.framework.satoken.interceptor.DefaultSaTokenParseInterceptor;
import cc.uncarbon.module.interceptor.SaTokenRouteInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * 将自定义拦截器加入到拦截器队列中
 * @author Uncarbon
 */
@Configuration
public class SaTokenInterceptorConfiguration implements WebMvcConfigurer {

    @Resource
    private HelioProperties helioProperties;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        1. 请求头解析, 设定用户上下文
         */
        registry
                .addInterceptor(new DefaultSaTokenParseInterceptor())
                .addPathPatterns("/**");

        /*
        2. 路由拦截器, 使几乎所有接口都需要登录
         */
        registry
                .addInterceptor(new SaTokenRouteInterceptor(helioProperties))
                .addPathPatterns("/**")
                .excludePathPatterns(helioProperties.getSecurity().getExcludeRoutes());
    }
}
