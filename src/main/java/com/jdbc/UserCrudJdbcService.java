package com.jdbc;

import com.domain.User;
import com.domain.UserState;

import java.sql.*;
import java.util.Scanner;

public class UserCrudJdbcService {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB = "jdbc:mysql://localhost:3306/learn_jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void insertUserStatement() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();
        System.out.println("Enter email: ");
        String email = sc.next();
        System.out.println("Enter firstName: ");
        String firstName = sc.next();
        System.out.println("Enter lastName: ");
        String lastName = sc.next();
        UserState status = UserState.NEW;

        String insertQuery = "INSERT INTO `users` (`login`, `password`, `firstName`,`lastName` , `email` ,`status`)" +
                " VALUES ('" + login + "','" + password + "','" + firstName + "','" + lastName + "','" + email + "','"
                + status + "')";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            System.out.println("Records insert: " + statement.executeUpdate(insertQuery));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoPrepareStatement() {
        String insertQuery = "INSERT INTO `users` (`login`, `password`, `firstName`,`lastName` ,`email` ,`status`)" +
                " VALUES (?, ?, ? ,?, ?, ?)";

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();
        System.out.println("Enter email: ");
        String email = sc.next();
        System.out.println("Enter firstName: ");
        String firstName = sc.next();
        System.out.println("Enter lastName: ");
        String lastName = sc.next();
        UserState status = UserState.NEW;

        try (Connection connection = getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, status.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();

        String sqlUpdate = "UPDATE `users` SET `password`=  ('" + password + "')   " +
                " WHERE  `login`= ('" + login + "')";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = sc.next();

        String sqlDelete = "DELETE FROM `users`" + "WHERE `login`= ('" + login + "')";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectUsers() {
        String selectQuery = "SELECT * FROM `users`;";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                User u = new User();
                u.setLogin(resultSet.getString("login"));
                u.setPassword(resultSet.getString(2));
                u.setEmail(resultSet.getString(3));
                u.setStatus(UserState.valueOf(resultSet.getString("status")));
                System.out.println(u.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertUserStatement();
        insertIntoPrepareStatement();
        updateUser();
        delete();
        selectUsers();
    }
}
