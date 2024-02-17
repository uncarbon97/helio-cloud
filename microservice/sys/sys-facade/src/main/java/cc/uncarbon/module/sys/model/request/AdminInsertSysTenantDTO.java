package cc.uncarbon.module.sys.model.request;

import cc.uncarbon.framework.core.constant.HelioConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;


/**
 * 后台管理-新增系统租户
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminInsertSysTenantDTO extends AdminUpdateSysTenantDTO {

    @ApiModelProperty(value = "租户ID(纯数字)", required = true)
    @Min(value = 1L, message = "租户ID须为正整数")
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "管理员账号", required = true)
    @Size(min = 6, max = 16, message = "【管理员账号】长度须在 6 至 16 位之间")
    @NotBlank(message = "管理员账号不能为空")
    private String tenantAdminUsername;

    @ApiModelProperty(value = "管理员密码", required = true)
    @Size(min = 8, max = 20, message = "【管理员密码】长度须在 8 至 20 位之间")
    @NotBlank(message = "管理员密码不能为空")
    private String tenantAdminPassword;

    @ApiModelProperty(value = "管理员邮箱", required = true)
    @Pattern(message = "管理员邮箱格式不正确", regexp = HelioConstant.Regex.EMAIL)
    @NotBlank(message = "管理员邮箱不能为空")
    private String tenantAdminEmail;

    @ApiModelProperty(value = "管理员手机号", required = true)
    @Pattern(message = "管理员手机号格式不正确", regexp = HelioConstant.Regex.CHINA_MAINLAND_PHONE_NO)
    @NotBlank(message = "管理员手机号不能为空")
    private String tenantAdminPhoneNo;

}
