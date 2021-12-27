package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysDataDictFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDataDictDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysDataDictDTO;
import cc.uncarbon.module.sys.model.response.SysDataDictBO;
import cc.uncarbon.module.sys.service.SysDataDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;


/**
 * 数据字典Facade接口实现类
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
public class SysDataDictFacadeImpl implements SysDataDictFacade {

    @Resource
    private SysDataDictService sysDataDictService;


    @Override
    public PageResult<SysDataDictBO> adminList(PageParam pageParam, AdminListSysDataDictDTO dto) {
        return sysDataDictService.adminList(pageParam, dto);
    }

    @Override
    public SysDataDictBO getOneById(Long entityId) throws BusinessException {
        return sysDataDictService.getOneById(entityId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysDataDictDTO dto) {
        return sysDataDictService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysDataDictDTO dto) {
        sysDataDictService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(List<Long> ids) {
        sysDataDictService.adminDelete(ids);
    }

}
