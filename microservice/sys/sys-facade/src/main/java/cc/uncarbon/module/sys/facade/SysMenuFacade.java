package cc.uncarbon.module.sys.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysMenuDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysMenuDTO;
import cc.uncarbon.module.sys.model.response.SysMenuBO;

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
     * 通用-详情
     *
     * @deprecated 使用 getOneById(java.lang.Long, boolean) 替代
     */
    @Deprecated
    SysMenuBO getOneById(Long entityId) throws BusinessException;

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
     */
    void adminDelete(List<Long> ids);

    /**
     * 后台管理-取当前账号可见侧边菜单
     */
    List<SysMenuBO> adminListSideMenu();

    /**
     * 后台管理-取当前账号所有可见菜单(包括按钮类型)
     */
    List<SysMenuBO> adminListVisibleMenu();

    /**
     * 通用-清除Redis中所有菜单缓存
     */
    void cleanMenuCacheInRedis();

    /**
     * 通用-根据用户ID，清除Redis中相关菜单缓存
     * @param userId 用户ID；为null则清除所有
     */
    void cleanMenuCacheInRedis(Long userId);

}
