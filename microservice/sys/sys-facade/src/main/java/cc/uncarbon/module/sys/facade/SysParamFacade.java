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
     */
    SysParamBO getOneById(Long entityId);

    /**
     * 后台管理-新增
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
     * @param key 键名
     * @return 成功返回值, 失败返回null
     */
    String getValueByKey(String key);

    /**
     * 根据键名取值
     * @param key 键名
     * @param defaultValue 默认值
     * @return 成功返回值, 失败返回defaultValue
     */
    String getValueByKey(String key, String defaultValue);
}
