package com.desktopapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// cria um modelo para o objeto produto

@Entity
@Table(name = "tbProducts")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    public Long getidProduct() {
        return idProduct;
    }

    public void setidProduct(Long id) {
        this.idProduct = id;
    }
    private String nameProd;

    public String getNameProd() {
        return nameProd;
    }

    public void setNameProd(String name) {
        this.nameProd = name;
    }
    private Double priceProd;

    public Double getPriceProd() {
        return priceProd;
    }

    public void setPriceProd(Double price) {
        this.priceProd = price;
    }
}
