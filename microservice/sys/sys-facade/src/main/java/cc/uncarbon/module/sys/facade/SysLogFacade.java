package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminListSysLogDTO;
import cc.uncarbon.module.sys.model.response.SysLogBO;

/**
 * 后台操作日志Facade接口
 */
public interface SysLogFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysLogBO> adminList(PageParam pageParam, AdminListSysLogDTO dto);

    /**
     * 通用-详情
     *
     * @deprecated 使用 getOneById(java.lang.Long, boolean) 替代
     */
    @Deprecated
    SysLogBO getOneById(Long entityId) throws BusinessException;

    /**
     * 通用-详情
     *
     * @param entityId 实体类主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return BO
     */
    SysLogBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException;

}
