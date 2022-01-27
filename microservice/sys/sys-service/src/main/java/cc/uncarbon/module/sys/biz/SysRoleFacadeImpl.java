package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.constant.SysConstant;
import cc.uncarbon.module.sys.facade.SysRoleFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysRoleDTO;
import cc.uncarbon.module.sys.model.response.SysRoleBO;
import cc.uncarbon.module.sys.service.SysRoleService;
import cc.uncarbon.module.sys.service.SysUserRoleRelationService;
import cn.hutool.core.collection.CollUtil;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 后台角色Facade接口实现类
 * @author Uncarbon
 */
@Slf4j
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
public class SysRoleFacadeImpl implements SysRoleFacade {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserRoleRelationService sysUserRoleRelationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public PageResult<SysRoleBO> adminList(PageParam pageParam, AdminListSysRoleDTO dto) {
        return sysRoleService.adminList(pageParam, dto);
    }

    @Override
    public SysRoleBO getOneById(Long entityId) throws BusinessException {
        return this.getOneById(entityId, true);
    }

    @Override
    public SysRoleBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException {
        return sysRoleService.getOneById(entityId, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysRoleDTO dto) {
        return sysRoleService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysRoleDTO dto) {
        sysRoleService.adminUpdate(dto);

        /*
        清除该角色下所有用户, 菜单相关的缓存
         */
        List<Long> userIds = sysUserRoleRelationService.listUserIdByRoleIds(CollUtil.newArrayList(dto.getId()));
        userIds.forEach(
                userId -> {
                    String redisKey;

                    redisKey = String.format(SysConstant.REDIS_KEY_SIDE_MENU_BY_USERID, userId);
                    stringRedisTemplate.delete(redisKey);

                    redisKey = String.format(SysConstant.REDIS_KEY_VISIBLE_MENU_BY_USERID, userId);
                    stringRedisTemplate.delete(redisKey);
                }
        );
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysRoleService.adminDelete(ids);
    }

}
