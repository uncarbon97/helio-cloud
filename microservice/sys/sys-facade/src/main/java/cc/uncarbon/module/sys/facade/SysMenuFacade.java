package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysMenuDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysMenuDTO;
import cc.uncarbon.module.sys.model.response.SysMenuBO;

import java.util.Collection;
import java.util.List;

/**
 * 后台菜单Facade接口
 */
public interface SysMenuFacade {

    /**
     * 后台管理-列表
     */
    List<SysMenuBO> adminList(AdminListSysMenuDTO dto);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @return null or BO
     */
    SysMenuBO getOneById(Long id);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    SysMenuBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException;

    /**
     * 后台管理-新增
     * @return 主键ID
     */
    Long adminInsert(AdminInsertOrUpdateSysMenuDTO dto);

    /**
     * 后台管理-编辑
     */
    void adminUpdate(AdminInsertOrUpdateSysMenuDTO dto);

    /**
     * 后台管理-删除
     * @param ids 主键IDs
     */
    void adminDelete(Collection<Long> ids);

    /**
     * 后台管理-取当前账号可见侧边菜单
     */
    List<SysMenuBO> adminListSideMenu();

    /**
     * 后台管理-取当前账号所有可见菜单(包括按钮类型)
     */
    List<SysMenuBO> adminListVisibleMenu();

}
