package cc.uncarbon.module.adminapi.web.auth;


import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.context.TenantContext;
import cc.uncarbon.framework.core.context.TenantContextHolder;
import cc.uncarbon.framework.core.context.UserContext;
import cc.uncarbon.framework.core.context.UserContextHolder;
import cc.uncarbon.framework.ratelimit.annotation.UseRateLimit;
import cc.uncarbon.framework.ratelimit.stratrgy.impl.RateLimitByClientIPStrategy;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.adminapi.aspect.extension.SysLogAspectExtensionForSysUserLogin;
import cc.uncarbon.module.adminapi.helper.CaptchaHelper;
import cc.uncarbon.module.adminapi.helper.RolePermissionCacheHelper;
import cc.uncarbon.module.adminapi.model.interior.AdminCaptchaContainer;
import cc.uncarbon.module.adminapi.model.response.AdminCaptchaVO;
import cc.uncarbon.module.adminapi.util.AdminStpUtil;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.facade.SysUserFacade;
import cc.uncarbon.module.sys.model.request.SysUserLoginDTO;
import cc.uncarbon.module.sys.model.response.SysUserLoginBO;
import cc.uncarbon.module.sys.model.response.SysUserLoginVO;
import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@Api(value = "后台管理-鉴权接口", tags = {"后台管理-鉴权接口"})
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminAuthController {

    private final RolePermissionCacheHelper rolePermissionCacheHelper;

    private final CaptchaHelper captchaHelper;

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysUserFacade sysUserFacade;


    @SysLog(value = "登录后台用户", syncSave = true, extension = SysLogAspectExtensionForSysUserLogin.class, queryIPLocation = true)
    @ApiOperation(value = "登录")
    @PostMapping(value = "/auth/login")
    public ApiResult<SysUserLoginVO> login(@RequestBody @Valid SysUserLoginDTO dto) {
        // 登录验证码核验；前端项目搜索关键词「 Helio: 登录验证码」
        // AdminApiErrorEnum.CAPTCHA_VALIDATE_FAILED.assertTrue(captchaHelper.validate(dto.getCaptchaId(), dto.getCaptchaAnswer()))

        // RPC调用, 失败抛异常, 成功返回用户信息
        SysUserLoginBO userInfo = sysUserFacade.adminLogin(dto);

        // 构造用户上下文
        UserContext userContext = UserContext.builder()
                .userId(userInfo.getId())
                .userName(userInfo.getUsername())
                .userPhoneNo(userInfo.getPhoneNo())
                .userTypeStr("ADMIN_USER")
                .extraData(null)
                .rolesIds(userInfo.getRoleIds())
                .roles(userInfo.getRoles())
                .build();

        // 将用户ID注册到 SA-Token ，并附加一些业务字段
        AdminStpUtil.login(userInfo.getId(), dto.getRememberMe());
        AdminStpUtil.getSession().set(UserContext.CAMEL_NAME, userContext);
        AdminStpUtil.getSession().set(TenantContext.CAMEL_NAME, userInfo.getTenantContext());

        // 更新角色-权限缓存
        rolePermissionCacheHelper.putCache(userInfo.getRoleIdPermissionMap());

        // 返回登录token
        SysUserLoginVO tokenInfo = SysUserLoginVO.builder()
                .tokenName(AdminStpUtil.getTokenName())
                .tokenValue(AdminStpUtil.getTokenValue())
                .roles(userInfo.getRoles())
                .permissions(userInfo.getPermissions())
                .build();

        return ApiResult.data("登录成功", tokenInfo);
    }

    @SaCheckLogin(type = AdminStpUtil.TYPE)
    @ApiOperation(value = "登出")
    @PostMapping(value = "/auth/logout")
    public ApiResult<Void> logout() {
        AdminStpUtil.logout();
        UserContextHolder.clear();
        TenantContextHolder.clear();

        return ApiResult.success();
    }

    @UseRateLimit(mark = "admin-api-auth-captcha", duration = 60, max = 10, strategy = RateLimitByClientIPStrategy.class)
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/auth/captcha")
    public ApiResult<AdminCaptchaVO> captcha() {
        // 核验方法：captchaHelper.validate
        AdminCaptchaContainer captchaContainer = captchaHelper.generate();
        return ApiResult.data(new AdminCaptchaVO(captchaContainer));
    }

}
