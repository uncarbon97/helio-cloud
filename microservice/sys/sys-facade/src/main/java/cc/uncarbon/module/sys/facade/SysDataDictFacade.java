package cc.uncarbon.module.sys.facade;

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
     */
    SysDataDictBO getOneById(Long entityId);

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
