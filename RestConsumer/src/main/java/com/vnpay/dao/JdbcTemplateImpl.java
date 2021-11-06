package com.vnpay.dao;

import com.vnpay.common.MessageConfig;
import com.vnpay.connection.DBConnection;
import com.vnpay.model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplateImpl implements JdbcTemplate {

    private static final Logger logger = LogManager.getLogger(JdbcTemplateImpl.class);

    /**
     * create connection to database and save information's request
     * @param request
     * @return true if success
     */
    @Override
    public boolean insertRequest(Request request) {
        Connection connectionDB = null;
        PreparedStatement prepStmt = null;
        boolean result = false;
        try {
            connectionDB = DBConnection.getConnection();
            if(connectionDB == null){
                throw new SQLException("Can not connect to database");
            }
            logger.info("Connect to data success");
            prepStmt = connectionDB.prepareStatement(MessageConfig.SQL_INSERT);
            prepStmt.setString(1, request.getAccountNo());
            prepStmt.setString(2, request.getAddValue());
            prepStmt.setString(3, request.getAddtionalData());
            prepStmt.setString(4, request.getApiID());
            prepStmt.setString(5, request.getBankCode());
            prepStmt.setString(6, request.getCheckSum());
            prepStmt.setInt(7, request.getDebitAmount());
            prepStmt.setString(8, request.getItem());
            prepStmt.setString(9, request.getMessageType());
            prepStmt.setString(10, request.getMobile());
            prepStmt.setString(11, request.getOrderCode());
            prepStmt.setString(12, request.getPayDate());
            prepStmt.setString(13, request.getPromotionCode());
            prepStmt.setString(14, request.getQueueNameResponse());
            prepStmt.setInt(15, request.getRealAmount());
            prepStmt.setString(16, request.getRespCode());
            prepStmt.setString(17, request.getRespDesc());
            prepStmt.setString(18, request.getTokenKey());
            prepStmt.setString(19, request.getTraceTransfer());
            prepStmt.setString(20, request.getUserName());
            logger.info("Insert to database: {}", MessageConfig.SQL_INSERT);
            int rowsInserted = prepStmt.executeUpdate();

            if (rowsInserted > 0){
                logger.info("Insert to database success");
                result = true;
            }

        } catch (SQLException e) {
            logger.error("Can not insert data to database: ", e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (connectionDB != null) {
                    connectionDB.close();
                }
            } catch (SQLException ex) {
                logger.error("Can not close connection: ", ex);
            }
            return result;
        }
    }
}
