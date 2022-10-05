package cc.uncarbon.module.common.biz;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.common.enums.OssErrorEnum;
import cc.uncarbon.module.common.facade.OssUploadDownloadFacade;
import cc.uncarbon.module.common.model.request.UploadFileAttributeDTO;
import cc.uncarbon.module.common.model.response.OssFileDownloadReplyBO;
import cc.uncarbon.module.common.model.response.OssFileInfoBO;
import cc.uncarbon.module.common.model.response.OssFileUploadResultVO;
import cc.uncarbon.module.common.service.OssFileInfoService;
import cn.hutool.core.util.StrUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import cn.xuyanwu.spring.file.storage.exception.FileStorageRuntimeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 文件上传下载门面
 *
 * @author Uncarbon
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OssUploadDownloadFacadeImpl implements OssUploadDownloadFacade {

    private final OssFileInfoService ossFileInfoService;
    private final FileStorageService fileStorageService;

    @Override
    public OssFileInfoBO findByHash(String md5) {
        return ossFileInfoService.getOneByMd5(md5);
    }

    @Override
    public OssFileInfoBO upload(byte[] fileBytes, @NonNull UploadFileAttributeDTO attr) throws BusinessException {
        FileInfo fileInfo;
        try {
            fileInfo = fileStorageService
                    .of(fileBytes)
                    .setOriginalFilename(attr.getOriginalFilename())
                    // 不手动指定，由框架自动生成存储文件名
                    .setSaveFilename(null)
                    .setContentType(attr.getContentType())
                    // 如果需要缩略图
                    // .setSaveThFilename()
                    // .setThContentType(contentType)
                    .upload();
        } catch (FileStorageRuntimeException fsre) {
            log.error("[文件上传下载门面][上传] FileStorageRuntimeException >> ", fsre);
            throw new BusinessException(OssErrorEnum.FILE_UPLOAD_FAILED);
        }

        log.info("[文件上传下载门面][上传] 正常上传成功 >> successFileInfo={}", fileInfo);

        /*
        记录存入 DB
         */
        return ossFileInfoService.save(fileInfo, attr);
    }

    @Override
    public OssFileDownloadReplyBO downloadById(Long fileInfoId) throws BusinessException {
        OssFileInfoBO ossFileInfo = ossFileInfoService.getOneById(fileInfoId, true);

        /*
        这里请根据实际业务性质调整
        有的业务出于安全目的，不能暴露直链，只能通过服务端代理下载后，返回 byte[]
        有的业务没有限制，上传后文件完全可以直接通过对象存储直链下载，如此还能节约服务端上传带宽
        但本地存储又没有直链，只能通过文件ID；
        默认地，此处按【本地存储or对象存储直链为空：通过服务端代理下载；对象存储：通过对象存储直链下载】返回
         */
        boolean redirect2DirectUrl = false;
        byte[] fileBytes = null;
        if (
                OssFileInfoService.isLocalPlatform(ossFileInfo.getStoragePlatform())
                || StrUtil.isEmpty(ossFileInfo.getDirectUrl())
        ) {
            FileInfo fileInfo = OssFileInfoService.toFileInfo(ossFileInfo);
            try {
                fileBytes = fileStorageService.download(fileInfo).bytes();
            } catch (FileStorageRuntimeException fsre) {
                log.error("[文件上传下载门面][下载] FileStorageRuntimeException >> ", fsre);
                throw new BusinessException(OssErrorEnum.FILE_DOWNLOAD_FAILED);
            }
        } else {
            redirect2DirectUrl = true;
        }
        return OssFileDownloadReplyBO.builder()
                .redirect2DirectUrl(redirect2DirectUrl)
                .fileBytes(fileBytes)
                .directUrl(ossFileInfo.getDirectUrl())
                .storageFilename(ossFileInfo.getStorageFilenameFull())
                .build();
    }

    @Override
    public OssFileUploadResultVO toUploadResult(OssFileInfoBO ossFileInfo, String requestUrl) {
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
                OssFileInfoService.isLocalPlatform(ossFileInfo.getStoragePlatform())
                        || StrUtil.isEmpty(ossFileInfo.getDirectUrl())
        ) {
            // TODO 考虑一下
            ret.setUrl(String.format("%s/%s"
                    , requestUrl
                    , ossFileInfo.getId()));
        } else {
            ret.setUrl(ossFileInfo.getDirectUrl());
        }

        return ret;
    }
}
