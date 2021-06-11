package com.example.ubereats.cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
    List<Cart> getAll();

    @Query("SELECT * FROM Cart WHERE Cart.restaurantId LIKE :restaurantId")
    List<Cart> getByRestaurantId(String restaurantId);

    @Insert
    void insertOrder(Cart maps);

    @Update
    void updateItem(Cart cart);

    @Delete
    void deleteItem(Cart cart);
}
