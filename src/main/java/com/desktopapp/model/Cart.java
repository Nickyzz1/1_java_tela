package com.desktopapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// cria um modelo para o objeto cart

@Entity
@Table(name = "tbCart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCart;

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long id) {
        this.idCart = id;
    }

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
    private Double valueProd;

    public Double getValueProd() {
        return valueProd;
    }

    public void setValueProd(Double value) {
        this.valueProd = value;
    }

    private int quantProd;

    public int getQuant() {
        return quantProd;
    }

    public void setQuat(int quant) {
        this.quantProd = quant;
    }
}
