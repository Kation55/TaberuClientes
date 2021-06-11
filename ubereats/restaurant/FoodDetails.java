package com.example.ubereats.restaurant;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;


public class FoodDetails{

    public String nombre,ingredientes,descripcion,imageURL,categoria,boolportada;
    public String itemId;
    public String restaurantId;
    public int precio;
    public Bitmap bitmap;

    public FoodDetails(String nombre, String ingredientes, int precio, String descripcion, String categoria,String boolportada,String imageURL) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.categoria = categoria;
        this.boolportada = boolportada;
        this.imageURL=imageURL;


    }

    public static FoodDetails of (Map<String, Object> data) {

        String nombre,ingredientes,descripcion,imageURL,categoria,boolportada;
        int precio;

        double puntuacion;
        Timestamp diaCreacion;

        nombre = (String) data.get("Nombre");
        descripcion = (String) data.get("Descripcion");
        ingredientes = (String) data.get("Ingredientes");
        precio = ((Number)data.get("Precio")).intValue();
        categoria = (String) data.get("Categoria");
        imageURL = (String)data.get("ImageURL");

        FoodDetails foodDetails = new FoodDetails(nombre, ingredientes, precio, descripcion,
                categoria, "",imageURL);

        return foodDetails;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Nombre", nombre);
        map.put("Descripcion", descripcion);
        map.put("Ingredientes", ingredientes);
        map.put("Precio", precio);
        map.put("Categoria", categoria);
        map.put("EsPortada", boolportada);
        map.put("ImageURL", imageURL);

        return map;
    }



    public String getIngredientes() {
        return ingredientes;
    }

    public int getPrecio() {
        return precio;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEsPortada() {
        return boolportada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getNombre() {
        return nombre;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEsPortada(String boolportada) {
        this.boolportada = boolportada;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}