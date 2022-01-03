package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDataDictDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysDataDictDTO;
import cc.uncarbon.module.sys.model.response.SysDataDictBO;

import java.util.List;


/**
 * 数据字典Facade接口
 *
 * @author Uncarbon
 */
public interface SysDataDictFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysDataDictBO> adminList(PageParam pageParam, AdminListSysDataDictDTO dto);

    /**
     * 通用-详情
     *
     * @deprecated 使用 getOneById(java.lang.Long, boolean) 替代
     */
    @Deprecated
    SysDataDictBO getOneById(Long entityId) throws BusinessException;

    /**
     * 通用-详情
     *
     * @param entityId 实体类主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysDataDictBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException;

    /**
     * 后台管理-添加
     *
     * @return 主键ID
     */
    Long adminInsert(AdminInsertOrUpdateSysDataDictDTO dto);

    /**
     * 后台管理-编辑
     */
    void adminUpdate(AdminInsertOrUpdateSysDataDictDTO dto);

    /**
     * 后台管理-删除
     */
    void adminDelete(List<Long> ids);

}
