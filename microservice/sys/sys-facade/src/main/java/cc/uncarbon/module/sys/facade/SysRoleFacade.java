package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysRoleDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysRoleDTO;
import cc.uncarbon.module.sys.model.response.SysRoleBO;

import java.util.List;

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
     */
    SysRoleBO getOneById(Long entityId);

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
     */
    void adminDelete(List<Long> ids);

}
