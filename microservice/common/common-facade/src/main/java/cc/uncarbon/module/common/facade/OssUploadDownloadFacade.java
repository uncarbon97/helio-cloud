package cc.uncarbon.module.common.facade;

import cc.uncarbon.framework.core.exception.BusinessException;
import cc.uncarbon.module.common.model.request.UploadFileAttributeDTO;
import cc.uncarbon.module.common.model.response.OssFileDownloadReplyBO;
import cc.uncarbon.module.common.model.response.OssFileInfoBO;
import cc.uncarbon.module.common.model.response.OssFileUploadResultVO;
import lombok.NonNull;

/**
 * 文件上传下载门面
 *
 * @author Uncarbon
 */
public interface OssUploadDownloadFacade {

    /**
     * 根据哈希值，查找是否已有文件
     */
    OssFileInfoBO findByHash(String md5);

    /**
     * 正常上传文件到服务端
     *
     * @param fileBytes 文件数据
     * @param attr      附加属性
     */
    OssFileInfoBO upload(byte[] fileBytes, @NonNull UploadFileAttributeDTO attr) throws BusinessException;

    /**
     * 根据文件ID下载
     *
     * @throws BusinessException 业务异常，如：文件ID无效；原始文件不存在
     */
    OssFileDownloadReplyBO downloadById(Long fileInfoId) throws BusinessException;

    /**
     * 将 OssFileInfoBO 转换为 OssFileUploadResultVO
     */
    OssFileUploadResultVO toUploadResult(OssFileInfoBO ossFileInfo, String requestUrl);

}
