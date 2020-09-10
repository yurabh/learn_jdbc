package com.dao;

import org.apache.log4j.Logger;
import com.dao.interfaces.DaoFactory;
import com.dao.interfaces.DaoGenerick;
import com.domain.User;
import com.domain.UserState;

import java.sql.SQLException;

public class DaoRunner {

    private static final Logger log = Logger.getLogger(DaoRunner.class);

    public static void main(String[] args) {
        try {
            DaoFactory factory = new MySqlDaoFactory();
            DaoGenerick daoGenerick = factory.getDao(factory.getConnection(), User.class);
            User user = new User();
            user.setLogin("Nazar661");
            user.setPassword("yyyyyyyey");
            user.setEmail("5555@gmail");
            user.setStatus(UserState.ACTIVE.NEW);
            user = (User) daoGenerick.create(user);
            System.out.println(user);


//
//            DaoGenerick DaoGenerick1 = factory.getUserDao(factory.getConnection());
//            User user1 = (User) DaoGenerick1.read("yura");
//            System.out.println(user1);

//
//            DaoGenerick<User, String> DaoGenerick2 = factory.getUserDao(factory.getConnection());
//            User user2 = new User();
//            user2.setLogin("5555");
//            user2.setPassword("5555");
//            user2.setEmail("5555");
//            user2.setStatus(UserState.NEW);
//            DaoGenerick2.delete(user2);
//
//
//            DaoGenerick<User, String> DaoGenerick3 = factory.getUserDao(factory.getConnection());
//            User user6 = new User();
//            user6.setLogin("1");
//            user6.setPassword("11111");
//            user6.setEmail("11111");
//            user6.setStatus(UserState.NEW);
//            DaoGenerick3.update(user6);
//
//
//            DaoGenerick daoGenerick4 = factory.getUserDao(factory.getConnection());
//            List<User> users = daoGenerick4.getAll();
//            for (User item : users) {
////                System.out.println(item);
////            }
////
////


//            DaoGenerick daoGenerick7 = factory.getDao(factory.getConnection(), Product.class);
//            Product product = new Product();
//            product.setNameProduct("yura6");
//            product.setBrandProduct("t1111761");
//            product.setDate(LocalDate.now());
//            product.setEndDateSecond(LocalDate.of(2019, 5, 15));
//            product.setEndDate(LocalDate.of(2020, 7, 12));
//            Product product1 = (Product) daoGenerick7.create(product);
//            System.out.println(product1);


//
//            DaoGenerick daoGenerick9 = factory.getProductDao(factory.getConnection());
//            Product product2 = (Product) daoGenerick9.read("Samsung");
//            System.out.println(product2);


//            Product product3 = new Product();
//            product3.setNameProduct("Samsung");
//            product3.setBrandProduct("111");
//            product3.setDate(LocalDate.of(2011, 5, 22));
//            product3.setEndDateSecond(LocalDate.of(2019, 5, 11));
//            product3.setEndDate(LocalDate.of(2020, 7, 12));
//            DaoGenerick daoGenerick10 = factory.getProductDao(factory.getConnection());
//            daoGenerick10.update(product3);


            //DaoGenerick daoGenerick8 = factory.getProductDao(factory.getConnection());
            //daoGenerick8.delete(product);

        } catch (
                DaoException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
