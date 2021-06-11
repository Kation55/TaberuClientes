package com.example.ubereats;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import java.util.List;

public class FragmentShoppingCart extends Fragment {

    private int height, totalPrice;
    private String restaurantId;
    private TextView priceView;
    private CartDatabase cartDatabase;
    private FrameLayout restaurantFragmentLayoult;

    public FragmentShoppingCart(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        Button cart = view.findViewById(R.id.cartButton);

        cartDatabase = Room.databaseBuilder(container.getContext().getApplicationContext(),
                CartDatabase.class, "cart").build();

        priceView = view.findViewById(R.id.price);
        new LoadItemCart(restaurantId, cartDatabase).execute();

        cart.setOnClickListener((view1) -> {
            restaurantFragmentLayoult = (FrameLayout) container.findViewById(R.id.restaurantFragment);

            Fragment shoppingList = new FragmentShoppingList(restaurantId, totalPrice);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_bottom_top, R.anim.exit_top_bottom, R.anim.enter_bottom_top, R.anim.exit_top_bottom);
            transaction.replace(R.id.restaurantFragment, shoppingList).commit();
            transaction.addToBackStack(null);

            height = restaurantFragmentLayoult.getHeight();

            getFragmentManager().addOnBackStackChangedListener(() -> {
                if (restaurantFragmentLayoult.getHeight() < 500) {
                    slideView(restaurantFragmentLayoult, height, 1000);
                } else {
                    slideView(restaurantFragmentLayoult, 1000, height);
                }
            });
        });

        return view;
    }

    private void slideView(View view,
                           int currentHeight,
                           int newHeight) {
        if (view != null) {
            ValueAnimator slideAnimator = ValueAnimator
                    .ofInt(currentHeight, newHeight)
                    .setDuration(500);

            slideAnimator.addUpdateListener(animation1 -> {
                Integer value = (Integer) animation1.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            });

            /*  We use an animationSet to play the animation  */

            AnimatorSet animationSet = new AnimatorSet();
            animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animationSet.play(slideAnimator);
            animationSet.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadItemCart(restaurantId, cartDatabase).execute();
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
            totalPrice = price;
            priceView.setText("Total: $" + price + ".00");
        }
    }
}