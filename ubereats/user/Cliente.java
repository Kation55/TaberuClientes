package com.example.ubereats.user;

import com.google.firebase.Timestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class  Cliente
{

    private String nombre;
    private String email, telefono;

    private Timestamp diaCreacion;
    private static Cliente cliente;
    private double puntuacion;

    public Cliente(String nombre, String email, String telefono) {

        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;

        BigDecimal bd = new BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP);
        this.puntuacion = 4 + bd.doubleValue();
        this.diaCreacion = Timestamp.now();
    }

    public static Cliente of (Map<String, Object> data) {
        String nombre, email, telefono;

        double puntuacion;
        Timestamp diaCreacion;

        nombre = (String) data.get("Nombre");
        email = (String) data.get("Email");
        telefono = (String) data.get("Telefono");
        diaCreacion = (Timestamp) data.get("DiaCreacion");
        puntuacion = (Double) data.get("Puntuacion");
        Cliente cliente = new Cliente(nombre, email, telefono);

        cliente.setPuntuacion(puntuacion);
        cliente.setDiaCreacion(diaCreacion);

        return cliente;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Nombre", nombre);
        map.put("Email", email);
        map.put("Telefono", telefono);
        map.put("DiaCreacion", diaCreacion);
        map.put("Puntuacion", puntuacion);

        return map;
    }

    private Cliente()
    {

    }

    public static Cliente getInstance()
    {
        if(cliente == null)
            cliente = new Cliente();
        return cliente;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public String getEmail() {
        return email;
    }

    public  String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Timestamp getDiaCreacion() {
        return diaCreacion;
    }

    public void setDiaCreacion(Timestamp diaCreacion) {
        this.diaCreacion = diaCreacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public static void setCliente(Cliente cliente) {
        Cliente.cliente = cliente;
    }
}
