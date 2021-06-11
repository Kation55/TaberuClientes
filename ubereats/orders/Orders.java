package com.example.ubereats.orders;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Orders {
    @PrimaryKey
    @NonNull
    public String orderID;

    @ColumnInfo(name = "restaurant")
    public String restaurant;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "price")
    public String price;

}
