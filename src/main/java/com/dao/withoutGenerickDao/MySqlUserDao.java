package com.dao.withoutGenerickDao;

import org.apache.log4j.Logger;
import com.dao.interfaces.DaoGenerick;
import com.domain.User;
import com.domain.UserState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements DaoGenerick<User, String> {
    private static final Logger log = Logger.getLogger(MySqlUserDao.class);
    private Connection connection;

    public MySqlUserDao(Connection connection) throws SQLException {
        if (connection == null)
            throw new SQLException("Empty Connection for MysqlUserDao");
        this.connection = connection;
    }

    @Override
    public User create(User user) {
//        Scanner sc = new Scanner(System.in);
        String insertQuery = "INSERT INTO `user` (`login`, `password`, `email`) VALUES" +
                "(?, ?, ?)";
        String selectLastRecord = "SELECT * FROM `user` WHERE login = ? ";
//        System.out.println("Enter login: ");
//        String login = sc.next();
//        System.out.println("Enter paswword");
//        String password = sc.next();
//        System.out.println("Enter email");
//        String email = sc.next();
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            log.trace("Insert (login in our query and password and email) into our Db");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            int rows = preparedStatement.executeUpdate();
            log.trace("Finish insert our query");
            if (rows != 1) {
                throw new SQLException("Created more than one record" + rows);
            }
        } catch (SQLException e) {
            log.error("problem with inserting our qury into our Db");
            log.error(e.getMessage());
        }

        User user1 = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectLastRecord)) {
            log.trace("Find  query in our Db");
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            log.trace("We are puting our query into the objec User");
            user1.setLogin(resultSet.getString("login"));
            user1.setPassword(resultSet.getString("password"));
            user1.setEmail(resultSet.getString("email"));
            user1.setStatus(UserState.valueOf(resultSet.getString("status")));
            log.trace("Our object is right we returned this object");
            return user1;
        } catch (SQLException e) {
            log.error("We aren'n puting this query into the object User");
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public User read(String login) {
        User user1 = new User();
        String readQuery = "SELECT * FROM `user` WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(readQuery)) {
            log.trace("We find query with our data base");
            preparedStatement.setString(1, login);
            log.trace("We reads with key login our query");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            log.trace("We are puting our query into the objec User");
            user1.setLogin(resultSet.getString(1));
            user1.setPassword(resultSet.getString(2));
            user1.setEmail(resultSet.getString(3));
            user1.setStatus(UserState.valueOf(resultSet.getString("status")));
            log.trace("Query in the Object User a returned object User");
        } catch (SQLException e) {
            log.error("Probjec with find or with puting a query in the Object User");
            log.error(e.getMessage());
        }
        return user1;
    }

    @Override
    public void delete(User user) {
        String qerySelect = "DELETE FROM `user` WHERE `login` = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(qerySelect)) {
            log.info("Find query in our Data base");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.executeUpdate();
            log.info("Is our query deleted");
        } catch (SQLException e) {
            log.error("We aren't find and delete object in the Data Base");
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter please your new login");
//        String login = sc.next();
//        System.out.println("Enter please your new password");
//        String password = sc.next();
//        System.out.println("Enter please your new email");
//        String email = sc.next();
        User user1 = new User();
        String queryUpdate = "UPDATE `user` SET  `password = ?`,`email` = ?" +
                "WHERE  login = ?";
        String qverySelect = "SELECT * FROM `user` WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(qverySelect);) {
            log.trace("We reads our query there in data base for puting in our Object User");
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user1.setLogin(resultSet.getString("login"));
            user1.setPassword(resultSet.getString("password"));
            user1.setEmail(resultSet.getString("email"));
            user1.setStatus(UserState.valueOf(resultSet.getString("status")));
        } catch (SQLException e) {
            log.error("we are not puting that query in the db");
            log.error(e.getMessage());
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate)) {
            preparedStatement.setString(1, user.getLogin());
            log.trace("we update this data base");
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("is theres databa dont  have our saput : " + rows);
            }
            log.trace("This is data base update");
        } catch (SQLException e) {
            log.error("Not update query in this data base");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        String allQery = "SELECT * From `user`";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(allQery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while ((resultSet.next())) {
                User user1 = new User();
                user1.setLogin(resultSet.getString(1));
                user1.setPassword(resultSet.getString(2));
                user1.setEmail(resultSet.getString(3));
                users.add(user1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
