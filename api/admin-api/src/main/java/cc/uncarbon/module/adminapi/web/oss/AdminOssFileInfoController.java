package cc.uncarbon.module.adminapi.web.oss;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.oss.facade.OssFileInfoFacade;
import cc.uncarbon.module.oss.model.request.AdminListOssFileInfoDTO;
import cc.uncarbon.module.oss.model.response.OssFileInfoBO;
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


@SaCheckLogin(type = AdminStpUtil.TYPE)
@Tag(name = "后台管理-上传文件信息管理接口")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminOssFileInfoController {

    // 功能权限串前缀
    private static final String PERMISSION_PREFIX = "OssFileInfo:";

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private OssFileInfoFacade ossFileInfoFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @Operation(summary = "分页列表")
    @GetMapping(value = "/oss/file/infos")
    public ApiResult<PageResult<OssFileInfoBO>> list(PageParam pageParam, AdminListOssFileInfoDTO dto) {
        return ApiResult.data(ossFileInfoFacade.adminList(pageParam, dto));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @Operation(summary = "详情")
    @GetMapping(value = "/oss/file/infos/{id}")
    public ApiResult<OssFileInfoBO> getById(@PathVariable Long id) {
        return ApiResult.data(ossFileInfoFacade.getOneById(id, true));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @Operation(summary = "删除")
    @DeleteMapping(value = "/oss/file/infos")
    public ApiResult<Void> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        ossFileInfoFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

}
