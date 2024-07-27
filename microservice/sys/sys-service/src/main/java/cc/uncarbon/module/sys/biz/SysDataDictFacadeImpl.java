package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysDataDictFacade;
import cc.uncarbon.module.sys.model.request.AdminSysDataDictClassifiedInsertOrUpdateDTO;
import cc.uncarbon.module.sys.model.request.AdminSysDataDictClassifiedListDTO;
import cc.uncarbon.module.sys.model.request.AdminSysDataDictItemInsertOrUpdateDTO;
import cc.uncarbon.module.sys.model.request.AdminSysDataDictItemListDTO;
import cc.uncarbon.module.sys.model.response.SysDataDictClassifiedBO;
import cc.uncarbon.module.sys.model.response.SysDataDictItemBO;
import cc.uncarbon.module.sys.service.SysDataDictService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collection;
import java.util.List;


/**
 * 数据字典Facade接口实现类
 */
@RequiredArgsConstructor
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
@Slf4j
public class SysDataDictFacadeImpl implements SysDataDictFacade {

    private final SysDataDictService sysDataDictService;


    @Override
    public PageResult<SysDataDictClassifiedBO> adminListClassified(PageParam pageParam, AdminSysDataDictClassifiedListDTO dto) {
        return sysDataDictService.adminListClassified(pageParam, dto);
    }

    @Override
    public Long adminInsertClassified(AdminSysDataDictClassifiedInsertOrUpdateDTO dto) {
        return sysDataDictService.adminInsertClassified(dto);
    }

    @Override
    public void adminUpdateClassified(AdminSysDataDictClassifiedInsertOrUpdateDTO dto) {
        sysDataDictService.adminUpdateClassified(dto);
    }

    @Override
    public void adminDeleteClassified(Collection<Long> ids) {
        sysDataDictService.adminDeleteClassified(ids);
    }

    @Override
    public PageResult<SysDataDictItemBO> adminListItem(PageParam pageParam, AdminSysDataDictItemListDTO dto) {
        return sysDataDictService.adminListItem(pageParam, dto);
    }

    @Override
    public Long adminInsertItem(AdminSysDataDictItemInsertOrUpdateDTO dto) {
        return sysDataDictService.adminInsertItem(dto);
    }

    @Override
    public void adminUpdateItem(AdminSysDataDictItemInsertOrUpdateDTO dto) {
        sysDataDictService.adminUpdateItem(dto);
    }

    @Override
    public void adminDeleteItem(Collection<Long> ids, Long classifiedId) {
        sysDataDictService.adminDeleteItem(ids, classifiedId);
    }

    @Override
    public List<SysDataDictItemBO> listEnabledItemsByClassifiedCode(@Nonnull String classifiedCode) {
        return sysDataDictService.listEnabledItemsByClassifiedCode(classifiedCode);
    }
}
