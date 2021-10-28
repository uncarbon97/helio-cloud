package cc.uncarbon.module.sys.facade;

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
     */
    SysLogBO getOneById(Long entityId);

}
