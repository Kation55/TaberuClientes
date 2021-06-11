package com.example.ubereats.user;


import com.google.firebase.Timestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class  AddressDetails{

    private String ali,next,desc,country,state,cal01,cal02,ref, id;
    private int cpp;
    private static AddressDetails addetails;

    public AddressDetails(String ali, String next, String desc,
                          String country,String state,String cal01,String cal02, String ref,Integer cpp) {

        this.ali = ali;
        this.next = next;
        this.desc = desc;
        this.country = country;
        this.state = state;
        this.cal01 = cal01;
        this.cal02 = cal02;
        this.ref = ref;
        this.cpp = cpp;


    }

    public static AddressDetails of(Map<String, Object> data) {
        String ali, next, desc, country,state,cal01,cal02,ref;
        int cpp;

        ali = (String) data.get("Alias");
        next = (String)data.get("NumeroExterior");
        desc = (String)data.get("Descripcion");
        country = (String)data.get("Pais");
        state = (String)data.get("Estado");
        cal01 = (String)data.get("Calle01");
        cal02 = (String)data.get("Calle02");
        ref = (String)data.get("Referencias");
        cpp = ((Number)data.get("CPP")).intValue();



        AddressDetails addetails = new AddressDetails(ali,next,desc,country,state,cal01,cal02,ref,cpp);

        return addetails;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("Alias", ali);
        map.put("NumeroExterior", next);
        map.put("Descripcion", desc);
        map.put("Pais", country);
        map.put("Estado", state);
        map.put("Calle01", cal01);
        map.put("Calle02", cal02);
        map.put("Referencias", ref);
        map.put("CPP", cpp);

        return map;
    }

    private AddressDetails() {

    }

    public static AddressDetails getInstance() {
        if (addetails == null)
            addetails = new AddressDetails();
        return addetails;
    }

    public String getAlias() {
        return ali;
    }

    public String getNumeroExterior() {
        return next;
    }

    public String getDescripcion() {
        return desc;
    }

    public String getPais() {
        return country;
    }

    public String getEstado() {
        return state;
    }

    public String getCalle01() {
        return cal01;
    }

    public String getCalle02() {
        return cal02;
    }

    public String getRefencias() {
        return ref;
    }

    public int getCPP() {
        return cpp;
    }

    public String getId() {
        return id;
    }

    public void setAlias(String ali) {
        this.ali = ali;
    }

    public void setNumeroExterior(String next) {
        this.next = next;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String desc) {
        this.desc = desc;
    }


    public void setPais(String country) {
        this.country = country;
    }

    public void setEstado(String state) {
        this.state = state;
    }

    public void setCalle01(String cal01) {
        this.cal01 = cal01;
    }

    public void setCalle02(String cal02) {
        this.cal02 = cal02;
    }

    public void setReferencias(String ref) {
        this.ref = ref;
    }

    public void setCPP(int cpp) {
        this.cpp = cpp;
    }
}
