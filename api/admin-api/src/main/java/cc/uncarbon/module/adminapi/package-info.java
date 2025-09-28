/**
 * 本模块专门存放用于后台管理的HTTP-API
 * -
 * 使用的是注解鉴权，即所有接口都得指定是否需要登录或权限标识字符串，以实现细粒度鉴权
 * 默认相关SA-Token Util：{@link cc.uncarbon.module.adminapi.util.AdminStpUtil}
 * 详见{@link cc.uncarbon.module.adminapi.interceptor.AdminSaTokenParseInterceptor}
 */

package cc.uncarbon.module.adminapi;
