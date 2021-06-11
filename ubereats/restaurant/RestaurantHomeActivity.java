package com.example.ubereats.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.ubereats.FragmentShoppingCart;
import com.example.ubereats.FragmentShoppingList;
import com.example.ubereats.R;
import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;
import com.example.ubereats.recycleView.CouponRecycleView;
import com.example.ubereats.recycleView.LoadCardRecycle;
import com.example.ubereats.recycleView.MenuRecycleView;
import com.example.ubereats.recycleView.RestaurantSmallRecycleView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RestaurantHomeActivity extends AppCompatActivity {

    private Fragment fragmentShoppingCart;
    private CartDatabase cartDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_home);

        cartDatabase = Room.databaseBuilder(this.getApplicationContext(),
                CartDatabase.class, "cart").build();

        TextView name = (TextView) findViewById(R.id.restaurantName);
        name.setText(getIntent().getStringExtra("RESTAURANT_NAME"));

        TextView category = (TextView) findViewById(R.id.textView26);
        category.setText(getIntent().getStringExtra("RESTAURANT_CATEGORY"));

        TextView time = (TextView) findViewById(R.id.textView27);
        time.setText(getIntent().getStringExtra("RESTAURANT_TIME"));

        TextView rate = (TextView) findViewById(R.id.textView28);
        rate.setText(getIntent().getStringExtra("RESTAURANT_RATE"));

        RecyclerView menus = (RecyclerView) findViewById(R.id.category1);
        menus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        MenuRecycleView menuRecycleView = new MenuRecycleView(null);
        menus.setAdapter(new LoadCardRecycle());

        new LoadFoodCards(menus, getIntent().getStringExtra("RESTAURANT_ID") ).execute();
        new LoadItemCart(getIntent().getStringExtra("RESTAURANT_ID"), cartDatabase).execute();
    }

    private void setFoodCard(RecyclerView recyclerView, ArrayList<FoodDetails> foodDetail) {
        MenuRecycleView menuRecycleView = new MenuRecycleView(foodDetail);
        recyclerView.setAdapter(menuRecycleView);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        new LoadItemCart(getIntent().getStringExtra("RESTAURANT_ID"), cartDatabase).execute();
    }

    private class LoadFoodCards extends AsyncTask<Void, Void, Boolean> {

        private ArrayList<FoodDetails> foodDetail;
        private RecyclerView food;
        private String id;

        public LoadFoodCards(RecyclerView food, String id) {
            this.food = food;
            this.id = id;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                foodDetail = FoodService.getInstance().getFood(id).get();
                foodDetail.forEach( foodMenu -> {
                    try {
                        if (foodMenu.getImageURL() != null) {
                            URL newurl = new URL(foodMenu.getImageURL());
                            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            foodMenu.setBitmap(mIcon_val);
                        } else {
                            foodMenu.setBitmap(null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                setFoodCard(food, foodDetail);
            }
        }
    }

    private class LoadItemCart extends AsyncTask<Void, Void, Void> {

        private String restaurantId;
        private CartDatabase db;
        private List<Cart> cartList;

        public LoadItemCart(String restaurantId, CartDatabase db){
            this.restaurantId = restaurantId;
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartList = db.cartDAO().getByRestaurantId(restaurantId);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (!cartList.isEmpty()) {
                fragmentShoppingCart = new FragmentShoppingCart(getIntent().getStringExtra("RESTAURANT_ID"));
                getSupportFragmentManager().beginTransaction().add(R.id.restaurantFragment, fragmentShoppingCart).commit();
            }
        }
    }
}
