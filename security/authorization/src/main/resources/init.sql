/*
Navicat MySQL Data Transfer

Source Server         : 云驾培开发环境
Source Server Version : 100021
Source Host           : 192.168.87.195:8806
Source Database       : system-copy

Target Server Type    : MYSQL
Target Server Version : 100021
File Encoding         : 65001

Date: 2017-08-28 17:39:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_button
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_button` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `eng_name` varchar(50) DEFAULT NULL COMMENT '英文名称',
  `action` varchar(100) DEFAULT NULL COMMENT '动作',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `alias` varchar(50) DEFAULT NULL COMMENT '别名',
  `page_id` varchar(36) DEFAULT NULL COMMENT '所属页面',
  `id_path` varchar(255) DEFAULT NULL COMMENT 'id路径',
  `name_path` varchar(255) DEFAULT NULL COMMENT '名称路径',
  `code_path` varchar(255) DEFAULT NULL COMMENT '编码路径',
  `sort_order` int(3) DEFAULT NULL COMMENT '排序',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按钮表';

-- ----------------------------
-- Table structure for sys_client_detail
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_client_detail` (
  `id` varchar(128) NOT NULL COMMENT '主键id',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `client_id` varchar(128) NOT NULL COMMENT '客户端id',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密码',
  `scope` varchar(256) DEFAULT NULL COMMENT '范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '重定向路径',
  `authorities` varchar(256) DEFAULT NULL COMMENT '批准',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'token有效性',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '刷新token有效性',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '额外信息',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '自动批准',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Table structure for sys_employee
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_employee` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `third_party_id` bigint(20) DEFAULT NULL COMMENT '第三方id（原人事系统主键id）',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `idcard_number` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月',
  `sex` bit(1) DEFAULT NULL COMMENT '性别（0男士 1女士）',
  `nation` varchar(10) DEFAULT NULL COMMENT '民族',
  `photo` varchar(200) DEFAULT NULL COMMENT '员工头像（文件路径）',
  `idcard_address_province` varchar(20) DEFAULT NULL COMMENT '身份证地址（省）',
  `idcard_address_city` varchar(20) DEFAULT NULL COMMENT '身份证地址（市）',
  `idcard_address_area` varchar(20) DEFAULT NULL COMMENT '身份证地址（区）',
  `idcard_address_detail` varchar(100) DEFAULT NULL COMMENT '身份证地址（详细地址）',
  `idcard_iIssuing_authority` varchar(100) DEFAULT NULL COMMENT '身份证发证机关',
  `idcard_valid_date` datetime DEFAULT NULL COMMENT '身份证有效期起',
  `idcard_invalid_date` datetime DEFAULT NULL COMMENT '身份证有效期止',
  `residence_permit_issuing_date` datetime DEFAULT NULL COMMENT '居住证签发日期',
  `residence_permit_expiration_date` datetime DEFAULT NULL COMMENT '居住证有效年限（居住证有效期截止日期）',
  `identity_card_expiration_date` datetime DEFAULT NULL COMMENT '身份证有效年限（身份证有效期截止日期）',
  `residence_permit_address_province` varchar(20) DEFAULT NULL COMMENT '居住证地址（省）',
  `residence_permit_address_city` varchar(20) DEFAULT NULL COMMENT '居住证地址（市）',
  `residence_permit_address_area` varchar(20) DEFAULT NULL COMMENT '居住证地址（区）',
  `residence_permit_address_detail` varchar(100) DEFAULT NULL COMMENT '居住证地址（详细地址）',
  `now_address_province` varchar(20) DEFAULT NULL COMMENT '现住址（省）',
  `now_address_city` varchar(20) DEFAULT NULL COMMENT '现住址（市）',
  `now_address_area` varchar(20) DEFAULT NULL COMMENT '现住址（区）',
  `now_address_detail` varchar(100) DEFAULT NULL COMMENT '现住址（详细地址）',
  `place_of_origin_province` varchar(20) DEFAULT NULL COMMENT '籍贯（省）',
  `place_of_origin_city` varchar(20) DEFAULT NULL COMMENT '籍贯（市）',
  `place_of_origin_area` varchar(20) DEFAULT NULL COMMENT '籍贯（区）',
  `place_of_birth` varchar(100) DEFAULT NULL COMMENT '出生地',
  `work_card` varchar(40) DEFAULT NULL COMMENT '工号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `mobile` varchar(20) DEFAULT NULL COMMENT '家庭电话',
  `health_condition` varchar(10) DEFAULT NULL COMMENT '身体状况（健康、一般、残疾）',
  `hobby` varchar(50) DEFAULT NULL COMMENT '特长爱好',
  `height` varchar(10) DEFAULT NULL COMMENT '身高（CM）',
  `blood_type` varchar(5) DEFAULT NULL COMMENT '血型（A、B、O、AB）',
  `political_status` varchar(10) DEFAULT NULL COMMENT '政治面貌（党员、团员、群众、民主党派）',
  `graduation_start_date` datetime DEFAULT NULL COMMENT '毕业时间起',
  `graduation_end_date` datetime DEFAULT NULL COMMENT '毕业时间止',
  `graduated_school` varchar(50) DEFAULT NULL COMMENT '毕业院校',
  `education` varchar(20) DEFAULT NULL COMMENT '学历',
  `subject` varchar(20) DEFAULT NULL COMMENT '专业',
  `academic_type` varchar(20) DEFAULT NULL COMMENT '学历类型（普通统招/成人高考/自考/电大/网络教育）',
  `job_title` varchar(10) DEFAULT NULL COMMENT '职称（高级/中级/低级）',
  `title_full_name` varchar(50) DEFAULT NULL COMMENT '职称全称',
  `start_word_date` datetime DEFAULT NULL COMMENT '参加工作时间',
  `registration_date` datetime DEFAULT NULL COMMENT '报到时间',
  `driver_license_no` varchar(50) DEFAULT NULL COMMENT '驾驶证档案编号',
  `driver_license_receive_date` datetime DEFAULT NULL COMMENT '驾驶证领取日期',
  `driver_license_start_date` datetime DEFAULT NULL COMMENT '驾驶证有效日期起',
  `driver_license_end_date` datetime DEFAULT NULL COMMENT '驾驶证有效日期止',
  `driver_license_expiration_date` datetime DEFAULT NULL COMMENT '驾驶证有效期（截止日期）',
  `driver_license_iIssuing_authority` varchar(100) DEFAULT NULL COMMENT '驾驶证发证机关',
  `coach_card_receive_date` datetime DEFAULT NULL COMMENT '教练证领取日期',
  `coach_card_expiration_date` datetime DEFAULT NULL COMMENT '教练证有效期（截止日期）',
  `coach_card_iIssuing_authority` varchar(100) DEFAULT NULL COMMENT '教练证发证机关',
  `quasi_driving_type` varchar(10) DEFAULT NULL COMMENT '准驾车型（C1，C2，C3，C4，D，E，N，M，P）',
  `coach_card_no` varchar(50) DEFAULT NULL COMMENT '教练证号',
  `coach_card_quasi_teaching_category` varchar(10) DEFAULT NULL COMMENT '准教类别（C1，C2）',
  `quasi_teaching_type` varchar(10) DEFAULT NULL COMMENT '准教车型（A3，B1，B2，C1，C2，C3，M）',
  `risk_deposit` decimal(10,2) DEFAULT NULL COMMENT '风险押金',
  `risk_deposit_expiration_date` datetime DEFAULT NULL COMMENT '风险押金日期',
  `emergency_contact` varchar(10) DEFAULT NULL COMMENT '紧急联系人',
  `relationship` varchar(10) DEFAULT NULL COMMENT '与本人关系',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `marital_status` bit(1) DEFAULT NULL COMMENT '婚否（0未婚  1已婚）',
  `marriage_registration_date` datetime DEFAULT NULL COMMENT '结婚登记日期',
  `spouse_name` varchar(10) DEFAULT NULL COMMENT '配偶姓名',
  `spouse_identity_card` varchar(20) DEFAULT NULL COMMENT '配偶身份证',
  `spouse_work_company` varchar(100) DEFAULT NULL COMMENT '工作单位',
  `spouse_duties` varchar(20) DEFAULT NULL COMMENT '配偶职务',
  `spouse_education` varchar(20) DEFAULT NULL COMMENT '配偶学历',
  `spouse_phone` varchar(20) DEFAULT NULL COMMENT '配偶手机号码',
  `status` int(3) DEFAULT NULL COMMENT '在职状态（0正式员工   1试用期  2已离职）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Table structure for sys_employee_org
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_employee_org` (
  `employee_id` varchar(36) DEFAULT NULL COMMENT '员工',
  `org_id` varchar(36) DEFAULT NULL COMMENT '组织id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工-部门表';

-- ----------------------------
-- Table structure for sys_employee_position
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_employee_position` (
  `employee_id` varchar(36) DEFAULT NULL COMMENT '员工',
  `position_id` varchar(36) DEFAULT NULL COMMENT '岗位'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工-岗位表';

-- ----------------------------
-- Table structure for sys_field
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_field` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `eng_name` varchar(50) DEFAULT NULL COMMENT '英文名称',
  `action` varchar(100) DEFAULT NULL COMMENT '动作',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `alias` varchar(50) DEFAULT NULL COMMENT '别名',
  `page_id` varchar(36) DEFAULT NULL COMMENT '所属页面',
  `id_path` varchar(255) DEFAULT NULL COMMENT 'id路径',
  `name_path` varchar(255) DEFAULT NULL COMMENT '名称路径',
  `code_path` varchar(255) DEFAULT NULL COMMENT '编码路径',
  `sort_order` int(3) DEFAULT NULL COMMENT '排序',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字段表';

-- ----------------------------
-- Table structure for sys_global_variable
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_global_variable` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `code` varchar(255) DEFAULT NULL COMMENT '变量编码',
  `value` varchar(255) DEFAULT NULL COMMENT '变量值',
  `comment` varchar(255) DEFAULT NULL COMMENT '说明',
  `model` varchar(255) DEFAULT NULL COMMENT '所属模块',
  `version` int(10) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全局变量表';

-- ----------------------------
-- Table structure for sys_info_change_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_info_change_log` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `info_type` varchar(50) DEFAULT NULL COMMENT '信息类型：片区用户及角色（AREA、USER、ROLE）',
  `info_column` varchar(100) DEFAULT NULL COMMENT '变更字段',
  `new_value` varchar(255) DEFAULT NULL COMMENT '变更后内容',
  `old_value` varchar(255) DEFAULT NULL COMMENT '变更前内容',
  `column_name` varchar(100) DEFAULT NULL COMMENT '字段名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='敏感信息变更日志表：现在设计支持片区信息、用户信息、角色信息编辑时保存更改日志。';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `eng_name` varchar(100) DEFAULT NULL COMMENT '英文名称',
  `action` varchar(100) DEFAULT NULL COMMENT '动作',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `alias` varchar(50) DEFAULT NULL COMMENT '别名',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父级',
  `id_path` varchar(255) DEFAULT NULL COMMENT 'id路径',
  `name_path` varchar(255) DEFAULT NULL COMMENT '名称路径',
  `code_path` varchar(255) DEFAULT NULL COMMENT '编码路径',
  `sort_order` int(3) DEFAULT NULL COMMENT '排序',
  `is_loading` bit(1) DEFAULT NULL COMMENT '是否需要通过action加载下级菜单（0.否 1.是）',
  `is_expand` bit(1) DEFAULT NULL COMMENT '是否默认展开（0.否 1.是）',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_org` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `code` varchar(20) DEFAULT NULL COMMENT '编码',
  `alias` varchar(20) DEFAULT NULL COMMENT '别名',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父级',
  `id_path` varchar(255) DEFAULT NULL COMMENT 'id路径',
  `name_path` varchar(255) DEFAULT NULL COMMENT '名称路径',
  `code_path` varchar(255) DEFAULT NULL COMMENT '编码路径',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `logo` varchar(255) DEFAULT NULL COMMENT '公司logo',
  `has_personnel_system` bit(1) DEFAULT NULL COMMENT '是否具备人事管理系统（员工来源是否是从原系统同步）',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `type` int(3) DEFAULT NULL COMMENT '特殊部门标识（0.非特殊部门  1.牌证部门  2.片区  3.门店）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织表';

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_position` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `code` varchar(20) DEFAULT NULL COMMENT '编码',
  `alias` varchar(20) DEFAULT NULL COMMENT '别名',
  `description` varchar(255) DEFAULT NULL COMMENT '别名描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位表';

-- ----------------------------
-- Table structure for sys_region
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_region` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `code` varchar(50) DEFAULT NULL COMMENT '地区编码',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `level` varchar(20) DEFAULT NULL COMMENT '等级（“PROVINCE”、“CITY”、“AREA”）',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父级',
  `path` varchar(255) DEFAULT NULL COMMENT '路径（英文.分隔）',
  `name_path` varchar(255) DEFAULT NULL COMMENT '名称路径全称（英文.分隔）',
  `post_code` varchar(255) DEFAULT NULL COMMENT '邮政编码',
  `sort` int(11) DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中国区域表';

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_resource` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父级',
  `path` varchar(255) DEFAULT NULL COMMENT 'id路径（用.分隔）',
  `sort` int(11) DEFAULT NULL COMMENT '排序值（数字小的排前面）',
  `value` varchar(100) DEFAULT NULL COMMENT '标识字段',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `alias` varchar(50) DEFAULT NULL COMMENT '别名',
  `type` varchar(20) DEFAULT NULL COMMENT '类别（“page”，“button”，“field”）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `status` bit(1) DEFAULT NULL COMMENT '停用状态（0.启用 1.停用）',
  `alias` varchar(50) DEFAULT NULL COMMENT '别名',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_role_resource` (
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源',
  `identifier` varchar(50) DEFAULT NULL COMMENT '字段权限标识（暂未定义:类似于“不可见”“可见”“可读”等字段权限划分）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-资源对照表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '通用字段：主键id',
  `org_id` varchar(36) DEFAULT NULL COMMENT '通用字段：组织id（驾校id）',
  `created_at` datetime DEFAULT NULL COMMENT '通用字段：创建时间',
  `last_updated` datetime DEFAULT NULL COMMENT '通用字段：更新时间（只保留最后一次操作时间）',
  `created_user` varchar(36) DEFAULT NULL COMMENT '通用字段：创建人',
  `updated_user` varchar(36) DEFAULT NULL COMMENT '通用字段：更新人（只保留最后一次操作人）',
  `disable` bit(1) DEFAULT NULL COMMENT '通用字段：禁用标识（0.可用  1.禁用）',
  `delete_flag` bit(1) DEFAULT NULL COMMENT '通用字段：逻辑删除标识（0.未删除 1.已删除）',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `alias` varchar(50) DEFAULT NULL COMMENT '别名',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `valid_date` datetime DEFAULT NULL COMMENT '生效日期',
  `locked` bit(1) DEFAULT NULL COMMENT '锁定状态（0.未锁定 1.已锁定）',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` bit(1) DEFAULT NULL COMMENT '停用状态（0.启用 1.停用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';
