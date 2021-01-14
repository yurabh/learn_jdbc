package com.dao.runner;

import com.dao.DaoFactory;
import com.dao.DaoGeneric;
import com.dao.dao_factory.MySqlDaoFactory;
import com.domain.Product;
import com.exception.DaoException;
import com.domain.User;
import com.domain.UserState;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DaoRunner {

    public static void main(String[] args) {
        try {

            DaoFactory factory = new MySqlDaoFactory();
            DaoGeneric<User, String> daoGeneric = factory.getDao(factory.getConnection(), User.class);
            User user = new User("Andre", "andre", "job", "maria",
                    "nazar@1234", UserState.ACTIVE);
            user = daoGeneric.create(user);
            System.out.println(user.toString());


            DaoGeneric<User, String> daoGenericTwo = factory.getUserDao(factory.getConnection());
            User userFind = daoGenericTwo.read("Andre");
            System.out.println(userFind.toString());


            DaoGeneric<User, String> daoGenericThree = factory.getUserDao(factory.getConnection());
            User userFoDeleting = new User();
            userFoDeleting.setLogin("Andre");
            userFoDeleting.setPassword("11112");
            daoGenericThree.delete(userFoDeleting);


            DaoGeneric<User, String> daoGenericFour = factory.getUserDao(factory.getConnection());
            User userUpdate = new User();
            userUpdate.setLogin("Andre");
            userUpdate.setPassword("Andre");
            userUpdate.setEmail("job");
            daoGenericFour.update(userUpdate);

            DaoGeneric daoGenericFive = factory.getUserDao(factory.getConnection());
            List<User> users = daoGenericFive.getAll();
            users.forEach(i -> System.out.println(i));


            DaoGeneric<Product, String> daoGenericProduct = factory.getDao(factory.getConnection(), Product.class);
            Product product = new Product("orange", "boom",
                    LocalDate.now(),
                    LocalDate.of(2019, 5, 15),
                    LocalDate.of(2020, 7, 12));

            Product productCreated = daoGenericProduct.create(product);
            System.out.println(productCreated);


            DaoGeneric<Product, String> daoGenericProductTwo = factory.getProductDao(factory.getConnection());
            Product productFound = daoGenericProductTwo.read("beer");
            System.out.println(productFound.toString());


            DaoGeneric<Product, String> daoGenericProductThree = factory.getProductDao(factory.getConnection());
            Product productForUpdating = new Product();
            productForUpdating.setNameProduct("beer");
            productForUpdating.setBrandProduct("tomas");
            productForUpdating.setEndDate(LocalDate.of(2011, 5, 22));
            daoGenericProductThree.update(productForUpdating);


            DaoGeneric<Product, String> daoGenericProductDelete = factory.getProductDao(factory.getConnection());
            Product productDelete = new Product();
            productDelete.setNameProduct("beer");
            productDelete.setBrandProduct("tomas");
            daoGenericProductDelete.delete(productDelete);


            DaoGeneric<Product, String> daoGenericProductFive = factory.getProductDao(factory.getConnection());
            List<Product> products = daoGenericProductFive.getAll();
            products.forEach(i -> System.out.println(i));

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
