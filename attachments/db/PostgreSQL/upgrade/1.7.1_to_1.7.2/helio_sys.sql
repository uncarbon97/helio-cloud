-- v1.7.2 - Uncarbon - 新增上传文件信息后台管理菜单
-- 需导入至【helio_sys】库
INSERT INTO sys_menu (id, tenant_id, revision, del_flag, created_at, created_by, updated_at,
                      updated_by, title, parent_id, type, permission, icon, sort, status, component,
                      external_link)
VALUES (20220922152710, NULL, 1, 0, '2022-12-25 23:09:30', 'admin', '2022-12-26 22:02:31', 'admin', '文件管理', 0, 0,
        'Oss', 'ant-design:file-outlined', 4, 1, NULL, '');
INSERT INTO sys_menu (id, tenant_id, revision, del_flag, created_at, created_by, updated_at,
                      updated_by, title, parent_id, type, permission, icon, sort, status, component,
                      external_link)
VALUES (20220922152714, NULL, 1, 0, '2022-09-22 15:27:14', 'helio-generator', '2022-12-26 22:03:14', 'admin',
        '上传文件信息管理', 20220922152710, 1, 'OssFileInfo', 'ant-design:save-twotone', 1, 1, '/oss/OssFileInfo/index',
        '');
INSERT INTO sys_menu (id, tenant_id, revision, del_flag, created_at, created_by, updated_at,
                      updated_by, title, parent_id, type, permission, icon, sort, status, component,
                      external_link)
VALUES (20220922152715, NULL, 1, 0, '2022-09-22 15:27:14', 'helio-generator', '2022-12-26 22:01:58', 'admin', '查询',
        20220922152714, 2, 'OssFileInfo:retrieve', NULL, 1, 1, NULL, '');
INSERT INTO sys_menu (id, tenant_id, revision, del_flag, created_at, created_by, updated_at,
                      updated_by, title, parent_id, type, permission, icon, sort, status, component,
                      external_link)
VALUES (20220922152718, NULL, 1, 0, '2022-09-22 15:27:14', 'helio-generator', '2022-12-26 22:02:09', 'admin', '删除',
        20220922152714, 2, 'OssFileInfo:delete', NULL, 2, 1, NULL, '');
