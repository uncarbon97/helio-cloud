package cc.uncarbon.module.web.oss;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.framework.web.model.request.IdsDTO;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.common.facade.OssFileInfoFacade;
import cc.uncarbon.module.common.model.request.AdminListOssFileInfoDTO;
import cc.uncarbon.module.common.model.response.OssFileInfoBO;
import cc.uncarbon.module.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 后台管理-上传文件信息管理接口
 *
 * @author Uncarbon
 */
@RequiredArgsConstructor
@SaCheckLogin(type = AdminStpUtil.TYPE)
@Slf4j
@Api(value = "上传文件信息管理接口", tags = {"上传文件信息管理接口"})
@RequestMapping(HelioConstant.Version.HTTP_API_VERSION_V1 + "/oss/ossFileInfos")
@RestController
public class AdminOssFileInfoController {

    // 功能权限串前缀
    private static final String PERMISSION_PREFIX = "OssFileInfo:";

    @DubboReference(version = HelioConstant.Version.DUBBO_VERSION_V1, validation = HelioConstant.Dubbo.ENABLE_VALIDATION)
    private OssFileInfoFacade ossFileInfoFacade;


    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "分页列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public ApiResult<PageResult<OssFileInfoBO>> list(PageParam pageParam, AdminListOssFileInfoDTO dto) {
        return ApiResult.data(ossFileInfoFacade.adminList(pageParam, dto));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.RETRIEVE)
    @ApiOperation(value = "详情", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{id}")
    public ApiResult<OssFileInfoBO> getById(@PathVariable Long id) {
        return ApiResult.data(ossFileInfoFacade.getOneById(id, true));
    }

    @SaCheckPermission(type = AdminStpUtil.TYPE, value = PERMISSION_PREFIX + HelioConstant.Permission.DELETE)
    @ApiOperation(value = "删除", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping
    public ApiResult<?> delete(@RequestBody @Valid IdsDTO<Long> dto) {
        ossFileInfoFacade.adminDelete(dto.getIds());

        return ApiResult.success();
    }

}
