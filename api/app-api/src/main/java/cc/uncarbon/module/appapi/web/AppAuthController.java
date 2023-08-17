package cc.uncarbon.module.appapi.web;


import cc.uncarbon.framework.web.model.response.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(value = "APP鉴权接口", tags = {"APP鉴权接口"})
@RequestMapping("/api/v1")
@RestController
public class AppAuthController {

    /*
    本微服务模块的所有接口默认为都需要登录，放行接口请在 app-api.yml 配置文件的 helio.security.exclude-routes 中设置
    相关拦截器代码请见 CustomInterceptorConfiguration.java
     */

    @ApiOperation(value = "登录")
    @PostMapping("/auth/login")
    public ApiResult<?> login() {
        /*
        编码时请参考 saas-api 的 AdminAuthController#login
         */

        return ApiResult.success();
    }

}
