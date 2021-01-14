package com.dao.dao_factory;

import com.dao.DaoGeneric;
import com.exception.DaoException;
import org.apache.log4j.Logger;
import com.dao.DaoCreator;
import com.dao.DaoFactory;
import com.dao.impl.ProductDao;
import com.dao.impl.UserDao;
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
    private Map<Class, DaoCreator> daos;

    public MySqlDaoFactory() {
        loadDbProperties();
        driverRegistration();
        loadDaos();
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            log.info("Getting connection...");
            connection = DriverManager.getConnection(DB, USER, PASSWORD);
            log.info("Connection done!!!");
        } catch (SQLException e) {
            log.error("Problem with connection: !!!");
            log.error(e.getMessage());
        }
        return connection;
    }

    @Override
    public DaoGeneric getUserDao(Connection connection) {
        return new UserDao(connection);
    }

    @Override
    public DaoGeneric getProductDao(Connection connection) {
        return new ProductDao(connection);
    }

    @Override
    public DaoGeneric getDao(Connection connection, Class daoClass) throws DaoException {
        DaoCreator daoCreator = daos.get(daoClass);
        if (daoCreator == null) {
            throw new DaoException("Dao for class " + daoClass + "not found");
        }
        return daoCreator.create(connection);
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
        daos.put(User.class, new DaoCreator() {
            @Override
            public DaoGeneric create(Connection connection) {
                return new UserDao(connection);
            }
        });

        daos.put(Product.class, new DaoCreator() {
            @Override
            public DaoGeneric create(Connection connection) {
                return new ProductDao(connection);
            }
        });
    }
}
