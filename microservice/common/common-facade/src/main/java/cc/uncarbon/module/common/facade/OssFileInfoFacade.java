package cc.uncarbon.module.common.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.core.page.PageParam;
import cc.uncarbon.framework.core.page.PageResult;
import cc.uncarbon.module.common.model.request.AdminListOssFileInfoDTO;
import cc.uncarbon.module.common.model.response.OssFileInfoBO;

import java.util.Collection;

/**
 * 上传文件信息 Facade 接口
 *
 * @author Uncarbon
 */
public interface OssFileInfoFacade {

    /**
     * 后台管理-分页列表
     */
    PageResult<OssFileInfoBO> adminList(PageParam pageParam, AdminListOssFileInfoDTO dto);

    /**
     * 根据 ID 取详情
     *
     * @param id 主键ID
     * @return null or BO
     */
    OssFileInfoBO getOneById(Long id);

    /**
     * 根据 ID 取详情
     *
     * @param id               主键ID
     * @param throwIfInvalidId 是否在 ID 无效时抛出异常
     * @return null or BO
     */
    OssFileInfoBO getOneById(Long id, boolean throwIfInvalidId) throws BusinessException;

    /**
     * 后台管理-删除
     */
    void adminDelete(Collection<Long> ids);

    /**
     * 根据 MD5 取文件信息
     */
    OssFileInfoBO getOneByMd5(String md5);
}
