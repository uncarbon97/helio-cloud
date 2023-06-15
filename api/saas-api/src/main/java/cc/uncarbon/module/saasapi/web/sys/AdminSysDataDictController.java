package cc.uncarbon.module.saasapi.web.sys;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.facade.SysDataDictFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDataDictDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysDataDictDTO;
import cc.uncarbon.module.sys.model.response.SysDataDictBO;
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
@Api(value = "数据字典管理接口", tags = {"数据字典管理接口"})
@RequestMapping(HelioConstant.Version.HTTP_API_VERSION_V1 + "/sys/dataDicts")
@RestController
public class AdminSysDataDictController {

    private static final String PERMISSION_PREFIX = "SysDataDict:" ;

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysDataDictFacade sysDataDictFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "分页列表")
    @GetMapping
    public ApiResult<PageResult<SysDataDictBO>> list(PageParam pageParam, AdminListSysDataDictDTO dto) {
        return ApiResult.data(sysDataDictFacade.adminList(pageParam, dto));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "详情")
    @GetMapping(value = "/{id}")
    public ApiResult<SysDataDictBO> getById(@PathVariable Long id) {
        return ApiResult.data(sysDataDictFacade.getOneById(id, true));
    }

    @SysLog(value = "新增数据字典")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.CREATE)
    @ApiOperation(value = "新增")
    @PostMapping
    public ApiResult<?> insert(@RequestBody @Valid AdminInsertOrUpdateSysDataDictDTO dto) {
        sysDataDictFacade.adminInsert(dto);

        return ApiResult.success();
    }

    @SysLog(value = "编辑数据字典")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.UPDATE)
    @ApiOperation(value = "编辑")
    @PutMapping(value = "/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody @Valid AdminInsertOrUpdateSysDataDictDTO dto) {
        dto.setId(id);
        sysDataDictFacade.adminUpdate(dto);

        return ApiResult.success();
    }

    @SysLog(value = "删除数据字典")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @ApiOperation(value = "删除")
    @DeleteMapping
    public ApiResult<?> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        sysDataDictFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

}
