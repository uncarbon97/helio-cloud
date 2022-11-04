-- 增加对应后台管理菜单
INSERT INTO `sys_menu`(`id`, `tenant_id`, `revision`, `del_flag`, `created_at`, `created_by`, `updated_at`,
                       `updated_by`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`,
                       `external_link`)
VALUES (20220922152714, NULL, 1, 0, '2022-09-22 15:27:14', 'helio-generator', '2022-09-22 15:27:14',
        'helio-generator', '上传文件信息管理', 0, 1, 'OssFileInfo', 'ant-design:flag-outlined', 100, 1,
        '/oss/OssFileInfo/index', '');
INSERT INTO `sys_menu`(`id`, `tenant_id`, `revision`, `del_flag`, `created_at`, `created_by`, `updated_at`,
                       `updated_by`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`,
                       `external_link`)
VALUES (20220922152715, NULL, 1, 0, '2022-09-22 15:27:14', 'helio-generator', '2022-09-22 15:27:14',
        'helio-generator', '查询', 1572848462892752896, 2, 'OssFileInfo:retrieve', NULL, 1, 1, NULL, '');
INSERT INTO `sys_menu`(`id`, `tenant_id`, `revision`, `del_flag`, `created_at`, `created_by`, `updated_at`,
                       `updated_by`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`,
                       `external_link`)
VALUES (20220922152716, NULL, 1, 1, '2022-09-22 15:27:14', 'helio-generator', '2022-09-22 15:35:48', 'admin',
        '新增', 1572848462892752896, 2, 'OssFileInfo:create', NULL, 2, 1, NULL, '');
INSERT INTO `sys_menu`(`id`, `tenant_id`, `revision`, `del_flag`, `created_at`, `created_by`, `updated_at`,
                       `updated_by`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`,
                       `external_link`)
VALUES (20220922152717, NULL, 1, 1, '2022-09-22 15:27:14', 'helio-generator', '2022-09-22 15:35:55', 'admin',
        '编辑', 1572848462892752896, 2, 'OssFileInfo:update', NULL, 4, 1, NULL, '');
INSERT INTO `sys_menu`(`id`, `tenant_id`, `revision`, `del_flag`, `created_at`, `created_by`, `updated_at`,
                       `updated_by`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`,
                       `external_link`)
VALUES (20220922152718, NULL, 1, 0, '2022-09-22 15:27:14', 'helio-generator', '2022-09-22 15:36:03', 'admin',
        '删除', 1572848462892752896, 2, 'OssFileInfo:delete', NULL, 2, 1, NULL, '');
