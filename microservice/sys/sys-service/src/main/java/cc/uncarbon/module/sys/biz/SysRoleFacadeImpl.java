package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysRoleFacade;
import cc.uncarbon.module.sys.model.request.AdminBindRoleMenuRelationDTO;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysRoleDTO;
import cc.uncarbon.module.sys.model.response.SysRoleBO;
import cc.uncarbon.module.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 后台角色Facade接口实现类
 */
@RequiredArgsConstructor
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
@Slf4j
public class SysRoleFacadeImpl implements SysRoleFacade {

    private final SysRoleService sysRoleService;


    @Override
    public PageResult<SysRoleBO> adminList(PageParam pageParam, AdminListSysRoleDTO dto) {
        return sysRoleService.adminList(pageParam, dto);
    }

    @Override
    public SysRoleBO getOneById(Long id) {
        return sysRoleService.getOneById(id);
    }

    @Override
    public SysRoleBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return sysRoleService.getOneById(id, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysRoleDTO dto) {
        return sysRoleService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysRoleDTO dto) {
        sysRoleService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysRoleService.adminDelete(ids);
    }

    @Override
    public Set<String> adminBindMenus(AdminBindRoleMenuRelationDTO dto) {
        return sysRoleService.adminBindMenus(dto);
    }

    @Override
    public List<SysRoleBO> adminSelectOptions() {
        return sysRoleService.adminSelectOptions();
    }
}
