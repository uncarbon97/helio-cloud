package cc.uncarbon.module.adminapi.web.sys;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.facade.SysMenuFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysMenuDTO;
import cc.uncarbon.module.sys.model.response.SysMenuBO;
import cc.uncarbon.module.adminapi.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@SaCheckLogin(type = AdminStpUtil.TYPE)
@Tag(name = "后台菜单管理接口")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminSysMenuController {

    private static final String PERMISSION_PREFIX = "SysMenu:";

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysMenuFacade sysMenuFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @Operation(summary = "列表")
    @GetMapping(value = "/sys/menus")
    public ApiResult<List<SysMenuBO>> list() {
        return ApiResult.data(sysMenuFacade.adminList());
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @Operation(summary = "详情")
    @GetMapping(value = "/sys/menus/{id}")
    public ApiResult<SysMenuBO> getById(@PathVariable Long id) {
        return ApiResult.data(sysMenuFacade.getOneById(id, true));
    }

    @SysLog(value = "新增后台菜单")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.CREATE)
    @Operation(summary = "新增")
    @PostMapping(value = "/sys/menus")
    public ApiResult<Void> insert(@RequestBody @Valid AdminInsertOrUpdateSysMenuDTO dto) {
        sysMenuFacade.adminInsert(dto);

        return ApiResult.success();
    }

    @SysLog(value = "编辑后台菜单")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.UPDATE)
    @Operation(summary = "编辑")
    @PutMapping(value = "/sys/menus/{id}")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid AdminInsertOrUpdateSysMenuDTO dto) {
        dto.setId(id);
        sysMenuFacade.adminUpdate(dto);

        return ApiResult.success();
    }

    @SysLog(value = "删除后台菜单")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @Operation(summary = "删除")
    @DeleteMapping(value = "/sys/menus")
    public ApiResult<Void> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        sysMenuFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

    @Operation(summary = "取当前账号可见侧边菜单")
    @GetMapping("/sys/menus/side")
    public ApiResult<List<SysMenuBO>> adminListSideMenu() {
        return ApiResult.data(sysMenuFacade.adminListSideMenu());
    }

    @Operation(summary = "取当前账号所有可见菜单")
    @GetMapping("/sys/menus/all")
    public ApiResult<List<SysMenuBO>> adminListVisibleMenu() {
        return ApiResult.data(sysMenuFacade.adminListVisibleMenu());
    }

}
