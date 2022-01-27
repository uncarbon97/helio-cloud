package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysRoleDTO;
import cc.uncarbon.module.sys.model.response.SysRoleBO;
import java.util.Collection;

/**
 * 后台角色Facade接口
 */
public interface SysRoleFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysRoleBO> adminList(PageParam pageParam, AdminListSysRoleDTO dto);

    /**
     * 通用-详情
     *
     * @deprecated 使用 getOneById(java.lang.Long, boolean) 替代
     */
    @Deprecated
    SysRoleBO getOneById(Long entityId) throws BusinessException;

    /**
     * 通用-详情
     *
     * @param entityId 实体类主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysRoleBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException;

    /**
     * 后台管理-新增
     * @return 主键ID
     */
    Long adminInsert(AdminInsertOrUpdateSysRoleDTO dto);

    /**
     * 后台管理-编辑
     */
    void adminUpdate(AdminInsertOrUpdateSysRoleDTO dto);

    /**
     * 后台管理-删除
     * @param ids 主键IDs
     */
    void adminDelete(Collection<Long> ids);

}
