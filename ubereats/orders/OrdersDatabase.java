package com.example.ubereats.orders;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Orders.class}, version = 1)
public abstract class OrdersDatabase extends RoomDatabase {
    public abstract OrdersDAO mapsDAO();
}
