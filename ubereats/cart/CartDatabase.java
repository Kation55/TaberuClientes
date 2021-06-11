package com.example.ubereats.cart;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase{
    public abstract CartDAO cartDAO();
}
