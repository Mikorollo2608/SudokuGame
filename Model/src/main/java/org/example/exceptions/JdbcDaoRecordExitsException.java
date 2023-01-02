package org.example.exceptions;

public class JdbcDaoRecordExitsException extends DaoException {
    public JdbcDaoRecordExitsException(String message) {
        super(message);
    }
}
