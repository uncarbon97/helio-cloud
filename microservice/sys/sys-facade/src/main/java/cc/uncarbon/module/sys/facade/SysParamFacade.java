package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysParamDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysParamDTO;
import cc.uncarbon.module.sys.model.response.SysParamBO;

import java.util.Collection;

/**
 * 系统参数Facade接口
 */
public interface SysParamFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<SysParamBO> adminList(PageParam pageParam, AdminListSysParamDTO dto);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @return null or BO
     */
    SysParamBO getOneById(Long id);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysParamBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException;

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
     * @param ids 主键IDs
     */
    void adminDelete(Collection<Long> ids);

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
