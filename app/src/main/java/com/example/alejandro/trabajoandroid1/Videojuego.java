package com.example.alejandro.trabajoandroid1;

import java.io.Serializable;

/**
 * Created by Alejandro on 18/12/2016.
 */

public class Videojuego implements Serializable{
    private Integer Id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String tipo;
    private Integer img;


    public Videojuego(Integer id, String nombre, Integer stock, Double precio, String tipo, Integer img) {
        Id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.tipo = tipo;
        this.img = img;

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", tipo='" + tipo + '\'' +
                ", img=" + img +
                '}';
    }
}
