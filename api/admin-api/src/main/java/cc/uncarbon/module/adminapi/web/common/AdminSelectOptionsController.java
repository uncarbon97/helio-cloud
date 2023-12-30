package cc.uncarbon.module.adminapi.web.common;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "后台管理-下拉框数据源接口", tags = {"后台管理-下拉框数据源接口"})
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminSelectOptionsController {

    /*
    这里集中存放所有用于后台管理的下拉框数据源接口
    避免多人协作时，不知道原来是否已经有了，或者写在某个边边角角里
    造成重复开发
     */

}
