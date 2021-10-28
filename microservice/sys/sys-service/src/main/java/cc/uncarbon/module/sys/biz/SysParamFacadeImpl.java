package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysParamFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysParamDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysParamDTO;
import cc.uncarbon.module.sys.model.response.SysParamBO;
import cc.uncarbon.module.sys.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

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
public class SysParamFacadeImpl implements SysParamFacade {

    @Resource
    private SysParamService sysParamService;


    @Override
    public PageResult<SysParamBO> adminList(PageParam pageParam, AdminListSysParamDTO dto) {
        return sysParamService.adminList(pageParam, dto);
    }

    @Override
    public SysParamBO getOneById(Long entityId) {
        return sysParamService.getOneById(entityId);
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
    public void adminDelete(List<Long> ids) {
        sysParamService.adminDelete(ids);
    }

    @Override
    public String getValueByKey(String key) {
        return sysParamService.getParamValueByKey(key);
    }

    @Override
    public String getValueByKey(String key, String defaultValue) {
        return sysParamService.getParamValueByKey(key, defaultValue);
    }
}
