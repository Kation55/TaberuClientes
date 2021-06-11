package com.example.ubereats;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;
import com.example.ubereats.checkout.CheckoutItemsActivity;
import com.example.ubereats.checkout.CheckoutService;
import com.example.ubereats.recycleView.ItemRecycleView;
import com.example.ubereats.restaurant.RestaurantProductActivity;

import java.util.List;

public class FragmentShoppingList extends Fragment {
    private CartDatabase cartDatabase;

    private String restaurantId;
    private RecyclerView orders;
    private int price;
    private TextView amount;

    public FragmentShoppingList(String restaurantId, int price) {
        this.restaurantId = restaurantId;
        this.price = price;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        FrameLayout restaurantFragmentLayoult = (FrameLayout) container.findViewById(R.id.restaurantFragment);

        cartDatabase = Room.databaseBuilder(container.getContext().getApplicationContext(),
                CartDatabase.class, "cart").build();

        orders = view.findViewById(R.id.orderRecycle);
        orders.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        new LoadItemCart(restaurantId, cartDatabase).execute();

        amount = (TextView) view.findViewById(R.id.textView33);
        amount.setText("$"+price+".00");

        Button checkout = view.findViewById(R.id.button10);

        checkout.setOnClickListener((view2) -> {
            CheckoutService.getInstance().newCheckout(restaurantId);
            Intent intent = new Intent(container.getContext(), CheckoutItemsActivity.class);
            container.getContext().startActivity(intent);
        });

        return view;
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
            ItemRecycleView itemRecycleView = new ItemRecycleView(cartList, db, amount);
            orders.setAdapter(itemRecycleView);
        }
    }
}