package com.sida.xiruo.xframework.util;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis - 通用分页拦截器
 * @author
 * Created by  on 14-4-15.
 */
@Deprecated
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
@SuppressWarnings("rawtypes")
public class PageHelper implements Interceptor {
    private final  static org.slf4j.Logger logger = LoggerFactory.getLogger(PageHelper.class);


    public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();

    /**
     * 开始分页
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(Integer pageNum, Integer pageSize) {
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 5;
        localPage.set(new Page(pageNum, pageSize));
    }

    /**
     * 开始分页
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(Integer pageNum, Integer pageSize, String countSqlPrefix) {
        Page page = new Page(pageNum, pageSize);
        page.setCountSqlPrefix(countSqlPrefix);
        page.setCustomCountSql(true);
        localPage.set(page);
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     * @return
     */
    public static Page endPage() {
        Page page = localPage.get();
        localPage.remove();
        return page;
    }

    //    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        if (localPage.get() == null) {
            return invocation.proceed();
        }
        if (invocation.getTarget() instanceof StatementHandler) {
            Page page = localPage.get();
            if(page.isPageable()) {
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
                // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
                // 可以分离出最原始的的目标类)
                while (metaStatementHandler.hasGetter("h")) {
                    Object object = metaStatementHandler.getValue("h");
                    metaStatementHandler = SystemMetaObject.forObject(object);
                }
                // 分离最后一个代理对象的目标类
                while (metaStatementHandler.hasGetter("target")) {
                    Object object = metaStatementHandler.getValue("target");
                    metaStatementHandler = SystemMetaObject.forObject(object);
                }
                MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
                //分页信息if (localPage.get() != null) {
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
                // 分页参数作为参数对象parameterObject的一个属性
                String sql = boundSql.getSql();
                // 重写sql
                String pageSql = buildPageSql(sql, page);
                //重写分页sql
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                Connection connection = (Connection) invocation.getArgs()[0];
                // 重设分页参数里的总页数等
                setPageParameter(sql, connection, mappedStatement, boundSql, page);

                //只做一次分页， 后续查询不分页（解决子查询分页问题）
                page.setPageable(false);
            }
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
            Page page = localPage.get();
            page.setResult((List) result);
            return result;
        }
        return null;
    }

    /**
     * 只拦截这两种类型的
     * <br>StatementHandler
     * <br>ResultSetHandler
     * @param target
     * @return
     */
//    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    //    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 修改原SQL为分页SQL
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
//        StringBuilder pageSql = new StringBuilder(200);
//        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
//        pageSql.append(sql);
//        pageSql.append(" ) temp where rownum <= ").append(page.getEndRow());
//        pageSql.append(") where row_id > ").append(page.getStartRow());

        StringBuilder pageSql = new StringBuilder(200);
        // pageSql.append("select * from ( ");
        pageSql.append(sql);
        pageSql.append("   limit ").append(page.getStartRow());
        pageSql.append(",").append(page.getPageSize());
        return pageSql.toString();
    }

    /**
     * 获取总记录数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Page page) {
        // 记录总记录数
        String countSql = "select count(0) from (" + sql + ") temptable";


        //自定义count语句（提升效率）
        if(page.isCustomCountSql() && BlankUtil.isNotEmpty(page.getCountSqlPrefix())) {
            int firstFromIndex = sql.toLowerCase().indexOf("from");
            int lastGroupByIndex = sql.toLowerCase().lastIndexOf("group");  //最后一个group by
            int lastParenthesisIndex = sql.toLowerCase().lastIndexOf(")");//最后一个右括号
            int firstParamIndex = sql.toLowerCase().indexOf("?");       //第一个参数
            //from之前不能有变量参数
            if(firstFromIndex != -1 &&
                    (firstParamIndex == -1 || firstParamIndex>firstFromIndex)) {
                //如果有group by，在外套一层count()
                if (lastGroupByIndex!=-1 && lastGroupByIndex > lastParenthesisIndex) {
                    countSql = "select count(0) from (" + page.getCountSqlPrefix() + " " + sql.substring(firstFromIndex) + ") temptable";
                } else {
                    countSql = page.getCountSqlPrefix() + " " + sql.substring(firstFromIndex);
                }
            }
        }
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            //添加查询条件中包含循环参数时的设置     2016-10-6 had
            for (ParameterMapping pm : boundSql.getParameterMappings()){
                String propertyName = pm.getProperty();
                if(boundSql.hasAdditionalParameter(propertyName)){
                    countBS.setAdditionalParameter(propertyName,boundSql.getAdditionalParameter(propertyName));
                }
            }
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotal(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setPages(totalPage);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 代入参数值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * Description: 分页
     * Author: liuzh
     * Update: liuzh(2014-04-16 10:56)
     */
    public static class Page<E> implements Serializable {
        private int pageNum;
        private int pageSize;
        private int startRow;
        private int endRow;
        private long total;
        private int pages;
        private List<E> result;
        //是否启动分页
        private boolean pageable = true;
        //是否启用自定义countSql
        private boolean customCountSql;
        //customCountSqlPrefix + from之后的原SQL 形成新的总数统计SQL，
        private String countSqlPrefix;

        public Page(Integer pageNum, Integer pageSize) {
            pageNum = pageNum == null? 0 : pageNum;
            pageSize = pageSize == null? 10 : pageSize;
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
            this.endRow = pageNum * pageSize;
        }

        public List<E> getResult() {
            return result;
        }

        public void setResult(List<E> result) {
            this.result = result;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public boolean isPageable() {
            return pageable;
        }

        public void setPageable(boolean pageable) {
            this.pageable = pageable;
        }

        public boolean isCustomCountSql() {
            return customCountSql;
        }

        public void setCustomCountSql(boolean customCountSql) {
            this.customCountSql = customCountSql;
        }

        public String getCountSqlPrefix() {
            return countSqlPrefix;
        }

        public void setCountSqlPrefix(String countSqlPrefix) {
            this.countSqlPrefix = countSqlPrefix;
        }

        @Override
        public String toString() {
            return "Page{" +
                    "pageNum=" + pageNum +
                    ", pageSize=" + pageSize +
                    ", startRow=" + startRow +
                    ", endRow=" + endRow +
                    ", total=" + total +
                    ", pages=" + pages +
                    '}';
        }
    }
}
