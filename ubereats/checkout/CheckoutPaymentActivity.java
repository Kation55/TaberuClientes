package com.example.ubereats.checkout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.recycleView.CouponRecycleView;
import com.example.ubereats.recycleView.CreditCardsRecycleView;
import com.example.ubereats.user.ClienteService;
import com.example.ubereats.user.CuponDetails;
import com.example.ubereats.user.PaymentDetails;
import com.example.ubereats.user.UserCuponActivity;
import com.example.ubereats.user.UserPaymentActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CheckoutPaymentActivity extends AppCompatActivity {

    private RecyclerView cards;
    private Button payment, addPayment, addCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_payment);

        cards = findViewById(R.id.cardRecycle);


        payment = findViewById(R.id.button9);
        addPayment = findViewById(R.id.button3);
        addCoupon = findViewById(R.id.button2);

        payment.setText("PAGAR $" + getIntent().getIntExtra("COST", 0) + ".00");

        cards.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new LoadPayment().execute();

        addPayment.setOnClickListener((view) -> {
            Intent intent = new Intent(this, UserPaymentActivity.class);
            startActivity(intent);
        });

        addCoupon.setOnClickListener((view) -> {
            Intent intent = new Intent(this, UserCuponActivity.class);
            startActivity(intent);
        });

        payment.setOnClickListener((view) -> {
            if (CheckoutService.getInstance().getPaymentId() != null) {
                Intent intent = new Intent(this, CheckoutAddressActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        new LoadPayment().execute();
        new LoadCupones().execute();

        super.onResume();
    }

    private class LoadPayment extends AsyncTask<Void, Void, Boolean> {

        ArrayList<PaymentDetails> paymentArrayList;

        public LoadPayment(){}

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                paymentArrayList = ClienteService.getInstance().getPayment().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                CreditCardsRecycleView paymentRecycleView = new CreditCardsRecycleView(paymentArrayList, true);
                cards.setAdapter(paymentRecycleView);
            }
        }
    }

    private class LoadCupones extends AsyncTask<Void, Void, Boolean> {

        ArrayList<CuponDetails> cuponArrayList;

        public LoadCupones(){}

        @Override //Crea nuevo hilo
        protected Boolean doInBackground(Void... voids) {

            try {
                cuponArrayList = ClienteService.getInstance().getCupon().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override//Regresa hilo principal
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result)
            {
                CouponRecycleView cuponRecycleView = new CouponRecycleView(cuponArrayList);

            }
        }
    }
}
