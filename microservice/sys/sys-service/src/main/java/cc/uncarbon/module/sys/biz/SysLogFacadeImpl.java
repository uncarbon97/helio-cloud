package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysLogFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertSysLogDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysLogDTO;
import cc.uncarbon.module.sys.model.response.SysLogBO;
import cc.uncarbon.module.sys.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 系统日志Facade接口实现类
 */
@Slf4j
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
@RequiredArgsConstructor
public class SysLogFacadeImpl implements SysLogFacade {

    private final SysLogService sysLogService;


    @Override
    public PageResult<SysLogBO> adminList(PageParam pageParam, AdminListSysLogDTO dto) {
        return sysLogService.adminList(pageParam, dto);
    }

    @Override
    public SysLogBO getOneById(Long id) {
        return sysLogService.getOneById(id);
    }

    @Override
    public SysLogBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return sysLogService.getOneById(id, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertSysLogDTO dto) {
        return sysLogService.adminInsert(dto);
    }
}
