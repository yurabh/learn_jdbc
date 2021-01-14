package com.dao;

import com.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory {

    Connection getConnection() throws SQLException;

    DaoGeneric getUserDao(Connection connection) throws SQLException;

    DaoGeneric getProductDao(Connection connection) throws SQLException;

    DaoGeneric getDao(Connection connection, Class daoClass) throws DaoException;
}
