package cc.uncarbon.module.web.oss;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.framework.web.model.response.ApiResult;
import cc.uncarbon.module.common.facade.OssUploadDownloadFacade;
import cc.uncarbon.module.oss.enums.OssErrorEnum;
import cc.uncarbon.module.oss.model.request.UploadFileAttributeDTO;
import cc.uncarbon.module.oss.model.response.OssFileDownloadReplyBO;
import cc.uncarbon.module.oss.model.response.OssFileInfoBO;
import cc.uncarbon.module.oss.model.response.OssFileUploadResultVO;
import cc.uncarbon.module.oss.service.OssFileInfoService;
import cc.uncarbon.module.oss.util.URLUtil;
import cc.uncarbon.module.util.AdminStpUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import cn.xuyanwu.spring.file.storage.exception.FileStorageRuntimeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * 后台管理专用 - 上传、下载文件接口
 *
 * @author Uncarbon
 */
//                 👇 后台管理对应的鉴权工具类
@SaCheckLogin(type = AdminStpUtil.TYPE)
@RequiredArgsConstructor
@Slf4j
@Api(value = "【后台管理】上传、下载文件接口", tags = {"【后台管理】上传、下载文件接口"})
@RequestMapping(HelioConstant.Version.HTTP_API_VERSION_V1 + "/oss/files")
@RestController
public class AdminOssUploadDownloadController {

    private final OssUploadDownloadFacade ossUploadDownloadFacade;


    @ApiOperation(value = "上传文件", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping
    public ApiResult<OssFileUploadResultVO> upload(
            @RequestPart MultipartFile file, @RequestPart(required = false) @Valid UploadFileAttributeDTO attr,
            HttpServletRequest request
    ) throws IOException {
         /*

         1. 已存在相同 MD5 文件，直接返回 URL
         */
        String md5 = DigestUtil.md5Hex(file.getBytes());
        OssFileInfoBO bo = ossUploadDownloadFacade.findByHash(md5);
        if (bo == null) {

            /*
            2. 普通上传
             */
            attr = ObjectUtil.defaultIfNull(attr, UploadFileAttributeDTO::new);
            attr
                    .setOriginalFilename(file.getOriginalFilename())
                    .setContentType(file.getContentType())
                    .setMd5(md5)
            ;
            bo = ossUploadDownloadFacade.upload(file.getBytes(), attr);
        }

        return ApiResult.data(this.toUploadResult(bo, request.getRequestURL().toString()));
    }

    @ApiOperation(value = "下载文件(根据文件ID)", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @GetMapping(value = "/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        OssFileDownloadReplyBO reply;
        try {
            reply = ossUploadDownloadFacade.downloadById(id);
        } catch (FileStorageRuntimeException fsre) {
            throw new BusinessException(OssErrorEnum.FILE_STORAGE_RUNTIME_ERROR);
        }

        if (reply.isRedirect2DirectUrl()) {
            // 302重定向
            response.sendRedirect(reply.getDirectUrl());
            return;
        }

        response.setHeader(Header.CONTENT_TYPE.getValue(), MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String downFileName = URLEncoder.encode(reply.getStorageFilename(), CharsetUtil.UTF_8);
        response.setHeader(Header.CONTENT_DISPOSITION.getValue(), "attachment;filename=" + downFileName);

        IoUtil.write(response.getOutputStream(), false, reply.getFileBytes());
    }

    /**
     * 将 OssFileInfoBO 转换为 OssFileUploadResultVO
     */
    private OssFileUploadResultVO toUploadResult(OssFileInfoBO ossFileInfo, String requestUrl) {
        OssFileUploadResultVO ret = new OssFileUploadResultVO();
        ret
                .setFileId(ossFileInfo.getId())
                .setFilename(ossFileInfo.getStorageFilenameFull());

        /*
        这里请根据实际业务性质调整
        有的业务出于安全目的，上传后文件只能通过文件ID才能下载
        有的业务没有限制，上传后文件完全可以直接通过对象存储直链下载
        但本地存储又没有直链，只能通过文件ID；
        默认地，此处按【本地存储or对象存储直链为空：通过文件ID下载；对象存储：通过对象存储直链下载】返回 url
         */
        if (
                // TODO 能否访问到
                OssFileInfoService.isLocalPlatform(ossFileInfo.getStoragePlatform())
                        || StrUtil.isEmpty(ossFileInfo.getDirectUrl())
        ) {
            ret.setUrl(
                    // TODO 优化拼接
                    // domain
                    URLUtil.getFullDomain(requestUrl)
                            // 拼接出相对路径
                            + URLUtil.getPath(requestUrl)
                            + "/" + ossFileInfo.getId()
            );
        } else {
            ret.setUrl(ossFileInfo.getDirectUrl());
        }

        return ret;
    }
}
