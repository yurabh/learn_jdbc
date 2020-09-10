package com.dao.mysqlClasses;

import com.dao.AbstractDao;
import com.dao.DaoException;
import com.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends AbstractDao<Product, String> {

    public ProductDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getInsertQuery() {
        String insertProduct = "INSERT INTO `product` (`product_name`,`product_brand`,`date_manifactore`,`end_datasecond`,`end_date`) VALUES " +
                "(?,?,?,?,?)";
        return insertProduct;
    }

    @Override
    protected String getSelectQuery() {
        String selectQuery = "SELECT * FROM `product` WHERE product_name = ?";
        return selectQuery;
    }

    @Override
    protected String getDeleteQuery() {
        String deleteQuery = "DELETE FROM `product` WHERE product_name= ? AND product_brand = ?";
        return deleteQuery;
    }

    @Override
    protected String getUpdateQuery() {
        String updateQury = "UPDATE `product` SET product_brand=? , date_manifactore = ?  WHERE `product_name` = ?";
        return updateQury;
    }

    @Override
    protected String getSelectAll() {
        String selectAll = "SELECT * FROM `product`";
        return selectAll;
    }

    @Override
    protected void setInsertStatement(PreparedStatement preparedStatement, Product object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getNameProduct());
            preparedStatement.setString(2, object.getBrandProduct());
            preparedStatement.setString(3, String.valueOf(object.getDate()));
            preparedStatement.setString(4, String.valueOf(object.getEndDateSecond()));
            preparedStatement.setString(5, String.valueOf(object.getEndDate()));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void setStatementKey(PreparedStatement preparedStatement, String key) throws DaoException {
        try {
            preparedStatement.setString(1, key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setSelectStateament(PreparedStatement preparedStatement, Product object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getNameProduct());
        } catch (SQLException e) {
            throw new DaoException("isnt");
        }
    }

    @Override
    protected void setUpadateStatement(PreparedStatement preparedStatement, Product object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getBrandProduct());
            preparedStatement.setString(2, String.valueOf(object.getEndDate()));
            preparedStatement.setString(3, object.getNameProduct());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setDeleteStatement(PreparedStatement preparedStatement, Product object) throws DaoException {
        try {
            preparedStatement.setString(1, object.getNameProduct());
            preparedStatement.setString(2, object.getBrandProduct());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Product> parseResultSet(ResultSet resultSet) throws DaoException {
        List<Product> products = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Product product = new Product();
                product.setNameProduct(resultSet.getString(1));
                product.setBrandProduct(resultSet.getString(2));
                //product.setDate(resultSet.getDate(3));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
