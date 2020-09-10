package com.jdbc;

import com.domain.User;
import com.domain.UserState;

import java.sql.*;
import java.util.Scanner;

public class UserCrudService {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB = "jdbc:mysql://localhost:3306/shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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

    public static void insertIntoPrepareStatement() {
        String insertQuery = "INSERT INTO `user` (`login`, `password`, `email`) VALUES" +
                "(?, ?, ?)";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = sc.next();
        System.out.println("Enter paswword");
        String password = sc.next();
        System.out.println("Enter email");
        String email = sc.next();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String login = sc.next();
        System.out.println("Enter paswword");
        String password = sc.next();
        System.out.println("Enter email");
        String email = sc.next();
        String insertQuery = "INSERT INTO `user` (`login`, `password`, `email`) VALUES" +
                "('" + login + "','" + password + "','" + email + "')";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            int value = statement.executeUpdate(insertQuery);
            System.out.println("Records insert: " + value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter password");
        String Password = sc.next();
        System.out.println("Enter login: ");
        String Login = sc.next();
        String sqlUpdate = "UPDATE `user` SET `password`=  ('" + Password + "')   WHERE  `login`= ('" + Login + "')";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter login: ");
        String Login = sc.next();
        String sqlDelete = "DELETE FROM `user`" + "WHERE `login`= ('" + Login + "')";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectUsers() {
        String selectQuery = "SELECT * FROM `user`;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
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
        insertUser();
        //updateUser();
        //delete();
        //insertIntoPrepareStatement();
        //selectUsers();
    }
}
