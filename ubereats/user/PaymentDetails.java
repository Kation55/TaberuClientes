package com.example.ubereats.user;

import java.util.HashMap;
import java.util.Map;

public class PaymentDetails {

    private String ncard,dvto,cvv,type,country, id;
    private int cpp;
    private static PaymentDetails addetails;

    public PaymentDetails(String ncard, String dvto, String cvv, String type
            , String country, Integer cpp) {

        this.ncard = ncard;
        this.dvto = dvto;
        this.cvv = cvv;
        this.type = type;
        this.country = country;
        this.cpp = cpp;

    }

    public static PaymentDetails of(Map<String, Object> data) {
        String ncard, dvto, cvv,type,country;
        int cpp;

        ncard = (String) data.get("Numero de Tarjeta");
        dvto = (String)data.get("Fecha de vto");
        cvv = (String)data.get("CVV");
        type = (String)data.get("Tipo");
        country = (String)data.get("Pais");
        cpp = ((Number)data.get("CPP")).intValue();



        PaymentDetails addetails = new PaymentDetails(ncard,dvto,cvv,type,country,cpp);

        return addetails;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Numero de Tarjeta", ncard);
        map.put("Fecha de vto", dvto);
        map.put("CVV", cvv);
        map.put("Tipo", type);
        map.put("Pais", country);
        map.put("CPP", cpp);

        return map;
    }

    private PaymentDetails() {

    }

    public static PaymentDetails getInstance() {
        if (addetails == null)
            addetails = new PaymentDetails();
        return addetails;
    }

    public String getNumerodeTarjeta() {
        return ncard;
    }

    public String getFechavto() {
        return dvto;
    }

    public String getCVV() {
        return cvv;
    }

    public String getType() {
        return type;
    }

    public String getPais() {
        return country;
    }

    public int getCPP() {
        return cpp;
    }

    public String getId() {
        return id;
    }

    public void setNumerodeTarjeta(String ncard) {
        this.ncard = ncard;
    }

    public void setFechavto(String dvto) {
        this.dvto = dvto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCVV(String cvv) {
        this.cvv = cvv;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setPais(String country) {
        this.country = country;
    }

    public void setCPP(int cpp) {
        this.cpp = cpp;
    }
}