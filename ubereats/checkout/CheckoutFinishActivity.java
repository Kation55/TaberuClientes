package com.example.ubereats.checkout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.ubereats.R;
import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;
import com.example.ubereats.home.HomeActivity;
import com.example.ubereats.recycleView.ItemCheckoutRecycleView;
import com.example.ubereats.user.AddressDetails;
import com.example.ubereats.user.ClienteService;
import com.example.ubereats.user.PaymentDetails;

import java.util.List;

public class CheckoutFinishActivity extends AppCompatActivity {

    private TextView creditType, creditDigits;
    private TextView directionType, directionNickname;
    private TextView description;
    private Button finish;
    private RecyclerView orders;

    private CartDatabase cartDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_finish);

        cartDatabase = Room.databaseBuilder(this.getApplicationContext(),
                CartDatabase.class, "cart").build();

        orders = findViewById(R.id.orderRecycle);
        orders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new LoadItemCart(CheckoutService.getInstance().getRestaurantId(), cartDatabase).execute();

        creditType = findViewById(R.id.cardType);
        creditDigits = findViewById(R.id.cardOwner);

        PaymentDetails credit = ClienteService.getInstance().getCardById(
                CheckoutService.getInstance().getPaymentId()
        );

        creditType.setText(credit.getType());
        creditDigits.setText(credit.getNumerodeTarjeta());

        directionType = findViewById(R.id.addressType);
        directionNickname = findViewById(R.id.addressNickname);

        AddressDetails addressDetails = ClienteService.getInstance().getAddressById(
                CheckoutService.getInstance().getAddressId()
        );

        directionType.setText(addressDetails.getAlias());
        directionNickname.setText(addressDetails.getDescripcion());

        description = findViewById(R.id.textView49);
        if (CheckoutService.getInstance().getOrderDescription() != null)
            description.setText(CheckoutService.getInstance().getOrderDescription());

        finish = findViewById(R.id.button11);

        finish.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

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
            ItemCheckoutRecycleView itemRecycleView = new ItemCheckoutRecycleView(cartList);
            orders.setAdapter(itemRecycleView);

            new RemoveItem(cartList, db).execute();
        }
    }

    private class RemoveItem extends AsyncTask<Void, Void, Void> {

        private CartDatabase db;
        private List<Cart> cartList;


        public RemoveItem(List<Cart> cartList, CartDatabase db){
            this.cartList = cartList;
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartList.forEach(cart -> {
                db.cartDAO().deleteItem(cart);
            });
            return null;
        }
    }
}
