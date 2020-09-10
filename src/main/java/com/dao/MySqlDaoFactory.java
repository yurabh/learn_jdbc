package com.dao;

import org.apache.log4j.Logger;
import com.dao.interfaces.DaoCreatar;
import com.dao.interfaces.DaoFactory;
import com.dao.interfaces.DaoGenerick;
import com.dao.mysqlClasses.ProductDao;
import com.dao.mysqlClasses.UserDao;
import com.domain.Product;
import com.domain.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {
    private String DRIVER;
    private String DB;
    private String USER;
    private String PASSWORD;
    private static final Logger log = Logger.getLogger(MySqlDaoFactory.class);
    private Map<Class, DaoCreatar> daos;

    public MySqlDaoFactory() {
        loadDbProperties();
        driverRegistration();
        loadDaos();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            log.info("Getting connection...");
            connection = DriverManager.getConnection(DB, USER, PASSWORD);
            log.info("Connection done!!!");
        } catch (SQLException e) {
            log.error("PROBLEM WITH CONNECTION!!!");
            log.error(e.getMessage());
        }
        return connection;
    }

    @Override
    public DaoGenerick getUserDao(Connection connection) throws SQLException {
        return new UserDao(connection);
    }

    @Override
    public DaoGenerick getDao(Connection connection, Class daoClass) throws DaoException {
        DaoCreatar daoCreater = daos.get(daoClass);
        if (daoCreater == null) {
            throw new DaoException("Dao for class " + daoClass + "not found");
        }
        return daoCreater.create(connection);
    }

    @Override
    public DaoGenerick getProductDao(Connection connection) throws SQLException {
        return new ProductDao(connection);
    }

    private void loadDbProperties() {
        Properties properties = new Properties();
        try {
            log.info("Getting db properties from file");
            properties.load(MySqlDaoFactory.class.getResourceAsStream("/db.properties"));
            DRIVER = properties.getProperty("DRIVER");
            DB = properties.getProperty("DB");
            USER = properties.getProperty("USER");
            PASSWORD = properties.getProperty("PASSWORD");
            log.info("Loading db properties done");
        } catch (IOException e) {
            log.error("Problem with getting db properties from file");
            log.error(e.getStackTrace());
        }
    }

    private void driverRegistration() {
        try {
            log.info("Starting JDBC driver registration");
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("Problem with driver registration ");
            log.error(e.getStackTrace());
        }
    }

    private void loadDaos() {
        daos = new HashMap<>();
        daos.put(User.class, new DaoCreatar() {
            @Override
            public DaoGenerick create(Connection connection) {
                return new UserDao(connection);
            }
        });

        daos.put(Product.class, new DaoCreatar() {
            @Override
            public DaoGenerick create(Connection connection) {
                return new ProductDao(connection);
            }
        });
    }
}
