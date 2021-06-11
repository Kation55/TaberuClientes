package com.example.ubereats.cart;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cart {
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "menuOrderId")
    public String menuOrderId;

    @ColumnInfo(name = "restaurantId")
    public String restaurantId;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "standarCost")
    public int standarCost;

    @ColumnInfo(name = "name")
    public String name ;
}
