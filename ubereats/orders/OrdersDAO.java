package com.example.ubereats.orders;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrdersDAO {
    @Query("SELECT * FROM Orders")
    List<Orders> getAll();

    @Insert
    public void insertOrder(Orders maps);
}
