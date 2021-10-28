package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysTenantDTO;
import cc.uncarbon.module.sys.model.request.AdminUpdateSysTenantDTO;
import cc.uncarbon.module.sys.model.response.SysTenantBO;

import java.util.List;

/**
 * 系统租户Facade接口
 */
public interface SysTenantFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysTenantBO> adminList(PageParam pageParam, AdminListSysTenantDTO dto);

    /**
     * 通用-详情
     */
    SysTenantBO getOneById(Long entityId);

    /**
     * 后台管理-新增
     * @return 主键ID
     */
    Long adminInsert(AdminInsertSysTenantDTO dto);

    /**
     * 后台管理-编辑
     */
    void adminUpdate(AdminUpdateSysTenantDTO dto);

    /**
     * 后台管理-删除
     */
    void adminDelete(List<Long> ids);

}
