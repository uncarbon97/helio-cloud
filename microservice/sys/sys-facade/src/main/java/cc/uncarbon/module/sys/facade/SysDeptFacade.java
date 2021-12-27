package cc.uncarbon.module.sys.facade;

import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDeptDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysDeptDTO;
import cc.uncarbon.module.sys.model.response.SysDeptBO;

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
     */
    void adminDelete(List<Long> ids);

}
