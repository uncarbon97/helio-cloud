package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysParamFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysParamDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysParamDTO;
import cc.uncarbon.module.sys.model.response.SysParamBO;
import cc.uncarbon.module.sys.service.SysParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 系统参数Facade接口实现类
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
public class SysParamFacadeImpl implements SysParamFacade {

    private final SysParamService sysParamService;


    @Override
    public PageResult<SysParamBO> adminList(PageParam pageParam, AdminListSysParamDTO dto) {
        return sysParamService.adminList(pageParam, dto);
    }

    @Override
    public SysParamBO getOneById(Long id) {
        return sysParamService.getOneById(id);
    }

    @Override
    public SysParamBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return sysParamService.getOneById(id, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysParamDTO dto) {
        return sysParamService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysParamDTO dto) {
        sysParamService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysParamService.adminDelete(ids);
    }

    @Override
    public String getValueByName(String name) {
        return sysParamService.getParamValueByName(name);
    }

    @Override
    public String getValueByName(String name, String defaultValue) {
        return sysParamService.getParamValueByName(name, defaultValue);
    }
}
