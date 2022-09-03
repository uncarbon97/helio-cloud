package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.facade.SysUserFacade;
import cc.uncarbon.module.sys.model.request.*;
import cc.uncarbon.module.sys.model.response.SysUserBO;
import cc.uncarbon.module.sys.model.response.SysUserLoginBO;
import cc.uncarbon.module.sys.model.response.VbenAdminUserInfoVO;
import cc.uncarbon.module.sys.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collection;
import java.util.Set;

/**
 * 后台用户Facade接口实现类
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
public class SysUserFacadeImpl implements SysUserFacade {

    private final SysUserService sysUserService;


    @Override
    public PageResult<SysUserBO> adminList(PageParam pageParam, AdminListSysUserDTO dto) {
        return sysUserService.adminList(pageParam, dto);
    }

    @Override
    public SysUserBO getOneById(Long id) {
        return sysUserService.getOneById(id);
    }

    @Override
    public SysUserBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return sysUserService.getOneById(id, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysUserDTO dto) {
        return sysUserService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysUserDTO dto) {
        sysUserService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysUserService.adminDelete(ids);
    }

    @Override
    public SysUserLoginBO adminLogin(SysUserLoginDTO dto) {
        return sysUserService.adminLogin(dto);
    }

    @Override
    public VbenAdminUserInfoVO adminGetCurrentUserInfo() {
        return sysUserService.adminGetCurrentUserInfo();
    }

    @Override
    public void adminResetUserPassword(AdminResetSysUserPasswordDTO dto) {
        sysUserService.adminResetUserPassword(dto);
    }

    @Override
    public void adminUpdateCurrentUserPassword(AdminUpdateCurrentSysUserPasswordDTO dto) {
        sysUserService.adminUpdateCurrentUserPassword(dto);
    }

    @Override
    public void adminBindRoles(AdminBindUserRoleRelationDTO dto) {
        sysUserService.adminBindRoles(dto);
    }

    @Override
    public Set<Long> listRelatedRoleIds(Long userId) {
        return sysUserService.listRelatedRoleIds(userId);
    }
}
