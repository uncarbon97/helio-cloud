package cc.uncarbon.module.config;

import cc.uncarbon.framework.core.props.HelioProperties;
import cc.uncarbon.framework.satoken.interceptor.DefaultSaTokenParseInterceptor;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
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

    private final HelioProperties helioProperties;


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
        放行接口请在配置文件的 helio.security.exclude-routes 中设置

        @see http://sa-token.dev33.cn/doc/index.html#/use/route-check
         */
        registry
                .addInterceptor(new SaInterceptor(
                        (handler) -> StpUtil.checkLogin()
                ))
                .addPathPatterns("/**")
                .excludePathPatterns(helioProperties.getSecurity().getExcludeRoutes());
    }
}
