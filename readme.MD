这里是设计云1.0之前版本的后端项目代码。
作者：Xiruo.Jiang
创建时间：2018-09-20 10:00:00

代码介绍：
/pom.xml
整个项目的Maven构建文件，整个项目采用三级构建结构。

common 公共模块
-parent 所以模块需指定此parent，定义公共属性
-xdomain 领域模型框架
-xframework 框架代码，定义一些公共方法和抽象实现
-xutil 公共工具类

interface 接口定义模块，领域层定义
-activity-intf 活动功能模块接口
-authorization-intf 安全认证功能模块接口
-content-intf 内容功能模块接口
-operation-intf 运营功能模块接口
-schedule-intf 定时任务功能模块接口
-system-intf 系统管理功能模块接口
-training-intf 培训考试功能模块接口（用于代码参考，无需构建）

module 业务服务具体实现，领域模型中的基础设施层实现
-activity-module 活动功能模块实现
-content-module 内容功能模块实现
-operation-module 运营功能模块实现
-system-module 系统管理功能模块实现
-training-module 培训考试功能模块实现（用于代码参考，无需构建）

po pojo/vo/dto定义，数据模型的抽象，领域模型中的聚合/聚合根/值对象
-po-activity 活动po
-po-authorization 安全认证po
-po-common 公共po定义
-po-content 内容po
-po-event 事件po
-po-operation 运营po
-po-schedule 计划任务po
-po-system 系统管理po
-po-training 培训考试po（用于代码参考，无需构建）

security 安全认证和网关实现
-authorization 安全认证模块实现
-security-edge 安全网关模块实现

service 公共基础服务实现
-config 全局配置服务
-discovery 注册与发现服务
-event 事件服务
-schedule 计划任务服务

服务启动顺序：
1. discovery--> required
2. config--> required
3. authorization--> required
4. security-edge--> required
5. schedule--> optional
6. event--> optional
7. system-module --> required
8. others business service

------------------------------------------------------------------------------------