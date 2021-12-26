package cc.uncarbon.module.sys.biz;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.context.UserContextHolder;
import cc.uncarbon.module.sys.constant.SysConstant;
import cc.uncarbon.module.sys.facade.SysMenuFacade;
import cc.uncarbon.module.sys.model.request.AdminInsertOrUpdateSysMenuDTO;
import cc.uncarbon.module.sys.model.request.AdminListSysMenuDTO;
import cc.uncarbon.module.sys.model.response.SysMenuBO;
import cc.uncarbon.module.sys.service.SysMenuService;
import cc.uncarbon.module.sys.service.SysParamService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 后台菜单Facade接口实现类
 * @author Uncarbon
 */
@Slf4j
@DubboService(
        version = HelioConstant.Version.DUBBO_VERSION_V1,
        validation = HelioConstant.Dubbo.ENABLE_VALIDATION,
        timeout = HelioConstant.Dubbo.TIMEOUT,
        retries = HelioConstant.Dubbo.RETRIES
)
public class SysMenuFacadeImpl implements SysMenuFacade {

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysParamService sysParamService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<SysMenuBO> adminList(AdminListSysMenuDTO dto) {
        return sysMenuService.adminList(dto);
    }

    @Override
    public SysMenuBO getOneById(Long entityId) {
        return sysMenuService.getOneById(entityId);
    }

    @Override
    public Long adminInsert(AdminInsertOrUpdateSysMenuDTO dto) {
        Long id = sysMenuService.adminInsert(dto);
        this.cleanMenuCacheInRedis();

        return id;
    }

    @Override
    public void adminUpdate(AdminInsertOrUpdateSysMenuDTO dto) {
        sysMenuService.adminUpdate(dto);
        this.cleanMenuCacheInRedis();
    }

    @Override
    public void adminDelete(List<Long> ids) {
        sysMenuService.adminDelete(ids);
        this.cleanMenuCacheInRedis();
    }

    @Override
    public List<SysMenuBO> adminListSideMenu() {
        String redisKey = String.format(SysConstant.REDIS_KEY_SIDE_MENU_BY_USERID, UserContextHolder.getUserContext().getUserId());
        Object redisValue = stringRedisTemplate.opsForValue().get(redisKey);

        if (redisValue == null) {
            redisValue = sysMenuService.adminListSideMenu();
            // 记录到缓存
            String sysMenuCacheDuration = sysParamService.getParamValueByName(SysConstant.PARAM_KEY_CACHE_MENU_DURATION, "30");
            stringRedisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(redisValue), Integer.parseInt(sysMenuCacheDuration), TimeUnit.MINUTES);
        } else {
            redisValue = JSONUtil.parse(redisValue).toBean(ArrayList.class);
        }

        return (List<SysMenuBO>) redisValue;
    }

    @Override
    public List<SysMenuBO> adminListVisibleMenu() {
        String redisKey = String.format(SysConstant.REDIS_KEY_VISIBLE_MENU_BY_USERID, UserContextHolder.getUserContext().getUserId());
        Object redisValue = stringRedisTemplate.opsForValue().get(redisKey);

        if (redisValue == null) {
            redisValue = sysMenuService.adminListVisibleMenu();
            // 记录到缓存
            String sysMenuCacheDuration = sysParamService.getParamValueByName(SysConstant.PARAM_KEY_CACHE_MENU_DURATION, "30");
            stringRedisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(redisValue), Integer.parseInt(sysMenuCacheDuration), TimeUnit.MINUTES);
        } else {
            redisValue = JSONUtil.parse(redisValue).toBean(ArrayList.class);
        }

        return (List<SysMenuBO>) redisValue;
    }

    @Override
    public void cleanMenuCacheInRedis() {
       this.cleanMenuCacheInRedis(null);
    }

    @Override
    public void cleanMenuCacheInRedis(Long userId) {
        String redisKey;
        if (ObjectUtil.isNull(userId)) {
            redisKey = "*";
        } else {
            redisKey = "userId_" + StrUtil.toString(userId);
        }

        log.info("[清除Redis中相关菜单缓存] >> redisKey={}", redisKey);

        Set<String> keys = stringRedisTemplate.keys(SysConstant.REDIS_KEY_SIDE_MENU + redisKey);
        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
        }

        keys = stringRedisTemplate.keys(SysConstant.REDIS_KEY_VISIBLE_MENU + redisKey);
        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
        }
    }
}
