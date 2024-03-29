package cc.uncarbon.module.sys.entity;

import cc.uncarbon.framework.core.enums.GenderEnum;
import cc.uncarbon.framework.crud.entity.HelioBaseEntity;
import cc.uncarbon.module.sys.enums.SysUserStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;


/**
 * 后台用户
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "sys_user")
public class SysUserEntity extends HelioBaseEntity<Long> {

	@ApiModelProperty(value = "账号")
	@TableField(value = "pin")
	private String pin;

	@ApiModelProperty(value = "密码")
	@TableField(value = "pwd")
	private String pwd;

	@ApiModelProperty(value = "盐")
	@TableField(value = "salt")
	private String salt;

	@ApiModelProperty(value = "昵称")
	@TableField(value = "nickname")
	private String nickname;

	@ApiModelProperty(value = "状态")
	@TableField(value = "status")
	private SysUserStatusEnum status;

	@ApiModelProperty(value = "性别")
	@TableField(value = "gender")
	private GenderEnum gender;

	@ApiModelProperty(value = "邮箱")
	@TableField(value = "email")
	private String email;

	@ApiModelProperty(value = "手机号")
	@TableField(value = "phone_no")
	private String phoneNo;

	@ApiModelProperty(value = "最后登录时刻")
	@TableField(value = "last_login_at")
	private LocalDateTime lastLoginAt;

}
