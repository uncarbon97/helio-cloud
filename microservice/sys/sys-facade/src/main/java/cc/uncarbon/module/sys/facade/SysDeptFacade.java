package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDeptDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysDeptDTO;
import cc.uncarbon.module.sys.model.response.SysDeptBO;
import java.util.Collection;
import java.util.List;

/**
 * 部门Facade接口
 */
public interface SysDeptFacade {

    /**
     * 后台管理-列表
     */
    List<SysDeptBO> adminList(AdminListSysDeptDTO dto);

    /**
     * 通用-详情
     *
     * @deprecated 使用 getOneById(java.lang.Long, boolean) 替代
     */
    @Deprecated
    SysDeptBO getOneById(Long entityId) throws BusinessException;

    /**
     * 通用-详情
     *
     * @param entityId 实体类主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysDeptBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException;

    /**
     * 后台管理-新增
     * @return 主键ID
     */
    Long adminInsert(AdminInsertOrUpdateSysDeptDTO dto);

    /**
     * 后台管理-编辑
     */
    void adminUpdate(AdminInsertOrUpdateSysDeptDTO dto);

    /**
     * 后台管理-删除
     * @param ids 主键IDs
     */
    void adminDelete(Collection<Long> ids);

}
