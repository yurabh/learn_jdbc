package com.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Product {

    private String nameProduct;

    private String brandProduct;

    private LocalDate date;

    private LocalDate endDateSecond;

    private LocalDate endDate;

    public Product() {
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getBrandProduct() {
        return brandProduct;
    }

    public void setBrandProduct(String brandProduct) {
        this.brandProduct = brandProduct;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getEndDateSecond() {
        return endDateSecond;
    }

    public void setEndDateSecond(LocalDate endDateSecond) {
        this.endDateSecond = endDateSecond;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(nameProduct, product.nameProduct) &&
                Objects.equals(brandProduct, product.brandProduct) &&
                Objects.equals(date, product.date) &&
                Objects.equals(endDateSecond, product.endDateSecond) &&
                Objects.equals(endDate, product.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameProduct, brandProduct, date, endDateSecond, endDate);
    }

    @Override
    public String toString() {
        return "Product{" +
                "nameProduct='" + nameProduct + '\'' +
                ", brandProduct='" + brandProduct + '\'' +
                ", date=" + date +
                ", endDateSecond=" + endDateSecond +
                ", endDate=" + endDate +
                '}';
    }
}
