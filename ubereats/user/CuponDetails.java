package com.example.ubereats.user;

import java.util.HashMap;
import java.util.Map;

public class CuponDetails {

    private String ncup,codc;
    private int canticup;
    private static CuponDetails addetails;

    public CuponDetails(String ncup, String codc, Integer canticup) {

        this.ncup = ncup;
        this.codc = codc;
        this.canticup = canticup;

    }

    public static CuponDetails of(Map<String, Object> data) {
        String ncup, codc;
        int canticup;

        ncup = (String) data.get("Nombre Cupon");
        codc = (String)data.get("Codigo Cupon");
        canticup = ((Number)data.get("Cantidad")).intValue();



        CuponDetails addetails = new CuponDetails(ncup,codc,canticup);

        return addetails;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Nombre Cupon", ncup);
        map.put("Codigo Cupon", codc);
        map.put("Cantidad", canticup);

        return map;
    }

    private CuponDetails() {

    }

    public static CuponDetails getInstance() {
        if (addetails == null)
            addetails = new CuponDetails();
        return addetails;
    }

    public String getNombreCupon() {
        return ncup;
    }

    public String getCodigoCupon() {
        return codc;
    }

    public int getCantidad() {
        return canticup;
    }


    public void setNombreCupon(String ncup) {
        this.ncup = ncup;
    }

    public void setCodigoCupon(String codc) {
        this.codc = codc;
    }

    public void setCantidad(int canticup) {
        this.canticup = canticup;
    }
}