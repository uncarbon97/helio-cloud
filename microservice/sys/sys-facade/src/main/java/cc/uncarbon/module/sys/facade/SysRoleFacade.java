package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminBindRoleMenuRelationDTO;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysRoleDTO;
import cc.uncarbon.module.sys.model.response.SysRoleBO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 后台角色Facade接口
 */
public interface SysRoleFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysRoleBO> adminList(PageParam pageParam, AdminListSysRoleDTO dto);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @return null or BO
     */
    SysRoleBO getOneById(Long id);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysRoleBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException;

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

    /**
     * 后台管理-绑定角色与菜单关联关系
     *
     * @return 新菜单ID集合对应的权限名
     */
    Set<String> adminBindMenus(AdminBindRoleMenuRelationDTO dto);

    /**
     * 后台管理-下拉框数据
     */
    List<SysRoleBO> adminSelectOptions();

}
