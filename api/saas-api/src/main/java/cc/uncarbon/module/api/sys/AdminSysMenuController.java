package cc.uncarbon.module.api.sys;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.sys.facade.SysMenuFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysMenuDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysMenuDTO;
import cc.uncarbon.module.sys.model.response.SysMenuBO;
import cc.uncarbon.module.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Uncarbon
 */
@SaCheckLogin(type = AdminStpUtil.TYPE)
@Slf4j
@Api(value = "后台菜单管理接口", tags = {"后台菜单管理接口"})
@RequestMapping(HelioConstant.Version.SAAS_API_VERSION_V1 + "/sys/menus")
@RestController
public class AdminSysMenuController {

    private static final String PERMISSION_PREFIX = "SysMenu:";
    
    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysMenuFacade sysMenuFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public ApiResult<List<SysMenuBO>> list(AdminListSysMenuDTO dto) {
        return ApiResult.data(sysMenuFacade.adminList(dto));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "详情", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{id}")
    public ApiResult<SysMenuBO> getById(@PathVariable Long id) {
        return ApiResult.data(sysMenuFacade.getOneById(id));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.CREATE)
    @ApiOperation(value = "新增", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    public ApiResult<?> insert(@RequestBody @Valid AdminInsertOrUpdateSysMenuDTO dto) {
        sysMenuFacade.adminInsert(dto);

        return ApiResult.success();
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.UPDATE)
    @ApiOperation(value = "编辑", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody @Valid AdminInsertOrUpdateSysMenuDTO dto) {
        dto.setId(id);
        sysMenuFacade.adminUpdate(dto);

        return ApiResult.success();
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @ApiOperation(value = "删除", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping
    public ApiResult<?> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        sysMenuFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

    @ApiOperation(value = "取当前账号可见侧边菜单", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/side")
    public ApiResult<List<SysMenuBO>> adminListSideMenu() {
        return ApiResult.data(sysMenuFacade.adminListSideMenu());
    }

    @ApiOperation(value = "取当前账号所有可见菜单", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/all")
    public ApiResult<List<SysMenuBO>> adminListVisibleMenu() {
        return ApiResult.data(sysMenuFacade.adminListVisibleMenu());
    }

}
