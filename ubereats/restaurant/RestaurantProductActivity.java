package com.example.ubereats.restaurant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.ubereats.FragmentShoppingCart;
import com.example.ubereats.R;
import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;
import com.example.ubereats.orders.OrdersDatabase;

import java.util.Calendar;
import java.util.List;

public class RestaurantProductActivity extends AppCompatActivity {

    private Fragment fragmentShoppingCart;
    private FoodDetails foodDetails;
    private CartDatabase cartDatabase;
    private int quantity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_product);

        cartDatabase = Room.databaseBuilder(this.getApplicationContext(),
                CartDatabase.class, "cart").build();

        quantity = 1;

        foodDetails = FoodService.getInstance().getPosition(
                getIntent().getIntExtra("POSITION", 0)
        );

        setValues();

        fragmentShoppingCart = new FragmentShoppingCart(foodDetails.restaurantId);

        getSupportFragmentManager().beginTransaction().add(R.id.restaurantFragment, fragmentShoppingCart).commit();
    }

    public void increase(View view) {
        quantity++;
        ((TextView) findViewById(R.id.textView39)).setText(quantity+"");
    }

    public void decrease(View view) {
        if (quantity > 1) {
            quantity--;
            ((TextView) findViewById(R.id.textView39)).setText(quantity+"");
        }
    }

    public void addItem(View view) {
        new AddItemCart(
                foodDetails.itemId,
                foodDetails.restaurantId,
                quantity,
                foodDetails.precio,
                foodDetails.nombre,
                cartDatabase
        ).execute();
    }

    private void setValues() {
        ((ImageView) findViewById(R.id.imageView10)).setImageBitmap(foodDetails.getBitmap());

        ((TextView) findViewById(R.id.textView36)).setText(foodDetails.getNombre());
        ((TextView) findViewById(R.id.textView37)).setText(foodDetails.getCategoria());
        ((TextView) findViewById(R.id.textView41)).setText(foodDetails.getDescripcion());
        ((TextView) findViewById(R.id.textView43)).setText(foodDetails.getIngredientes());
    }

    private class AddItemCart extends AsyncTask<Void, Void, Void> {
        private String itemId, restaurantId, name;
        private int quantity, standarCost;
        private CartDatabase db;

        public AddItemCart(String itemId, String restaurantId, int quantity, int standarCost, String name, CartDatabase db) {
            this.itemId = itemId;
            this.name = name;
            this.restaurantId = restaurantId;
            this.quantity = quantity;
            this.standarCost  = standarCost;
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Cart cart = new Cart();
            cart.id = Calendar.getInstance().getTime().toString();
            cart.menuOrderId = itemId;
            cart.quantity = quantity;
            cart.restaurantId = restaurantId;
            cart.standarCost = standarCost;
            cart.name = name;

            db.cartDAO().insertOrder(cart);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            finish();
        }
    }

}
