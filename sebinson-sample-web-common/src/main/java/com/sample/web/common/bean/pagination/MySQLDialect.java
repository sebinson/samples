package com.sample.web.common.bean.pagination;

/**
 * MySql数据库分页查询SQL封装实现
 *
 */
public class MySQLDialect extends Dialect {

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {
            return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
        } else {
            return sql + " limit " + limitPlaceholder;
        }
    }

}
