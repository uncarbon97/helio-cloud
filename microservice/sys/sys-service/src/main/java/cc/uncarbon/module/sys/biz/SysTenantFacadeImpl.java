package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.constant.SysConstant;
import cc.uncarbon.module.sys.entity.SysTenantEntity;
import cc.uncarbon.module.sys.facade.SysTenantFacade;
import cc.uncarbon.module.sys.model.request.*;
import cc.uncarbon.module.sys.model.response.SysTenantBO;
import cc.uncarbon.module.sys.service.SysRoleService;
import cc.uncarbon.module.sys.service.SysTenantService;
import cc.uncarbon.module.sys.service.SysUserRoleRelationService;
import cc.uncarbon.module.sys.service.SysUserService;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统租户Facade接口实现类
 */
@RequiredArgsConstructor
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
@Slf4j
public class SysTenantFacadeImpl implements SysTenantFacade {

    private final SysTenantService sysTenantService;
    private final SysRoleService sysRoleService;
    private final SysUserRoleRelationService sysUserRoleRelationService;
    private final SysUserService sysUserService;


    @Override
    public PageResult<SysTenantBO> adminList(PageParam pageParam, AdminListSysTenantDTO dto) {
        return sysTenantService.adminList(pageParam, dto);
    }

    @Override
    public SysTenantBO getOneById(Long id) {
        return sysTenantService.getOneById(id);
    }

    @Override
    public SysTenantBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException {
        return sysTenantService.getOneById(id, throwIfInvalidId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long adminInsert(AdminInsertSysTenantDTO dto) {
        log.info("[后台管理-新增系统租户] >> 入参={}", dto);
        sysTenantService.checkExistence(dto);

        /*
        1. 加入一个新租户(tenant)
        这里是直接顺带创建管理员账号了, 你可以根据业务需要决定是否创建
         */
        SysTenantEntity entity = sysTenantService.adminInsert(dto);

        Long newTenantEntityId = entity.getId();
        Long newTenantId = entity.getTenantId();

        /*
        2. 创建一个新角色(role)
        注意: 这里并没有指派其可见菜单，需要用超管账号授权
         */
        Long newRoleId = sysRoleService.adminInsert(
                AdminInsertOrUpdateSysRoleDTO.builder()
                        .tenantId(newTenantId)
                        .title(dto.getTenantName() + "管理员")
                        .value(SysConstant.TENANT_ADMIN_ROLE_VALUE)
                        .build()
        );

        // 3. 创建一个新用户
        Long newUserId = sysUserService.adminInsert(
                AdminInsertOrUpdateSysUserDTO.builder()
                        .tenantId(newTenantId)
                        .username(dto.getTenantAdminUsername())
                        .passwordOfNewUser(dto.getTenantAdminPassword())
                        .nickname(dto.getTenantName() + "管理员")
                        .email(dto.getTenantAdminEmail())
                        .phoneNo(dto.getTenantAdminPhoneNo())
                        .build()
        );

        // 4. 将新用户绑定至新角色上
        sysUserRoleRelationService.adminInsert(newTenantId, newUserId, newRoleId);

        // 5. 把管理员账号更新进库
        SysTenantEntity update = new SysTenantEntity().setTenantAdminUserId(newUserId);
        update.setId(newTenantEntityId);
        sysTenantService.adminUpdate(update);
        return newTenantId;
    }

    @Override
    public void adminUpdate(AdminUpdateSysTenantDTO dto) {
        sysTenantService.adminUpdate(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void adminDelete(Collection<Long> ids) {
        // 确定租户IDs
        List<SysTenantBO> sysTenantInfos = sysTenantService.listByIds(ids, false);
        if (CollUtil.isEmpty(sysTenantInfos)) {
            return;
        }
        Set<Long> tenantIds = sysTenantInfos.stream().map(SysTenantBO::getTenantId).collect(Collectors.toSet());

        // 删除租户管理员角色、租户
        sysRoleService.adminDeleteTenantRoles(tenantIds, Collections.singleton(SysConstant.TENANT_ADMIN_ROLE_VALUE));
        sysTenantService.adminDelete(ids);
    }

}
