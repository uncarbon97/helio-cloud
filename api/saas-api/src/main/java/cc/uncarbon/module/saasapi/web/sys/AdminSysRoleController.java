package cc.uncarbon.module.saasapi.web.sys;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.saasapi.helper.RolePermissionCacheHelper;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.facade.SysRoleFacade;
import cc.uncarbon.module.sys.model.request.AdminBindRoleMenuRelationDTO;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysRoleDTO;
import cc.uncarbon.module.sys.model.response.SysRoleBO;
import cc.uncarbon.module.saasapi.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;


@SaCheckLogin(type = AdminStpUtil.TYPE)
@Slf4j
@Api(value = "后台角色管理接口", tags = {"后台角色管理接口"})
@RequestMapping(HelioConstant.Version.HTTP_API_VERSION_V1 + "/sys/roles")
@RestController
@RequiredArgsConstructor
public class AdminSysRoleController {

    private static final String PERMISSION_PREFIX = "SysRole:" ;

    private final RolePermissionCacheHelper rolePermissionCacheHelper;

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysRoleFacade sysRoleFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "分页列表")
    @GetMapping
    public ApiResult<PageResult<SysRoleBO>> list(PageParam pageParam, AdminListSysRoleDTO dto) {
        return ApiResult.data(sysRoleFacade.adminList(pageParam, dto));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "详情")
    @GetMapping(value = "/{id}")
    public ApiResult<SysRoleBO> getById(@PathVariable Long id) {
        return ApiResult.data(sysRoleFacade.getOneById(id, true));
    }

    @SysLog(value = "新增后台角色")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.CREATE)
    @ApiOperation(value = "新增")
    @PostMapping
    public ApiResult<?> insert(@RequestBody @Valid AdminInsertOrUpdateSysRoleDTO dto) {
        sysRoleFacade.adminInsert(dto);

        return ApiResult.success();
    }

    @SysLog(value = "编辑后台角色")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.UPDATE)
    @ApiOperation(value = "编辑")
    @PutMapping(value = "/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody @Valid AdminInsertOrUpdateSysRoleDTO dto) {
        dto.setId(id);
        sysRoleFacade.adminUpdate(dto);

        return ApiResult.success();
    }

    @SysLog(value = "删除后台角色")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @ApiOperation(value = "删除")
    @DeleteMapping
    public ApiResult<?> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        sysRoleFacade.adminDelete(dto.getIds());

        // 角色删除时，删除对应缓存键
        rolePermissionCacheHelper.deleteCache(dto.getIds());

        return ApiResult.success();
    }

    @SysLog(value = "绑定角色与菜单关联关系")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + "bindMenus")
    @ApiOperation(value = "绑定角色与菜单关联关系")
    @PutMapping(value = "/{id}/menus")
    public ApiResult<?> bindMenus(@PathVariable Long id, @RequestBody @Valid AdminBindRoleMenuRelationDTO dto) {
        dto.setRoleId(id);
        Set<String> newPermissions = sysRoleFacade.adminBindMenus(dto);

        // 覆盖更新缓存
        rolePermissionCacheHelper.putCache(dto.getRoleId(), newPermissions);

        return ApiResult.success();
    }

}