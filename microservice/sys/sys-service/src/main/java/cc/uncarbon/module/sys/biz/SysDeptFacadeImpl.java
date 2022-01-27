package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.sys.facade.SysDeptFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysDeptDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysDeptDTO;
import cc.uncarbon.module.sys.model.response.SysDeptBO;
import cc.uncarbon.module.sys.service.SysDeptService;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 部门Facade接口实现类
 * @author Uncarbon
 */
@Slf4j
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
public class SysDeptFacadeImpl implements SysDeptFacade {

    @Resource
    private SysDeptService sysDeptService;


    @Override
    public List<SysDeptBO> adminList(AdminListSysDeptDTO dto) {
        return sysDeptService.adminList(dto);
    }

    @Override
    public SysDeptBO getOneById(Long entityId) throws BusinessException {
        return this.getOneById(entityId, true);
    }

    @Override
    public SysDeptBO getOneById(Long entityId, boolean throwIfInvalidId) throws BusinessException {
        return sysDeptService.getOneById(entityId, throwIfInvalidId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysDeptDTO dto) {
        return sysDeptService.adminInsert(dto);
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysDeptDTO dto) {
        sysDeptService.adminUpdate(dto);
    }

    @Override
    public void adminDelete(Collection<Long> ids) {
        sysDeptService.adminDelete(ids);
    }

}
