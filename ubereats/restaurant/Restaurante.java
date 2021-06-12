package com.example.ubereats.restaurant;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Restaurante{

    private String nombre, descripcion, email, telefono, categoria, id;
    private int cpp;
    private Bitmap bitmap;

    private double puntuacion;
    private Timestamp diaCreacion;

    public Restaurante(String nombre, String descripcion, String email, String telefono,
                       String categoria) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.email = email;
        this.telefono = telefono;
        this.categoria = categoria;

        BigDecimal bd = new BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP);
        this.puntuacion = 4 + bd.doubleValue();
        this.diaCreacion = Timestamp.now();
    }

    public static Restaurante of (Map<String, Object> data) {
        String nombre, descripcion, email, telefono, categoria;

        int cpp;

        double puntuacion;
        Timestamp diaCreacion;

        nombre = (String) data.get("Nombre");
        descripcion = (String) data.get("Descripcion");
        email = (String) data.get("Email");
        telefono = (String) data.get("Telefono");
        categoria = (String) data.get("Categoria");
        puntuacion = (Double) data.get("Puntuacion");
        diaCreacion = (Timestamp) data.get("DiaCreacion");

        Restaurante restaurante = new Restaurante(nombre, descripcion, email, telefono, categoria);

        restaurante.setPuntuacion(puntuacion);
        restaurante.setDiaCreacion(diaCreacion);

        return restaurante;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Nombre", nombre);
        map.put("Descripcion", descripcion);
        map.put("Email", email);
        map.put("Telefono", telefono);
        map.put("Categoria", categoria);
        map.put("Puntuacion", puntuacion);
        map.put("DiaCreacion", diaCreacion);

        return map;
    }

    public double getPuntuacion() {
        return puntuacion;
    }


    public String getCategoria() {
        return categoria;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Timestamp getDiaCreacion() {
        return diaCreacion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDiaCreacion(Timestamp diaCreacion) {
        this.diaCreacion = diaCreacion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
