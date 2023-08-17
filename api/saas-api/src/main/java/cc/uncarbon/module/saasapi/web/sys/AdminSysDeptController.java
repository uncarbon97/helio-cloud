package cc.uncarbon.module.saasapi.web.sys;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.facade.SysDeptFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDeptDTO;
import cc.uncarbon.module.sys.model.response.SysDeptBO;
import cc.uncarbon.module.saasapi.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@SaCheckLogin(type = AdminStpUtil.TYPE)
@Slf4j
@Api(value = "部门管理接口", tags = {"部门管理接口"})
@RequestMapping("/api/v1")
@RestController
public class AdminSysDeptController {

    private static final String PERMISSION_PREFIX = "SysDept:";

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private SysDeptFacade sysDeptFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "列表")
    @GetMapping(value = "/sys/depts")
    public ApiResult<List<SysDeptBO>> list() {
        return ApiResult.data(sysDeptFacade.adminList());
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "详情")
    @GetMapping(value = "/sys/depts/{id}")
    public ApiResult<SysDeptBO> getById(@PathVariable Long id) {
        return ApiResult.data(sysDeptFacade.getOneById(id));
    }

    @SysLog(value = "新增部门")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.CREATE)
    @ApiOperation(value = "新增")
    @PostMapping(value = "/sys/depts")
    public ApiResult<?> insert(@RequestBody @Valid AdminInsertOrUpdateSysDeptDTO dto) {
        sysDeptFacade.adminInsert(dto);

        return ApiResult.success();
    }

    @SysLog(value = "编辑部门")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.UPDATE)
    @ApiOperation(value = "编辑")
    @PutMapping(value = "/sys/depts/{id}")
    public ApiResult<?> update(@PathVariable Long id, @RequestBody @Valid AdminInsertOrUpdateSysDeptDTO dto) {
        dto.setId(id);
        sysDeptFacade.adminUpdate(dto);

        return ApiResult.success();
    }

    @SysLog(value = "删除部门")
    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/sys/depts")
    public ApiResult<?> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        sysDeptFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

}
