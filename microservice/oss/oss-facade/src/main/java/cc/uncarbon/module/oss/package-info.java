/**
 * 文件上传下载服务二方库
 * 作用：
 * 1️⃣单独存放常量、枚举、DTO、静态工具类等，便于其他服务引入使用
 * 2️⃣B-facade可以引入A-facade实现二次包装，无需引入整个B模块
 * 3️⃣Facade只开放必要的接口方法，避免乱调用
 * 4️⃣如果有两个Maven模块互相引入，会造成循环依赖问题，无法启动；拆分二方库可以在一定程度上缓解该问题（A-service引入B-facade，B-service引入A-facade）
 */

package cc.uncarbon.module.oss;
