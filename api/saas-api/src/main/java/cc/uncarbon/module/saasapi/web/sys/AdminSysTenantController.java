package cc.uncarbon.module.saasapi.web.sys;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.facade.SysTenantFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminUpdateSysTenantDTO;
import cc.uncarbon.module.sys.model.response.SysTenantBO;
import cc.uncarbon.module.saasapi.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@SaCheckLogin(type = AdminStpUtil.TYPE)
@Slf4j
@Api(value = "系统租户管理接口", tags = {"系统租户管理接口"})
@RequestMapping(HelioConstant.Version.HTTP_API_VERSION_V1 + "/sys/tenants")
@RestController
public class AdminSysTenantController {

    private static final String PERMISSION_PREFIX = "SysTenant:";

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysTenantFacade sysTenantFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "分页列表")
    @GetMapping
    public ApiResult<PageResult<SysTenantBO>> list(PageParam pageParam, AdminListSysTenantDTO dto) {
        return ApiResult.data(sysTenantFacade.adminList(pageParam, dto));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "详情")
    @GetMapping(value = "/{id}")
    public ApiResult<SysTenantBO> getById(@PathVariable Long id) {
        return ApiResult.data(sysTenantFacade.getOneById(id, true));
    }

    @SysLog(value = "新增系统租户")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.CREATE)
    @ApiOperation(value = "新增")
    @PostMapping
    public ApiResult<?> insert(@RequestBody @Valid AdminInsertSysTenantDTO dto) {
        sysTenantFacade.adminInsert(dto);

        return ApiResult.success();
    }

    @SysLog(value = "编辑系统租户")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.UPDATE)
    @ApiOperation(value = "编辑")
    @PutMapping(value = "/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody @Valid AdminUpdateSysTenantDTO dto) {
        dto.setId(id);
        sysTenantFacade.adminUpdate(dto);

        return ApiResult.success();
    }

    @SysLog(value = "删除系统租户")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @ApiOperation(value = "删除")
    @DeleteMapping
    public ApiResult<?> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        sysTenantFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

}