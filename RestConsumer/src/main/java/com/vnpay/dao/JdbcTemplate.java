package com.vnpay.dao;

import com.vnpay.model.Request;

/**
 * Interface of JDBCTemplate
 */
public interface JdbcTemplate {
    /**
     * create connection to database and save information's request
     * @param request
     * @return true if success
     */
    boolean insertRequest(Request request);
}
