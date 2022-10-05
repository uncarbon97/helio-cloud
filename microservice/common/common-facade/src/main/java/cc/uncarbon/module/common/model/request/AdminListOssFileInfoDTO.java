package cc.uncarbon.module.common.model.request;

import cc.uncarbon.framework.core.constant.HelioConstant;
import cc.uncarbon.module.sys.enums.GenericStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 后台管理-分页列表上传文件信息 DTO
 *
 * @author Uncarbon
 */
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminListOssFileInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "前置路径(关键词)")
    private String path;

    @ApiModelProperty(value = "文件名(关键词)")
    private String filename;

    @ApiModelProperty(value = "扩展名(关键词)")
    private String extendName;

    @ApiModelProperty(value = "文件大小")
    private Long filesize;

    @ApiModelProperty(value = "MD5(关键词)")
    private String md5;

    @ApiModelProperty(value = "状态")
    private GenericStatusEnum status;

    @ApiModelProperty(value = "类别编号")
    private Integer classified;

    @ApiModelProperty(value = "时间区间起")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = HelioConstant.Jackson.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = HelioConstant.Jackson.DATE_TIME_FORMAT)
    private LocalDateTime beginAt;

    @ApiModelProperty(value = "时间区间止")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = HelioConstant.Jackson.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = HelioConstant.Jackson.DATE_TIME_FORMAT)
    private LocalDateTime endAt;

}
