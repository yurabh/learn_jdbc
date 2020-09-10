package com.dao.mysqlClasses;

import com.dao.AbstractDao;
import com.dao.DaoException;
import com.domain.User;
import com.domain.UserState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User, String> {
    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getInsertQuery() {
        String insertQuery = "INSERT INTO `user` (`login`, `password`, `email`) VALUES" +
                "(?, ?, ?)";
        return insertQuery;
    }

    @Override
    protected String getSelectQuery() {
        String selectLastRecord = "SELECT * FROM `user` WHERE login = ?";
        return selectLastRecord;
    }

    @Override
    protected String getDeleteQuery() {
        String qerySelect = "DELETE FROM `user` WHERE `login` = ? AND password = ?";
        return qerySelect;
    }

    @Override
    protected String getUpdateQuery() {
        String queryUpdate = "UPDATE `user` SET  password = ? ,email = ? WHERE  login  = ?";
        return queryUpdate;
    }

    @Override
    protected String getSelectAll() {
        String allQery = "SELECT * From `user`";
        return allQery;
    }

    @Override
    protected void setInsertStatement(PreparedStatement preparedStatement, User object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getLogin());
            preparedStatement.setString(2, object.getPassword());
            preparedStatement.setString(3, object.getEmail());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void setSelectStateament(PreparedStatement preparedStatement, User object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getLogin());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void setStatementKey(PreparedStatement preparedStatement, String key) throws DaoException {
        try {
            preparedStatement.setString(1, key);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void setDeleteStatement(PreparedStatement preparedStatement, User object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getLogin());
            preparedStatement.setString(2, object.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUpadateStatement(PreparedStatement preparedStatement, User object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getPassword());
            preparedStatement.setString(2, object.getEmail());
            preparedStatement.setString(3, object.getLogin());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws DaoException {
        List<User> users = new ArrayList<>();
        try {

            while ((resultSet.next())) {
                User user = new User();
                user.setLogin(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                user.setStatus(UserState.valueOf(resultSet.getString("status")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }
}
