package com.sample.web.common.intercepters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.PropertyException;

import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.StringUtils;

import com.sample.common.tools.ReflectHelper;
import com.sample.web.common.bean.pagination.Dialect;
import com.sample.web.common.bean.pagination.Page;

/**
 * Mybatis的分页查询拦截器，通过拦截StatementHandler的prepare方法来实现。
 * 只有在参数列表中包括Page类型的参数时才进行分页查询。 在多参数的情况下，只对第一个Page类型的参数生效。
 * 另外，在参数列表中，Page类型的参数无需用@Param来标注
 * 
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

    private static Dialect dialectObject = null; // 数据库方言
    private static String pageSqlId = ""; // mybaits的数据库xml映射文件中需要拦截的ID(正则匹配)

    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler,
                    "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate,
                    "mappedStatement");
            /**
             * 方法1：通过ＩＤ来区分是否需要分页．.*query.* 方法2：传入的参数是否有page参数，如果有，则分页，
             */
            if (mappedStatement.getId().matches(pageSqlId)) { // 拦截需要分页的SQL
                BoundSql boundSql = delegate.getBoundSql();
                // 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    // throw new
                    // NullPointerException("boundSql.getParameterObject() is null!");
                    return ivk.proceed();
                } else {
                    Page page = null;
                    if (parameterObject instanceof Page) { // 参数就是Page实体
                        page = (Page) parameterObject;
                    } else if (parameterObject instanceof Map) {
                        for (Entry entry : (Set<Entry>) ((Map) parameterObject).entrySet()) {
                            if (entry.getValue() instanceof Page) {
                                page = (Page) entry.getValue();
                                break;
                            }
                        }
                    } else { // 参数为某个实体，该实体拥有Pages属性
                        page = ReflectHelper.getValueByFieldType(parameterObject, Page.class);
                        if (page == null) {
                            return ivk.proceed();
                        }
                    }

                    String sql = boundSql.getSql();
                    PreparedStatement countStmt = null;
                    ResultSet rs = null;
                    try {
                        Connection connection = (Connection) ivk.getArgs()[0];
                        String countSql = "select count(1) from (" + sql + ") tmp_count"; // 记录统计
                        countStmt = connection.prepareStatement(countSql);
                        ReflectHelper.setValueByFieldName(boundSql, "sql", countSql);
                        DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
                                parameterObject, boundSql);
                        parameterHandler.setParameters(countStmt);
                        rs = countStmt.executeQuery();
                        int count = 0;
                        if (rs.next()) {
                            count = ((Number) rs.getObject(1)).intValue();
                        }
                        page.setRowCount(count);
                    } finally {
                        try {
                            rs.close();
                        } catch (Exception e) {
                        }
                        try {
                            countStmt.close();
                        } catch (Exception e) {
                        }
                    }
                    String pageSql = this.generatePagesSql(sql, page);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
                }
            }
        }
        return ivk.proceed();
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     * 
     * @param sql
     * @param page
     */
    private String generatePagesSql(String sql, Page page) {
        if (page != null && dialectObject != null) {
            // pageNow默认是从1，而已数据库是从0开始计算的．所以(page.getPageNow()-1)
            int pageNow = page.getPageNow();
            return dialectObject.getLimitString(sql, (pageNow <= 0 ? 0 : pageNow - 1) * page.getPageSize(),
                    page.getPageSize());
        }
        return sql;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties p) {
        String dialect = ""; // 数据库方言
        dialect = p.getProperty("dialect");
        if (StringUtils.isEmpty(dialect)) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                LogFactory.getLog("error").error("pageSqlId property is not found!", e);
            }
        } else {
            try {
                dialectObject = (Dialect) Class.forName(dialect).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(dialect + ", init fail!\n" + e);
            }
        }
        pageSqlId = p.getProperty("pageSqlId");// 根据id来区分是否需要分页
        if (StringUtils.isEmpty(pageSqlId)) {

            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                LogFactory.getLog("error").error("pageSqlId property is not found!", e);
            }
        }
    }
}
