package cc.uncarbon.module.common.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.oss.facade.OssFileInfoFacade;
import cc.uncarbon.module.oss.model.request.AdminListOssFileInfoDTO;
import cc.uncarbon.module.oss.model.response.OssFileInfoBO;
import cc.uncarbon.module.common.service.OssFileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collection;

/**
 * 上传文件信息 Facade 接口实现类
 *
 * @author Uncarbon
 */
@Slf4j
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
@RequiredArgsConstructor
public class OssFileInfoFacadeImpl implements OssFileInfoFacade {

    private final OssFileInfoService ossFileInfoService;


    @Override
    public PageResult<OssFileInfoBO> adminList(PageParam pageParam, AdminListOssFileInfoDTO dto) {
        return ossFileInfoService.adminList(pageParam, dto);
    }

    @Override
    public OssFileInfoBO getOneById(Long id) {
        return ossFileInfoService.getOneById(id);
    }

    @Override
    public OssFileInfoBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return ossFileInfoService.getOneById(id, throwIfInvalidId);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        ossFileInfoService.adminDelete(ids);
    }

    @Override
    public OssFileInfoBO getOneByMd5(String md5) {
        return ossFileInfoService.getOneByMd5(md5);
    }

}
