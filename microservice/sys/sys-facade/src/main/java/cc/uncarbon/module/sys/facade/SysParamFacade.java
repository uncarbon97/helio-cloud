package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysParamDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysParamDTO;
import cc.uncarbon.module.sys.model.response.SysParamBO;

import java.util.List;

/**
 * 系统参数Facade接口
 */
public interface SysParamFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysParamBO> adminList(PageParam pageParam, AdminListSysParamDTO dto);

    /**
     * 通用-详情
     *
     * @deprecated 使用 getOneById(java.lang.Long, boolean) 替代
     */
    @Deprecated
    SysParamBO getOneById(Long entityId) throws BusinessException;

    /**
     * 后台管理-新增
     *
     * @return 主键ID
     */
    Long adminInsert(AdminInsertOrUpdateSysParamDTO dto);

    /**
     * 后台管理-编辑
     */
    void adminUpdate(AdminInsertOrUpdateSysParamDTO dto);

    /**
     * 后台管理-删除
     */
    void adminDelete(List<Long> ids);

    /**
     * 根据键名取值
     *
     * @param name 键名
     * @return 成功返回键值，失败返回null
     */
    String getValueByName(String name);

    /**
     * 根据键名取值
     *
     * @param name         键名
     * @param defaultValue 默认值
     * @return 成功返回键值，失败返回defaultValue
     */
    String getValueByName(String name, String defaultValue);
}
