package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertSysLogDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysLogDTO;
import cc.uncarbon.module.sys.model.response.SysLogBO;

/**
 * 系统日志Facade接口
 */
public interface SysLogFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysLogBO> adminList(PageParam pageParam, AdminListSysLogDTO dto);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @return null or BO
     */
    SysLogBO getOneById(Long id);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysLogBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException;

    /**
     * 后台管理-新增
     * 主要用于RPC新增操作日志
     * @return 主键ID
     */
    Long adminInsert(AdminInsertSysLogDTO dto);

}
