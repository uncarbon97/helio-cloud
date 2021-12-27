package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysTenantFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminUpdateSysTenantDTO;
import cc.uncarbon.module.sys.model.response.SysTenantBO;
import cc.uncarbon.module.sys.service.SysTenantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统租户Facade接口实现类
 * @author Uncarbon
 */
@Slf4j
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
public class SysTenantFacadeImpl implements SysTenantFacade {

    @Resource
    private SysTenantService sysTenantService;


    @Override
    public PageResult<SysTenantBO> adminList(PageParam pageParam, AdminListSysTenantDTO dto) {
        return sysTenantService.adminList(pageParam, dto);
    }

    @Override
    public SysTenantBO getOneById(Long entityId) throws BusinessException {
        return sysTenantService.getOneById(entityId, true);
    }

    @Override
    public SysTenantBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException {
        return sysTenantService.getOneById(entityId, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertSysTenantDTO dto) {
        return sysTenantService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminUpdateSysTenantDTO dto) {
        sysTenantService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(List<Long> ids) {
        sysTenantService.adminDelete(ids);
    }

}
