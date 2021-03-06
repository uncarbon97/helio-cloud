package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.context.UserContextHolder;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.annotation.SysLog;
import cc.uncarbon.module.sys.entity.SysTenantEntity;
import cc.uncarbon.module.sys.entity.SysUserRoleRelationEntity;
import cc.uncarbon.module.sys.facade.SysTenantFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysUserDTO;
import cc.uncarbon.module.sys.model.request.AdminInsertSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminUpdateSysTenantDTO;
import cc.uncarbon.module.sys.model.response.SysTenantBO;
import cc.uncarbon.module.sys.service.SysRoleService;
import cc.uncarbon.module.sys.service.SysTenantService;
import cc.uncarbon.module.sys.service.SysUserRoleRelationService;
import cc.uncarbon.module.sys.service.SysUserService;
import cn.hutool.core.bean.BeanUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

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
@RequiredArgsConstructor
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
    @SysLog(value = "新增系统租户")
    @Transactional(rollbackFor = Exception.class)
    public Long adminInsert(AdminInsertSysTenantDTO dto) {
        log.info("[后台管理-新增系统租户] >> 入参={}", dto);
        sysTenantService.checkExistence(dto);

        /*
        1. 加入一个新租户(tenant)
        这里是直接顺带创建管理员账号了, 你可以根据业务需要决定是否创建
         */

        dto.setId(null);
        SysTenantEntity entity = new SysTenantEntity();
        BeanUtil.copyProperties(dto, entity);

        sysTenantService.save(entity);

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
                        .value("Admin")
                        .build()
        );

        /*
        3. 创建一个新用户
         */
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

        /*
        4. 将新用户绑定至新角色上
         */
        sysUserRoleRelationService.save(
                SysUserRoleRelationEntity.builder()
                        .tenantId(newTenantId)
                        .userId(newUserId)
                        .roleId(newRoleId)
                        // 可能不会自动填充字段，手动补上
                        .createdAt(LocalDateTime.now())
                        .createdBy(UserContextHolder.getUserContext().getUserName())
                        .updatedAt(LocalDateTime.now())
                        .updatedBy(UserContextHolder.getUserContext().getUserName())
                        .build()
        );

        entity = new SysTenantEntity();
        entity
                .setTenantAdminUserId(newUserId)
                .setId(newTenantEntityId);

        /*
        5. 把管理员账号更新进库
         */
        sysTenantService.updateById(entity);

        return newTenantId;
    }

    @Override
    public void adminUpdate(AdminUpdateSysTenantDTO dto) {
        sysTenantService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysTenantService.adminDelete(ids);
    }

}
