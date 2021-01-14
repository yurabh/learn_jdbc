package com.dao.impl;

import com.dao.DaoGeneric;
import com.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T, V> implements DaoGeneric<T, V> {

    private static final Logger log = Logger.getLogger(AbstractDao.class);

    private Connection connection = null;

    protected abstract String getInsertQuery();

    protected abstract String getSelectQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getSelectAll();

    protected abstract void setInsertStatement(PreparedStatement preparedStatement, T object) throws DaoException;

    protected abstract void setStatementKey(PreparedStatement preparedStatement, V key) throws DaoException;

    protected abstract void setSelectStatement(PreparedStatement preparedStatement, T object) throws DaoException;

    protected abstract void setDeleteStatement(PreparedStatement preparedStatement, T object) throws DaoException;

    protected abstract void setUpdateStatement(PreparedStatement preparedStatement, T object) throws DaoException;

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws DaoException;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T create(T object) throws DaoException {
        String insertQuery = getInsertQuery();
        String selectLastRecord = getSelectQuery();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            log.trace("Insert into our DВ..");
            setInsertStatement(preparedStatement, object);
            int rows = preparedStatement.executeUpdate();
            log.trace("Finish insert our query");
            if (rows != 1) {
                throw new DaoException("Created more than one record" + rows);
            }
        } catch (SQLException e) {
            log.error("problem with inserting our query into our Db");
            log.error(e.getMessage());
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectLastRecord)) {
            log.trace("Find query in our Db");
            setSelectStatement(preparedStatement, object);
            ResultSet resultSet = preparedStatement.executeQuery();
            log.trace("We are putting our query into the object User");
            List<T> objects = parseResultSet(resultSet);
            if (objects == null || objects.size() != 1) {
                throw new DaoException("Returned more than one record");
            }
            object = objects.iterator().next();
            log.trace("Our object is right we returned this object");
        } catch (SQLException e) {
            log.error("We aren't putting this query into the object User");
            log.error(e.getMessage());
        }
        return object;
    }

    @Override
    public T read(V key) throws DaoException {
        String readQuery = getSelectQuery();
        List<T> users = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(readQuery)) {
            log.trace("Find in Db");
            setStatementKey(preparedStatement, key);
            log.trace("Read with Data Base");
            ResultSet resultSet = preparedStatement.executeQuery();
            users = parseResultSet(resultSet);
            log.trace("Created User for returned");
        } catch (SQLException e) {
            log.error("We couldn't find and read query in Db");
            log.error(e.getMessage());
        }

        if (users == null || users.size() == 0) {
            throw new DaoException("Record with key " + key + "not found");
        }

        if (users.size() > 1) {
            throw new DaoException("Returned more than one record");
        }
        return users.iterator().next();
    }

    @Override
    public void delete(T object) throws DaoException {
        String findQuery = getDeleteQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            log.trace("We found query for deleting");
            setDeleteStatement(preparedStatement, object);
            preparedStatement.executeUpdate();
            log.trace("This query which we found is deleted");
        } catch (SQLException e) {
            log.error("we couldn't deleted query");
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(T object) throws DaoException {
        String updateQuery = getUpdateQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            log.trace("Find query in DataBase");
            setUpdateStatement(preparedStatement, object);
            preparedStatement.executeUpdate();
            log.trace("Query is found and updated");
        } catch (SQLException e) {
            log.trace("We couldn't update query in this db");
            log.error(e.getMessage());
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        String selectAll = getSelectAll();
        List<T> users = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            users = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
