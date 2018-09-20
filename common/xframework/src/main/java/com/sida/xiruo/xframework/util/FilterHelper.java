//package com.sida.xiruo.xframework.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.sida.xiruo.common.util.Xiruo;
//import com.sida.xiruo.po.common.BaseEntity;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.springframework.util.StringUtils;
//
//import java.sql.Connection;
//import java.util.Properties;
//
///**
// * Ext Filter过滤条件拦截器
// * 作用：执行查询之前, 修改原SQL语句,在原SQL后拼接上EXT filter组件传递上来的条件参数, 形成新的SQL
// * 使用案例：
// *           FilterHelper.appendFilter(menu);
//             List<SysMenu> menus = sysMenuMapper.selectMenuForList(menu);
//             FilterHelper.removeFilter();
// * create by hbd
// * 2016-7-15
// */
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
//public class FilterHelper implements Interceptor {
//    @SuppressWarnings("unused")
////	private static final Logger logger = Logger.getLogger(FilterHelper.class);
//
//	public static final ThreadLocal<BaseEntity> local = new ThreadLocal<BaseEntity>();
//
//    /**
//     * filter字符串是从ext列表页面gridfilter控件中传递过来的
//     */
//	public static void appendFilter(BaseEntity filter) {
//        Thread current = Thread.currentThread();
//        System.err.println("------------>append threadId: "+current.getId());
////        logger.info("------------>append threadId: "+current.getId());
//        local.set(filter);
//    }
//
//    /**
//     * 查询结果后清除local，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
//     * @return
//     */
//    public static BaseEntity removeFilter() {
//        BaseEntity filter = local.get();
//        local.remove();
//        return filter;
//    }
//
//	public Object intercept(Invocation invocation) throws Throwable {
//        if (local.get() == null) {
//            return invocation.proceed();
//        }
//       // Thread current = Thread.currentThread();
////        logger.info("------------>intercept threadId: "+current.getId());
////        logger.info("------------>intercept get: "+JSONObject.toJSONString(local.get()));
////        System.err.println("------------>intercept threadId: "+current.getId());
////        System.err.println("------------>intercept get: "+JSONObject.toJSONString(local.get()));
//        if(invocation.getTarget() instanceof StatementHandler){
//            BaseEntity filter = local.get();
//            if(filter.isFilterable()) {
//                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//                MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
//                Object object = null;
//                while (metaStatementHandler.hasGetter("h")) {
//                    object = metaStatementHandler.getValue("h");
//                    metaStatementHandler = SystemMetaObject.forObject(object);
//
//                    // 分离最后一个代理对象的目标类
//                    while (metaStatementHandler.hasGetter("target")) {
//                        object = metaStatementHandler.getValue("target");
//                        metaStatementHandler = SystemMetaObject.forObject(object);
//                    }
//                }
//
//                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
//
//                String sql = boundSql.getSql();
//                String filterSql = buildFilterSql(sql, filter);
//                metaStatementHandler.setValue("delegate.boundSql.sql", filterSql);
//
//                filter.setFilterable(false);
//            }
//            return invocation.proceed();
//        }
//        return invocation.proceed();
//    }
//
//    /**
//     * 包装原SQL,组装成带Filter条件的SQL
//     * @param sql
//     * @param filter
//     * @return
//     */
//    private String buildFilterSql(String sql, BaseEntity filter) {
//        if(BlankUtil.isNotEmpty(filter.getFilter()) || BlankUtil.isNotEmpty(filter.getSort())) {
//            StringBuilder filterSql = new StringBuilder(200);
//            filterSql.append("select * from ( ");
//            filterSql.append(sql);
//            filterSql.append(") temptable where 1=1 ");
//            /*if (BlankUtil.isNotEmpty(filter.getFilter())) {
//                filterSql.append(mergeFilterConditions(filter.getFilter()));
//            }*/
//            if (BlankUtil.isNotEmpty(filter.getSort())) {
//                filterSql.append(mergeOrderConditions(filter));
//            }
//            return filterSql.toString();
//        }
//        return sql;
//    }
//
//    /**
//     * 解析ext组件filter参数字符串为SQL条件.
//     * @param filter
//     * @return
//     */
//    protected String mergeFilterConditions(String filter) {
//        StringBuffer buffer = new StringBuffer();
//        if(StringUtils.hasLength(filter)) {
//            //替换单引号和双杠,防止SQL注入
//            String filterJsonString = filter.replaceAll("'","").replaceAll("--","");
//            JSONArray filterJsonArray = JSON.parseArray(filterJsonString);
//            int size = filterJsonArray.size();
//            JSONObject json = null;
//            String field = "";
//            String operator = "";
//            String value = "";
//            JSONArray valueArray = null;
//            StringBuilder builder = new StringBuilder(",");
//            for(int i = 0; i < size; i++) {
//                json = (JSONObject)filterJsonArray.get(i);
//                field = Xiruo.nullToEmpty(json.get("property"));
//                value = Xiruo.nullToEmpty(json.get("value"));
//                operator = Xiruo.nullToEmpty(json.get("operator"));
//                if(field.toLowerCase().endsWith("date") || field.toLowerCase().endsWith("time")) {
//                    if(operator.equals("lt")) {
//                        buffer.append(" and ").append(field).append("<=DATE_FORMAT('" + value + " 23:59:59', '%Y-%m-%d %H:%i:%s')");
//                    } else if(operator.equals("gt")) {
//                        buffer.append(" and ").append(field).append(">=DATE_FORMAT('" + value + " 00:00:00', '%Y-%m-%d %H:%i:%s')");
//                    } else if(operator.equals("eq")) {
//                        buffer.append(" and DATE_FORMAT(").append(field).append(",'%Y-%m-%d')='").append(value).append("'");
//                    }
//                } else {
//                    if(operator.equals("<")) {
//                        buffer.append(" and ").append(field).append("<='").append(value).append("'");
//                    } else if(operator.equals(">")) {
//                        buffer.append(" and ").append(field).append(">='").append(value).append("'");
//                    } else if(operator.equals("=")) {
//                        buffer.append(" and ").append(field).append("='").append(value).append("'");
//                    } else if(operator.equals("lt")) {
//                        buffer.append(" and ").append(field).append("<=" + value);
//                    } else if(operator.equals("gt")) {
//                        buffer.append(" and ").append(field).append(">=" + value);
//                    } else if(operator.equals("eq")) {
//                        buffer.append(" and ").append(field).append("=").append(value);
//                    } else if(operator.equals("in")) {
//                        valueArray = json.getJSONArray("value");
//                        int len = valueArray.size();
//                        builder.delete(1, builder.length());
//                        for(int j = 0; j < len; j++) {
//                            builder.append(valueArray.getString(j)).append(",");
//                        }
//                        buffer.append(" and instr('" + builder + "',concat(',',").append(field).append(",','))>0 ");
//                    } else if(operator.equals("like")) {
//                        buffer.append(" and ").append(field).append(" like '%").append(value).append("%'");
//                    }
//                }
//            }
//        }
//        return buffer.toString();
//    }
//
//    /**
//     * 解析ext组件sort参数字符串为排序条件.
//     * @param BaseEntity
//     * @return
//     */
//    protected String mergeOrderConditions(BaseEntity BaseEntity) {
//        StringBuffer buffer = new StringBuffer();
//        String order = Xiruo.nullToEmpty(BaseEntity.getSort());
//        if(StringUtils.hasLength(order)) {
//            String filterJsonString = order;
//            JSONArray filterJsonArray = JSON.parseArray(filterJsonString);
//            JSONObject json = null;
//            String field = "";
//            String direction = "";
//            if(filterJsonArray.size()>0) {
//                json = (JSONObject) filterJsonArray.get(0);
//                field = Xiruo.nullToEmpty(json.get("property"));
//                direction = Xiruo.nullToEmpty(json.get("direction"));
//                if(BlankUtil.isNotEmpty(field)) {
//                    if(BaseEntity.getHumpToUnderline()) {
//                        field = StringUtil.humpToUnderline(field);
//                    }
//                    if (direction.toUpperCase().equals("DESC")) {
//                        buffer.append(" order by ").append(field).append(" ").append(direction);
//                    } else {
//                        buffer.append(" order by ").append(field).append(" ").append(direction);
//                    }
//                }
//            }
//        }
//        return buffer.toString();
//    }
//
//    /**
//     * 只拦截这类型
//     * <br>StatementHandler
//     * @param target
//     * @return
//     */
//    public Object plugin(Object target) {
//        if (target instanceof StatementHandler) {
//            return Plugin.wrap(target, this);
//        } else {
//            return target;
//        }
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//
//}