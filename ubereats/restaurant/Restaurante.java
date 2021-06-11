package com.example.ubereats.restaurant;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Restaurante{

    private String nombre, descripcion, calle, ciudad,
            house, estado, email, telefono, categoria, id;
    private int cpp;
    private Bitmap bitmap;

    private double puntuacion;
    private Timestamp diaCreacion;

    public Restaurante(String nombre, String descripcion, String calle, String ciudad,
                       String house, int cpp, String estado, String email, String telefono,
                       String categoria) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.calle = calle;
        this.ciudad = ciudad;
        this.house = house;
        this.cpp = cpp;
        this.estado = estado;
        this.email = email;
        this.telefono = telefono;
        this.categoria = categoria;

        BigDecimal bd = new BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP);
        this.puntuacion = 4 + bd.doubleValue();
        this.diaCreacion = Timestamp.now();
    }

    public static Restaurante of (Map<String, Object> data) {
        String nombre, descripcion, calle, ciudad,
                house, estado, email, telefono, categoria;

        int cpp;

        double puntuacion;
        Timestamp diaCreacion;

        nombre = (String) data.get("Nombre");
        descripcion = (String) data.get("Descripcion");
        calle = (String) data.get("Calle");
        ciudad = (String) data.get("Ciudad");
        house = (String) data.get("House");
        cpp = ((Number) data.get("Cpp")).intValue();
        estado = (String) data.get("Estado");
        email = (String) data.get("Email");
        telefono = (String) data.get("Telefono");
        categoria = (String) data.get("Categoria");
        puntuacion = (Double) data.get("Puntuacion");
        diaCreacion = (Timestamp) data.get("DiaCreacion");

        Restaurante restaurante = new Restaurante(nombre, descripcion, calle, ciudad,
                house, cpp, estado, email, telefono, categoria);

        restaurante.setPuntuacion(puntuacion);
        restaurante.setDiaCreacion(diaCreacion);

        return restaurante;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Nombre", nombre);
        map.put("Descripcion", descripcion);
        map.put("Calle", calle);
        map.put("Ciudad", ciudad);
        map.put("House", house);
        map.put("Cpp", cpp);
        map.put("Estado", estado);
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

    public String getCalle() {
        return calle;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getCpp() {
        return cpp;
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

    public String getEstado() {
        return estado;
    }

    public String getHouse() {
        return house;
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

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCpp(int cpp) {
        this.cpp = cpp;
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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setHouse(String house) {
        this.house = house;
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