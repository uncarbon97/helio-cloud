package cc.uncarbon.module.api.unittest;

import cc.uncarbon.framework.redis.lock.RedisDistributedLock;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Uncarbon
 * @date 2021-07-06
 */
@Slf4j
@RequestMapping(value = "/unittest/redisDistributedLock")
@RestController
public class RedisDistributedLockUnitTest {

    @Resource
    private RedisDistributedLock redisDistributedLock;


    @GetMapping
    @SneakyThrows
    public ApiResult<?> case1() {
        try {
            // 这里设置锁最多持有3秒，应大于业务实际执行时长
            redisDistributedLock.lock("lock1", 3);
            log.info("业务操作");
        } catch (Exception e) {
            log.error("这里自行处理异常");
        } finally {
            redisDistributedLock.unlockSafely("lock1");
        }

        return ApiResult.success(DateUtil.now() + "业务完成");
    }
}
