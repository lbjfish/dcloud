###**这里是设计云1.0之前版本的后端项目代码。**
#####作者：Xiruo.Jiang
#####创建时间：2018-09-20 10:00:00

#####代码介绍：
*/pom.xml*<br/>
整个项目的Maven构建文件，整个项目采用三级构建结构。<br/>

#####common 公共模块<br/>
-parent 所以模块需指定此parent，定义公共属性<br/>
-xdomain 领域模型框架<br/>
-xframework 框架代码，定义一些公共方法和抽象实现<br/>
-xutil 公共工具类<br/>
<br/>
#####interface 接口定义模块，领域层定义<br/>
-activity-intf 活动功能模块接口<br/>
-authorization-intf 安全认证功能模块接口<br/>
-content-intf 内容功能模块接口<br/>
-operation-intf 运营功能模块接口<br/>
-schedule-intf 定时任务功能模块接口<br/>
-system-intf 系统管理功能模块接口<br/>
-training-intf 培训考试功能模块接口（用于代码参考，无需构建）<br/>
<br/>
#####module 业务服务具体实现，领域模型中的基础设施层实现<br/>
-activity-module 活动功能模块实现<br/>
-content-module 内容功能模块实现<br/>
-operation-module 运营功能模块实现<br/>
-system-module 系统管理功能模块实现<br/>
-training-module 培训考试功能模块实现（用于代码参考，无需构建）<br/>
<br/>
#####po pojo/vo/dto定义，数据模型的抽象，领域模型中的聚合/聚合根/值对象<br/>
-po-activity 活动po<br/>
-po-authorization 安全认证po<br/>
-po-common 公共po定义<br/>
-po-content 内容po<br/>
-po-event 事件po<br/>
-po-operation 运营po<br/>
-po-schedule 计划任务po<br/>
-po-system 系统管理po<br/>
-po-training 培训考试po（用于代码参考，无需构建）<br/>
<br/>
#####security 安全认证和网关实现<br/>
-authorization 安全认证模块实现<br/>
-security-edge 安全网关模块实现<br/>
<br/>
#####service 公共基础服务实现<br/>
-config 全局配置服务<br/>
-discovery 注册与发现服务<br/>
-event 事件服务<br/>
-schedule 计划任务服务<br/>
<br/>
#####服务启动顺序：<br/>
1. discovery--> required<br/>
2. config--> required<br/>
3. authorization--> required<br/>
4. security-edge--> required<br/>
5. schedule--> optional<br/>
6. event--> optional<br/>
7. system-module --> required<br/>
8. others business service<br/>
<br/>
------------------------------------------------------------------------------------