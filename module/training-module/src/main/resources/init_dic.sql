# truncate table system.sys_dictionary_group;
# truncate table system.sys_dictionary_data;
# 所属科目
delete from system.sys_dictionary_group where group_code='subject';
insert into system.sys_dictionary_group(id,group_code,group_name,remark,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1001,'subject','所属科目','','0',now(),'0',now(),0,0);
delete from system.sys_dictionary_data where group_code='subject';
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1001,0,1001,'subject','1','科一','','1001',1,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1002,0,1001,'subject','2','科二','','1002',2,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1003,0,1001,'subject','3','科三','','1003',3,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1004,0,1001,'subject','4','科四','','1004',4,0,0,now(),0,now(),0,0);
# 习题题型
delete from system.sys_dictionary_group where group_code='exercise_type';
insert into system.sys_dictionary_group(id,group_code,group_name,remark,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1002,'exercise_type','习题题型','','0',now(),'0',now(),0,0);
delete from system.sys_dictionary_data where group_code='exercise_type';
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1005,0,1002,'exercise_type','1','单选题','','1005',1,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1006,0,1002,'exercise_type','2','多选题','','1006',2,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1007,0,1002,'exercise_type','3','判断题','','1007',3,0,0,now(),0,now(),0,0);
# 习题表现形式
delete from system.sys_dictionary_group where group_code='exercise_style';
insert into system.sys_dictionary_group(id,group_code,group_name,remark,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1003,'exercise_style','习题表现形式','','0',now(),'0',now(),0,0);
delete from system.sys_dictionary_data where group_code='exercise_style';
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1008,0,1003,'exercise_style','1','文字题','','1008',1,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1009,0,1003,'exercise_style','2','图片题','','1009',2,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1010,0,1003,'exercise_style','3','动画题','','1010',3,0,0,now(),0,now(),0,0);
# 习题难度
delete from system.sys_dictionary_group where group_code='exercise_difficulty';
insert into system.sys_dictionary_group(id,group_code,group_name,remark,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1004,'exercise_difficulty','习题难度','','0',now(),'0',now(),0,0);
delete from system.sys_dictionary_data where group_code='exercise_difficulty';
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1011,0,1004,'exercise_difficulty','1','容易','','1011',1,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1012,0,1004,'exercise_difficulty','2','中等','','1012',2,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1013,0,1004,'exercise_difficulty','3','困难','','1013',3,0,0,now(),0,now(),0,0);
# 练习类型
delete from system.sys_dictionary_group where group_code='pratice_type';
insert into system.sys_dictionary_group(id,group_code,group_name,remark,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1005,'pratice_type','练习类型','','0',now(),'0',now(),0,0);
delete from system.sys_dictionary_data where group_code='pratice_type';
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1014,0,1005,'pratice_type','1','顺序练习','','1014',1,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1015,0,1005,'pratice_type','2','章节练习','','1015',2,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1016,0,1005,'pratice_type','3','题型练习','','1016',3,0,0,now(),0,now(),0,0);
insert into system.sys_dictionary_data(id,parent_id,group_id,group_code,dic_code,dic_name,remark,path,sort,org_id,created_user,created_at,updated_user,last_updated,disable,delete_flag) values(1017,0,1005,'pratice_type','4','分组练习','','1017',4,0,0,now(),0,now(),0,0);
