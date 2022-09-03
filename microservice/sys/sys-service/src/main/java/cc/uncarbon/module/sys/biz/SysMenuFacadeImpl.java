package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.sys.facade.SysMenuFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysMenuDTO;
import cc.uncarbon.module.sys.model.response.SysMenuBO;
import cc.uncarbon.module.sys.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collection;
import java.util.List;

/**
 * 后台菜单Facade接口实现类
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
public class SysMenuFacadeImpl implements SysMenuFacade {

    private final SysMenuService sysMenuService;


    @Override
    public List<SysMenuBO> adminList() {
        return sysMenuService.adminList();
    }

    @Override
    public SysMenuBO getOneById(Long id) {
        return sysMenuService.getOneById(id);
    }

    @Override
    public SysMenuBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return sysMenuService.getOneById(id, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysMenuDTO dto) {
        return sysMenuService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysMenuDTO dto) {
        sysMenuService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysMenuService.adminDelete(ids);
    }

    @Override
    public List<SysMenuBO> adminListSideMenu() {
        return sysMenuService.adminListSideMenu();
    }

    @Override
    public List<SysMenuBO> adminListVisibleMenu() {
        return sysMenuService.adminListVisibleMenu();
    }
}
