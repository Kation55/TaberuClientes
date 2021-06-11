package com.example.ubereats.checkout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.ubereats.R;
import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;
import com.example.ubereats.recycleView.ItemRecycleView;

import java.util.List;

public class CheckoutItemsActivity extends AppCompatActivity {
    private TextView amount;
    private EditText description;
    private RecyclerView orders;
    private CartDatabase cartDatabase;
    private Button checkoutPayment;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_items);

        this.amount = findViewById(R.id.textView34);
        this.description = findViewById(R.id.descriptionOrder);

        this.checkoutPayment = findViewById(R.id.button9);

        cartDatabase = Room.databaseBuilder(this.getApplicationContext(),
                CartDatabase.class, "cart").build();

        orders = findViewById(R.id.orderRecycle);
        orders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String restaurantId = CheckoutService.getInstance().getRestaurantId();

        new LoadItemCart(restaurantId, cartDatabase).execute();

        checkoutPayment.setOnClickListener((view) -> {
            CheckoutService.getInstance().setOrderDescription(
                    description.getText().toString()
            );

            Intent intent = new Intent(this, CheckoutPaymentActivity.class);
            intent.putExtra("COST", totalPrice);

            startActivity(intent);
        });
    }

    private class LoadItemCart extends AsyncTask<Void, Void, Void> {

        private String restaurantId;
        private CartDatabase db;
        private List<Cart> cartList;

        private int price;

        public LoadItemCart(String restaurantId, CartDatabase db){
            this.restaurantId = restaurantId;
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartList = db.cartDAO().getByRestaurantId(restaurantId);

            price = 0;
            cartList.forEach( cart -> {
                price += cart.standarCost*cart.quantity;
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ItemRecycleView itemRecycleView = new ItemRecycleView(cartList, db, amount);
            amount.setText("$" + price + ".00");

            totalPrice = price;

            orders.setAdapter(itemRecycleView);
        }
    }
}
